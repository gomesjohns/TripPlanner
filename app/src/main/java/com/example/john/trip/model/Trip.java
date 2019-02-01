package com.example.john.trip.model;

public class Trip {
    String tripId;
    String tripName;
    String startDate;
    String endDate;

    public Trip() {
    }

    public Trip(String tripId, String tripName, String start, String end) {
        this.tripId = tripId;
        this.tripName = tripName;
        this.startDate = start;
        this.endDate = end;
    }

    public String getTripName() {
        return tripName;
    }
    public String getTripId() {
        return tripId;
    }
    public String getStartDate() {
        return startDate;
    }
    public String getEndDate() {
        return endDate;
    }
}
