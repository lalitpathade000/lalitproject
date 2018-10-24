package com.lalit.lalit_.lalitproject.Model;

public class Movies {
    String id;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImg() {
        return img;
    }

    public String getDate() {
        return date;
    }

    public Boolean getAdult() {
        return adult;
    }

    String title;
    String img;
    String date;
    Boolean adult;

    public Movies(String id, String title, String img, String date, Boolean adult) {
        this.id = id;
        this.title = title;
        this.img = img;
        this.date = date;
        this.adult = adult;
    }
}
