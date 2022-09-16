package com.example.iusuapp.models;

public class Complaint {
    private int complaintId;
    private String subject;
    private String message;
    private String date;
    private String regNo;
    private String firstName;
    private String lastName;
    private String guildPostId;
    private String gpTitle;


    public Complaint(int complaintId, String subject, String message, String date, String regNo, String firstName, String lastName, String guildPostId, String gpTitle) {
        this.complaintId = complaintId;
        this.subject = subject;
        this.message = message;
        this.date = date;
        this.regNo = regNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.guildPostId = guildPostId;
        this.gpTitle = gpTitle;
    }


    public int getComplaintId() {
        return complaintId;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getRegNo() {
        return regNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGuildPostId() {
        return guildPostId;
    }

    public String getGpTitle() {
        return gpTitle;
    }
}
