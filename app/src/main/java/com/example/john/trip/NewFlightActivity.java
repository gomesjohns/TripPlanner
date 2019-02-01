package com.example.john.trip;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.john.trip.helper.CloseKeyboard;
import com.example.john.trip.helper.DatePickerHelper;
import com.example.john.trip.model.Flight;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewFlightActivity extends AppCompatActivity {
    private TextInputEditText departureDate, flightNumber, seats, confirmationNum, departureCityAirport, departureTime, departureTerminal, departureGate, arrivalCityAirport,
            arrivalDate, arrivalTime, arrivalTerminal, arrivalGate;
    private AutoCompleteTextView airline;
    private CloseKeyboard closeKeyboardHelper;
    private DatePickerHelper datePickerHelper;
    private String newFlight_tripId, newFlight_tripDateRange,
    newFlight_tripDurationTimeRemaining, newFlight_tripName, newFlight_tripImage;
    private Flight flight;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_flight);

        //Init views, database, place api, and listeners
        initViews();
        initHelperClasses();
        initListeners();

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            newFlight_tripName = extras.getString("tripName");
            newFlight_tripDateRange = extras.getString("tripDateRange");
            newFlight_tripDurationTimeRemaining = extras.getString("tripDurationTimeRemaining");
            newFlight_tripId = extras.getString("tripId");
            newFlight_tripImage = extras.getString("tripImage");
        }

        //Back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //Action bar menu
    @Override //Create save trip menu from XML
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.trip_save_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override //Back button
    public boolean onSupportNavigateUp() {
        Intent myIntent = new Intent(NewFlightActivity.this, TripDetailsActivity.class);
        NewFlightActivity.this.startActivity(myIntent);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        if (menuItem.getTitle() != null) {
            if (menuItem.getTitle().equals("saveTrip")) {
                addFlight();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    //Init views
    private void initViews() {
        airline = findViewById(R.id.addFlight_textInputAutocomplete_airline);
        flightNumber = findViewById(R.id.addFlight_textInputEditText_flightNumber);
        seats = findViewById(R.id.addFlight_textInputEditText_seats);
        confirmationNum = findViewById(R.id.addFlight_textInputEditText_confirmationNum);
        //Departure
        departureCityAirport = findViewById(R.id.addFlight_textInputEditText_departureCityAirport);
        departureDate = findViewById(R.id.addFlight_textInputEditText_departureDate);
        departureTime = findViewById(R.id.addFlight_textInputEditText_departureTime);
        departureTerminal = findViewById(R.id.addFlight_textInputEditText_departureTerminal);
        departureGate = findViewById(R.id.addFlight_textInputEditText_departureGate);
        //Arrival
        arrivalCityAirport = findViewById(R.id.addFlight_textInputEditText_arrivalCityAirport);
        arrivalDate = findViewById(R.id.addFlight_textInputEditText_arrivalDate);
        arrivalTime = findViewById(R.id.addFlight_textInputEditText_arrivalTime);
        arrivalTerminal = findViewById(R.id.addFlight_textInputEditText_arrivalTerminal);
        arrivalGate = findViewById(R.id.addFlight_textInputEditText_arrivalGate);

        databaseReference = FirebaseDatabase.getInstance().getReference("TripDatabase");
    }

    //Init all the helper classes
    private void initHelperClasses() {
        closeKeyboardHelper = new CloseKeyboard();
        datePickerHelper = new DatePickerHelper();
    }

    private void initListeners() {
        //Date pickers
        departureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboardHelper.close(NewFlightActivity.this, v);
                //Departure date picker- uses DatePickerHelper
                datePickerHelper.datePickerDialog(NewFlightActivity.this, departureDate);
                //Listener to show clear text button on text change
                departureDate.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        //showClearButton.showBtn(departureDate.getHint().toString().toLowerCase());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });
    }

    private void addFlight()
    {
        String airline_text= airline.getText().toString();
        String flightNum_text= flightNumber.getText().toString();
        String seats_text= seats.getText().toString();
        String confirmationNum_text= confirmationNum.getText().toString();
        String dCityAirport_text= departureCityAirport.getText().toString();
        String dDate_text= departureDate.getText().toString();
        String dTime_text= departureTime.getText().toString();
        String dTerm_text= departureTerminal.getText().toString();
        String dGate_text= departureGate.getText().toString();
        String aCityAirport_text= arrivalCityAirport.getText().toString();
        String aDate_text= arrivalDate.getText().toString();
        String aTime_text= arrivalTime.getText().toString();
        String aTerm_text= arrivalTerminal.getText().toString();
        String aGate_text= arrivalGate.getText().toString();

        flight = new Flight(airline_text, flightNum_text, seats_text, confirmationNum_text
        ,dCityAirport_text, dDate_text, dTime_text, dTerm_text, dGate_text, aCityAirport_text,
                aDate_text, aTime_text, aTerm_text, aGate_text);

        String id = databaseReference.push().getKey();
        databaseReference.child(newFlight_tripId).child("Flight").child(id).setValue(flight);
        Toast.makeText(NewFlightActivity.this, "Flight Added Successfully", Toast.LENGTH_LONG).show();
        //Start TripDetailsActivity
        Intent myIntent = new Intent(NewFlightActivity.this, TripDetailsActivity.class);
        myIntent.putExtra("tripName", newFlight_tripName);
        myIntent.putExtra("tripDateRange", newFlight_tripDateRange);
        myIntent.putExtra("tripDurationTimeRemaining", newFlight_tripDurationTimeRemaining);
        myIntent.putExtra("tripId", newFlight_tripId);
        myIntent.putExtra("tripImage", newFlight_tripImage);
        NewFlightActivity.this.startActivity(myIntent);

    }

}
