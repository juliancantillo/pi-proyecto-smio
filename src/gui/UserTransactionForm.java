/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.User;
import entities.dao.UserModel;
import gui.helpers.GBHelper;
import gui.helpers.Gap;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
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
public abstract class UserTransactionForm extends JFrame implements ActionListener{

  public User user;
  public UserModel userModel;
  private JTextField fldIdentification;
  private JTextField fldName;
  private JTextField fldCharge;
  private JTextField fldSearchId;
  private boolean isLoaded = false;


  public UserTransactionForm() {
    super(R.STR_RECHARGE);

    initForm();
  }

  private void initForm() {
    userModel = new UserModel();
    JPanel panel = new JPanel();

    panel.setLayout(new BorderLayout(R.H, R.W));
    panel.setBorder(BorderFactory.createEmptyBorder(R.VGAP, R.HGAP, R.VGAP, R.HGAP));

    panel.add(pnlSearch(), BorderLayout.NORTH);
    panel.add(pnlActions(), BorderLayout.CENTER);
    panel.add(pnlButtons(), BorderLayout.SOUTH);

    add(panel);
    
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setResizable(false);
    setSize(420, 300);
  }
  
  private JPanel pnlSearch(){
    JPanel panel = new JPanel();

    panel.setLayout(new GridBagLayout());
    
    JLabel lblBuscar = new JLabel(R.STR_SEARCH);
    
    fldSearchId = new JTextField();
    
    JButton btnSearch = new JButton(R.STR_SEARCH);
    JButton btnCancel = new JButton(R.STR_CANCEL);
    
    btnSearch.addActionListener(this);
    btnCancel.addActionListener(this);
    
    btnCancel.setActionCommand(R.CMD_CANCEL_SEARCH);
    btnSearch.setActionCommand(R.CMD_SEARCH);
    
    GBHelper pos = new GBHelper();
    
    panel.add(lblBuscar, pos);
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(fldSearchId, pos.nextCol().expandW());
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(btnSearch, pos.nextCol());
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(btnCancel, pos.nextCol());
    
    panel.add(new Gap(R.VGAP), pos.nextRow());
    
    panel.add( pnlFields(), pos.nextRow().width(7).expandW().expandW() );
    
    return panel;
  }

  private JPanel pnlFields() {
    JPanel panel = new JPanel();
    panel.setBorder(BorderFactory.createTitledBorder(R.STR_USER_INFO));
    panel.setLayout(new GridBagLayout());

    JLabel lblIdentification = new JLabel(R.STR_IDENTIFICATION);
    JLabel lblName = new JLabel(R.STR_NAME);
    JLabel lblCharge = new JLabel(R.STR_CHARGE);

    fldIdentification = new JTextField();
    fldName = new JTextField();    
    fldCharge = new JTextField();
    
    fldName.setEditable(false);
    fldIdentification.setEditable(false);
    fldCharge.setEditable(false);

    GBHelper pos = new GBHelper();

    panel.add(lblIdentification, pos);
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(fldIdentification, pos.nextCol().expandW());

    panel.add(new Gap(R.VGAP), pos.nextRow());

    panel.add(lblName, pos.nextRow());
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(fldName, pos.nextCol().expandW());

    panel.add(new Gap(R.VGAP), pos.nextRow());

    panel.add(lblCharge, pos.nextRow());
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(fldCharge, pos.nextCol().expandW());

    panel.add(new Gap(), pos.nextRow().expandH());

    return panel;
  }
  
  abstract JPanel pnlActions();
  
  private JPanel pnlButtons() {
    JPanel pnl = new JPanel();
    pnl.setLayout(new FlowLayout(FlowLayout.RIGHT));

    JButton btnAccept = new JButton(R.STR_ACCEPT);
    JButton btnClose = new JButton(R.STR_CANCEL);

    btnAccept.setActionCommand(R.CMD_ACCEPT);
    btnClose.setActionCommand(R.CMD_CLOSE);

    btnAccept.addActionListener(this);
    btnClose.addActionListener(this);

    pnl.add(btnAccept);
    pnl.add(btnClose);

    return pnl;
  }
  
  private void setData() {
    fldIdentification.setText(user.getIdentification());
    fldName.setText(String.format("%s %s",user.getFirstname(), user.getLastname()));
    fldCharge.setText(String.valueOf(user.getCharge()));
    
    isLoaded = true;
  }
  
  private void cleanData() {
    fldSearchId.setText("");
    fldIdentification.setText("");
    fldName.setText("");
    fldCharge.setText("");
    
    isLoaded = false;
    
    user = new User();
  }

  public boolean getIsLoaded() {
    return isLoaded;
  }
  
  @Override
  public void actionPerformed(ActionEvent e) {
    String cmd = e.getActionCommand();
        
    if(cmd.equals(R.CMD_SEARCH)){
      try {
        String criteria = fldSearchId.getText();
        user = userModel.whereIdentification( criteria );
        if(user != null){
          setData();
        }else{
          JOptionPane.showMessageDialog(this, String.format(R.STR_NOT_FOUND, criteria), R.STR_USERS, JOptionPane.INFORMATION_MESSAGE);
        }
      } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, String.format(R.ERROR_LOAD_DATA_FAILS, ex.getMessage()), R.STR_USERS, JOptionPane.ERROR_MESSAGE);
      }
    }
    
    if(cmd.equals(R.CMD_CANCEL_SEARCH)){
      cleanData();
    }
    
    if(cmd.equals(R.CMD_CLOSE)){
      this.dispose();
    }
    
    if(cmd.equals(R.CMD_ACCEPT)){
      this.dispose();
    }
  }
  
  

}
