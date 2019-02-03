package com.example.john.trip;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.john.trip.model.Hotel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewHotelActivity extends AppCompatActivity {

    private TextInputEditText hotelLocation, checkInDate, checkInTime, checkOutDate,
    checkOutTime;
    private String newHotel_tripName, newHotel_tripDateRange, newHotel_tripDurationTimeRemaining,
    newHotel_tripId, newHotel_tripImage;
    private AutoCompleteTextView hotelName;
    private DatabaseReference databaseReference;
    private Hotel hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_hotel);

        //Init views
        initViews();
        //Init db
        databaseReference = FirebaseDatabase.getInstance().getReference("TripDatabase");

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            newHotel_tripName = extras.getString("tripName");
            newHotel_tripDateRange = extras.getString("tripDateRange");
            newHotel_tripDurationTimeRemaining = extras.getString("tripDurationTimeRemaining");
            newHotel_tripId = extras.getString("tripId");
            newHotel_tripImage = extras.getString("tripImage");
        }

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
        Intent myIntent = new Intent(NewHotelActivity.this, TripDetailsActivity.class);
        NewHotelActivity.this.startActivity(myIntent);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        if (menuItem.getTitle() != null) {
            if (menuItem.getTitle().equals("save")) {
                addHotel();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    //----------------------------------------------------------------------------------------------
    //Private methods
    //----------------------------------------------------------------------------------------------
    //Initialize views
    private void initViews() {
        hotelName = findViewById(R.id.addHotel_autoCompleteTextView_searchHotel);
        hotelLocation = findViewById(R.id.addHotel_textInputEditText_location);
        checkInDate = findViewById(R.id.addHotel_textInputEditText_checkInDate);
        checkInTime = findViewById(R.id.addHotel_textInputEditText_checkInTime);
        checkOutDate = findViewById(R.id.addHotel_textInputEditText_checkOutDate);
        checkOutTime = findViewById(R.id.addHotel_textInputEditText_checkOutTime);
    }

    //Method to add hotel to the database
    private void addHotel()
    {
        String nameHotel = hotelName.getText().toString();
        String locationHotel = hotelLocation.getText().toString();
        String dateCheckIn = checkInDate.getText().toString();
        String timeCheckIn = checkInTime.getText().toString();
        String dateCheckOut = checkOutDate.getText().toString();
        String timeCheckOut = checkOutTime.getText().toString();

        hotel = new Hotel(nameHotel, locationHotel, dateCheckIn, timeCheckIn, dateCheckOut
        , timeCheckOut);

        String id = databaseReference.push().getKey();
        databaseReference.child(newHotel_tripId).child("Hotel").child(id).setValue(hotel);
        Toast.makeText(NewHotelActivity.this, "Hotel Added Successfully",
                Toast.LENGTH_LONG).show();

        //Start TripDetailsActivity
        Intent myIntent = new Intent(NewHotelActivity.this, TripDetailsActivity.class);
        myIntent.putExtra("tripName", newHotel_tripName);
        myIntent.putExtra("tripDateRange", newHotel_tripDateRange);
        myIntent.putExtra("tripDurationTimeRemaining", newHotel_tripDurationTimeRemaining);
        myIntent.putExtra("tripId", newHotel_tripId);
        myIntent.putExtra("tripImage", newHotel_tripImage);
        NewHotelActivity.this.startActivity(myIntent);
    }
}
