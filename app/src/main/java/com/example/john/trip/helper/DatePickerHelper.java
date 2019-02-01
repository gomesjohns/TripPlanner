package com.example.john.trip.helper;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.design.widget.TextInputEditText;

import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DatePickerHelper {
    //Global Vars
    private Calendar calendarDepart, calendarReturn;
    private int year;
    private int month;
    private int day;
    private DatePickerDialog datePickerDialog;
    public String dateFormatted;

    ClearText clearText;

    //Constructor
    public DatePickerHelper() {

    }

    public void datePickerDialog(Context context, final TextInputEditText textInput) {
        //--------------------------------------Departure------------------------------------
        if (textInput.getHint().toString().toLowerCase().equals("depart")) {
            if (calendarDepart != null) {
                year = calendarDepart.get(Calendar.YEAR);
                month = calendarDepart.get(Calendar.MONTH);
                day = calendarDepart.get(Calendar.DAY_OF_MONTH);
            } else {
                calendarDepart = Calendar.getInstance();
                year = calendarDepart.get(Calendar.YEAR);
                month = calendarDepart.get(Calendar.MONTH);
                day = calendarDepart.get(Calendar.DAY_OF_MONTH);
            }

            //Date picker dialog
            datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendarDepart.set(year, month, dayOfMonth, 0, 0, 0);
                    Date selectedDate = calendarDepart.getTime();

                    DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy",Locale.ENGLISH);
                    dateFormatted = dateFormat.format(selectedDate);

                    dateValidation(textInput);
                }
            }, year, month, day);
            minMaxDatePicker(textInput); //Set min date allowed
            datePickerDialog.show();
        }
        //--------------------------------------Return------------------------------------
        else if (textInput.getHint().toString().toLowerCase().equals("return")) {
            calendarReturn = Calendar.getInstance();
            if (calendarDepart == null) {
                year = calendarReturn.get(Calendar.YEAR);
                month = calendarReturn.get(Calendar.MONTH);
                day = calendarReturn.get(Calendar.DAY_OF_MONTH);
            } else {
                year = calendarDepart.get(Calendar.YEAR);
                month = calendarDepart.get(Calendar.MONTH);
                day = calendarDepart.get(Calendar.DAY_OF_MONTH);
            }

            //Date picker dialog
            datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendarReturn.set(year, month, dayOfMonth, 0, 0, 0);
                    Date selectedDate = calendarReturn.getTime();

                    DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy",Locale.ENGLISH);
                    dateFormatted = dateFormat.format(selectedDate);

                    dateValidation(textInput);
                }
            }, year, month, day);
            minMaxDatePicker(textInput); //Set min date allowed
            datePickerDialog.show();
        }
    }

    //Method to check if departure date is before return date, if not set error
    private void dateValidation(TextInputEditText textInputEditText) {
        if (calendarReturn != null && calendarDepart != null) {
            if (calendarReturn.getTime().before(calendarDepart.getTime())) {
                textInputEditText.setFocusableInTouchMode(true);
                textInputEditText.requestFocus();
                textInputEditText.setError("Invalid date");
                clearText = new ClearText();
                clearText.ClearTextEditText(textInputEditText);
            } else {
                textInputEditText.setText(dateFormatted);
                textInputEditText.setError(null);
            }
        } else {
            textInputEditText.setText(dateFormatted);
            textInputEditText.setError(null);
        }
    }

    //Method to set minimum date on date picker dialog
    private void minMaxDatePicker(TextInputEditText textInputEditText) {
        if (textInputEditText.getHint().equals("Return") && calendarDepart != null) {
            datePickerDialog.getDatePicker().setMinDate(calendarDepart.getTimeInMillis());
        } else {
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        }
    }

    public void setCalendarDepart() {
        calendarDepart = Calendar.getInstance();
        year = calendarDepart.get(Calendar.YEAR);
        month = calendarDepart.get(Calendar.MONTH);
        day = calendarDepart.get(Calendar.DAY_OF_MONTH);
    }

    public void setCalendarReturn() {
        calendarReturn = Calendar.getInstance();
        if (calendarDepart == null) {
            year = calendarReturn.get(Calendar.YEAR);
            month = calendarReturn.get(Calendar.MONTH);
            day = calendarReturn.get(Calendar.DAY_OF_MONTH);
        } else {
            year = calendarDepart.get(Calendar.YEAR);
            month = calendarDepart.get(Calendar.MONTH);
            day = calendarDepart.get(Calendar.DAY_OF_MONTH);
        }
    }
}
