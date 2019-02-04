package com.example.john.trip.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Trip implements Parcelable {
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

    protected Trip(Parcel in) {
        tripId = in.readString();
        tripName = in.readString();
        startDate = in.readString();
        endDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tripId);
        dest.writeString(tripName);
        dest.writeString(startDate);
        dest.writeString(endDate);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Trip> CREATOR = new Parcelable.Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };
}