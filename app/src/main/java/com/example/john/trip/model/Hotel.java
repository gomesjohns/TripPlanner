package com.example.john.trip.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Hotel implements Parcelable {
    private String hotelName;
    private String hotelLocation;
    private String checkInDate;
    private String checkInTime;
    private String checkOutDate;
    private String checkOutTime;

    public Hotel()
    {}
    public Hotel(String hotelName, String hotelLocation, String checkInDate,
                 String checkInTime, String checkOutDate, String checkOutTime) {
        this.hotelName = hotelName;
        this.hotelLocation = hotelLocation;
        this.checkInDate = checkInDate;
        this.checkInTime = checkInTime;
        this.checkOutDate = checkOutDate;
        this.checkOutTime = checkOutTime;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelLocation() {
        return hotelLocation;
    }

    public void setHotelLocation(String hotelLocation) {
        this.hotelLocation = hotelLocation;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    protected Hotel(Parcel in) {
        hotelName = in.readString();
        hotelLocation = in.readString();
        checkInDate = in.readString();
        checkInTime = in.readString();
        checkOutDate = in.readString();
        checkOutTime = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hotelName);
        dest.writeString(hotelLocation);
        dest.writeString(checkInDate);
        dest.writeString(checkInTime);
        dest.writeString(checkOutDate);
        dest.writeString(checkOutTime);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Hotel> CREATOR = new Parcelable.Creator<Hotel>() {
        @Override
        public Hotel createFromParcel(Parcel in) {
            return new Hotel(in);
        }

        @Override
        public Hotel[] newArray(int size) {
            return new Hotel[size];
        }
    };
}