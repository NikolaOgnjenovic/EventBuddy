package com.mrmi.eventbuddy;

public class Event {

    private final String eventTitle;
    private final String eventDescription;
    private final String eventType;
    private final String eventDate;
    private final String eventTime;
    private final String eventAuthor;
    private final String eventId;
    private final String eventAddress;
    private final String eventCity;

    public Event(String eventTitle, String eventDescription, String eventType, String eventDate, String eventTime, String eventAuthor, String eventAddress, String eventCity) {
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.eventType = eventType;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventAuthor = eventAuthor;
        this.eventId = String.valueOf(System.currentTimeMillis());
        this.eventAddress = eventAddress;
        this.eventCity = eventCity;
    }

    public Event() {
        this.eventTitle = "";
        this.eventDescription = "";
        this.eventType = "";
        this.eventDate = "";
        this.eventTime = "";
        this.eventAuthor = "";
        this.eventId = String.valueOf(System.currentTimeMillis());
        this.eventAddress = "";
        this.eventCity = "";
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public String getEventAuthor() {
        return eventAuthor;
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventAddress() {return eventAddress;}
    public String getEventCity() {return eventCity;}
}
