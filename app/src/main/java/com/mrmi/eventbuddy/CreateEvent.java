package com.mrmi.eventbuddy;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateEvent extends AppCompatActivity {

    private Button createEventButton, backButton, timeButton, dateButton;
    private EditText titleEditText, descriptionEditText, authorEditText, addressEditText;
    private String time = "";
    private String date = "";
    private UserDatabase userDatabase;
    private Toast toast;
    private Spinner eventTypeSpinner;
    private final List<String> cities = new ArrayList<>();
    private AutoCompleteTextView cityText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        initialiseViews();
        initialiseListeners();
        initialiseAdapters();

        userDatabase = new UserDatabase(this);

        try {
            //JSONArray: [{JSONObject}, {JSONObject},...]
            JSONArray database = new JSONArray(readJSONFromAsset());
            int databaseLength = database.length();
            for(int i=0; i < databaseLength; ++i) {
                JSONObject entry = database.getJSONObject(i);
                    cities.add(entry.getString("name"));
            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }

    //CITIES OF THE WORLD HAVE BEEN TAKEN FROM https://datahub.io/core/world-cities#data-cli
    //The credit goes to https://github.com/lexman and https://okfn.org/
    //A huge thank you to them for allowing others to freely use this valuable data
    private String readJSONFromAsset() {
        String json;
        try {
            InputStream is = getAssets().open("cities.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                json = new String(buffer, StandardCharsets.UTF_8);
            } else {
                json = new String(buffer);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void initialiseViews() {
        createEventButton = findViewById(R.id.createEventButton);
        backButton = findViewById(R.id.backButton);
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        eventTypeSpinner = findViewById(R.id.eventTypeSpinner);
        timeButton = findViewById(R.id.timeButton);
        dateButton = findViewById(R.id.dateButton);
        authorEditText = findViewById(R.id.authorEditText);
        cityText = findViewById(R.id.cityEditText);
        addressEditText = findViewById(R.id.addressEditText);
    }

    private void initialiseAdapters() {
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.eventTypes,android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventTypeSpinner.setAdapter(spinnerAdapter);

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cities);

        cityText.setAdapter(cityAdapter);
    }

    private void initialiseListeners() {
        createEventButton.setOnClickListener(v -> {
            //Get event data: the title, description, type and author
            String title = titleEditText.getText().toString(),
                    description = descriptionEditText.getText().toString(),
                    type = eventTypeSpinner.getSelectedItem().toString(),
                    author = authorEditText.getText().toString(),
                    city = cityText.getText().toString(),
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
                EventDatabase.addEvent(event);
                userDatabase.addCreatedEventID(event.getId());
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