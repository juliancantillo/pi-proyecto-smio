/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.dao;

import dbc.DBConnector;
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
public class UserModel implements DBModel {

  private Connection conn;
  private DBConnector dbc;

  public UserModel() {
    dbc = new DBConnector();
    conn = dbc.connect();
  }

  @Override
  public int create(Object obj) throws SQLException {
    User user = (User) obj;
    String insert = String.format("INSERT INTO users( identification, firstname, lastname, address) VALUES ('%s', '%s', '%s', '%s');",
            user.getIdentification(),
            user.getFirstname(),
            user.getLastname(),
            user.getAddress()
    );

    Statement st = conn.createStatement();
    int id = st.executeUpdate(insert, Statement.RETURN_GENERATED_KEYS);
    user.setCardId(id);

    return id;

  }

  @Override
  public ResultSet read() throws SQLException {
    Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    ResultSet rs = st.executeQuery("SELECT * FROM users");

    if (rs != null) {
      return rs;
    }

    return null;
  }

  @Override
  public User read(int id) throws SQLException {
    User user = null;

    String sql = String.format("SELECT * FROM users WHERE users.id = '%s'", id);

    Statement st;
    st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    ResultSet rs = st.executeQuery(sql);

    if (rs.first()) {
      user = new User(
              rs.getInt("id"),
              rs.getString("firstname"),
              rs.getString("lastname"),
              rs.getString("address"),
              rs.getString("identification"),
              rs.getDouble("charge")
      );
    }

    return user;
  }

  @Override
  public void update(Object obj) throws SQLException {
    User user = (User) obj;

    String sql = String.format("UPDATE users SET identification='%s', firstname='%s', lastname='%s', address='%s' WHERE id = %s;",
            user.getIdentification(),
            user.getFirstname(),
            user.getLastname(),
            user.getAddress(),
            user.getCardId()
    );
    Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    st.executeUpdate(sql);

  }

  @Override
  public void delete(int id) throws SQLException {
    String sql = String.format("DELETE FROM users WHERE id = %s", id);

    Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    st.executeUpdate(sql);

  }

  public User whereIdentification(String identification) throws SQLException {
    User user = null;

    String sql = String.format("SELECT * FROM users WHERE identification LIKE '%s'", identification);

    Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    ResultSet rs = st.executeQuery(sql);

    if (rs.first()) {
      user = new User(
              rs.getInt("id"),
              rs.getString("firstname"),
              rs.getString("lastname"),
              rs.getString("address"),
              rs.getString("identification"),
              rs.getDouble("charge")
      );
    }

    return user;
  }

  public User[] searchByIdentification(String identification) throws SQLException {
    User user[] = null;

    String sql = String.format("SELECT * FROM users WHERE identification LIKE '%s%%'", identification);

    Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    ResultSet rs = st.executeQuery(sql);

    rs.last();
    user = new User[rs.getRow()];
    rs.beforeFirst();
    while (rs.next()) {
      user[rs.getRow() - 1] = new User(
              rs.getInt("id"),
              rs.getString("firstname"),
              rs.getString("lastname"),
              rs.getString("address"),
              rs.getString("identification"),
              rs.getDouble("charge")
      );
    }

    return user;
  }

  public int addCharge(User user, Double charge) throws SQLException {
    String sql = String.format("UPDATE users SET  charge=%s WHERE users.id = %s;", charge, user.getCardId());

    chargeLog(user, charge);

    Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    return st.executeUpdate(sql);
  }

  public void chargeLog(User user, Double charge) throws SQLException {
    String sql = String.format("INSERT INTO user_recharges(charge, user_id) VALUES (%s, %s);", charge, user.getCardId());

    System.out.println(sql);

    Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    st.executeUpdate(sql);
  }

  public String[] getChargeLog(User user) throws SQLException {
    String[] log;
    String sql = String.format("SELECT charge, charged_at FROM user_recharges WHERE user_id = %s;", user.getCardId());

    Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    ResultSet rs = st.executeQuery(sql);

    rs.last();
    log = new String[rs.getRow()];
    rs.beforeFirst();
    while (rs.next()) {
      log[rs.getRow() - 1] = String.format("$%s - %s", rs.getDouble("charge"), rs.getString("charged_at"));
    }

    return log;
  }

}
