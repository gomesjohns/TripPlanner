package com.example.john.trip.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.john.trip.R;
import com.example.john.trip.model.Flight;
import com.example.john.trip.model.Hotel;
import com.example.john.trip.model.Trip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TripDetailsRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Global Vars
    private Context context;
    private FrameLayout timeline1, timeline2;
    private ArrayList<Object> tripDetialsArrayList;
    private final int VIEW_TYPE_FLIGHT = 0;
    private final int VIEW_TYPE_HOTEL = 1;

    public TripDetailsRVAdapter(Context c, ArrayList<Object> arrayList) {
        context = c;
        tripDetialsArrayList = arrayList;
    }

    //----------------------------------------------------------------------------------------------
    //Override methods, inflate layout, bind text with layout
    //----------------------------------------------------------------------------------------------
    @Override
    public int getItemCount() {
        return tripDetialsArrayList.size();
    }

    @Override
    public int getItemViewType(int pos) {
        if (tripDetialsArrayList.get(pos) instanceof Flight) {
            return VIEW_TYPE_FLIGHT;
        } else if (tripDetialsArrayList.get(pos) instanceof Hotel) {
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
            return new HotelViewHolder(LayoutInflater.from(context).inflate(R.layout.hotel_row, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (position == 0) {
            timeline1.setVisibility(View.INVISIBLE);
        }
        if (position > 0 && position == (tripDetialsArrayList.size() - 1)) {
            timeline2.setVisibility(View.INVISIBLE);
        }
        if (tripDetialsArrayList.size() == 1) {
            timeline1.setVisibility(View.INVISIBLE);
            timeline2.setVisibility(View.INVISIBLE);
        }

        //Flight
        if (viewHolder instanceof FlightViewHolder) {
            Log.v("TAG", "---------------------------------------------------------------- FLIGHT");
            Flight flight = (Flight) tripDetialsArrayList.get(position);
            ((FlightViewHolder) viewHolder).populate(flight);
        }
        //Hotel
        if (viewHolder instanceof HotelViewHolder) {
            Log.v("TAG", "---------------------------------------------------------------- HOTEL");
            Hotel hotel = (Hotel) tripDetialsArrayList.get(position);
            ((HotelViewHolder) viewHolder).populate(hotel);
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
            timeline1 = itemView.findViewById(R.id.timeline1);
            timeline2 = itemView.findViewById(R.id.timeline2);

            //Flight
            tripDetails_flight_departToArrival = itemView.findViewById(R.id.flightRow_title);
            tripDetails_flight_airline = itemView.findViewById(R.id.flightRow_airline_flightNum);
            tripDetails_flight_departureTime = itemView.findViewById(R.id.flightRow_departTime);
            tripDetails_flight_departureTermGate = itemView.findViewById(R.id.flightRow_departure_term_gate);
            tripDetails_flight_arrivalTime = itemView.findViewById(R.id.flightRow_arrivalTime);
            tripDetails_flight_arrivalTermGate = itemView.findViewById(R.id.flightRow_arrival_term_gate);
        }

        public void populate(Flight flight) {
            tripDetails_flight_departToArrival.setText(flight.getDepartureCityAirport()
                    + " to " + flight.getArrivalCityAirport());
        }
    }

    public class HotelViewHolder extends RecyclerView.ViewHolder {
        TextView tripDetails_hotel_hotelName, tripDetails_hotel_hotelLocation;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            timeline1 = itemView.findViewById(R.id.timeline1);
            timeline2 = itemView.findViewById(R.id.timeline2);

            //Hotel
            tripDetails_hotel_hotelName = itemView.findViewById(R.id.hotelRow_hotelName);
            tripDetails_hotel_hotelLocation = itemView.findViewById(R.id.hotelRow_hotelLocation);
        }

        public void populate(Hotel hotel) {
            tripDetails_hotel_hotelName.setText(hotel.getHotelName());
        }
    }

}


