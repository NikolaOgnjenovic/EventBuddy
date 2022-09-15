package com.mrmi.eventbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView eventListView;
    private Button createEventButton;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EventListViewAdapter eventAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseViews();
        initialiseListeners();
        initialiseAdapters();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadDatabase();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void initialiseViews() {
        eventListView = findViewById(R.id.eventListView);
        createEventButton = findViewById(R.id.createEventButton);
        swipeRefreshLayout = findViewById(R.id.refreshLayout);
        searchView = findViewById(R.id.searchView);
    }

    private void initialiseAdapters() {
        eventAdapter = new EventListViewAdapter(this, 0, EventDatabase.eventList);

        eventListView.setAdapter(eventAdapter);
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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<Event> filteredEvents = new ArrayList<>();
                for(Event event : EventDatabase.eventList) {
                    if(event.getTitle().toLowerCase().contains(s.toLowerCase())) {
                        filteredEvents.add(event);
                    }
                }

                EventListViewAdapter filteredAdapter = new EventListViewAdapter(getApplicationContext(), 0, filteredEvents);
                eventListView.setAdapter(filteredAdapter);
                return false;
            }
        });
    }

    private void loadDatabase() {
        EventDatabase.loadEventList(() -> eventAdapter.notifyDataSetChanged());
    }
}