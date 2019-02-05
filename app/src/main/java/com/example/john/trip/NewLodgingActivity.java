package com.example.john.trip;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.john.trip.model.Lodging;
import com.example.john.trip.model.SelectedTrip;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewLodgingActivity extends AppCompatActivity {

    private TextInputEditText lodgingLocation, checkInDate, checkInTime, checkOutDate, checkOutTime;
    private AutoCompleteTextView lodgingName;
    private DatabaseReference databaseReference;
    private Lodging lodging;
    private SelectedTrip myTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lodging);

        //Init views
        initViews();
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
        lodgingLocation = findViewById(R.id.addLodging_textInputEditText_location);
        checkInDate = findViewById(R.id.addLodging_textInputEditText_checkInDate);
        checkInTime = findViewById(R.id.addLodging_textInputEditText_checkInTime);
        checkOutDate = findViewById(R.id.addLodging_textInputEditText_checkOutDate);
        checkOutTime = findViewById(R.id.addLodging_textInputEditText_checkOutTime);
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

        lodging = new Lodging(nameLodging, locationLodging, dateCheckIn, timeCheckIn, dateCheckOut
        , timeCheckOut);

        String id = databaseReference.push().getKey();
        databaseReference.child(myTrip.getTripId()).child("lodging"+id).setValue(lodging);
        Toast.makeText(NewLodgingActivity.this, "Lodging Added Successfully",
                Toast.LENGTH_LONG).show();

        //Start TripDetailsActivity
        Intent myIntent = new Intent(NewLodgingActivity.this, TripDetailsActivity.class);
        myIntent.putExtra("tripObj", myTrip);
        NewLodgingActivity.this.startActivity(myIntent);
    }
}
