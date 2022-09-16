package com.example.iusuapp.models;

public class Events {

    private int id;
    private String image;
    private String title;
    private String description;
    private String date;
    private String venue;
    private String time;
    private String goId;
    private String gpostTitle;

    public Events(int id, String image, String title, String description, String date, String venue, String time, String goId, String gpostTitle) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.description = description;
        this.date = date;
        this.venue = venue;
        this.time = time;
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

    public String getVenue() {
        return venue;
    }

    public String getTime() {
        return time;
    }

    public String getGoId() {
        return goId;
    }

    public String getGpostTitle() {
        return gpostTitle;
    }
}