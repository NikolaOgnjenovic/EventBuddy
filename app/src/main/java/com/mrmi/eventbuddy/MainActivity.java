package com.mrmi.eventbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {

    private ListView eventListView;
    private Button createEventButton;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EventListViewAdapter eventAdapter;

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
    }

    private void initialiseAdapters() {
        eventAdapter = new EventListViewAdapter(this, EventDatabase.eventList);

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
    }

    private void loadDatabase() {
        EventDatabase.loadEventList(() -> eventAdapter.notifyDataSetChanged());
    }
}