package com.example.john.trip.model;

public class Flight extends Trip {
    private String flightId;
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

    public Flight(String id, String airline, String flightNumber, String seats, String confirmationNum,
                  String departureCityAirport, String departureDate, String departureTime,
                  String departureTerminal, String departureGate, String arrivalCityAirport,
                  String arrivalDate, String arrivalTime, String arrivalTerminal, String arrivalGate) {
        this.flightId = id;
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
        startDate = this.departureDate;
        endDate = this.arrivalDate;
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

}