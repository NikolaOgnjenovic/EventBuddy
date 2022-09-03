package com.mrmi.eventbuddy;

import android.content.Context;

import java.util.Set;

public class UserDatabase {
    private final Preferences preferences;

    public UserDatabase(Context context) {
        preferences = new Preferences(context);
    }

    public Set<String> getInterestedEventIDs() {
        return preferences.getStringSet("interestedEventIDs");
    }

    public Set<String> getGoingEventIDs() {
        return preferences.getStringSet("goingEventIDs");

    }

    public Set<String> getCreatedEventIDs() {
        return preferences.getStringSet("createdEventIDs");
    }

    public void addInterestedEventID(String eventID) {
       preferences.addStringToSet("interestedEventIDs", eventID);
    }

    public void addGoingEventID(String eventID) {
        preferences.addStringToSet("goingEventIDs", eventID);
    }

    public void addCreatedEventID(String eventID) {
        preferences.addStringToSet("createdEventIDs", eventID);
    }

    public void removeInterestedEventID(String eventID) {
        preferences.removeStringFromSet("interestedEventIDs", eventID);
    }

    public void removeGoingEventID(String eventID) {
        preferences.removeStringFromSet("goingEventIDs", eventID);
    }

    public void removeCreatedEventID(String eventID) {
        preferences.removeStringFromSet("createdEventIDs", eventID);
    }

    public void removeEvent(String eventID) {
        removeInterestedEventID(eventID);
        removeGoingEventID(eventID);
        removeCreatedEventID(eventID);
    }

    //Returns true if the current user has created the event with the id eventID
    public boolean userCreatedEvent(String eventID) {
        return getCreatedEventIDs().contains(eventID);
    }

    public void updateEventChildCount(String eventID, String eventChildName, boolean shouldAddEvent) {
        if(shouldAddEvent) {
            if(eventChildName.equals("interestedCount")) {
                addInterestedEventID(eventID);
            } else {
                addGoingEventID(eventID);
            }
        } else {
            if(eventChildName.equals("interestedCount")) {
                removeInterestedEventID(eventID);
            } else {
                removeGoingEventID(eventID);
            }
        }
    }
}
