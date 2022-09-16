package com.example.iusuapp.models;

public class News {

    private int id;
    private String image;
    private String title;
    private String description;
    private String date;
    private String goId;
    private String gpostTitle;

    public News(int id, String image, String title, String description, String date, String goId, String gpostTitle) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.description = description;
        this.date = date;
        this.goId = goId;
        this.gpostTitle = gpostTitle;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getGoId() {
        return goId;
    }

    public String getGpostTitle() {
        return gpostTitle;
    }
}