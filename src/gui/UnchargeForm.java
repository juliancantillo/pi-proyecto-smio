/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
    
    slctTickets = new JComboBox();
    
    JLabel lblTest = new JLabel(R.STR_UNCHARGE);
    panel.add(lblTest);
    return panel;
  }
  
}
