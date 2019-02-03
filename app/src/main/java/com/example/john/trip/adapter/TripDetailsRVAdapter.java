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

public class TripDetailsRVAdapter extends RecyclerView.Adapter<TripDetailsRVAdapter.ViewHolder> {

    Context context;
    FrameLayout timeline1, timeline2;
    Trip trip;
    ArrayList<Object> myArrayList;

    public TripDetailsRVAdapter(Context c, Trip t) {
        context = c;
        trip =t;
        myArrayList = new ArrayList<>();
        myArrayList.addAll(trip.getFlightList());
        myArrayList.addAll(trip.getHotelArrayList());
    }

    //----------------------------------------------------------------------------------------------
    //Override methods, inflate layout, bind text with layout
    //----------------------------------------------------------------------------------------------
   @Override
    public int getItemViewType(int pos)
    {
        if(myArrayList !=null)
        {
            if(myArrayList.get(pos).getClass().getName().substring(28).equals("Flight"))
            {
                return 0;
            }
            else {
                return 1;
            }
        }
        else {
            return 2;
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i==0)
        {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.flight_row, viewGroup, false));
        }
        else
        {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.hotel_row, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (i == 0) {
            timeline1.setVisibility(View.INVISIBLE);
        }
        if (i > 0 && i == (trip.getFlightList().size()+trip.getHotelArrayList().size() - 1)) {
            timeline2.setVisibility(View.INVISIBLE);
        }
        if (trip.getFlightList().size()+trip.getHotelArrayList().size() ==1) {
            timeline1.setVisibility(View.INVISIBLE);
            timeline2.setVisibility(View.INVISIBLE);
        }


            /*//Flight
            if (getItemViewType(i)==0)
            {
                viewHolder.tripDetails_flight_departToArrival.setText(trip.getFlightList().get(i).getDepartureCityAirport()+ " to "+ trip.getFlightList().get(i).getArrivalCityAirport());
            }
            else{
                viewHolder.tripDetails_hotel_hotelName.setText(trip.getHotelArrayList().get(i).getHotelName());
            }
            *//*viewHolder.tripDetails_flight_departToArrival.setText(flightArrayList.get(i).getDepartureCityAirport()
                    + " to " + flightArrayList.get(i).getArrivalCityAirport());
            viewHolder.tripDetails_flight_airline.setText(flightArrayList.get(i).getAirline()
                    + " " + flightArrayList.get(i).getFlightNumber());
            viewHolder.tripDetails_flight_departureTime.setText(flightArrayList.get(i).getDepartureTime());
            viewHolder.tripDetails_flight_departureTermGate.setText(flightArrayList.get(i).getDepartureTerminal()
                    + " / " + flightArrayList.get(i).getDepartureGate());
            viewHolder.tripDetails_flight_arrivalTime.setText(flightArrayList.get(i).getArrivalTime());
            viewHolder.tripDetails_flight_arrivalTermGate.setText(flightArrayList.get(i).getArrivalTerminal()
                    + " / " + flightArrayList.get(i).getArrivalGate());*//*


            //Hotel
            //
            //viewHolder.tripDetails_hotel_hotelLocation.setText(hotelArrayList.get(i- flightArrayList.size()).getHotelLocation());*/
    }

    @Override
    public int getItemCount() {
        return myArrayList.size();
    }

    //----------------------------------------------------------------------------------------------
    //Get views from layout
    //----------------------------------------------------------------------------------------------
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tripDetails_flight_departToArrival, tripDetails_flight_airline, tripDetails_flight_departureTime, tripDetails_flight_departureTermGate
                ,tripDetails_flight_arrivalTime, tripDetails_flight_arrivalTermGate, tripDetails_hotel_hotelName, tripDetails_hotel_hotelLocation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeline1 = itemView.findViewById(R.id.timeline1);
            timeline2 = itemView.findViewById(R.id.timeline2);

            //Flight
            tripDetails_flight_departToArrival= itemView.findViewById(R.id.flightRow_title);
            tripDetails_flight_airline= itemView.findViewById(R.id.flightRow_airline_flightNum);
            tripDetails_flight_departureTime =itemView.findViewById(R.id.flightRow_departTime);
            tripDetails_flight_departureTermGate = itemView.findViewById(R.id.flightRow_departure_term_gate);
            tripDetails_flight_arrivalTime = itemView.findViewById(R.id.flightRow_arrivalTime);
            tripDetails_flight_arrivalTermGate = itemView.findViewById(R.id.flightRow_arrival_term_gate);
            //Hotel
            tripDetails_hotel_hotelName = itemView.findViewById(R.id.hotelRow_hotelName);
            tripDetails_hotel_hotelLocation = itemView.findViewById(R.id.hotelRow_hotelLocation);
        }
    }
}


