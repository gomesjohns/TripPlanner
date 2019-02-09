package com.trip.planner.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.john.tripPlanner.R;
import com.trip.planner.model.Flight;
import com.trip.planner.model.Lodging;
import com.trip.planner.model.Trip;

import java.util.ArrayList;

public class TripDetailsRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Global Vars
    private Context context;
    private ConstraintLayout dateLayout;
    private FrameLayout timeline1, timeline2, timelineDate;
    private ArrayList<Object> tripDetailsArrayList;
    private final int VIEW_TYPE_FLIGHT = 0;
    private final int VIEW_TYPE_HOTEL = 1;
    private TextView dateRow_textView;

    public TripDetailsRVAdapter(Context c, ArrayList<Object> arrayList) {
        context = c;
        tripDetailsArrayList = arrayList;
    }

    //----------------------------------------------------------------------------------------------
    //Private Methods
    //----------------------------------------------------------------------------------------------
    private void drawTimeline(int position) {
        if (position == 0) {
            timeline1.setVisibility(View.INVISIBLE);
            timelineDate.setVisibility(View.INVISIBLE);
        }
        if (position > 0 && position == (tripDetailsArrayList.size() - 1)) {
            timeline2.setVisibility(View.INVISIBLE);
        }
        if (tripDetailsArrayList.size() == 1) {
            timeline1.setVisibility(View.INVISIBLE);
            timeline2.setVisibility(View.INVISIBLE);
            timelineDate.setVisibility(View.INVISIBLE);
        }
    }
    
    private void addDateHeader(int position) {
        if (position == 0)
        {
            dateLayout.setVisibility(View.VISIBLE);
        }
        else{
            String d1 = ((Trip) tripDetailsArrayList.get(position)).getStartDate();
            String d2 = ((Trip) tripDetailsArrayList.get(position-1)).getStartDate();
            if(!d1.equals(d2))
            {
                dateLayout.setVisibility(View.VISIBLE);
            }
            else {
                ((ViewManager)dateLayout.getParent()).removeView(dateLayout);
            }
        }

    }
    
    private void initDrawableViews(View itemView)
    {
        timeline1 = itemView.findViewById(R.id.timeline1);
        timeline2 = itemView.findViewById(R.id.timeline2);
        timelineDate = itemView.findViewById(R.id.timelineDate);
        dateLayout = itemView.findViewById(R.id.myDateLayout);
        dateRow_textView = itemView.findViewById(R.id.dateRow_textView);
    }

    //----------------------------------------------------------------------------------------------
    //Override methods, inflate layout, bind text with layout
    //----------------------------------------------------------------------------------------------
    @Override
    public int getItemCount() {
        return tripDetailsArrayList.size();
    }

    @Override
    public int getItemViewType(int pos) {
        if (tripDetailsArrayList.get(pos) instanceof Flight) {
            return VIEW_TYPE_FLIGHT;
        } else if (tripDetailsArrayList.get(pos) instanceof Lodging) {
            return VIEW_TYPE_HOTEL;
        } else {
            return -1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_FLIGHT) {
            return new FlightViewHolder(LayoutInflater.from(context).inflate(R.layout.flight_row, viewGroup, false));
        } else {
            return new LodgingViewHolder(LayoutInflater.from(context).inflate(R.layout.lodging_row, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        drawTimeline(position);
        addDateHeader(position);


        //Flight
        if (viewHolder instanceof FlightViewHolder) {
            Log.v("TAG", "---------------------------------------------------------------- FLIGHT ");
            Flight flight = (Flight) tripDetailsArrayList.get(position);
            ((FlightViewHolder) viewHolder).populate(flight);
        }
        //Lodging
        if (viewHolder instanceof LodgingViewHolder) {
            Log.v("TAG", "---------------------------------------------------------------- HOTEL ");
            Lodging lodging = (Lodging) tripDetailsArrayList.get(position);
            ((LodgingViewHolder) viewHolder).populate(lodging, position);
        }
    }

    //----------------------------------------------------------------------------------------------
    //Get views from layout
    //----------------------------------------------------------------------------------------------
    public class FlightViewHolder extends RecyclerView.ViewHolder {

        TextView tripDetails_flight_departToArrival, tripDetails_flight_airline, tripDetails_flight_departureTime,
                tripDetails_flight_departureTermGate, tripDetails_flight_arrivalTime, tripDetails_flight_arrivalTermGate;

        public FlightViewHolder(@NonNull View itemView) {
            super(itemView);
            initDrawableViews(itemView);

            //Flight
            tripDetails_flight_departToArrival = itemView.findViewById(R.id.flightRow_title);
            tripDetails_flight_airline = itemView.findViewById(R.id.flightRow_airline_flightNum);
            tripDetails_flight_departureTime = itemView.findViewById(R.id.flightRow_departTime);
            tripDetails_flight_departureTermGate = itemView.findViewById(R.id.flightRow_departure_term_gate);
            tripDetails_flight_arrivalTime = itemView.findViewById(R.id.flightRow_arrivalTime);
            tripDetails_flight_arrivalTermGate = itemView.findViewById(R.id.flightRow_arrival_term_gate);
        }

        public void populate(Flight flight) {
            dateRow_textView.setText(flight.getDepartureDate());
            tripDetails_flight_departToArrival.setText(flight.getDepartureCityAirport()
                    + " to " + flight.getArrivalCityAirport());
            tripDetails_flight_airline.setText(flight.getAirline()+" "+flight.getFlightNumber());
            tripDetails_flight_departureTime.setText(flight.getDepartureTime());
            tripDetails_flight_departureTermGate.setText(flight.getDepartureTerminal()+" / "+flight.getDepartureGate());
            tripDetails_flight_arrivalTime.setText(flight.getArrivalTime());
            tripDetails_flight_arrivalTermGate.setText(flight.getArrivalTerminal()+" / "+flight.getArrivalGate());
        }
    }

    public class LodgingViewHolder extends RecyclerView.ViewHolder {
        TextView tripDetails_lodge_lodgeName, tripDetails_lodge_lodgeLocation, tripDetails_lodge_lodgeTime;

        public LodgingViewHolder(@NonNull View itemView) {
            super(itemView);
            initDrawableViews(itemView);

            //Lodging
            tripDetails_lodge_lodgeName = itemView.findViewById(R.id.lodgeRow_lodgeName);
            tripDetails_lodge_lodgeLocation = itemView.findViewById(R.id.lodgeRow_lodgeLocation);
            tripDetails_lodge_lodgeTime = itemView.findViewById(R.id.lodgeRow_lodgeTime);
        }

        public void populate(Lodging lodging, int position) {
            //Check if previous object is a lodge object
            if(position!=0)
            {
                if(lodging.isLodgingCopy())
                {
                    dateRow_textView.setText(lodging.getCheckOutDate());
                    tripDetails_lodge_lodgeTime.setText("Check-out "+lodging.getCheckOutTime()); //Show checkout time
                }
                else {
                    dateRow_textView.setText(lodging.getCheckInDate());
                    tripDetails_lodge_lodgeTime.setText("Check-in "+lodging.getCheckInTime()); //Show checkin time
                }
            }
            else {
                dateRow_textView.setText(lodging.getCheckInDate());
                tripDetails_lodge_lodgeTime.setText("Check-in "+lodging.getCheckInTime());
            }
            tripDetails_lodge_lodgeName.setText(lodging.getLodgingName());
            tripDetails_lodge_lodgeLocation.setText(lodging.getLodgingLocation());

        }
    }

}


