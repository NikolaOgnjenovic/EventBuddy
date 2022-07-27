package com.mrmi.eventbuddy;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Calendar;

public class CreateEvent extends AppCompatActivity {

    private Button createEventButton, backButton, eventTimeButton, eventDateButton;
    private EditText eventTitleEditText, eventDescriptionEditText, eventAuthorEditText, eventTypeEditText;
    private String eventTime, eventDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        initialiseViews();
        initialiseListeners();
    }

    private void initialiseViews() {
        createEventButton = findViewById(R.id.createEventButton);
        backButton = findViewById(R.id.backButton);
        eventTitleEditText = findViewById(R.id.eventTitleEditText);
        eventDescriptionEditText = findViewById(R.id.eventDescriptionEditText);
        eventAuthorEditText = findViewById(R.id.eventAuthorEditText);
        eventTypeEditText = findViewById(R.id.eventTypeEditText);
        eventTimeButton = findViewById(R.id.eventTimeButton);
        eventDateButton = findViewById(R.id.eventDateButton);
    }

    private void initialiseListeners() {
        createEventButton.setOnClickListener(v -> {
            //Test
            Event event = new Event(eventTitleEditText.getText().toString(),
                    eventDescriptionEditText.getText().toString(),
                    eventAuthorEditText.getText().toString(),
                    eventDate,
                    eventTypeEditText.getText().toString(),
                    eventTime
            );

            System.out.println(event);
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        eventTimeButton.setOnClickListener(v -> {
            Calendar currentTime = Calendar.getInstance();
            int hour = currentTime.get(Calendar.HOUR_OF_DAY);
            int minute = currentTime.get(Calendar.MINUTE);

            TimePickerDialog timePicker;
            timePicker = new TimePickerDialog(this, (timePicker1, selectedHour, selectedMinute) -> eventTime = (selectedHour + ":" + selectedMinute), hour, minute, true);
            timePicker.setTitle(getResources().getString(R.string.select_time));
            timePicker.show();
        });

        eventTimeButton.setOnClickListener(v -> {
            Calendar currentTime = Calendar.getInstance();
            int hour = currentTime.get(Calendar.HOUR_OF_DAY);
            int minute = currentTime.get(Calendar.MINUTE);

            TimePickerDialog timePicker = new TimePickerDialog(this, TimePickerDialog.THEME_HOLO_LIGHT, (timePicker1, selectedHour, selectedMinute) -> eventTime = (selectedHour + ":" + selectedMinute), hour, minute, true);
            timePicker.setTitle(getResources().getString(R.string.select_time));
            timePicker.show();
        });

        eventDateButton.setOnClickListener(v -> {
            final Calendar newCalendar = Calendar.getInstance();
            DatePickerDialog datePicker = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                eventDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(newDate.getTime());
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

            datePicker.show();
        });
    }
}