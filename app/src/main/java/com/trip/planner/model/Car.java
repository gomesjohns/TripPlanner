package com.trip.planner.model;

public class Car extends Trip{
    private String companyName;
    private String confirmationNum;
    private String locationPickup;
    private String locationDropOff;
    private String pickupDate;
    private String pickupTime;
    private String dropOffDate;
    private String dropOffTime;
    private String notes;

    public Car(String companyName, String confirmationNum, String locationPickup, String locationDropOff,
               String pickupDate, String pickupTime, String dropOffDate, String dropOffTime, String notes) {
        this.companyName = companyName;
        this.confirmationNum = confirmationNum;
        this.locationPickup = locationPickup;
        this.locationDropOff = locationDropOff;
        this.pickupDate = pickupDate;
        this.pickupTime = pickupTime;
        this.dropOffDate = dropOffDate;
        this.dropOffTime = dropOffTime;
        this.notes = notes;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getConfirmationNum() {
        return confirmationNum;
    }

    public String getLocationPickup() {
        return locationPickup;
    }

    public String getLocationDropOff() {
        return locationDropOff;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public String getDropoffDate() {
        return dropOffDate;
    }

    public String getDropoffTime() {
        return dropOffTime;
    }

    public String getNotes() {
        return notes;
    }
}
