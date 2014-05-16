/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package exceptions;

import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Universidad del Valle
 * @author Julian Andres Cantillo // cod: 1431263 - 3743
 */
public class DataBaseException extends SQLException{

  public DataBaseException(String reason) {
    super(reason);
    
    JOptionPane.showMessageDialog(null, reason);
  } 
  
}
