
package entities.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Entity CRUD interface
 * @author Julian Cantillo
 */
public interface DBModel {
    
  public int create( Object obj ) throws SQLException;
  public ResultSet read() throws SQLException;
  public Object read( int id ) throws SQLException;
  public void update( Object obj ) throws SQLException;
  public void delete( int id ) throws SQLException;
  
}
