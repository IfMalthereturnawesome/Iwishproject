package com.example.iwishproject.model;

import java.beans.Transient;

public class Wish {
  private int id;
  private String title;
  private String description;
  private double price;
  private String link;
  private int listID;

  private String photos;


  public Wish() {
  }

  public Wish(int id, String title, String description, double price, String link, String photos) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.price = price;
    this.link = link;
    this.photos = photos;
  }

  public String getPhotos() {
    return photos;
  }

  public void setPhotos(String photos) {
    this.photos = photos;
  }

@Transient
  public String getPhotosImagePath() {
    if (photos == null) return "user-photos/" + "gave.jpg";

    return "user-photos" + "/" + photos;
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

  public int getListID() {
    return listID;
  }

  public void setListID(int listID) {
    this.listID = listID;
  }
}
