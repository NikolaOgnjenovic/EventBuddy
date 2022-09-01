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

    private Button createEventButton, backButton, timeButton, dateButton;
    private EditText titleEditText, descriptionEditText, authorEditText, typeEditText, addressEditText, cityEditText;
    private String time = "";
    private String date = "";
    private EventDatabase eventDatabase;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        initialiseViews();
        initialiseListeners();

        eventDatabase = new EventDatabase(this);
    }

    private void initialiseViews() {
        createEventButton = findViewById(R.id.createEventButton);
        backButton = findViewById(R.id.backButton);
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        typeEditText = findViewById(R.id.typeEditText);
        timeButton = findViewById(R.id.timeButton);
        dateButton = findViewById(R.id.dateButton);
        authorEditText = findViewById(R.id.authorEditText);
        cityEditText = findViewById(R.id.cityEditText);
        addressEditText = findViewById(R.id.addressEditText);
    }

    private void initialiseListeners() {
        createEventButton.setOnClickListener(v -> {
            //Get event data: the title, description, type and author
            String title = titleEditText.getText().toString(),
                    description = descriptionEditText.getText().toString(),
                    type = typeEditText.getText().toString(),
                    author = authorEditText.getText().toString(),
                    city = cityEditText.getText().toString(),
                    address = addressEditText.getText().toString();

            //If the author input field is empty, set the author value to "anonymous author"
            if (author.equals("")) {
                author = getString(R.string.anonymous_author);
            }

            //Show toasts which inform the user that they have data they need to input
            if (title.equals("")) {
                showToast(getString(R.string.eventTitleToast));
            } else if (description.equals("")) {
                showToast(getString(R.string.eventDescriptionToast));
            } else if (type.equals("")) {
                showToast(getString(R.string.eventTypeToast));
            } else if (date.equals("")) {
                showToast(getString(R.string.eventDateToast));
            } else if (time.equals("")) {
                showToast(getString(R.string.eventTimeToast));
            } else if (city.equals("")) {
                showToast(getString(R.string.eventCityToast));
            } else {
                Event event = new Event(title, description, type, date, time, author, address, city);
                eventDatabase.addEvent(event);
            }
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        //Display a time picker which sets the event time string's value
        timeButton.setOnClickListener(v -> {
            Calendar currentTime = Calendar.getInstance();
            int hour = currentTime.get(Calendar.HOUR_OF_DAY);
            int minute = currentTime.get(Calendar.MINUTE);

            TimePickerDialog timePicker;
            timePicker = new TimePickerDialog(this, (timePicker1, selectedHour, selectedMinute) -> time = (selectedHour + ":" + selectedMinute), hour, minute, true);
            timePicker.setTitle(getResources().getString(R.string.select_time));
            timePicker.show();
        });

        //Display a date picker which sets the event date string's value
        dateButton.setOnClickListener(v -> {
            final Calendar newCalendar = Calendar.getInstance();
            DatePickerDialog datePicker = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                date = DateFormat.getDateInstance(DateFormat.MEDIUM).format(newDate.getTime());
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