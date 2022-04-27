package com.example.iwishproject.model;

public class User {

  private int ID;

  private String eMail;

  private String password;

  private String firstName;

  private String lastName;


  public User(int id, String eMail, String password, String firstName, String lastName) {
    this.ID = id;
    this.eMail = eMail;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
  }
  public User() {
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String geteMail() {
    return eMail;
  }

  public void seteMail(String eMail) {
    this.eMail = eMail;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public int getID() {
    return ID;
  }

  public void setID(int ID) {
    this.ID = ID;
  }
}
