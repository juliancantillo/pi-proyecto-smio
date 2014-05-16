
package smio;

import dbc.DBConnector;
import gui.MainForm;
import resources.R;

/**
 *
 * @author Usuario
 */
public class SMIO {
  
  public static final Double price = 1600.0;
  
  public SMIO() {
  }
  
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    SMIO mainApp = new SMIO();
        
    DBConnector dbc = new DBConnector();
    dbc.connect();
    
    MainForm form = new MainForm();
    form.setVisible(true);
    
  }
  
}
