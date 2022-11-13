package com.mrmi.eventbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView eventListView;
    private Button createEventButton;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EventListViewAdapter eventAdapter;
    private SearchView searchView;
    private Spinner eventTypeSpinner;

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
        eventTypeSpinner = findViewById(R.id.eventTypeSpinner);
        initialiseSpinner();
    }

    private void initialiseAdapters() {
        eventAdapter = new EventListViewAdapter(this, 0, EventDatabase.eventList);

        eventListView.setAdapter(eventAdapter);
    }

    private void initialiseSpinner() {
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.eventFilters,android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventTypeSpinner.setAdapter(spinnerAdapter);
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
                filterAdapter(s, "Title");
                return false;
            }
        });

        eventTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String eventType = adapterView.getItemAtPosition(i).toString();
                filterAdapter(eventType, "Type");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });
    }

    private void filterAdapter(String filter, String comparisonType) {
        List<Event> filteredEvents = new ArrayList<>();

        if(comparisonType.equals("Type")) {
            filteredEvents = filterEventArrayByType(filter);
        } else if(comparisonType.equals("Title")) {
            filteredEvents = filterEventArrayByTitle(filter);
        }

        EventListViewAdapter filteredAdapter = new EventListViewAdapter(getApplicationContext(), 0, filteredEvents);
        eventListView.setAdapter(filteredAdapter);
    }

    private List<Event> filterEventArrayByTitle(String filter) {
        List<Event> filteredEvents = new ArrayList<>();
        for(Event event : EventDatabase.eventList) {
            if(event.getTitle().toLowerCase().contains(filter.toLowerCase())) {
                filteredEvents.add(event);
            }
        }

        return filteredEvents;
    }

    private List<Event> filterEventArrayByType(String filter) {
        List<Event> filteredEvents = new ArrayList<>();

        if(filter.equals("All")) {
            System.out.println("[MRMI]: ALL FILTERED");
            return EventDatabase.eventList;
        }
        for(Event event : EventDatabase.eventList) {
            if(event.getType().equals(filter)){
                filteredEvents.add(event);
            }
        }

        return filteredEvents;
    }
    private void loadDatabase() {
        EventDatabase.loadEventList(() -> eventAdapter.notifyDataSetChanged());
    }
}