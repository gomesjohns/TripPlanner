package com.example.john.trip.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SelectedTrip implements Parcelable {
    private String tripId;
    private String tripName;
    private String dateRange;
    private String tripDurationTimeRemaining;
    private String tripImage;

    public SelectedTrip(String tripId, String tripName, String dateRange, String tripDurationTimeRemaining, String tripImage) {
        this.tripId = tripId;
        this.tripName = tripName;
        this.dateRange = dateRange;
        this.tripDurationTimeRemaining = tripDurationTimeRemaining;
        this.tripImage = tripImage;
    }

    public String getTripId() {
        return tripId;
    }

    public String getDateRange() {
        return dateRange;
    }

    public String getTripDurationTimeRemaining() {
        return tripDurationTimeRemaining;
    }

    public String getTripImage() {
        return tripImage;
    }

    public String getTripName() {
        return tripName;
    }



    protected SelectedTrip(Parcel in) {
        tripId = in.readString();
        tripName = in.readString();
        dateRange = in.readString();
        tripDurationTimeRemaining = in.readString();
        tripImage = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tripId);
        dest.writeString(tripName);
        dest.writeString(dateRange);
        dest.writeString(tripDurationTimeRemaining);
        dest.writeString(tripImage);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SelectedTrip> CREATOR = new Parcelable.Creator<SelectedTrip>() {
        @Override
        public SelectedTrip createFromParcel(Parcel in) {
            return new SelectedTrip(in);
        }

        @Override
        public SelectedTrip[] newArray(int size) {
            return new SelectedTrip[size];
        }
    };
}