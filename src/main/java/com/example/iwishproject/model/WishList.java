package com.example.iwishproject.model;

import java.beans.Transient;

public class WishList {

    private int id;
    private String description;
    private String title;
    private String photos;

    private int userID;

    public WishList() {

    }

    public WishList(int id, String title, String description, int userID, String photos) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userID = userID;
        this.photos = photos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Transient
    public String getPhotosImagePath() {
        if (photos == null || photos.isEmpty()) return "user-photos/" + "tillykke-med-foedselsdagen-1.jpg";

        return "user-photos" + "/" + photos;
    }
}
