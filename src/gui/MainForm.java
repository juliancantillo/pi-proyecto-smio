
package gui;

import entities.Bus;
import entities.Line;
import entities.User;
import entities.dao.BusModel;
import entities.dao.LineModel;
import entities.dao.UserModel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import gui.helpers.ResultsetTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import resources.R;

/**
 * Universidad del Valle
 * @author Julian Andres Cantillo // cod: 1431263 - 3743
 */
public class MainForm extends JFrame implements ActionListener, TableModelListener, MouseListener, WindowListener{
  
  private JTable tblBuses;
  private JTable tblLines;
  private JTable tblUsers;
  private JTextField fldFilterLines;
      
  public MainForm() {
    super( R.APP_TITLE );
        
    setLayout(new BorderLayout(R.H, R.W));
        
    add(toolBar(), BorderLayout.BEFORE_FIRST_LINE);
    add(tabs(), BorderLayout.CENTER);
    setJMenuBar(formMenu());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
  }
  
  private JMenuBar formMenu(){
    JMenuBar menuBar = new JMenuBar();
    
    JMenu mnFile = new JMenu( R.MN_FILE );
    JMenu mnHelp = new JMenu(R.MN_HELP);
    JMenu mnCreate = new JMenu(R.MN_CREATE);
    
    JMenuItem mnItemExit = new JMenuItem(R.MN_EXIT);
    JMenuItem mnItemAbout = new JMenuItem(R.MN_ABOUT);
    JMenuItem mnItemNewBus = new JMenuItem(R.STR_NEW_BUS);
    JMenuItem mnItemNewLine = new JMenuItem(R.STR_NEW_LINE);
    JMenuItem mnItemNewUser = new JMenuItem(R.STR_NEW_USER);
    
    mnItemExit.setActionCommand(R.CMD_EXIT);
    mnItemAbout.setActionCommand(R.CMD_SHOW_ABOUT_DLG);
    mnItemNewBus.setActionCommand(R.CMD_NEW_BUS);
    mnItemNewLine.setActionCommand(R.CMD_NEW_LINE);
    mnItemNewUser.setActionCommand(R.CMD_NEW_USER);
    
    mnFile.add(mnItemExit);
    mnHelp.add(mnItemAbout);
    mnCreate.add(mnItemNewBus);
    mnCreate.add(mnItemNewLine);
    mnCreate.add(mnItemNewUser);
    
    mnItemExit.addActionListener(this);
    mnItemAbout.addActionListener(this);
    mnItemNewBus.addActionListener(this);
    mnItemNewLine.addActionListener(this);
    mnItemNewUser.addActionListener(this);
    
    menuBar.add(mnFile);
    menuBar.add(mnCreate);
    menuBar.add(mnHelp);
    
    return menuBar;
  }
  
  private JToolBar toolBar(){
    JToolBar toolBar = new JToolBar();
    
    JButton btnNewBus = new JButton( R.STR_NEW_BUS );
    JButton btnNewLine = new JButton( R.STR_NEW_LINE );
    JButton btnNewUser = new JButton( R.STR_NEW_USER );
    JButton btnUpdateData = new JButton(R.STR_UPDATE_TABLES);
    JButton btnRecharge = new JButton(R.STR_RECHARGE);
    JButton btnUncharge = new JButton(R.STR_UNCHARGE);
    JButton btnSearch = new JButton(R.STR_SEARCH);
    
    btnNewBus.setActionCommand(R.CMD_NEW_BUS);
    btnNewLine.setActionCommand(R.CMD_NEW_LINE);
    btnNewUser.setActionCommand(R.CMD_NEW_USER);
    btnUpdateData.setActionCommand(R.CMD_UPDATE_TABLES);
    btnRecharge.setActionCommand(R.CMD_RECHARGE);
    btnUncharge.setActionCommand(R.CMD_UNRECHARGE);
    btnSearch.setActionCommand(R.CMD_SEARCH_FORM);
    
    btnNewBus.addActionListener(this);
    btnNewLine.addActionListener(this);
    btnNewUser.addActionListener(this);
    btnUpdateData.addActionListener(this);
    btnRecharge.addActionListener(this);
    btnUncharge.addActionListener(this);
    btnSearch.addActionListener(this);
    
    toolBar.add(btnNewBus);
    toolBar.add(btnNewLine);
    toolBar.add(btnNewUser);
    toolBar.add(btnUpdateData);
    toolBar.add(btnRecharge);
    toolBar.add(btnUncharge);
    toolBar.add(btnSearch);
    
    return toolBar;
  }
  
  private JTabbedPane tabs(){
    JTabbedPane tabs = new JTabbedPane();
    
    tabs.addTab(R.STR_BUS, buses());
    tabs.addTab(R.STR_LINES, lines());
    tabs.addTab(R.STR_USERS, users());
    
    return tabs;
  }
  
  private JPanel buses(){
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout()); 
    
    BusModel busModel = new BusModel();
    
    ResultSet tableData = null;
    try {
      tableData = busModel.read();
    } catch (SQLException ex) {
      Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    tblBuses = new JTable( new ResultsetTableModel( tableData, R.SRT_BUS_COLUMNS));
    tblBuses.getModel().addTableModelListener(this);
    tblBuses.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    tblBuses.addMouseListener(this);
        
    panel.add(new JScrollPane(tblBuses), BorderLayout.CENTER);
        
    return panel;
  }
  
  private JPanel lines(){
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    
    fldFilterLines = new JTextField();
       
    LineModel lineModel = new LineModel();
    
    ResultSet tableData = null;
    try {
      tableData = lineModel.read();
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(this, String.format(R.ERROR_LOAD_DATA_FAILS, ex.getMessage()), R.STR_ERROR, JOptionPane.ERROR_MESSAGE);
    }
    
    tblLines = new JTable( new ResultsetTableModel( tableData, R.SRT_LINE_COLUMNS));
    tblLines.getModel().addTableModelListener(this);
    tblLines.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    tblLines.addMouseListener(this);
    
    panel.add(new JScrollPane(tblLines), BorderLayout.CENTER);
    
    return panel;
  }
  
  private JPanel users(){
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
        
    UserModel userModel = new UserModel();
    
    ResultSet tableData = null;
    try {
      tableData = userModel.read();
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(this, String.format(R.ERROR_LOAD_DATA_FAILS, ex.getMessage()), R.STR_ERROR, JOptionPane.ERROR_MESSAGE);
    }
    
    tblUsers = new JTable( new ResultsetTableModel( tableData, R.SRT_USERS_COLUMNS));
    tblUsers.getModel().addTableModelListener(this);
    tblUsers.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    tblUsers.addMouseListener(this);
    
    panel.add(new JScrollPane(tblUsers), BorderLayout.CENTER);
    
    return panel;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    
    String cmd = e.getActionCommand();
    
    if(cmd.equals(R.CMD_EXIT)){
      System.exit(0);
    }
    
    if(cmd.equals(R.CMD_SHOW_ABOUT_DLG)){
      JOptionPane.showMessageDialog(this, R.STR_ABOUT_INFO , R.MN_ABOUT, JOptionPane.INFORMATION_MESSAGE);
    }
    
    if(cmd.equals(R.CMD_NEW_BUS)){
      BusForm busForm = new BusForm();
      busForm.setVisible(true);
      busForm.addWindowListener(this);
    }
    
    if(cmd.equals(R.CMD_UPDATE_TABLES)){
      ResultsetTableModel rm;
      rm = (ResultsetTableModel) tblBuses.getModel();
      rm.fireTableDataChanged();  
      
      rm = (ResultsetTableModel) tblLines.getModel();
      rm.fireTableDataChanged();
      
      rm = (ResultsetTableModel) tblUsers.getModel();
      rm.fireTableDataChanged();
      
    }
    
    if(cmd.equals(R.CMD_NEW_LINE)){
      LineForm lineForm = new LineForm();
      lineForm.setVisible(true);
      lineForm.addWindowListener(this);
    }
    
    if(cmd.equals(R.CMD_NEW_USER)){
      UserForm userForm = new UserForm();
      userForm.setVisible(true);
      userForm.addWindowListener(this);
    }
    
    if(cmd.equals(R.CMD_RECHARGE)){
      ChargeForm rForm = new ChargeForm();
      rForm.setVisible(true);
    }
    
    if(cmd.equals(R.CMD_UNRECHARGE)){
      UnchargeForm ucForm = new UnchargeForm();
      ucForm.setVisible(true);
    }
    
    if(cmd.equals(R.CMD_SEARCH_FORM)){
      SearchForm searchForm = new SearchForm();
      searchForm.setVisible(true);
    }
    
  }

  @Override
  public void tableChanged(TableModelEvent e) {
    try {
      System.out.println("Updating...");
      tblBuses.setModel(new ResultsetTableModel(new BusModel().read(), R.SRT_BUS_COLUMNS, this));
      tblBuses.updateUI();
      
      tblLines.setModel(new ResultsetTableModel(new LineModel().read(), R.SRT_LINE_COLUMNS, this));
      tblLines.updateUI();
      
      tblUsers.setModel(new ResultsetTableModel(new UserModel().read(), R.SRT_USERS_COLUMNS, this));
      tblUsers.updateUI();
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(this, String.format(R.ERROR_LOAD_DATA_FAILS, ex.getMessage()), R.STR_ERROR, JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if(e.getSource() == tblBuses){
      try {
        int row = tblBuses.getSelectedRow();
        int col = tblBuses.getSelectedColumn();
        Integer val = (Integer) tblBuses.getValueAt(row, 0);
        
        BusModel bm = new BusModel();
        Bus bus = bm.read(val);
        
        BusForm busForm = new BusForm(bus);
        busForm.addWindowListener(this);
        busForm.setVisible(true);
      } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, String.format(R.ERROR_LOAD_DATA_FAILS, ex.getMessage()), R.STR_ERROR, JOptionPane.ERROR_MESSAGE);
      }
      
    }
    
    if(e.getSource() == tblLines){
      try {
        int row = tblLines.getSelectedRow();
        Integer val = (Integer) tblLines.getValueAt(row, 0);
        
        LineModel lm = new LineModel();
        Line line = lm.read(val);
        
        LineForm lineForm = new LineForm(line);
        lineForm.addWindowListener(this);
        lineForm.setVisible(true);
      } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, String.format(R.ERROR_LOAD_DATA_FAILS, ex.getMessage()), R.STR_ERROR, JOptionPane.ERROR_MESSAGE);
      }
    }
    
    if(e.getSource() == tblUsers){
      try {
        int row = tblUsers.getSelectedRow();
        Integer val = (Integer) tblUsers.getValueAt(row, 0);
        
        UserModel um = new UserModel();
        User user = um.read(val);
        
        UserForm userForm = new UserForm(user);
        userForm.addWindowListener(this);
        userForm.setVisible(true);
      } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, String.format(R.ERROR_LOAD_DATA_FAILS, ex.getMessage()), R.STR_ERROR, JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {}

  @Override
  public void mouseReleased(MouseEvent e) {}

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}

  @Override
  public void windowOpened(WindowEvent e) {}

  @Override
  public void windowClosing(WindowEvent e) {}

  @Override
  public void windowClosed(WindowEvent e) {
    if(e.getSource() instanceof UserForm){
      ResultsetTableModel rm = (ResultsetTableModel) tblUsers.getModel();
      rm.fireTableDataChanged();
    }
    if(e.getSource() instanceof LineForm){
      ResultsetTableModel rm = (ResultsetTableModel) tblLines.getModel();
      rm.fireTableDataChanged();
    }
    if(e.getSource() instanceof BusForm){
      ResultsetTableModel rm = (ResultsetTableModel) tblBuses.getModel();
      rm.fireTableDataChanged();
    }
  }

  @Override
  public void windowIconified(WindowEvent e) {}

  @Override
  public void windowDeiconified(WindowEvent e) {}

  @Override
  public void windowActivated(WindowEvent e) {}

  @Override
  public void windowDeactivated(WindowEvent e) {}
  
  
}
