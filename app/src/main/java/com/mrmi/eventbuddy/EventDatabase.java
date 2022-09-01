package com.mrmi.eventbuddy;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class EventDatabase {

    private DatabaseReference databaseReference;
    private UserDatabase userDatabase;
    private final Context context;

    public EventDatabase(Context c)
    {
        context = c;
        initialiseDatabase();
    }

    public void addEvent(Event event) {
        HashMap<String, String> eventMap = new HashMap<>();
        eventMap.put("title", event.getTitle());
        eventMap.put("description", event.getDescription());
        eventMap.put("type", event.getType());
        eventMap.put("date", event.getDate());
        eventMap.put("time", event.getTime());
        eventMap.put("author", event.getAuthor());
        eventMap.put("id", event.getId());
        eventMap.put("address", event.getAddress());
        eventMap.put("city", event.getCity());
        eventMap.put("interestedCount", event.getInterestedCount());
        eventMap.put("goingCount", event.getGoingCount());

        databaseReference.child(event.getId()).setValue(eventMap);
        //databaseReference.push().setValue(eventMap);
        System.out.println("Added event to database");
    }

    public List<Event> getEventList() {
        List<Event> eventList = new ArrayList<>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    eventList.add(dataSnapshot1.getValue(Event.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return eventList;
    }

    //Change count of an event's property (input event ID, the name of the property - eventInterstedCount/eventGoingCount and the amount (+1 or -1)
    public void changeEventPropertyCount(String eventID, String eventPropertyName, boolean increaseValue) {
        DatabaseReference eventReference = databaseReference.child(eventID).child(eventPropertyName);

        changeUserDatabase(eventID, eventPropertyName, increaseValue);
        int amount = (increaseValue) ? 1 : -1;
        eventReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventReference.setValue(String.valueOf(Integer.parseInt(Objects.requireNonNull(dataSnapshot.getValue(String.class))) + amount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void changeUserDatabase(String eventID, String eventPropertyName, boolean increaseValue) {
        if(increaseValue) {
            if(eventPropertyName.equals("interestedCount")) {
                userDatabase.addInterestedEventID(eventID);
            } else {
                userDatabase.addGoingEventID(eventID);
            }
        } else {
            if(eventPropertyName.equals("interestedCount")) {
                userDatabase.removeInterestedEventID(eventID);
            } else {
                userDatabase.removeGoingEventID(eventID);
            }
        }
    }

    private void initialiseDatabase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://eventbuddy-bacb3-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference().child("Events");
        userDatabase = new UserDatabase(context);
    }
}
