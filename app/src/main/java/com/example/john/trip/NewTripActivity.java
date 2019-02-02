package com.example.john.trip;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
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

import com.example.john.trip.helper.ClearText;
import com.example.john.trip.helper.CloseKeyboard;
import com.example.john.trip.helper.DatePickerHelper;
import com.example.john.trip.helper.GooglePlaceApi;
import com.example.john.trip.helper.HideClearButton;
import com.example.john.trip.helper.ShowClearButton;
import com.example.john.trip.model.Trip;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewTripActivity extends AppCompatActivity {
    //Global Variables
    public static AutoCompleteTextView destinationAutocomplete;
    public static Button buttonClearText1, buttonClearText2, buttonClearText3;
    private TextInputEditText departureDate, returnDate;
    private ConstraintLayout newTripLayout;
    private DatabaseReference databaseReference;
    private String destination, start, end;
    private DatePickerHelper datePickerHelper;
    private CloseKeyboard closeKeyboardHelper;
    private ClearText clearText;
    private HideClearButton hideClearButton;
    private ShowClearButton showClearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);

        //Init views, database, helper classes, place api, and listeners
        initViews();//Init views and database
        initHelperClasses();//Init helper classes
        initListeners();//Init listeners

        //Back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        hideClearButton.hideBtn("allBtn");//hides clear text buttons
    }

    //Init views
    private void initViews() {
        databaseReference = FirebaseDatabase.getInstance().getReference("TripDatabase");

        destinationAutocomplete = findViewById(R.id.newTrip_autoCompleteTextView_destination);
        departureDate = findViewById(R.id.newTrip_textInputEditText_departureDate);
        returnDate = findViewById(R.id.newTrip_textInputEditText_returnDate);

        buttonClearText1 = findViewById(R.id.buttonClearText1);
        buttonClearText2 = findViewById(R.id.buttonClearText2);
        buttonClearText3 = findViewById(R.id.buttonClearText3);
        newTripLayout = findViewById(R.id.newTripLayout);
    }

    //Init all the helper classes
    private void initHelperClasses() {
        datePickerHelper = new DatePickerHelper();
        closeKeyboardHelper = new CloseKeyboard();
        clearText = new ClearText();
        hideClearButton = new HideClearButton();
        showClearButton = new ShowClearButton();
        new GooglePlaceApi(this, NewTripActivity.this); //Init Google Place Api
    }

    //Action bar menu for adding new trip
    @Override //Create save trip menu from XML
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override //Save trip action bar button function on click
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle() != null) {
            if (item.getTitle().equals("saveTrip")) {
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

    //Init listeners
    private void initListeners() {
        //Date pickers
        departureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                closeKeyboardHelper.close(NewTripActivity.this, v);
                //Departure date picker- uses DatePickerHelper
                datePickerHelper.datePickerDialog(NewTripActivity.this, departureDate);
                //Listener to show clear text button on text change
                departureDate.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        showClearButton.showBtn(departureDate.getHint().toString().toLowerCase());
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
                //Return date picker- uses DatePickerHelper
                datePickerHelper.datePickerDialog(NewTripActivity.this, returnDate);
                //Listener to show clear text button on text change
                returnDate.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        showClearButton.showBtn(returnDate.getHint().toString().toLowerCase());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });

        //Auto complete place field clear text button listener
        buttonClearText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearText.ClearTextAutoComplete(destinationAutocomplete);
            }
        });

        //Departure date edit text field clear text button listener
        buttonClearText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearText.ClearTextEditText(departureDate);
            }
        });

        //Return date edit text field clear text button listener
        buttonClearText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearText.ClearTextEditText(returnDate);
            }
        });
    }

    //Start Trip List Activity
    private void startTripListActivity() {
        //Start TripListActivity
        Intent myIntent = new Intent(NewTripActivity.this, TripListActivity.class);
        NewTripActivity.this.startActivity(myIntent);
    }

    //Adds trip to database
    private void addTrip() {
        destination = destinationAutocomplete.getText().toString().trim();
        start = departureDate.getText().toString().trim();
        end = returnDate.getText().toString().trim();

        if (!TextUtils.isEmpty(destination) && !TextUtils.isEmpty(start)
                && !TextUtils.isEmpty(end)
                && returnDate.getError() == null && departureDate.getError() == null) {
            String id = databaseReference.push().getKey();
            Trip trip = new Trip(id, destination, start, end);
            databaseReference.child(id).setValue(trip);
            Toast.makeText(NewTripActivity.this, "Trip Successfully Added", Toast.LENGTH_LONG).show();
            startTripListActivity();
        } else {
            Snackbar.make(newTripLayout, "Please Enter All Information", Snackbar.LENGTH_SHORT).show();
        }
    }
}
