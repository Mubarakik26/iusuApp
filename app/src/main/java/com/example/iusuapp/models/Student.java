package com.example.iusuapp.models;

public class Student {
    private String regNo;
    private String firstName;
    private String lastName;
    private String profileImage;
    private String gender;
    private String campus;
    private String phone;
    private String email;
    private String guildOfficialId;
    private String academicYear;
    private String guildTitle;
    private String guildDescription;

    public Student(String regNo, String firstName, String lastName, String profileImage, String gender, String campus, String phone, String email, String guildOfficialId, String academicYear, String guildTitle, String guildDescription) {
        this.regNo = regNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileImage = profileImage;
        this.gender = gender;
        this.campus = campus;
        this.phone = phone;
        this.email = email;
        this.guildOfficialId = guildOfficialId;
        this.academicYear = academicYear;
        this.guildTitle = guildTitle;
        this.guildDescription = guildDescription;
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

    public String getProfileImage() {
        return profileImage;
    }

    public String getGender() {
        return gender;
    }

    public String getCampus() {
        return campus;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getGuildOfficialId() {
        return guildOfficialId;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public String getGuildTitle() {
        return guildTitle;
    }

    public String getGuildDescription() {
        return guildDescription;
    }
}
