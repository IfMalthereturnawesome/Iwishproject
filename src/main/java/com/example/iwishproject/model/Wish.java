package com.example.iwishproject.model;

public class Wish {
  private int id;
  private String title;
  private String description;
  private double price;
  private String link;

  public Wish(int id, String title, String description, double price, String link) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.price = price;
    this.link = link;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }
}
