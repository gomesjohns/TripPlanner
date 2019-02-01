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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FlightListRVAdapter extends RecyclerView.Adapter<FlightListRVAdapter.ViewHolder> {

    Context context;
    ArrayList<Flight> flightArrayList;
    FrameLayout timeline1, timeline2;

    public FlightListRVAdapter(Context c, ArrayList<Flight> f) {
        context = c;
        flightArrayList = f;
    }

    //----------------------------------------------------------------------------------------------
    //Override methods, inflate layout, bind text with layout
    //----------------------------------------------------------------------------------------------
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.flight_row,
                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Log.v("----------------------", String.valueOf(flightArrayList.size()));
        if (i == 0) {
            timeline1.setVisibility(View.INVISIBLE);
        }
        if (i > 0 && i == (flightArrayList.size() - 1)) {
            timeline2.setVisibility(View.INVISIBLE);
        }
        if (flightArrayList.size()==1) {
            timeline1.setVisibility(View.INVISIBLE);
            timeline2.setVisibility(View.INVISIBLE);
        }

        viewHolder.tripDetails_departToArrival.setText(flightArrayList.get(i).getDepartureCityAirport()
                +" to "+flightArrayList.get(i).getArrivalCityAirport());
        viewHolder.tripDetails_airline.setText(flightArrayList.get(i).getAirline()
                +" "+flightArrayList.get(i).getFlightNumber());
        viewHolder.tripDetails_departureTime.setText(flightArrayList.get(i).getDepartureTime());
        viewHolder.tripDetails_departureTermGate.setText(flightArrayList.get(i).getDepartureTerminal()
                +" / "+flightArrayList.get(i).getDepartureGate());
        viewHolder.tripDetails_arrivalTime.setText(flightArrayList.get(i).getArrivalTime());
        viewHolder.tripDetails_arrivalTermGate.setText(flightArrayList.get(i).getArrivalTerminal()
                +" / "+flightArrayList.get(i).getArrivalGate());

    }

    @Override
    public int getItemCount() {
        return flightArrayList.size();
    }

    //----------------------------------------------------------------------------------------------
    //Get views from layout
    //----------------------------------------------------------------------------------------------
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tripDetails_departToArrival, tripDetails_airline, tripDetails_departureTime, tripDetails_departureTermGate
                ,tripDetails_arrivalTime, tripDetails_arrivalTermGate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeline1 = itemView.findViewById(R.id.timeline1);
            timeline2 = itemView.findViewById(R.id.timeline2);

            tripDetails_departToArrival= itemView.findViewById(R.id.flightRow_title);
            tripDetails_airline= itemView.findViewById(R.id.flightRow_airline_flightNum);
            tripDetails_departureTime =itemView.findViewById(R.id.flightRow_departTime);
            tripDetails_departureTermGate = itemView.findViewById(R.id.flightRow_departure_term_gate);
            tripDetails_arrivalTime = itemView.findViewById(R.id.flightRow_arrivalTime);
            tripDetails_arrivalTermGate = itemView.findViewById(R.id.flightRow_arrival_term_gate);
        }
    }
}


