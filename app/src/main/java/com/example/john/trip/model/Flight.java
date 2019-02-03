package com.example.john.trip.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Flight implements Parcelable {
    private String airline;
    private String flightNumber;
    private String seats;
    private String confirmationNum;
    private String departureCityAirport;
    private String departureDate;
    private String departureTime;
    private String departureTerminal;
    private String departureGate;
    private String arrivalCityAirport;
    private String arrivalDate;
    private String arrivalTime;
    private String arrivalTerminal;
    private String arrivalGate;

    public Flight(){

    }

    public Flight(String airline, String flightNumber, String seats, String confirmationNum,
                  String departureCityAirport, String departureDate, String departureTime,
                  String departureTerminal, String departureGate, String arrivalCityAirport,
                  String arrivalDate, String arrivalTime, String arrivalTerminal, String arrivalGate) {
        this.airline = airline;
        this.flightNumber = flightNumber;
        this.seats = seats;
        this.confirmationNum = confirmationNum;
        this.departureCityAirport = departureCityAirport;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.departureTerminal = departureTerminal;
        this.departureGate = departureGate;
        this.arrivalCityAirport = arrivalCityAirport;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.arrivalTerminal = arrivalTerminal;
        this.arrivalGate = arrivalGate;
    }

    public String getAirline() {
        return airline;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getSeats() {
        return seats;
    }

    public String getConfirmationNum() {
        return confirmationNum;
    }

    public String getDepartureCityAirport() {
        return departureCityAirport;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getDepartureTerminal() {
        return departureTerminal;
    }

    public String getDepartureGate() {
        return departureGate;
    }

    public String getArrivalCityAirport() {
        return arrivalCityAirport;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getArrivalTerminal() {
        return arrivalTerminal;
    }

    public String getArrivalGate() {
        return arrivalGate;
    }

    protected Flight(Parcel in) {
        airline = in.readString();
        flightNumber = in.readString();
        seats = in.readString();
        confirmationNum = in.readString();
        departureCityAirport = in.readString();
        departureDate = in.readString();
        departureTime = in.readString();
        departureTerminal = in.readString();
        departureGate = in.readString();
        arrivalCityAirport = in.readString();
        arrivalDate = in.readString();
        arrivalTime = in.readString();
        arrivalTerminal = in.readString();
        arrivalGate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(airline);
        dest.writeString(flightNumber);
        dest.writeString(seats);
        dest.writeString(confirmationNum);
        dest.writeString(departureCityAirport);
        dest.writeString(departureDate);
        dest.writeString(departureTime);
        dest.writeString(departureTerminal);
        dest.writeString(departureGate);
        dest.writeString(arrivalCityAirport);
        dest.writeString(arrivalDate);
        dest.writeString(arrivalTime);
        dest.writeString(arrivalTerminal);
        dest.writeString(arrivalGate);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Flight> CREATOR = new Parcelable.Creator<Flight>() {
        @Override
        public Flight createFromParcel(Parcel in) {
            return new Flight(in);
        }

        @Override
        public Flight[] newArray(int size) {
            return new Flight[size];
        }
    };
}