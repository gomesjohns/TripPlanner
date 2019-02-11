package com.trip.planner;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.trip.planner.helper.CloseKeyboard;
import com.trip.planner.helper.DatePickerUtil;
import com.trip.planner.helper.GooglePlaceApi;
import com.trip.planner.helper.ClearButton;
import com.trip.planner.model.Trip;

import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NewTripActivity extends AppCompatActivity {
    //Global Variables
    private AutoCompleteTextView destinationAutocomplete;
    private TextInputLayout destinationlayout, departureLayout, returnLayout;
    private TextInputEditText departureDate, returnDate;
    private ConstraintLayout newTripLayout;
    private DatabaseReference databaseReference;
    private DatePickerUtil datePickerUtil;
    private CloseKeyboard closeKeyboardHelper;
    private ClearButton clearButton;
    private ArrayList<Object> textViewArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);

        //Init views
        initViews();
        //Init helpers
        initHelpers();
        //Init listeners
        initListeners();
        //Generate clear buttons
        generateClearBtn();
        //Init db
        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_REFERENCE);
        //Back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //----------------------------------------------------------------------------------------------
    //Override methods
    //----------------------------------------------------------------------------------------------
    //Action bar menu for adding new trip
    @Override //Create save trip menu from XML
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override //Save trip action bar button function on click
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle() != null) {
            if (item.getTitle().equals("save")) {
                addTrip();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override //Back button
    public boolean onSupportNavigateUp() {
        startTripListActivity();//Start TripListActivity
        return true;
    }

    //----------------------------------------------------------------------------------------------
    //Private methods
    //----------------------------------------------------------------------------------------------
    //Init views
    private void initViews() {
        destinationAutocomplete = findViewById(R.id.newTrip_autoCompleteTextView_destination);
        departureDate = findViewById(R.id.newTrip_textInputEditText_departureDate);
        returnDate = findViewById(R.id.newTrip_textInputEditText_returnDate);

        destinationlayout= findViewById(R.id.newTrip_textInputLayout_destination);
        departureLayout = findViewById(R.id.newTrip_textInputLayout_depart);
        returnLayout = findViewById(R.id.newTrip_textInputLayout_return);

        textViewArrayList = new ArrayList<>();
        textViewArrayList.add(destinationAutocomplete);
        textViewArrayList.add(departureDate);
        textViewArrayList.add(returnDate);
        newTripLayout = findViewById(R.id.newTripLayout);
    }

    //Init all the helper classes
    private void initHelpers() {
        datePickerUtil = new DatePickerUtil();
        closeKeyboardHelper = new CloseKeyboard();
        clearButton = new ClearButton();
        new GooglePlaceApi(this, NewTripActivity.this, destinationAutocomplete
                , AutocompleteFilter.TYPE_FILTER_REGIONS); //Init Google Place Api
    }

    //Init listeners
    private void initListeners() {
        //Destination
        destinationAutocomplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clearButton.showBtn(destinationAutocomplete.getTag().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Date pickers
        departureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                closeKeyboardHelper.close(NewTripActivity.this, v);
                //Departure date picker- uses DatePickerUtil
                datePickerUtil.datePickerDialog(NewTripActivity.this, departureDate);
                //Listener to show clear text button on text change
                departureDate.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        clearButton.showBtn(departureDate.getTag().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            }
        });
        returnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboardHelper.close(NewTripActivity.this, v);
                //Return date picker- uses DatePickerUtil
                datePickerUtil.datePickerDialog(NewTripActivity.this, returnDate);
                //Listener to show clear text button on text change
                returnDate.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        clearButton.showBtn(returnDate.getTag().toString());
                    }
                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });

    }

    //Generate clear text button
    private void generateClearBtn()
    {
        ArrayList<TextInputLayout> inputLayoutList = new ArrayList<>();
        inputLayoutList.add(destinationlayout);
        inputLayoutList.add(departureLayout);
        inputLayoutList.add(returnLayout);


        clearButton.generateButtons(NewTripActivity.this, inputLayoutList);
    }

    //Start Trip List Activity
    private void startTripListActivity() {
        //Start TripListActivity
        Intent myIntent = new Intent(NewTripActivity.this, TripListActivity.class);
        NewTripActivity.this.startActivity(myIntent);
    }

    //Adds trip to database
    private void addTrip() {
        String tripLocation = destinationAutocomplete.getText().toString().trim();
        String[] tripName = tripLocation.split(",", 0);
        String tripDepart = departureDate.getText().toString().trim();
        String tripReturn = returnDate.getText().toString().trim();

        if (!TextUtils.isEmpty(tripLocation) && !TextUtils.isEmpty(tripDepart) && !TextUtils.isEmpty(tripReturn)
                && returnDate.getError() == null && departureDate.getError() == null) {
            String id = databaseReference.push().getKey();
            Trip trip = new Trip(id, tripName[0], tripLocation, tripDepart, tripReturn);
            databaseReference.child(id).setValue(trip);
            Toast.makeText(NewTripActivity.this, "Trip Added Successfully", Toast.LENGTH_LONG).show();
            startTripListActivity();
        } else {
            Snackbar.make(newTripLayout, "Please Enter All Information", Snackbar.LENGTH_SHORT).show();
        }
    }
}
