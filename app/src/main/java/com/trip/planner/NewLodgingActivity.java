package com.trip.planner;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.trip.planner.helper.CloseKeyboard;
import com.trip.planner.helper.DatePickerUtil;
import com.trip.planner.helper.GooglePlaceApi;
import com.trip.planner.helper.InputValidation;
import com.trip.planner.helper.TimePickerUtil;
import com.trip.planner.model.Lodging;
import com.trip.planner.model.SelectedTrip;

import com.example.john.tripPlanner.R;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewLodgingActivity extends AppCompatActivity {

    private TextInputEditText checkInDate, checkInTime, checkOutDate, checkOutTime;
    private AutoCompleteTextView lodgingName, lodgingLocation;
    private DatabaseReference databaseReference;
    private Lodging lodging;
    private SelectedTrip myTrip;
    private InputValidation inputValidation;
    private DatePickerUtil datePickerUtil;
    private TimePickerUtil timePickerUtil;
    private CloseKeyboard closeKeyboardHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lodging);

        //Init views
        initViews();
        //Init helpers
        initHelpers();
        //Init listeners
        initListeners();
        //Get extras
        getExtras();
        //Init db
        databaseReference = FirebaseDatabase.getInstance().getReference("TripDatabase");
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
        Intent myIntent = new Intent(NewLodgingActivity.this, TripDetailsActivity.class);
        myIntent.putExtra("tripObj", myTrip);
        NewLodgingActivity.this.startActivity(myIntent);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        if (menuItem.getTitle() != null) {
            if (menuItem.getTitle().equals("save")) {
                addLodging();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    //----------------------------------------------------------------------------------------------
    //Private methods
    //----------------------------------------------------------------------------------------------
    //Initialize views
    private void initViews() {
        lodgingName = findViewById(R.id.addLodging_autoCompleteTextView_searchLodging);
        lodgingLocation = findViewById(R.id.addLodging_textInputAutoComplete_location);
        checkInDate = findViewById(R.id.addLodging_textInputEditText_checkInDate);
        checkInTime = findViewById(R.id.addLodging_textInputEditText_checkInTime);
        checkOutDate = findViewById(R.id.addLodging_textInputEditText_checkOutDate);
        checkOutTime = findViewById(R.id.addLodging_textInputEditText_checkOutTime);
    }

    private void initHelpers()
    {
        closeKeyboardHelper = new CloseKeyboard();
        datePickerUtil = new DatePickerUtil();
        timePickerUtil = new TimePickerUtil();
        inputValidation = new InputValidation();
        timePickerUtil.timePickerDialog(NewLodgingActivity.this, checkInTime);
        timePickerUtil.timePickerDialog(NewLodgingActivity.this, checkOutTime);
        new GooglePlaceApi(this, NewLodgingActivity.this, lodgingLocation, AutocompleteFilter.TYPE_FILTER_ADDRESS); //Init Google Place Api
    }


    private void initListeners()
    {
        checkInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboardHelper.close(NewLodgingActivity.this, v);
                datePickerUtil.datePickerDialog(NewLodgingActivity.this, checkInDate);
            }
        });
        checkOutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboardHelper.close(NewLodgingActivity.this, v);
                datePickerUtil.datePickerDialog(NewLodgingActivity.this, checkOutDate);
            }
        });
        checkInTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboardHelper.close(NewLodgingActivity.this, v);
                timePickerUtil.timePickerDialog(NewLodgingActivity.this, checkInTime);
            }
        });
        checkOutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboardHelper.close(NewLodgingActivity.this, v);
                timePickerUtil.timePickerDialog(NewLodgingActivity.this, checkOutTime);
            }
        });
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

    //Method to add lodging to the database
    private void addLodging()
    {
        String nameLodging = lodgingName.getText().toString();
        String locationLodging = lodgingLocation.getText().toString();
        String dateCheckIn = checkInDate.getText().toString();
        String timeCheckIn = checkInTime.getText().toString();
        String dateCheckOut = checkOutDate.getText().toString();
        String timeCheckOut = checkOutTime.getText().toString();

        inputValidation.setErrorCount();
        inputValidation.validateInputAutoComplete(lodgingName, nameLodging);
        inputValidation.validateInputAutoComplete(lodgingLocation, locationLodging);
        inputValidation.validateTextViewInput(checkInDate, dateCheckIn);
        inputValidation.validateTextViewInput(checkInTime, timeCheckIn);
        inputValidation.validateTextViewInput(checkOutDate, dateCheckOut);
        inputValidation.validateTextViewInput(checkOutTime, timeCheckOut);


        if(inputValidation.getErrorCount() ==0)
        {
            String id = databaseReference.push().getKey();

            lodging = new Lodging(id, nameLodging, locationLodging, dateCheckIn, timeCheckIn, dateCheckOut
                    , timeCheckOut, dateCheckIn, dateCheckOut);
            databaseReference.child(myTrip.getTripId()).child("lodging"+id).setValue(lodging);
            Toast.makeText(NewLodgingActivity.this, "Lodging Added Successfully",
                    Toast.LENGTH_LONG).show();

            //Start TripDetailsActivity
            Intent myIntent = new Intent(NewLodgingActivity.this, TripDetailsActivity.class);
            myIntent.putExtra("tripObj", myTrip);
            NewLodgingActivity.this.startActivity(myIntent);
        }

    }
}
