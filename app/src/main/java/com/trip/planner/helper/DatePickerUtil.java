package com.trip.planner.helper;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.design.widget.TextInputEditText;

import com.trip.planner.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DatePickerUtil {
    //Global Vars
    private Calendar calendar1, calendar2;
    private int year;
    private int month;
    private int day;
    private DatePickerDialog datePickerDialog;
    public String dateFormatted;
    //ClearText clearText;

    //Constructor
    public DatePickerUtil() {

    }

    public void datePickerDialog(Context context, final TextInputEditText textInput) {
        //--------------------------------------Departure------------------------------------
        if (textInput.getTag().toString().equals("departureDate")||
            textInput.getTag().toString().equals("arrivalDate") ||
            textInput.getTag().toString().equals("checkInDate") ||
            textInput.getTag().toString().equals("checkOutDate")||
            textInput.getTag().toString().equals("pickUpDate")||
            textInput.getTag().toString().equals("dropOffDate")){
            if (calendar1 != null) {
                year = calendar1.get(Calendar.YEAR);
                month = calendar1.get(Calendar.MONTH);
                day = calendar1.get(Calendar.DAY_OF_MONTH);
            } else {
                calendar1 = Calendar.getInstance();
                year = calendar1.get(Calendar.YEAR);
                month = calendar1.get(Calendar.MONTH);
                day = calendar1.get(Calendar.DAY_OF_MONTH);
            }

            //Date picker dialog
            datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                    calendar1.set(year, month, dayOfMonth, 0, 0, 0);
                    Date selectedDate = calendar1.getTime();

                    DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_PATTERN,Locale.ENGLISH);
                    dateFormatted = dateFormat.format(selectedDate);

                    dateValidation(textInput);
                }
            }, year, month, day);
            minMaxDatePicker(textInput); //Set min date allowed
            datePickerDialog.show();
        }
        //--------------------------------------Return------------------------------------
        else if (textInput.getHint().toString().toLowerCase().equals("return")) {
            calendar2 = Calendar.getInstance();
            if (calendar1 == null) {
                year = calendar2.get(Calendar.YEAR);
                month = calendar2.get(Calendar.MONTH);
                day = calendar2.get(Calendar.DAY_OF_MONTH);
            } else {
                year = calendar1.get(Calendar.YEAR);
                month = calendar1.get(Calendar.MONTH);
                day = calendar1.get(Calendar.DAY_OF_MONTH);
            }

            //Date picker dialog
            datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                    calendar2.set(year, month, dayOfMonth, 0, 0, 0);
                    Date selectedDate = calendar2.getTime();

                    DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_PATTERN,Locale.ENGLISH);
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
        if (calendar2 != null && calendar1 != null) {
            if (calendar2.getTime().before(calendar1.getTime())) {
                textInputEditText.setFocusableInTouchMode(true);
                textInputEditText.requestFocus();
                textInputEditText.setError("Invalid date");
                //clearText= new ClearText();
                //clearText.clear(textInputEditText.getTag().toString(), textInputEditText);
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
        if (textInputEditText.getHint().equals("Return") && calendar1 != null) {
            datePickerDialog.getDatePicker().setMinDate(calendar1.getTimeInMillis());
        } else {
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        }
    }

    public void setCalendarDepart() {
        calendar1 = Calendar.getInstance();
        year = calendar1.get(Calendar.YEAR);
        month = calendar1.get(Calendar.MONTH);
        day = calendar1.get(Calendar.DAY_OF_MONTH);
    }

    public void setCalendarReturn() {
        calendar2 = Calendar.getInstance();
        if (calendar1 == null) {
            year = calendar2.get(Calendar.YEAR);
            month = calendar2.get(Calendar.MONTH);
            day = calendar2.get(Calendar.DAY_OF_MONTH);
        } else {
            year = calendar1.get(Calendar.YEAR);
            month = calendar1.get(Calendar.MONTH);
            day = calendar1.get(Calendar.DAY_OF_MONTH);
        }
    }
}
