package com.mrmi.eventbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView eventListView;
    private List<Event> eventList;
    private Button createEventButton;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseViews();
        initialiseListeners();
        loadDatabase();
    }

    private void initialiseViews() {
        eventListView = findViewById(R.id.eventListView);
        createEventButton = findViewById(R.id.createEventButton);
        swipeRefreshLayout = findViewById(R.id.refreshLayout);
    }

    private void initialiseAdapters() {
        eventListView.setAdapter(new EventListViewAdapter(this, eventList));
    }

    private void initialiseListeners() {
        createEventButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateEvent.class);
            startActivity(intent);
        });

        swipeRefreshLayout.setOnRefreshListener(
                () -> {
                    loadDatabase();
                    //Explicitly refreshes only once
                    swipeRefreshLayout.setRefreshing(false);
                }
        );
    }

    private void loadDatabase() {
        EventDatabase eventDatabase = new EventDatabase();
        eventList = eventDatabase.getEventList();
        //initialiseAdapters();
        new Handler().postDelayed(this::initialiseAdapters, 100);
    }
}