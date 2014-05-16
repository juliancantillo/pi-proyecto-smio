
package entities;

import java.io.Serializable;

/**
 * Universcodead del Valle
 * @author Julian Andres Cantillo // cod: 1431263 - 3743
 */
public class Bus implements Serializable{
  
  private int id;
  private String code;
  private Integer capacity;
  private String maker;
  private String model;
  private String type;

  public Bus(String code, Integer capacity, String maker, String model, String type) {
    this.code = code;
    this.capacity = capacity;
    this.maker = maker;
    this.model = model;
    this.type = type;
  }

  public Bus() {
  }
    
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Integer getCapacity() {
    return capacity;
  }

  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }

  public String getMaker() {
    return maker;
  }

  public void setMaker(String maker) {
    this.maker = maker;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }
  
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return String.format("%s - %s", this.code, this.type);
  }
  
  
    
}
