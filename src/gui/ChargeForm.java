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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import resources.R;

/**
 * Universidad del Valle
 *
 * @author Julian Andres Cantillo // cod: 1431263 - 3743
 */
public class ChargeForm extends UserTransactionForm {
  
  private JTextField fldChargeMount;

  public ChargeForm() {
  }
  
  @Override
  JPanel pnlActions(){
    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());
    
    JLabel lblChargeMount = new JLabel(R.STR_CHARGE_MOUNT);
    JButton btnAddCharge = new JButton(R.STR_ADD);
    
    btnAddCharge.addActionListener(this);
    btnAddCharge.setActionCommand(R.CMD_ADD_CHARGE);
    btnAddCharge.setFont(new Font(null, Font.BOLD, 17));
    
    fldChargeMount = new JTextField();
    
    GBHelper pos = new GBHelper();
    
    panel.add(lblChargeMount, pos);
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(fldChargeMount, pos.nextCol().expandW());
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(btnAddCharge, pos.nextCol());
    
    return panel;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    super.actionPerformed(e); //To change body of generated methods, choose Tools | Templates.
    String cmd = e.getActionCommand();
    
    if(cmd.equals(R.CMD_ADD_CHARGE)){
      if(!this.getIsLoaded()){
        return;
      }
      
      try {
        Double charge = Double.parseDouble(fldChargeMount.getText());
        Double tickets = Math.floor( charge / smio.SMIO.price );
        this.user.setCharge( this.user.getCharge() + charge );
        this.userModel.addCharge(this.user, charge, "charge", tickets);
        JOptionPane.showMessageDialog(this, String.format(R.STR_CHARGE_SUCCESS, charge), R.STR_CHARGE, JOptionPane.INFORMATION_MESSAGE);
      } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, String.format(R.ERROR_SAVE_FAILS, ex.getMessage()), R.STR_ERROR, JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  
  
  
}