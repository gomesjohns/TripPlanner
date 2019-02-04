package com.example.john.trip.helper;

import com.example.john.trip.Constants;
import com.example.john.trip.model.Trip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeRemainingCalculation
{
    //Global Vars
    ArrayList<Trip> trips;
    Date departureDate, returnDate;
    Calendar startCalDate, endCalDate;
    int pos= 0;
    private int daysRemaining, hoursRemaining, tripDuration;

    //Constructor
    public TimeRemainingCalculation(ArrayList<Trip> trips)
    {
        this.trips = trips;
    }

    //Calculate time from today to departure date
    public void timeCalculation(int position)
    {
        String departureFromDB= trips.get(position).getStartDate();//Get start date of trip from database
        String returnFromDB = trips.get(position).getEndDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_PATTERN); //Format date from database using specified pattern

        //Parse departure date, calculate days
        try
        {
            departureDate = simpleDateFormat.parse(departureFromDB);
            startCalDate= Calendar.getInstance();
            startCalDate.setTime(departureDate);

            returnDate = simpleDateFormat.parse(returnFromDB);
            endCalDate = Calendar.getInstance();
            endCalDate.setTime(returnDate);

            //Calculate days and hours remaining between system's current date and start date of trip
            daysRemaining = (int) TimeUnit.MILLISECONDS.toDays(startCalDate.getTimeInMillis()- System.currentTimeMillis());
            hoursRemaining = (int) TimeUnit.MILLISECONDS.toHours(startCalDate.getTimeInMillis()- System.currentTimeMillis());

            //Calculate days between departure and return
            tripDuration = (int)TimeUnit.MILLISECONDS.toDays(endCalDate.getTimeInMillis()- startCalDate.getTimeInMillis());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

    //----------------------------------------PRIVATE METHODS---------------------------------------
    private int getTripDurationText()
    {
        return tripDuration;
    }
    public String getTripDayYearText()
    {
        if (daysRemaining == 1 || tripDuration==1) {
            return "day";
        } else {
            return "days";
        }
    }
    private String getDaysRemainingText()
    {
        if (hoursRemaining <= 0 && hoursRemaining >-24) {
            return ", today";
        } else if (daysRemaining < 0) {
            return "";
        } else if(hoursRemaining>0 && hoursRemaining <=24) {
            return ", tomorrow";
        } else{
            return ", in " + daysRemaining+ " "+getTripDayYearText();
        }
    }


    //----------------------------------------PUBLIC METHODS---------------------------------------
    public String tripDurationTimeRemainingText(int pos)
    {
        //Insert day text dynamically based on number of days
        timeCalculation(pos);
        return getTripDurationText()+" "+getTripDayYearText()+" trip"+getDaysRemainingText();
    }

}
