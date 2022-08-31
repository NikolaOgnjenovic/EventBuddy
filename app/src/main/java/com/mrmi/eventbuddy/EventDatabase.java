package com.mrmi.eventbuddy;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventDatabase {

    private DatabaseReference databaseReference;

    public void addEvent(Event event) {
        initialiseDatabase();
        HashMap<String, String> eventMap = new HashMap<>();
        eventMap.put("eventTitle", event.getEventTitle());
        eventMap.put("eventDescription", event.getEventDescription());
        eventMap.put("eventType", event.getEventType());
        eventMap.put("eventDate", event.getEventDate());
        eventMap.put("eventTime", event.getEventTime());
        eventMap.put("eventAuthor", event.getEventAuthor());
        eventMap.put("eventId", String.valueOf(event.getEventId()));
        eventMap.put("eventAddress", event.getEventAddress());
        eventMap.put("eventCity", event.getEventCity());

        databaseReference.push().setValue(eventMap);

        System.out.println("Added event to database");
    }

    public List<Event> getEventList() {
        List<Event> eventList = new ArrayList<>();

        initialiseDatabase();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    eventList.add(dataSnapshot1.getValue(Event.class));
                    System.out.println("Read event and added to event list");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return eventList;
    }

    private void initialiseDatabase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://eventbuddy-bacb3-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference().child("Events");
    }
}
