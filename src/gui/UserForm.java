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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import resources.R;

/**
 * Universidad del Valle
 *
 * @author Julian Andres Cantillo // cod: 1431263 - 3743
 */
public class UserForm extends JFrame implements ActionListener {

  private User user;
  private UserModel userModel;
  private boolean isNew;
  private JTextField fldIdentification;
  private JTextField fldFirstname;
  private JTextField fldLastname;
  private JTextField fldAddress;
  private JTextField fldId;
  private JTextField fldCharge;
  private DefaultListModel lstRechargesModel;
  private JList lstRecharges;

  public UserForm(User user) {
    super(R.STR_LINE_DETAIL);
    this.user = user;
    this.isNew = false;

    initForm();
  }

  public UserForm() {
    super(R.STR_NEW_LINE);
    this.user = new User();
    this.isNew = true;

    initForm();
  }

  private void initForm() {
    userModel = new UserModel();
    JPanel panel = new JPanel();

    panel.setLayout(new BorderLayout(R.H, R.W));
    panel.setBorder(BorderFactory.createEmptyBorder(R.VGAP, R.HGAP, R.VGAP, R.HGAP));

    panel.add(pnlFields(), BorderLayout.CENTER);
    panel.add(pnlButtons(), BorderLayout.SOUTH);

    add(panel);

    if (!this.isNew) {
      this.setData();
    }

    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(400, 500);
  }

  private JPanel pnlFields() {
    JPanel panel = new JPanel();

    panel.setLayout(new GridBagLayout());

    JLabel lblIdentification = new JLabel(R.STR_IDENTIFICATION);
    JLabel lblFirstname = new JLabel(R.STR_FIRSTNAME);
    JLabel lblLastname = new JLabel(R.STR_LASTNAME);
    JLabel lblAddress = new JLabel(R.STR_ADDRESS);
    JLabel lblCharge = new JLabel(R.STR_CHARGE);
    JLabel lblId = new JLabel(R.STR_ID);

    fldIdentification = new JTextField();
    fldFirstname = new JTextField();
    fldLastname = new JTextField();
    fldAddress = new JTextField();
    fldId = new JTextField();
    fldCharge = new JTextField();

    fldId.setEditable(false);
    fldCharge.setEditable(false);

    GBHelper pos = new GBHelper();

    panel.add(lblId, pos);
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(fldId, pos.nextCol().expandW());

    panel.add(new Gap(R.VGAP), pos.nextRow());

    panel.add(lblIdentification, pos.nextRow());
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(fldIdentification, pos.nextCol().expandW());

    panel.add(new Gap(R.VGAP), pos.nextRow());

    panel.add(lblFirstname, pos.nextRow());
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(fldFirstname, pos.nextCol().expandW());

    panel.add(new Gap(R.VGAP), pos.nextRow());

    panel.add(lblLastname, pos.nextRow());
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(fldLastname, pos.nextCol().expandW());

    panel.add(new Gap(R.VGAP), pos.nextRow());

    panel.add(lblAddress, pos.nextRow());
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(fldAddress, pos.nextCol().expandW());

    panel.add(new Gap(R.VGAP), pos.nextRow());

    panel.add(lblCharge, pos.nextRow());
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(fldCharge, pos.nextCol().expandW());

    panel.add(new Gap(), pos.nextRow().expandH());
    
    panel.add(new Gap(R.VGAP), pos.nextRow());
    
    panel.add(pnlChargeLog(), pos.nextRow().width(3).expandH().expandW());

    return panel;
  }

  private JPanel pnlButtons() {
    JPanel pnl = new JPanel();
    pnl.setLayout(new FlowLayout(FlowLayout.RIGHT));

    JButton btnAccept = new JButton(R.STR_ACCEPT);
    JButton btnClose = new JButton(R.STR_CANCEL);
    JButton btnSave = new JButton(R.STR_SAVE);

    btnAccept.setActionCommand(R.CMD_ACCEPT);
    btnClose.setActionCommand(R.CMD_CLOSE);
    btnSave.setActionCommand(R.CMD_SAVE);

    btnAccept.addActionListener(this);
    btnClose.addActionListener(this);
    btnSave.addActionListener(this);

    pnl.add(btnAccept);
    pnl.add(btnSave);
    pnl.add(btnClose);

    return pnl;
  }

  private JPanel pnlChargeLog() {
    JPanel panel = new JPanel();

    panel.setLayout(new BorderLayout(R.H, R.W));
    panel.setBorder(BorderFactory.createTitledBorder(R.STR_BUS));

    lstRechargesModel = new DefaultListModel();
    try {
      for(String log : userModel.getChargeLog(user)){
        lstRechargesModel.addElement(log);
      }
    } catch (SQLException ex) {
      Logger.getLogger(UserForm.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    lstRecharges = new JList(lstRechargesModel);

    panel.add(new JScrollPane(lstRecharges), BorderLayout.CENTER);

    return panel;
  }

  private void setData() {
    fldId.setText(String.valueOf(user.getCardId()));
    fldIdentification.setText(user.getIdentification());
    fldLastname.setText(user.getLastname());
    fldAddress.setText(user.getAddress());
    fldFirstname.setText(user.getFirstname());
    fldCharge.setText(String.valueOf(user.getCharge()));
  }

  public User save() {
    user.setFirstname(fldFirstname.getText());
    user.setLastname(fldLastname.getText());
    user.setAddress(fldAddress.getText());
    user.setIdentification(fldIdentification.getText());
    
    return user;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String cmd = e.getActionCommand();

    if (cmd.equals(R.CMD_SAVE)) {
      try {
        if (isNew) {
          userModel.create(this.save());
        } else {
          userModel.update(this.save());
        }
        JOptionPane.showMessageDialog(this, R.STR_ITEM_SAVED, R.STR_LINE, JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
      } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, String.format(R.ERROR_SAVE_FAILS, ex.getMessage()), R.STR_ERROR, JOptionPane.ERROR_MESSAGE);
      }
    }

    if (cmd.equals(R.CMD_CLOSE)) {
      this.dispose();
    }

    if (cmd.equals(R.CMD_ACCEPT)) {
      this.dispose();
    }
  }

}
