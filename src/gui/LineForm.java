/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Bus;
import entities.Line;
import entities.dao.BusModel;
import entities.dao.LineModel;
import gui.helpers.GBHelper;
import gui.helpers.Gap;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import resources.R;

/**
 * Universidad del Valle
 *
 * @author Julian Andres Cantillo // cod: 1431263 - 3743
 */
public class LineForm extends JFrame implements ActionListener, ItemListener, ListSelectionListener {

  private Line line;
  private LineModel lineModel;
  private boolean isNew;
  private JTextField fldCode;
  private JComboBox fldType;
  private JComboBox slctBus;
  private JList lstBuses;
  private DefaultListModel lstBusesModel;
  private JTextField fldName;
  private JTextField fldDesc;
  private JTextField fldId;
  private JButton btnRemoveBus;
  private final String types[] = {"Alimentador", "Padrón", "Troncal"};

  public LineForm(Line line) {
    super(R.STR_LINE_DETAIL);
    this.line = line;
    this.isNew = false;

    initForm();
  }

  public LineForm() {
    super(R.STR_NEW_LINE);
    this.line = new Line();
    this.isNew = true;

    initForm();
  }

  private void initForm() {
    lineModel = new LineModel();
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
    setSize(450, 500);
    setResizable(false);
  }

  private JPanel pnlFields() {
    JPanel panel = new JPanel();

    panel.setLayout(new GridBagLayout());

    JLabel lblCode = new JLabel(R.STR_CODE);
    JLabel lblType = new JLabel(R.STR_TYPE);
    JLabel lblName = new JLabel(R.STR_NAME);
    JLabel lblDesc = new JLabel(R.STR_DESC);
    JLabel lblId = new JLabel(R.STR_ID);

    fldCode = new JTextField();
    fldType = new JComboBox(types);
    fldName = new JTextField();
    fldDesc = new JTextField();
    fldId = new JTextField();

    fldType.addItemListener(this);
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

    panel.add(lblType, pos.nextRow());
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(fldType, pos.nextCol().expandW());

    panel.add(new Gap(R.VGAP), pos.nextRow());

    panel.add(lblName, pos.nextRow());
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(fldName, pos.nextCol().expandW());

    panel.add(new Gap(R.VGAP), pos.nextRow());

    panel.add(lblDesc, pos.nextRow());
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(fldDesc, pos.nextCol().expandW());

    panel.add(new Gap(R.VGAP), pos.nextRow());

    panel.add(pnlBuses(), pos.width(3).nextRow().expandW().expandH());

    return panel;
  }

  private JPanel pnlBuses() {
    JPanel panel = new JPanel();

    panel.setLayout(new BorderLayout(R.H, R.W));
    panel.setBorder(BorderFactory.createTitledBorder(R.STR_BUS));

    btnRemoveBus = new JButton(R.STR_REMOVE);
    btnRemoveBus.addActionListener(this);
    btnRemoveBus.setActionCommand(R.CMD_REMOVE_BUS);
    btnRemoveBus.setEnabled(false);

    lstBusesModel = new DefaultListModel();
    lstBuses = new JList(lstBusesModel);

    lstBuses.addListSelectionListener(this);

    panel.add(pnlAddBuses(), BorderLayout.NORTH);
    panel.add(new JScrollPane(lstBuses), BorderLayout.CENTER);
    panel.add(btnRemoveBus, BorderLayout.SOUTH);

    return panel;
  }

  private JPanel pnlAddBuses() {
    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());

    BusModel busModel = new BusModel();

    JButton btnAddBus = new JButton(R.STR_ADD_BUS);
    btnAddBus.addActionListener(this);
    btnAddBus.setActionCommand(R.CMD_ADD_BUS);

    slctBus = new JComboBox();

    try {
      for (Bus bus : busModel.toArray()) {
        slctBus.addItem(bus);
      }

    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(this, String.format(R.ERROR_SQL, ex.getMessage()), R.STR_LINE, JOptionPane.ERROR_MESSAGE);
    }

    GBHelper pos = new GBHelper();

    panel.add(slctBus, pos.expandW());
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(btnAddBus, pos.nextCol());

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
    fldId.setText(String.valueOf(line.getId()));
    fldCode.setText(line.getCode());
    fldName.setText(line.getName());
    fldDesc.setText(line.getDescription());
    fldType.setSelectedItem(line.getType());

    try {
      for (Bus bus : lineModel.getBuses(line)) {
        lstBusesModel.addElement(bus);
      }
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(this, String.format(R.ERROR_SQL, ex.getMessage()), R.STR_LINE, JOptionPane.ERROR_MESSAGE);
    }

  }

  public Line save() {
    
    line.setCode(fldCode.getText());
    line.setName(fldName.getText());
    line.setType(fldType.getSelectedItem().toString());
    line.setDescription(fldDesc.getText());

    isNew = false;

    return line;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String cmd = e.getActionCommand();

    if (cmd.equals(R.CMD_SAVE)) {
      try {
        if (isNew) {
          lineModel.create(this.save());
        } else {
          lineModel.update(this.save());
        }
        JOptionPane.showMessageDialog(this, R.STR_ITEM_SAVED, R.STR_LINE, JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
      } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, String.format(R.ERROR_SAVE_FAILS, ex.getMessage()), R.STR_LINE, JOptionPane.ERROR_MESSAGE);
      }
    }

    if (cmd.equals(R.CMD_CLOSE)) {
      this.dispose();
    }

    if (cmd.equals(R.CMD_ACCEPT)) {
      this.dispose();
    }

    if (cmd.equals(R.CMD_ADD_BUS)) {
      Bus bus = (Bus) slctBus.getSelectedItem();
      lstBusesModel.addElement(bus);

      if (!isNew) {
        try {
          lineModel.addBus(line, bus);
        } catch (SQLException ex) {
          JOptionPane.showMessageDialog(this, String.format(R.ERROR_ADD_FAILS, ex.getMessage()), R.STR_LINE, JOptionPane.ERROR_MESSAGE);
        }
      }
    }

    if (cmd.equals(R.CMD_REMOVE_BUS)) {
      Bus bus = (Bus) lstBusesModel.getElementAt(lstBuses.getSelectedIndex());
      if (JOptionPane.showConfirmDialog(this, String.format(R.REMOVE_CONFIRM, bus.toString()), R.STR_LINE, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
        try {
          lineModel.removeBus(line, bus);
          lstBusesModel.removeElementAt(lstBuses.getSelectedIndex());
        } catch (SQLException ex) {
          JOptionPane.showMessageDialog(this, String.format(R.ERROR_REMOVE_FAILS, ex.getMessage()), R.STR_LINE, JOptionPane.ERROR_MESSAGE);
        }
      } else {
        System.out.print("NO");
      }
    }
  }

  @Override
  public void itemStateChanged(ItemEvent e) {

    if (e.getSource() == fldType) {
      String element = (String) e.getItem();
      System.out.println(element);

      BusModel busModel = new BusModel();
      slctBus.removeAllItems();
      try {
        for (Bus bus : busModel.toArray(element)) {
          slctBus.addItem(bus);
        }

      } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, String.format(R.ERROR_SQL, ex.getMessage()), R.STR_LINE, JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    if (e.getSource() == lstBuses) {
      btnRemoveBus.setEnabled(true);
    }
  }

}
