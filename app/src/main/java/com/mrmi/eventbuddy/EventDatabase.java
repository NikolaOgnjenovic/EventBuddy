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
import java.util.Objects;

public class EventDatabase {

    private static DatabaseReference databaseReference;
    public static final List<Event> eventList = new ArrayList<>();
    public static String databaseLink;

    public static void addEvent(Event event) {
        System.out.println("[MRMI]: EVENT DATABASE: ADDING EVENT");

        initialiseDatabase();

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
    }

    public static void loadEventList(DatabaseCallback databaseCallback) {
        System.out.println("[MRMI]: EVENT DATABASE: LOADING EVENT LIST");

        initialiseDatabase();

        eventList.clear();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    eventList.add(dataSnapshot1.getValue(Event.class));
                    databaseCallback.onCallback();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    //Change count of an event's property (input event ID, the name of the property - eventInterstedCount/eventGoingCount and the amount (+1 or -1)
    public static void updateEventChildCount(String eventID, String eventChildName, boolean shouldIncrement) {
        System.out.println("[MRMI]: EVENT DATABASE: UPDATING CHILD COUNT");

        initialiseDatabase();

        DatabaseReference eventReference = databaseReference.child(eventID).child(eventChildName);

        int amount = (shouldIncrement) ? 1 : -1;
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

    public static void removeEvent(String eventID) {
        initialiseDatabase();
        System.out.println("[MRMI]: EVENT DATABASE: REMOVING CHILD");
        databaseReference.child(eventID).removeValue();
    }

    private static void initialiseDatabase() {
        if (databaseReference == null) {
            System.out.println("[MRMI]: EVENT DATABASE: INITIALISING DATABASE");
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(databaseLink);
            databaseReference = firebaseDatabase.getReference().child("Events");
            System.out.println(databaseReference);
        } else {
            System.out.println("[MRMI]: Database reference is not null");
        }
    }
}
