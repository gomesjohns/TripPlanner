package com.example.john.trip.model;

public class Lodging extends Trip {
    private String lodgingName;
    private String lodgingLocation;
    private String checkInDate;
    private String checkInTime;
    private String checkOutDate;
    private String checkOutTime;

    public Lodging()
    {}
    public Lodging(String lodgingName, String lodgingLocation, String checkInDate,
                   String checkInTime, String checkOutDate, String checkOutTime) {
        this.lodgingName = lodgingName;
        this.lodgingLocation = lodgingLocation;
        this.checkInDate = checkInDate;
        this.checkInTime = checkInTime;
        this.checkOutDate = checkOutDate;
        this.checkOutTime = checkOutTime;
    }

    public String getLodgingName() {
        return lodgingName;
    }

    public String getLodgingLocation() {
        return lodgingLocation;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }
}