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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.trip.adapter.TripDetailsRVAdapter;
import com.example.john.trip.database.DeleteTrip;
import com.example.john.trip.model.Flight;
import com.example.john.trip.model.Lodging;
import com.example.john.trip.model.SelectedTrip;
import com.example.john.trip.model.Trip;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class TripDetailsActivity extends AppCompatActivity {
    //Global Vars
    private TextView toolbarTitle, toolbarDateRange;
    private FloatingActionButton tripDetails_fabLodging, tripDetails_fabFlight;
    private ImageView tripDetails_imageView_tripImage;
    private Toolbar toolbar;
    private TripDetailsRVAdapter tripDetailsRVAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Object> tempArrayList;
    private DatabaseReference databaseReference;
    private DatabaseReference tripReference;
    private SelectedTrip myTrip;

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
        tripReference = databaseReference.child(myTrip.getTripId());
        //Listeners
        initListeners();
        //Toolbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //Retrieve flight data
        retrieveFlightLodgingData();

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
            new DeleteTrip(myTrip.getTripId());
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
        tripDetails_fabLodging = findViewById(R.id.tripDetails_fabLodging);
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
            myTrip = extras.getParcelable("tripObj");
            //Set
            toolbarTitle.setText(myTrip.getTripName());
            toolbarDateRange.setText(myTrip.getDateRange()+ " (" + myTrip.getTripDurationTimeRemaining() + ")");
            //Set trip image
            Picasso.get().load(myTrip.getTripImage()).into(tripDetails_imageView_tripImage);
        }
    }

    //Listener
    private void initListeners() {
        //Fab
        tripDetails_fabFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(TripDetailsActivity.this, NewFlightActivity.class);
                myIntent(myIntent);
            }
        });
        tripDetails_fabLodging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(TripDetailsActivity.this, NewLodgingActivity.class);
                myIntent(myIntent);
            }
        });
    }

    private void myIntent(Intent myIntent)
    {
        //myIntent.putExtra("tripObj", myTrip);
        myIntent.putExtra("tripObj", myTrip);
        TripDetailsActivity.this.startActivity(myIntent);
    }

    //Get lodging data from database based on trip id
    private void retrieveFlightLodgingData()
    {
        tempArrayList = new ArrayList<>();

        //Get database ref
        tripReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tempArrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    if(dataSnapshot1.getKey().contains("flight"))
                    {
                        Flight flight = dataSnapshot1.getValue(Flight.class);
                        tempArrayList.add(flight);
                    }
                    else if(dataSnapshot1.getKey().contains("lodging")) {
                        Lodging lodging = dataSnapshot1.getValue(Lodging.class);
                        tempArrayList.add(lodging);
                        Lodging lodgingCopy = new Lodging(lodging.getLodgingId(),lodging.getLodgingName(),
                                lodging.getLodgingLocation(), lodging.getCheckInDate(), lodging.getCheckInTime(),
                                lodging.getCheckOutDate(), lodging.getCheckOutTime(), lodging.getCheckOutDate(), lodging.getCheckInDate());
                        lodgingCopy.setLodgingCopy(true);
                        tempArrayList.add(lodgingCopy);
                    }

                    //Comparator
                    Collections.sort(tempArrayList, new Comparator<Object>()
                    {
                        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_PATTERN);

                        @Override
                        public int compare(Object o1, Object o2)
                        {
                            String s1= ((Trip) o1).getStartDate();
                            String s2= ((Trip) o2).getStartDate();
                            try {
                                return (dateFormat.parse(s1).compareTo(dateFormat.parse(s2)));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return 0;
                        }
                    });
                }
                tripDetailsRVAdapter = new TripDetailsRVAdapter(TripDetailsActivity.this, tempArrayList);
                recyclerView.setAdapter(tripDetailsRVAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });
    }
}
