package com.mrmi.eventbuddy;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 2000);

        setDatabaseLink();
        EventDatabase.loadEventList(() -> System.out.println("[MRMI]: Loaded event list"));
    }

    private void setDatabaseLink() {
        String databaseLink = "";
        try {
            databaseLink = getApplicationContext().getPackageManager().getApplicationInfo(getApplicationContext().getPackageName(), PackageManager.GET_META_DATA).metaData.getString("firebaseLink");
        } catch(PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        EventDatabase.databaseLink = databaseLink;
    }
}