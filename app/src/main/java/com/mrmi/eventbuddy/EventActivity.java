package com.mrmi.eventbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EventActivity extends AppCompatActivity {

    private Button backButton, interestedButton, goingButton;
    private EventDatabase eventDatabase;
    private UserDatabase userDatabase;
    private String eventID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        eventDatabase = new EventDatabase(this);
        userDatabase = new UserDatabase(this);
        eventID = getIntent().getStringExtra("eventID");
        initialiseViews();
        initialiseListeners();
    }

    private void initialiseViews() {
        TextView author = findViewById(R.id.author);
        author.setText(getIntent().getStringExtra("author"));
        TextView date = findViewById(R.id.date);
        date.setText(getIntent().getStringExtra("date"));
        TextView time = findViewById(R.id.time);
        time.setText(getIntent().getStringExtra("time"));
        TextView type = findViewById(R.id.type);
        type.setText(getIntent().getStringExtra("type"));
        TextView description = findViewById(R.id.description);
        description.setText(getIntent().getStringExtra("description"));
        TextView title = findViewById(R.id.title);
        title.setText(getIntent().getStringExtra("title"));
        TextView city = findViewById(R.id.city);
        city.setText(getIntent().getStringExtra("city"));
        TextView address = findViewById(R.id.address);
        address.setText(getIntent().getStringExtra("address"));
        TextView interestedCount = findViewById(R.id.interestedCount);
        String interestedText = getString(R.string.interested) + " " + getIntent().getStringExtra("interestedCount");
        interestedCount.setText(interestedText);
        TextView goingCount = findViewById(R.id.goingCount);
        String goingText = getString(R.string.going) + " " + getIntent().getStringExtra("goingCount");
        goingCount.setText(goingText);

        backButton = findViewById(R.id.backButton);
        interestedButton = findViewById(R.id.interestedButton);
        goingButton = findViewById(R.id.goingButton);
    }

    private void initialiseListeners() {
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        interestedButton.setOnClickListener(v -> eventDatabase.changeEventPropertyCount(eventID, "interestedCount", !interestedInEvent(eventID)));
        goingButton.setOnClickListener(v -> eventDatabase.changeEventPropertyCount(eventID, "goingCount", !goingToEvent(eventID)));
    }

    private boolean interestedInEvent(String eventID) {
        return userDatabase.getInterestedEventIDs().contains(eventID);
    }

    private boolean goingToEvent(String eventID) {
        return userDatabase.getGoingEventIDs().contains(eventID);
    }
}