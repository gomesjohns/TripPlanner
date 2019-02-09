package com.trip.planner.model;

public class Lodging extends Trip {
    private String lodgingId;
    private String lodgingName;
    private String lodgingLocation;
    private String checkInDate;
    private String checkInTime;
    private String checkOutDate;
    private String checkOutTime;
    private boolean lodgingCopy;

    public Lodging()
    {}
    public Lodging(String id, String lodgingName, String lodgingLocation, String checkInDate,
                   String checkInTime, String checkOutDate, String checkOutTime, String startD,
                   String endD) {
        this.lodgingId = id;
        this.lodgingName = lodgingName;
        this.lodgingLocation = lodgingLocation;
        this.checkInDate = checkInDate;
        this.checkInTime = checkInTime;
        this.checkOutDate = checkOutDate;
        this.checkOutTime = checkOutTime;
        startDate = startD;
        endDate = endD;
    }

    public String getLodgingId() {
        return lodgingId;
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

    public boolean isLodgingCopy() {
        return lodgingCopy;
    }

    public void setLodgingCopy(boolean lodgingCopy) {
        this.lodgingCopy = lodgingCopy;
    }
}