package com.mrmi.eventbuddy;

public class Event{

    private final String eventTitle;
    private final String eventDescription;
    private final String eventAuthor;
    private final String eventDate;
    private final String eventType;
    private final String eventTime;
    private final long eventId;



    public Event(String eventTitle, String eventDescription, String eventAuthor, String eventDate, String eventType, String eventTime) {
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.eventAuthor = eventAuthor;
        this.eventDate = eventDate;
        this.eventType = eventType;
        this.eventTime = eventTime;
        this.eventId = System.currentTimeMillis();
    }

    public long getEventId() {
        return eventId;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getEventAuthor() {
        return eventAuthor;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventTime() {return eventTime;}
    public String getEventTitle() {
        return eventTitle;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventTitle='" + eventTitle + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", eventAuthor='" + eventAuthor + '\'' +
                ", eventDate='" + eventDate + '\'' +
                ", eventTime='" + eventTime + '\'' +
                ", eventType='" + eventType + '\'' +
                ", eventId=" + eventId +
                '}';
    }
}
