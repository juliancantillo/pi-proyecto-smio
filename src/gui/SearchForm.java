/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Bus;
import entities.Line;
import entities.User;
import entities.dao.BusModel;
import entities.dao.LineModel;
import entities.dao.UserModel;
import gui.helpers.GBHelper;
import gui.helpers.Gap;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class SearchForm extends JFrame implements ActionListener, ListSelectionListener {

  private JTextField fldUserId;
  private JComboBox slctBusType;
  private JTextField fldLineName;
  private final String types[] = {"Alimentador", "Padrón", "Troncal"};
  private DefaultListModel lstResultsModel;
  private JList lstResults;

  public SearchForm() {
    super(R.STR_SEARCH);
    setLayout(new GridBagLayout());
    
    GBHelper pos = new GBHelper();
    
    add(pnlSearchUsers(), pos.expandW());
    add(new Gap(R.VGAP), pos.nextRow());
    add(pnlSearchLines(), pos.nextRow().expandW());
    add(new Gap(R.VGAP), pos.nextRow());
    add(pnlSearchBuses(), pos.nextRow().expandW());
    add(new Gap(R.VGAP), pos.nextRow());
    add(pnlResults(), pos.nextRow().expandH().expandH());

    setSize(400, 350);
  }

  private JPanel pnlSearchUsers() {
    JPanel panel = new JPanel();

    panel.setLayout(new GridBagLayout());

    JLabel lblBuscar = new JLabel(R.STR_SEARCH_USERS);

    fldUserId = new JTextField();

    JButton btnSearch = new JButton(R.STR_SEARCH);

    btnSearch.addActionListener(this);

    btnSearch.setActionCommand(R.CMD_SEARCH_USERS);

    GBHelper pos = new GBHelper();

    panel.add(lblBuscar, pos);
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(fldUserId, pos.nextCol().expandW());
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(btnSearch, pos.nextCol());

    return panel;
  }

  private JPanel pnlSearchLines() {
    JPanel panel = new JPanel();

    panel.setLayout(new GridBagLayout());

    JLabel lblBuscar = new JLabel(R.STR_SEARCH_LINES);

    fldLineName = new JTextField();

    JButton btnSearch = new JButton(R.STR_SEARCH);

    btnSearch.addActionListener(this);

    btnSearch.setActionCommand(R.CMD_SEARCH_LINES);

    GBHelper pos = new GBHelper();

    panel.add(lblBuscar, pos);
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(fldLineName, pos.nextCol().expandW());
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(btnSearch, pos.nextCol());

    return panel;
  }

  private JPanel pnlSearchBuses() {
    JPanel panel = new JPanel();

    panel.setLayout(new GridBagLayout());

    JLabel lblBuscar = new JLabel(R.STR_SEARCH_BUSES);

    slctBusType = new JComboBox(types);

    JButton btnSearch = new JButton(R.STR_SEARCH);

    btnSearch.addActionListener(this);

    btnSearch.setActionCommand(R.CMD_SEARCH_BUSES);

    GBHelper pos = new GBHelper();

    panel.add(lblBuscar, pos);
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(slctBusType, pos.nextCol().expandW());
    panel.add(new Gap(R.HGAP), pos.nextCol());
    panel.add(btnSearch, pos.nextCol());

    return panel;
  }

  private JPanel pnlResults() {
    JPanel panel = new JPanel();

    panel.setLayout(new BorderLayout(R.H, R.W));
    panel.setBorder(BorderFactory.createTitledBorder(R.STR_BUS));

    lstResultsModel = new DefaultListModel();
    lstResults = new JList(lstResultsModel);
    lstResults.addListSelectionListener(this);

    panel.add(new JScrollPane(lstResults), BorderLayout.CENTER);

    return panel;
  }

  private void loadResults(Object[] results) {
    lstResultsModel.removeAllElements();
    for (Object item : results) {
      lstResultsModel.addElement(item);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String cmd = e.getActionCommand();
    
    if(cmd.equals(R.CMD_SEARCH_USERS)){
      UserModel userModel = new UserModel();
      try {
        loadResults( (User[]) userModel.searchByIdentification(fldUserId.getText() ));
      } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, String.format(R.ERROR_LOAD_DATA_FAILS, ex.getMessage()), R.STR_ERROR, JOptionPane.ERROR_MESSAGE);
      }
    }
    if(cmd.equals(R.CMD_SEARCH_LINES)){
      LineModel lineModel = new LineModel();
      try {
        loadResults( (Line[]) lineModel.searchByName(fldLineName.getText() ));
      } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, String.format(R.ERROR_LOAD_DATA_FAILS, ex.getMessage()), R.STR_ERROR, JOptionPane.ERROR_MESSAGE);
      }
    }
    if(cmd.equals(R.CMD_SEARCH_BUSES)){
      BusModel busModel = new BusModel();
      try {
        loadResults( (Bus[]) busModel.toArray( slctBusType.getSelectedItem().toString() ));
      } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, String.format(R.ERROR_LOAD_DATA_FAILS, ex.getMessage()), R.STR_ERROR, JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    if(e.getSource() == lstResults){
      Object object = lstResultsModel.getElementAt(lstResults.getSelectedIndex());
      
      if(object instanceof Bus){
        BusForm busForm = new BusForm( (Bus) object);
        busForm.setVisible(true);
      }
      
      if(object instanceof User){
        UserForm userForm = new UserForm( (User) object);
        userForm.setVisible(true);
      }
      
      if(object instanceof Line){
        LineForm lineForm = new LineForm( (Line) object);
        lineForm.setVisible(true);
      }
    }
  }

}
