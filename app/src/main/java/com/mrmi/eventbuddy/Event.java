package com.mrmi.eventbuddy;

public class Event{

    private String eventTitle;
    private String eventDescription;
    private String eventAuthor;
    private String eventDate;
    private String eventType;
    public static int counter = 0;
    private final int eventId;

    public Event(String eventTitle, String eventDescription, String eventAuthor, String eventDate, String eventType) {
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.eventAuthor = eventAuthor;
        this.eventDate = eventDate;
        this.eventType = eventType;
        this.eventId = counter++;
    }

    public int getEventId() {
        return eventId;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventAuthor() {
        return eventAuthor;
    }

    public void setEventAuthor(String eventAuthor) {
        this.eventAuthor = eventAuthor;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String geteventTitle() {
        return eventTitle;
    }

    public void seteventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }
}
