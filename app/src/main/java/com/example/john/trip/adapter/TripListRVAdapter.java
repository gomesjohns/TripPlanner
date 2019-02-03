package com.example.john.trip.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.john.trip.helper.TimeRemainingCalculation;
import com.example.john.trip.helper.TripImage;
import com.example.john.trip.model.Trip;
import com.example.john.trip.R;
import com.example.john.trip.TripDetailsActivity;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;


public class TripListRVAdapter extends RecyclerView.Adapter<TripListRVAdapter.ViewHolder>{

    //Variables
    Context context;
    ArrayList<Trip> tripList_trips;
    TripImage tripImageUrl;
    private String []tripName;
    private String tripLocation;
    TimeRemainingCalculation timeRemainingCalculation;

    //Constructor
    public TripListRVAdapter(Context c, ArrayList<Trip> t)
    {
        context= c;
        tripList_trips = t;
    }

    //----------------------------------------------------------------------------------------------
    //Override methods, inflate layout, bind text with layout
    //----------------------------------------------------------------------------------------------
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.trip_row,
                viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position)
    {
        //Instantiate Time Remaining Calculation helper class
        timeRemainingCalculation = new TimeRemainingCalculation(tripList_trips);

        //Get trip name
        getTripName(position);

        //Get and set trip image
        getTripImage();
        Picasso.get().load(tripImageUrl.getUrl()).into(viewHolder.tripList_tripImage);

        //Set information to text views
        viewHolder.tripList_tripName.setText(tripName[0]);//trip title
        viewHolder.tripList_tripLocation.setText(tripLocation);//trip location
        viewHolder.tripList_tripDateRange.setText(dateRangeText(position));//trip date range
        viewHolder.tripList_tripDaysRemaining.setText(String.valueOf(timeRemainingCalculation.tripDurationTimeRemainingText(position)));//trip duration and days remaining

        //On click method of each trip item
        viewHolder.tripList_tripListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start trip details activity, send trip data
                Intent myIntent = new Intent(context, TripDetailsActivity.class);
                getTripName(position);
                myIntent.putExtra("tripObj", tripList_trips.get(position));
                myIntent.putExtra("tripName", tripName[0]);//trip title
                myIntent.putExtra("tripDateRange", String.valueOf(dateRangeText(position))); //trip date range
                myIntent.putExtra("tripDurationTimeRemaining", String.valueOf(String.valueOf(timeRemainingCalculation.tripDurationTimeRemainingText(position)))); //trip duration
                myIntent.putExtra("tripId", tripList_trips.get(position).getTripId()); //trip id
                getTripImage();
                myIntent.putExtra("tripImage",String.valueOf(tripImageUrl.getUrl()));
                context.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return tripList_trips.size();
    }

    //----------------------------------------------------------------------------------------------
    //Private methods- joins dep and ret date, gets trip name form db, gets image based on trip name
    //----------------------------------------------------------------------------------------------
    //Departure date - Return Date
    private String dateRangeText(int position)
    {
        return tripList_trips.get(position).getStartDate()+ " - "+ tripList_trips.get(position).getEndDate();
    }

    //Get trip name from trip location in database
    private void getTripName(int pos)
    {
        //Format trip name data from location info in database
        tripLocation= tripList_trips.get(pos).getTripName();
        tripName= tripLocation.split(",",0);
    }

    //Get trip image
    private void getTripImage()
    {
        //Trip image placement from trip name
        tripImageUrl = new TripImage(tripName[0]);
    }

    //----------------------------------------------------------------------------------------------
    //Get views from layout
    //----------------------------------------------------------------------------------------------
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        //Variables
        ImageView tripList_tripImage;
        TextView tripList_tripName, tripList_tripDateRange, tripList_tripDaysRemaining, tripList_tripLocation;
        ConstraintLayout tripList_tripListLayout;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            //Init views
            tripList_tripImage= itemView.findViewById(R.id.imageView_tripList_tripImage);
            tripList_tripName = itemView.findViewById(R.id.textView_tripList_tripName);
            tripList_tripLocation= itemView.findViewById(R.id.textView_tripList_tripLocation);
            tripList_tripDateRange = itemView.findViewById(R.id.textView_tripList_tripDates);
            tripList_tripDaysRemaining = itemView.findViewById(R.id.textView_tripList_tripDurationTimeRemaining);
            tripList_tripListLayout = itemView.findViewById(R.id.tripLayout);
        }
    }
}
