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
import com.example.john.trip.helper.DatePickerUtil;
import com.example.john.trip.helper.InputValidation;
import com.example.john.trip.model.Flight;
import com.example.john.trip.model.SelectedTrip;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewFlightActivity extends AppCompatActivity {
    private TextInputEditText departureDate, flightNumber, seats, confirmationNum, departureCityAirport, departureTime, departureTerminal, departureGate, arrivalCityAirport,
            arrivalDate, arrivalTime, arrivalTerminal, arrivalGate;
    private AutoCompleteTextView airline;
    private CloseKeyboard closeKeyboardHelper;
    private DatePickerUtil datePickerUtil;
    private Flight flight;
    private DatabaseReference databaseReference;
    private SelectedTrip myTrip;
    private InputValidation inputValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_flight);

        //Init views
        initViews();
        //Ger extras
        getExtras();
        //Init Db
        databaseReference = FirebaseDatabase.getInstance().getReference("TripDatabase");
        //Init helpers
        initHelperClasses();
        //Init listeners
        initListeners();

        //Back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //----------------------------------------------------------------------------------------------
    //Override methods
    //----------------------------------------------------------------------------------------------
    //Action bar menu
    @Override //Create save trip menu from XML
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override //Back button
    public boolean onSupportNavigateUp() {
        Intent myIntent = new Intent(NewFlightActivity.this, TripDetailsActivity.class);
        myIntent.putExtra("tripObj", myTrip);
        NewFlightActivity.this.startActivity(myIntent);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        if (menuItem.getTitle() != null) {
            if (menuItem.getTitle().equals("save")) {
                addFlight();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    //----------------------------------------------------------------------------------------------
    //Private methods
    //----------------------------------------------------------------------------------------------
    //Initialize views
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
    }

    //Get extras
    private void getExtras()
    {
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            //Get
            myTrip = extras.getParcelable("tripObj");
        }
    }

    //Init all the helper classes
    private void initHelperClasses() {
        closeKeyboardHelper = new CloseKeyboard();
        datePickerUtil = new DatePickerUtil();
        inputValidation = new InputValidation();
    }

    //Listeners
    private void initListeners() {
        //Date pickers
        departureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboardHelper.close(NewFlightActivity.this, v);
                //Departure date picker- uses DatePickerUtil
                datePickerUtil.datePickerDialog(NewFlightActivity.this, departureDate);
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

    //Method to add flight to the database
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

        //Validate input
        inputValidation.validateInputAutoComplete(airline, airline_text);
        inputValidation.validateTextViewInput(flightNumber, flightNum_text);
        inputValidation.validateTextViewInput(departureCityAirport, dCityAirport_text);
        inputValidation.validateTextViewInput(departureDate, dDate_text);
        inputValidation.validateTextViewInput(departureTime, dTime_text);
        inputValidation.validateTextViewInput(arrivalCityAirport, aCityAirport_text);
        inputValidation.validateTextViewInput(arrivalDate, aDate_text);
        inputValidation.validateTextViewInput(arrivalTime, aTime_text);

        if(inputValidation.getErrorCount() ==0) {

            flight = new Flight(airline_text, flightNum_text, seats_text, confirmationNum_text
                    , dCityAirport_text, dDate_text, dTime_text, dTerm_text, dGate_text, aCityAirport_text,
                    aDate_text, aTime_text, aTerm_text, aGate_text);

            String id = databaseReference.push().getKey();
            databaseReference.child(myTrip.getTripId()).child("flight" + id).setValue(flight);
            Toast.makeText(NewFlightActivity.this, "Flight Added Successfully",
                    Toast.LENGTH_LONG).show();
            //Start TripDetailsActivity
            Intent myIntent = new Intent(NewFlightActivity.this, TripDetailsActivity.class);
            myIntent.putExtra("tripObj", myTrip);
            NewFlightActivity.this.startActivity(myIntent);
        }
    }

}
