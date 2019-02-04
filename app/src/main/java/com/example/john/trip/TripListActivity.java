package com.example.john.trip;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.john.trip.adapter.TripListRVAdapter;
import com.example.john.trip.model.Trip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TripListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TripListRVAdapter tripListRVAdapter;
    ArrayList<Trip> tripArrayList;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);

        //Init view
        recyclerView = findViewById(R.id.tripList_RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Retrieve data
        retrieveTripData();
    }

    //Action bar menu for adding new trip
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.trip_list_add_trip_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(TripListActivity.this, NewTripActivity.class);
        TripListActivity.this.startActivity(myIntent);
        return super.onOptionsItemSelected(item);
    }

    //Method to retrieve trip data from database to show in recycler view
    private void retrieveTripData() {
        tripArrayList = new ArrayList<>();
        //Add data to array list and TripListRVAdapter to display
        databaseReference = FirebaseDatabase.getInstance().getReference("TripDatabase");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tripArrayList.clear(); //Clear the list of any previous items, refresh

                //Iterate through each data object
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Trip trip = dataSnapshot1.getValue(Trip.class);
                    tripArrayList.add(trip);
                }

                //Sort the array list for trips
                Collections.sort(tripArrayList, new Comparator<Trip>() {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_PATTERN);

                    @Override
                    public int compare(Trip o1, Trip o2) {
                        try {
                            return dateFormat.parse(o1.getStartDate()).compareTo(dateFormat.parse(o2.getStartDate()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return 0;
                    }
                });
                tripListRVAdapter = new TripListRVAdapter(TripListActivity.this,tripArrayList);
                recyclerView.setAdapter(tripListRVAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Error", " DATABASE ERROR------------------" + databaseError.getMessage());
            }
        });

    }



}
