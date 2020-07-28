package com.example.curriculum;

public class Course {
    private String time;
    private String course;
    private String address;

    public Course(String time, String course, String address) {
        this.time = time;
        this.course = course;
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public String getCourse() {
        return course;
    }

    public String getAddress() {
        return address;
    }

}
