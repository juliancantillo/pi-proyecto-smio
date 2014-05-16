
package entities;

import java.io.Serializable;

/**
 * Universidad del Valle
 * @author Julian Andres Cantillo // cod: 1431263 - 3743
 */
public class Line implements Serializable{

  private Integer id;
  private String code;
  private String name;
  private String type;
  private String description;

  public Line() {
  }
  
  public Line(String code, String name, String type, String description) {
    this.code = code;
    this.name = name;
    this.type = type;
    this.description = description;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return String.format("%s - %s, %s", this.code, this.name, this.description);
  }
  
  
  
}
