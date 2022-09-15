package com.mrmi.eventbuddy;

class Event{

    private final String title;
    private final String description;
    private final String type;
    private final String date;
    private final String time;
    private final String author;
    private final String id;
    private final String address;
    private final String city;
    private final String interestedCount;
    private final String goingCount;

    //Counts and the id are also stored as strings because the realtime database parser doesn't auto convert strings to ints
    public Event(String title, String description, String type, String date, String time, String author, String address, String city) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.date = date;
        this.time = time;
        this.author = author;
        this.id = String.valueOf(System.currentTimeMillis());
        this.address = address;
        this.city = city;
        this.interestedCount = "0";
        this.goingCount = "0";
    }

    //Firebase's realtime database requires a no-argument constructor
    public Event() {
        this.title = "";
        this.description = "";
        this.type = "";
        this.date = "";
        this.time = "";
        this.author = "";
        this.id = String.valueOf(System.currentTimeMillis());
        this.address = "";
        this.city = "";
        this.interestedCount = "0";
        this.goingCount = "0";
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getAuthor() {
        return author;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getInterestedCount() {
        return interestedCount;
    }

    public String getGoingCount() {
        return goingCount;
    }
}
