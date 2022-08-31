package com.mrmi.eventbuddy;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Calendar;

public class CreateEvent extends AppCompatActivity {

    private Button createEventButton, backButton, eventTimeButton, eventDateButton;
    private EditText eventTitleEditText, eventDescriptionEditText, eventAuthorEditText, eventTypeEditText, eventAddressEditText, eventCityEditText;
    private String eventTime = "";
    private String eventDate = "";
    private EventDatabase eventDatabase;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        initialiseViews();
        initialiseListeners();

        eventDatabase = new EventDatabase();
    }

    private void initialiseViews() {
        createEventButton = findViewById(R.id.createEventButton);
        backButton = findViewById(R.id.backButton);
        eventTitleEditText = findViewById(R.id.eventTitleEditText);
        eventDescriptionEditText = findViewById(R.id.eventDescriptionEditText);
        eventTypeEditText = findViewById(R.id.eventTypeEditText);
        eventTimeButton = findViewById(R.id.eventTimeButton);
        eventDateButton = findViewById(R.id.eventDateButton);
        eventAuthorEditText = findViewById(R.id.eventAuthorEditText);
        eventCityEditText = findViewById(R.id.eventCityEditText);
        eventAddressEditText = findViewById(R.id.eventAddressEditText);
    }

    private void initialiseListeners() {
        createEventButton.setOnClickListener(v -> {
            //Get event data: the title, description, type and author
            String eventTitle = eventTitleEditText.getText().toString(),
                    eventDescription = eventDescriptionEditText.getText().toString(),
                    eventType = eventTypeEditText.getText().toString(),
                    eventAuthor = eventAuthorEditText.getText().toString(),
                    eventCity = eventCityEditText.getText().toString(),
                    eventAddress = eventAddressEditText.getText().toString();

            //If the author input field is empty, set the author value to "anonymous author"
            if (eventAuthor.equals("")) {
                eventAuthor = getString(R.string.anonymous_author);
            }

            //Show toasts which inform the user that they have data they need to input
            if (eventTitle.equals("")) {
                showToast(getString(R.string.eventTitleToast));
            } else if (eventDescription.equals("")) {
                showToast(getString(R.string.eventDescriptionToast));
            } else if (eventType.equals("")) {
                showToast(getString(R.string.eventTypeToast));
            } else if (eventDate.equals("")) {
                showToast(getString(R.string.eventDateToast));
            } else if (eventTime.equals("")) {
                showToast(getString(R.string.eventTimeToast));
            } else if (eventCity.equals("")) {
                showToast(getString(R.string.eventCityToast));
            } else {
                Event event = new Event(eventTitle, eventDescription, eventType, eventDate, eventTime, eventAuthor, eventAddress, eventCity);
                eventDatabase.addEvent(event);
            }
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        //Display a time picker which sets the event time string's value
        eventTimeButton.setOnClickListener(v -> {
            Calendar currentTime = Calendar.getInstance();
            int hour = currentTime.get(Calendar.HOUR_OF_DAY);
            int minute = currentTime.get(Calendar.MINUTE);

            TimePickerDialog timePicker;
            timePicker = new TimePickerDialog(this, (timePicker1, selectedHour, selectedMinute) -> eventTime = (selectedHour + ":" + selectedMinute), hour, minute, true);
            timePicker.setTitle(getResources().getString(R.string.select_time));
            timePicker.show();
        });

        //Display a date picker which sets the event date string's value
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

    //Shows a toast using a toast variable in order to prevent toast spamming
    public void showToast(String toastMessage) {
        if(toast!=null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT);
        toast.show();
    }
}