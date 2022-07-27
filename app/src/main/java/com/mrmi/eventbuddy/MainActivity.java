package com.mrmi.eventbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView eventListView;
    private List<Event> eventList;
    private Button createEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseViews();
        initialiseObjects();
        initialiseAdapters();
        initialiseListeners();
    }

    private void initialiseViews() {
        eventListView = findViewById(R.id.eventListView);
        createEventButton = findViewById(R.id.createEventButton);
    }

    private void initialiseObjects() {
        eventList = new ArrayList<>();
/*
        eventList.add(new Event("Title 1", "222", "3453", "4343", "431431"));
        eventList.add(new Event("Title 2", "222", "3453", "4343", "431431"));
        eventList.add(new Event("Title 3", "222", "3453", "4343", "431431"));
*/
    }

    private void initialiseAdapters() {
        eventListView.setAdapter(new EventListViewAdapter(this, eventList));
    }

    private void initialiseListeners() {
        createEventButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateEvent.class);
            startActivity(intent);
        });
    }
}