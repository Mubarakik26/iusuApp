package com.example.iusuapp.models;

public class Announcement {


    private int announcementId;
    private String title;
    private String message;
    private String date;
    private String go_id;
    private String author;

    public Announcement(int announcementId, String title, String message, String date, String go_id,String author) {
        this.announcementId = announcementId;
        this.title = title;
        this.message = message;
        this.date = date;
        this.go_id = go_id;
        this.author = author;

    }


    public int getAnnouncementId() {
        return announcementId;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }



    public String getGo_id() {
        return go_id;
    }

    public String getAuthor() {
        return author;
    }

}
