/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Universidad del Valle
 *
 * @author Julian Andres Cantillo // cod: 1431263 - 3743
 */
public class DBConnector {

  private final String host;
  private final String user;
  private final String password;
  private final String port;
  private final String database;
  private final String url;

  public DBConnector() {
    this.host = "localhost";
    this.database = "pi-smio";
    this.password = "16781010";
    this.user = "postgres";
    this.port =  "5432";
    
    this.url = String.format("jdbc:postgresql://%s:%s/%s", this.host, this.port, this.database);
  }

  public DBConnector(String host, String port, String database, String user, String password) {
    this.host = host;
    this.user = user;
    this.password = password;
    this.port = port;
    this.database = database;
    
    this.url = String.format("jdbc:postgresql://%s:%s/%s", this.host, this.port, this.database);
  }
  
  public Connection connect() {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println("");
      return null;
    }

    Connection connection = null;

    try {
      connection = DriverManager.getConnection(this.url, this.user,this.password);
    } catch (SQLException e) {

      System.out.println("Connection Failed! Check output console " + e.getMessage());
      return null;

    }
    
    return connection;
  }
  
  public void close(Connection con){
    try {
      con.close();
    } catch (SQLException ex) {
      System.out.println("Connection closing failed " + ex.getMessage());
    }
  }

}
