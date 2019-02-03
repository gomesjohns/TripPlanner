package com.example.john.trip.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Trip implements Parcelable {
    String tripId;
    String tripName;
    String startDate;
    String endDate;
    ArrayList<Flight> flightList;
    ArrayList<Hotel> hotelArrayList;

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

    public void setFlightList(Flight flight) {
        if(this.flightList == null)
        {
            flightList = new ArrayList<>();
            this.flightList.add(flight);
        }
        else {
            this.flightList.add(flight);
        }

    }

    public void setHotelArrayList(Hotel hotel) {

        if(this.hotelArrayList == null)
        {
            hotelArrayList = new ArrayList<>();
            this.hotelArrayList.add(hotel);
        }else {
            this.hotelArrayList.add(hotel);
        }
    }

    public ArrayList<Flight> getFlightList() {
        return flightList;
    }

    public ArrayList<Hotel> getHotelArrayList() {
        return hotelArrayList;
    }

    protected Trip(Parcel in) {
        tripId = in.readString();
        tripName = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        if (in.readByte() == 0x01) {
            flightList = new ArrayList<Flight>();
            in.readList(flightList, Flight.class.getClassLoader());
        } else {
            flightList = null;
        }
        if (in.readByte() == 0x01) {
            hotelArrayList = new ArrayList<Hotel>();
            in.readList(hotelArrayList, Hotel.class.getClassLoader());
        } else {
            hotelArrayList = null;
        }
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
        if (flightList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(flightList);
        }
        if (hotelArrayList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(hotelArrayList);
        }
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