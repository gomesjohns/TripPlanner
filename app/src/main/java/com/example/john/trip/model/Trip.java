package com.example.john.trip.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Trip {
    String tripId;
    String tripName;
    String tripLocation;
    String startDate;
    String endDate;

    public Trip() {
    }

    public Trip(String tripId, String tripName,String tripLocation, String start, String end) {
        this.tripId = tripId;
        this.tripName = tripName;
        this.tripLocation= tripLocation;
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
    public String getTripLocation() {
        return tripLocation;
    }
}