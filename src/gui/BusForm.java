/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Bus;
import entities.dao.BusModel;
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
import javax.swing.JComboBox;
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
public class BusForm extends JFrame implements ActionListener {

  private Bus bus;
  private BusModel busModel;
  private final boolean isNew;
  private JTextField fldCode;
  private JTextField fldCapacity;
  private JComboBox fldType;
  private JTextField fldMaker;
  private JTextField fldModel;
  private JTextField fldId;
  private final String types[] = {"Alimentador", "Padrón", "Troncal"};

  public BusForm(Bus bus) {
    super(R.STR_BUS_DETAIL);
    this.bus = bus;
    this.isNew = false;

    initForm();
  }

  public BusForm() {
    super(R.STR_NEW_BUS);
    this.bus = new Bus();
    this.isNew = true;

    initForm();
  }

  private void initForm() {
    busModel = new BusModel();
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
    setResizable(false);
    setSize(420, 300);
  }

  private JPanel pnlFields() {
    JPanel panel = new JPanel();

    panel.setLayout(new GridBagLayout());

    JLabel lblCode = new JLabel(R.STR_CODE);
    JLabel lblCapacity = new JLabel(R.STR_BUS_CAPACITY);
    JLabel lblType = new JLabel(R.STR_TYPE);
    JLabel lblMaker = new JLabel(R.STR_BUS_MAKER);
    JLabel lblModel = new JLabel(R.STR_BUS_MODEL);
    JLabel lblId = new JLabel(R.STR_ID);

    fldCode = new JTextField();
    fldCapacity = new JTextField();
    fldType = new JComboBox(types);
    fldMaker = new JTextField();
    fldModel = new JTextField();
    fldId = new JTextField();

    fldId.setEditable(false);

    GBHelper pos = new GBHelper();

    panel.add(lblId, pos);
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(fldId, pos.nextCol().expandW());

    panel.add(new Gap(R.VGAP), pos.nextRow());

    panel.add(lblCode, pos.nextRow());
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(fldCode, pos.nextCol().expandW());

    panel.add(new Gap(R.VGAP), pos.nextRow());

    panel.add(lblCapacity, pos.nextRow());
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(fldCapacity, pos.nextCol().expandW());

    panel.add(new Gap(R.VGAP), pos.nextRow());

    panel.add(lblType, pos.nextRow());
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(fldType, pos.nextCol().expandW());

    panel.add(new Gap(R.VGAP), pos.nextRow());

    panel.add(lblMaker, pos.nextRow());
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(fldMaker, pos.nextCol().expandW());

    panel.add(new Gap(R.VGAP), pos.nextRow());

    panel.add(lblModel, pos.nextRow());
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(fldModel, pos.nextCol().expandW());

    panel.add(new Gap(), pos.nextRow().expandH());

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

  private void setData() {
    fldId.setText(String.valueOf(bus.getId()));
    fldCode.setText(bus.getCode());
    fldMaker.setText(bus.getMaker());
    fldModel.setText(bus.getModel());
    fldType.setSelectedItem(bus.getType());
    fldCapacity.setText(String.valueOf(bus.getCapacity()));
  }

  public Bus save() {
    bus.setCode(fldCode.getText());
    bus.setCapacity(Integer.parseInt(fldCapacity.getText()));
    bus.setMaker(fldMaker.getText());
    bus.setModel(fldModel.getText());
    bus.setType(fldType.getSelectedItem().toString());
    
    return bus;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String cmd = e.getActionCommand();

    if (cmd.equals(R.CMD_SAVE)) {
      try {
        if (isNew) {
          int id = busModel.create(this.save());
          bus.setId(id);
        } else {
          busModel.update(this.save());
        }
        JOptionPane.showMessageDialog(this, R.STR_ITEM_SAVED, R.STR_BUS, JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
      } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, String.format(R.ERROR_SAVE_FAILS, ex.getMessage()), R.STR_BUS, JOptionPane.ERROR_MESSAGE);
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
