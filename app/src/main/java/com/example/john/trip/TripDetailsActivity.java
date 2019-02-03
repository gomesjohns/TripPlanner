package com.example.john.trip;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.trip.adapter.TripDetailsRVAdapter;
import com.example.john.trip.database.DeleteTrip;
import com.example.john.trip.model.Flight;
import com.example.john.trip.model.Hotel;
import com.example.john.trip.model.Trip;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TripDetailsActivity extends AppCompatActivity {
    //Global Vars
    private String tripDetails_tripName, tripDetails_tripDateRange, tripDetails_tripDurationTimeRemaining,
            tripDetails_tripId, tripDetails_tripImage;
    private TextView toolbarTitle, toolbarDateRange;
    private FloatingActionButton tripDetails_fabHotel, tripDetails_fabFlight;
    private ImageView tripDetails_imageView_tripImage;
    private Toolbar toolbar;
    private TripDetailsRVAdapter tripDetailsRVAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Flight> flightArrayList;
    private ArrayList<Hotel> hotelArrayList;
    private DatabaseReference databaseReference;
    private DatabaseReference tripReference;
    private Trip myTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        //Init views
        initViews();
        //Get extras
        getExtras();
        //Init DB
        databaseReference = FirebaseDatabase.getInstance().getReference("TripDatabase");
        tripReference = databaseReference.child(tripDetails_tripId);
        //Listeners
        initListeners();
        //Toolbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //Retrieve flight data
        retrieveFlightData();
        retrieveHotelData();

    }

    //----------------------------------------------------------------------------------------------
    //Override methods, inflate layout, bind text with layout
    //----------------------------------------------------------------------------------------------
    //Action bar menu for editing and deleting trip
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.trip_details_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Edit and delete trip menu actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.deleteTrip) {
            new DeleteTrip(tripDetails_tripId);
            Intent myIntent = new Intent(TripDetailsActivity.this, TripListActivity.class);
            TripDetailsActivity.this.startActivity(myIntent);
            Toast.makeText(this, "Trip deleted successfully", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.editTrip) {
            Intent myIntent = new Intent(TripDetailsActivity.this, NewTripActivity.class);
            TripDetailsActivity.this.startActivity(myIntent);
            Toast.makeText(this, "Trip edit", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    //Back button
    @Override
    public boolean onSupportNavigateUp() {
        Intent myIntent = new Intent(TripDetailsActivity.this, TripListActivity.class);
        TripDetailsActivity.this.startActivity(myIntent);
        return true;
    }

    //----------------------------------------------------------------------------------------------
    //Private methods
    //----------------------------------------------------------------------------------------------
    //Initialize views
    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        tripDetails_imageView_tripImage = findViewById(R.id.tripDetails_tripImage);
        tripDetails_fabFlight = findViewById(R.id.tripDetails_fabFlight);
        tripDetails_fabHotel = findViewById(R.id.tripDetails_fabHotel);
        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarDateRange = findViewById(R.id.toolbar_date_range);
        recyclerView = findViewById(R.id.tripDetails_RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //Get extras
    private void getExtras() {
        //Get extras from Trip List Activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //Get
            tripDetails_tripName = extras.getString("tripName");
            tripDetails_tripDateRange = extras.getString("tripDateRange");
            tripDetails_tripDurationTimeRemaining = extras.getString("tripDurationTimeRemaining");
            tripDetails_tripId = extras.getString("tripId");
            tripDetails_tripImage = extras.getString("tripImage");

            myTrip = extras.getParcelable("tripObj");
            //Set
            toolbarTitle.setText(tripDetails_tripName);
            toolbarDateRange.setText(tripDetails_tripDateRange + " (" + tripDetails_tripDurationTimeRemaining + ")");
            //Set trip image
            Picasso.get().load(tripDetails_tripImage).into(tripDetails_imageView_tripImage);

        }
    }

    //Listener
    private void initListeners() {
        //Fab
        tripDetails_fabFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(TripDetailsActivity.this, NewFlightActivity.class);
                myIntent.putExtra("tripName", tripDetails_tripName);
                myIntent.putExtra("tripDateRange", tripDetails_tripDateRange);
                myIntent.putExtra("tripDurationTimeRemaining", tripDetails_tripDurationTimeRemaining);
                myIntent.putExtra("tripId", tripDetails_tripId);
                myIntent.putExtra("tripImage", tripDetails_tripImage);
                TripDetailsActivity.this.startActivity(myIntent);
            }
        });
        tripDetails_fabHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(TripDetailsActivity.this, NewHotelActivity.class);
                myIntent.putExtra("tripName", tripDetails_tripName);
                myIntent.putExtra("tripDateRange", tripDetails_tripDateRange);
                myIntent.putExtra("tripDurationTimeRemaining", tripDetails_tripDurationTimeRemaining);
                myIntent.putExtra("tripId", tripDetails_tripId);
                myIntent.putExtra("tripImage", tripDetails_tripImage);
                TripDetailsActivity.this.startActivity(myIntent);
            }
        });
    }

    //Get flight data from database based on trip id
    private void retrieveFlightData() {
        DatabaseReference flightReference;
        flightArrayList = new ArrayList<>();

        //Get database ref
        flightReference = tripReference.child("Flight");

        flightReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                flightArrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Flight flight = dataSnapshot1.getValue(Flight.class);
                   // flightArrayList.add(flight);
                    myTrip.setFlightList(flight);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });
    }

    //Get hotel data from database based on trip id
    private void retrieveHotelData()
    {
        DatabaseReference hotelReference;
        hotelArrayList = new ArrayList<>();

        //Get database ref
        hotelReference = tripReference.child("Hotel");

        hotelReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               hotelArrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Hotel hotel = dataSnapshot1.getValue(Hotel.class);
                    //hotelArrayList.add(hotel);
                    myTrip.setHotelArrayList(hotel);
                }
                tripDetailsRVAdapter = new TripDetailsRVAdapter(TripDetailsActivity.this, myTrip);
                recyclerView.setAdapter(tripDetailsRVAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });
    }
}
