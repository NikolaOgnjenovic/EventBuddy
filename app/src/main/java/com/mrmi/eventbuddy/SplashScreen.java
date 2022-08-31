package com.mrmi.eventbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //The first data request from firebase always fails for some reason so we're retrieving the list on the splash screen first
        EventDatabase eventDatabase = new EventDatabase();
        List<Event> eventList = eventDatabase.getEventList();

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 3 * 1000);
    }
}