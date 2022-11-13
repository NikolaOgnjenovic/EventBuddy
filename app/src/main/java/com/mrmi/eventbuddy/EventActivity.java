package com.mrmi.eventbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EventActivity extends AppCompatActivity {

    private Button backButton, interestedButton, goingButton, deleteButton;
    private UserDatabase userDatabase;
    private String eventID;
    private TextView goingCount, interestedCount;
    private int goingCounter, interestedCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

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
        interestedCount = findViewById(R.id.interestedCount);
        interestedCounter = Integer.parseInt(getIntent().getStringExtra("interestedCount"));
        String interestedText = getString(R.string.interested) + " " + interestedCounter;/* getIntent().getStringExtra("interestedCount"*/
        interestedCount.setText(interestedText);
        goingCount = findViewById(R.id.goingCount);
        goingCounter = Integer.parseInt(getIntent().getStringExtra("goingCount"));
        String goingText = getString(R.string.going) + " " + goingCounter;
        goingCount.setText(goingText);

        backButton = findViewById(R.id.backButton);
        interestedButton = findViewById(R.id.interestedButton);
        goingButton = findViewById(R.id.goingButton);
        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setVisibility(View.INVISIBLE);

        if(userDatabase.userCreatedEvent(eventID)) {
            deleteButton.setVisibility(View.VISIBLE);
        }
    }

    private void initialiseListeners() {
        backButton.setOnClickListener(v -> {
             Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        interestedButton.setOnClickListener(v -> {
            //Change the count of the event's interested users in the database
            EventDatabase.updateEventChildCount(eventID, "interestedCount", !interestedInEvent(eventID));
            userDatabase.updateEventChildCount(eventID, "interestedCount", !interestedInEvent(eventID));

            //If the user is now interested in the event, increment the counter. Otherwise decrease it
            if(interestedInEvent(eventID)) {
                ++interestedCounter;
            } else {
                --interestedCounter;
            }
            //Display the refreshed counter. Cheeky workaround instead of accessing the database twice each click
            String interestedText = getString(R.string.interested) + " " + interestedCounter;
            interestedCount.setText(interestedText);
        });
        goingButton.setOnClickListener(v -> {
            //Change the count of the event's going users in the database
            EventDatabase.updateEventChildCount(eventID, "goingCount", !goingToEvent(eventID));
            userDatabase.updateEventChildCount(eventID, "goingCount", !goingToEvent(eventID));

            //If the user is now interested in the event, increment the counter. Otherwise decrease it
            if(goingToEvent(eventID)) {
                ++goingCounter;
            } else {
                --goingCounter;
            }
            //Display the refreshed counter. Cheeky workaround instead of accessing the database twice each click
            String goingText = getString(R.string.going) + " " + goingCounter;
            goingCount.setText(goingText);
        });

        if(userDatabase.userCreatedEvent(eventID)) {
            deleteButton.setOnClickListener(v -> {
                EventDatabase.removeEvent(eventID);
                userDatabase.removeEvent(eventID);
                startActivity(new Intent(EventActivity.this, MainActivity.class));
            });
        }
    }

    //Returns true if the event's ID IS contained in the interestedEventIDs set
    private boolean interestedInEvent(String eventID) {
        return userDatabase.getInterestedEventIDs().contains(eventID);
    }

    //Returns true if the event's ID IS contained in the goingEventIDs set
    private boolean goingToEvent(String eventID) {
        return userDatabase.getGoingEventIDs().contains(eventID);
    }
}