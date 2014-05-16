/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.dao;

import dbc.DBConnector;
import entities.Bus;
import entities.Line;
import entities.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Universidad del Valle
 *
 * @author Julian Andres Cantillo // cod: 1431263 - 3743
 */
public class LineModel implements DBModel {

  private Connection conn;
  private DBConnector dbc;

  public LineModel() {
    dbc = new DBConnector();
    conn = dbc.connect();
  }

  @Override
  public int create(Object obj) throws SQLException {
    Line line = (Line) obj;
    String insert = String.format("INSERT INTO line( code, name, type, description) VALUES ('%s', '%s', '%s', '%s');",
            line.getCode(),
            line.getName(),
            line.getType(),
            line.getDescription()
    );
    Statement st = conn.createStatement();
    int id = st.executeUpdate(insert, Statement.RETURN_GENERATED_KEYS);
    line.setId(id);

    return id;

  }

  @Override
  public ResultSet read() throws SQLException {
    Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    ResultSet rs = st.executeQuery("SELECT * FROM line");

    if (rs != null) {
      return rs;
    }

    return null;
  }

  @Override
  public Line read(int id) throws SQLException {
    Line line = null;

    String sql = String.format("SELECT * FROM line WHERE line.id = '%s'", id);

    Statement st;

    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    ResultSet rs = st.executeQuery(sql);

    rs.first();

    line = new Line(
            rs.getString("code"),
            rs.getString("name"),
            rs.getString("type"),
            rs.getString("description")
    );
    line.setId(rs.getInt("id"));

    return line;
  }

  @Override
  public void update(Object obj) throws SQLException {
    Line line = (Line) obj;

    String sql = String.format("UPDATE line SET code='%s', name='%s', type='%s', description='%s' WHERE id = %s;",
            line.getCode(),
            line.getName(),
            line.getType(),
            line.getDescription(),
            line.getId()
    );

    Statement st = conn.createStatement();
    st.executeUpdate(sql);

  }

  @Override
  public void delete(int id) throws SQLException {
    String sql = String.format("DELETE FROM line WHERE id = %s", id);
    Statement st = conn.createStatement();
    st.executeUpdate(sql);
  }

  public Bus[] getBuses(Line line) throws SQLException {
    Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    ResultSet rs = st.executeQuery(String.format("SELECT bus.* FROM public.bus, public.lines_has_buses, public.line WHERE lines_has_buses.bus_id = bus.id AND line.id = lines_has_buses.line_id AND line.id = %d;", line.getId()));

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

  public void addBus(Line line, Bus bus) throws SQLException {
    Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    st.execute(String.format("INSERT INTO lines_has_buses(line_id, bus_id) VALUES (%d, %d);", line.getId(), bus.getId()));
  }

  public void removeBus(Line line, Bus bus) throws SQLException {
    Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    st.executeUpdate(String.format("DELETE FROM lines_has_buses WHERE bus_id = %d AND line_id = %d;", bus.getId(), line.getId()));
  }

  public Line[] searchByName(String name) throws SQLException {
    Line line[] = null;

    String sql = String.format("SELECT * FROM line WHERE name LIKE '%s%%'", name);

    Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    ResultSet rs = st.executeQuery(sql);

    rs.last();
    line = new Line[rs.getRow()];
    rs.beforeFirst();
    while (rs.next()) {
      line[rs.getRow() - 1] = new Line(
              rs.getString("code"),
              rs.getString("name"),
              rs.getString("type"),
              rs.getString("description")
      );
    }

    return line;
  }

}
