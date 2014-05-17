/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import gui.helpers.GBHelper;
import gui.helpers.Gap;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import resources.R;

/**
 *
 * @author Usuario
 */
class UnchargeForm extends UserTransactionForm{
  
  private JComboBox slctTickets;

  public UnchargeForm() {
    this.setTitle(R.STR_UNCHARGE);
  }

  @Override
  JPanel pnlActions() {
    JPanel panel = new JPanel();
    
    panel.setLayout(new GridBagLayout());
    
    JLabel lblChargeMount = new JLabel(R.STR_UNCHARGE);
    JButton btnAddCharge = new JButton(R.STR_UNCHARGE);
    
    btnAddCharge.addActionListener(this);
    btnAddCharge.setActionCommand(R.CMD_UNRECHARGE);
    btnAddCharge.setFont(new Font(null, Font.BOLD, 17));
    
    slctTickets = new JComboBox();
    
    GBHelper pos = new GBHelper();
    
    panel.add(lblChargeMount, pos);
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(slctTickets, pos.nextCol().expandW());
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(btnAddCharge, pos.nextCol());
    
    return panel;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    super.actionPerformed(e);
    
    if(e.getActionCommand().equals(R.CMD_SEARCH)){
      
      System.out.println("Descontar");
      if(this.getIsLoaded()){
        
        Double charge = this.user.getCharge();
        int max_tickets_qty = (int) Math.floor( charge / smio.SMIO.price );

        for (int i = 1; i <= max_tickets_qty; i++) {
          slctTickets.addItem( new Integer(i) );
        }
      }
    }
    
    if(e.getActionCommand().equals(R.CMD_UNRECHARGE)){
      
      Double tickets = (Integer) slctTickets.getSelectedItem() * 1.0;
      Double uncharge = -1 * (tickets * smio.SMIO.price);
      this.user.setCharge( this.user.getCharge() + uncharge );
      try {
        this.userModel.addCharge(user, uncharge, "uncharge", tickets);
        JOptionPane.showMessageDialog(this, String.format(R.STR_UNCHARGE_SUCCESS, tickets), R.STR_UNCHARGE, JOptionPane.INFORMATION_MESSAGE);
      } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, String.format(R.ERROR_SAVE_FAILS, ex.getMessage()), R.STR_ERROR, JOptionPane.ERROR_MESSAGE);
      }
      
    }
  }
  
  
  
}
