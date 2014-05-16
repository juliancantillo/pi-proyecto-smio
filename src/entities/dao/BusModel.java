/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.dao;

import dbc.DBConnector;
import entities.Bus;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Universidad del Valle
 *
 * @author Julian Andres Cantillo // cod: 1431263 - 3743
 */
public class BusModel implements DBModel {

  private Connection conn;
  private DBConnector dbc;

  public BusModel() {
    dbc = new DBConnector();
    conn = dbc.connect();
  }

  @Override
  public int create(Object obj) throws SQLException {
    Bus bus = (Bus) obj;

    String insert = String.format("INSERT INTO bus( code, capacity, maker, model, bus_type) VALUES ('%s', '%d', '%s', '%s', '%s');",
            bus.getCode(),
            bus.getCapacity(),
            bus.getMaker(),
            bus.getModel(),
            bus.getType()
    );

      Statement st = conn.createStatement();
      int id = st.executeUpdate(insert, Statement.RETURN_GENERATED_KEYS);
      bus.setId(id);

      return id;

  }

  @Override
  public ResultSet read() throws SQLException {
    Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    ResultSet rs = st.executeQuery("SELECT * FROM bus");

    if (rs != null) {
      return rs;
    }

    return null;
  }

  @Override
  public Bus read(int id) throws SQLException {
    Bus bus = null;

    String sql = String.format("SELECT * FROM bus WHERE bus.id = '%s'", id);

    Statement st;
    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    ResultSet rs = st.executeQuery(sql);

    rs.first();

    bus = new Bus(
            rs.getString("code"),
            rs.getInt("capacity"),
            rs.getString("maker"),
            rs.getString("model"),
            rs.getString("bus_type"));
    bus.setId(rs.getInt("id"));

    return bus;
  }

  @Override
  public void update(Object obj) throws SQLException {
    Bus bus = (Bus) obj;

    String sql = String.format("UPDATE bus SET code='%s', capacity='%s', maker='%s', model='%s', bus_type='%s' WHERE id = %s",
            bus.getCode(),
            bus.getCapacity(),
            bus.getMaker(),
            bus.getModel(),
            bus.getType(),
            bus.getId()
    );
    
    System.out.println(sql);

    Statement st = conn.createStatement();
    st.executeUpdate(sql);

  }

  @Override
  public void delete(int id) throws SQLException {
    String sql = String.format("DELETE FROM bus WHERE id = %d", id);

    Statement st = conn.createStatement();
    st.executeUpdate(sql);
  }

  public ResultSet whereType(String type) throws SQLException {
    Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    ResultSet rs = st.executeQuery(String.format("SELECT * FROM bus WHERE bus_type LIKE '%s';", type));

    if (rs != null) {
      return rs;
    }

    return null;
  }

  public Bus[] toArray() throws SQLException {

    ResultSet rs = read();
    rs.last();
    Bus buses[] = new Bus[rs.getRow()];
    rs.beforeFirst();
    while (rs.next()) {
      buses[rs.getRow() - 1] = new Bus(
              rs.getString("code"),
              rs.getInt("capacity"),
              rs.getString("maker"),
              rs.getString("model"),
              rs.getString("bus_type"));
      buses[rs.getRow() - 1].setId(rs.getInt("id"));
    }

    return buses;
  }

  public Bus[] toArray(String type) throws SQLException {

    ResultSet rs = whereType(type);
    rs.last();
    Bus buses[] = new Bus[rs.getRow()];
    rs.beforeFirst();
    while (rs.next()) {
      buses[rs.getRow() - 1] = new Bus(
              rs.getString("code"),
              rs.getInt("capacity"),
              rs.getString("maker"),
              rs.getString("model"),
              rs.getString("bus_type"));
      buses[rs.getRow() - 1].setId(rs.getInt("id"));
    }

    return buses;
  }

}
