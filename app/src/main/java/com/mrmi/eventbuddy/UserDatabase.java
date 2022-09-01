package com.mrmi.eventbuddy;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class UserDatabase {

    private Set<String> interestedEventIDs, goingEventIDs, createdEventIDs;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    public UserDatabase(Context context) {
        initialise(context);
    }

    public void initialise(Context context) {
        sharedPreferences = context.getSharedPreferences("eventBuddySharedPreferences", Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
    }

    public Set<String> getInterestedEventIDs() {
        return sharedPreferences.getStringSet("interestedEventIDs", new HashSet<>());
    }

    public Set<String> getGoingEventIDs() {
        return sharedPreferences.getStringSet("goingEventIDs", new HashSet<>());

    }

    public Set<String> getCreatedEventIDs() {
        return sharedPreferences.getStringSet("createdEventIDs", new HashSet<>());
    }

    public void addInterestedEventID(String eventID) {
        interestedEventIDs = getInterestedEventIDs();
        interestedEventIDs.add(eventID);
        sharedPreferencesEditor.putStringSet("interestedEventIDs", interestedEventIDs).apply();
    }

    public void addGoingEventID(String eventID) {
        goingEventIDs = getInterestedEventIDs();
        goingEventIDs.add(eventID);
        sharedPreferencesEditor.putStringSet("goingEventIDs", goingEventIDs).apply();
    }

    public void addCreatedEventID(String eventID) {
        createdEventIDs = getInterestedEventIDs();
        createdEventIDs.add(eventID);
        sharedPreferencesEditor.putStringSet("createdEventIDs", createdEventIDs).apply();
    }

    public void removeInterestedEventID(String eventID) {
        interestedEventIDs = getInterestedEventIDs();
        interestedEventIDs.remove(eventID);
        sharedPreferencesEditor.putStringSet("interestedEventIDs", interestedEventIDs).apply();
    }

    public void removeGoingEventID(String eventID) {
        goingEventIDs = getInterestedEventIDs();
        goingEventIDs.remove(eventID);
        sharedPreferencesEditor.putStringSet("goingEventIDs", goingEventIDs).apply();
    }

    public void removeCreatedEventID(String eventID) {
        createdEventIDs = getInterestedEventIDs();
        createdEventIDs.remove(eventID);
        sharedPreferencesEditor.putStringSet("createdEventIDs", createdEventIDs).apply();
    }
}
