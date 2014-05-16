
package entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Universidad del Valle
 * @author Julian Andres Cantillo // cod: 1431263 - 3743
 */
public class User implements Serializable{
  
  private Integer id;
  private String identification;
  private String firstname;
  private String lastname;
  private String address;
  private Double charge;
  private String fullname;
  private final Date created_at;

  public User() {
    created_at = new Date();
  }
  
  public User(Integer cardId, String firstname, String lastname, String address, String identification) {
    this.id = cardId;
    this.firstname = firstname;
    this.lastname = lastname;
    this.fullname = firstname + " " + lastname;
    this.address = address;
    this.identification = identification;
    this.charge = 0.0;
    created_at = new Date();
  }

  public User( String firstname, String lastname, String address, String identification) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.fullname = firstname + " " + lastname;
    this.address = address;
    this.identification = identification;
    this.charge = 0.0;
    created_at = new Date();
  }
  
  public User(Integer cardId, String firstname, String lastname, String address, String identification, Double charge) {
    this.id = cardId;
    this.firstname = firstname;
    this.lastname = lastname;
    this.fullname = firstname + " " + lastname;
    this.address = address;
    this.identification = identification;
    this.charge = charge;
    created_at = new Date();
  }

  public Integer getCardId() {
    return id;
  }

  public void setCardId(int cardId) {
    this.id = cardId;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getFullname() {
    return fullname;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getIdentification() {
    return identification;
  }

  public void setIdentification(String identification) {
    this.identification = identification;
  }

  public Date getCreated_at() {
    return created_at;
  }

  public Double getCharge() {
    return charge;
  }

  public void setCharge(Double charge) {
    this.charge = charge;
  }

  @Override
  public String toString() {
    return String.format("%s %s - %s",this.getFirstname(), this.getLastname(), this.getIdentification());
  }
      
}
