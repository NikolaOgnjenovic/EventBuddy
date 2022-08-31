package com.mrmi.eventbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EventActivity extends AppCompatActivity {

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        initialiseViews();
        initialiseListeners();
    }

    private void initialiseViews() {
        TextView eventAuthor = findViewById(R.id.eventAuthor);
        eventAuthor.setText(getIntent().getStringExtra("Event author"));
        TextView eventDate = findViewById(R.id.eventDate);
        eventDate.setText(getIntent().getStringExtra("Event date"));
        TextView eventTime = findViewById(R.id.eventTime);
        eventTime.setText(getIntent().getStringExtra("Event time"));
        TextView eventType = findViewById(R.id.eventType);
        eventType.setText(getIntent().getStringExtra("Event type"));
        TextView eventDescription = findViewById(R.id.eventDescription);
        eventDescription.setText(getIntent().getStringExtra("Event description"));
        TextView eventTitle = findViewById(R.id.eventTitle);
        eventTitle.setText(getIntent().getStringExtra("Event title"));
        TextView eventCity = findViewById(R.id.eventCity);
        eventCity.setText(getIntent().getStringExtra("Event city"));
        TextView eventAddress = findViewById(R.id.eventAddress);
        eventAddress.setText(getIntent().getStringExtra("Event address"));

        backButton = findViewById(R.id.backButton);
    }

    private void initialiseListeners() {
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}