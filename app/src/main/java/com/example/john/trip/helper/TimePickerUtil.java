package com.example.john.trip.helper;

import android.app.TimePickerDialog;
import android.content.Context;
import android.support.design.widget.TextInputEditText;

import android.util.Log;
import android.widget.TimePicker;
import java.text.DateFormat;

import java.util.Calendar;

public class TimePickerUtil {
    private Calendar mCurrentTime;
    private int hour, min;
    public TimePickerUtil()
    {}
    public void timePickerDialog(Context context, TextInputEditText textInputEditText)
    {
        if(textInputEditText.getTag().toString().equals("checkIn"))
        {
            mCurrentTime = Calendar.getInstance();
            mCurrentTime.set(Calendar.HOUR_OF_DAY, 15);
            mCurrentTime.set(Calendar.MINUTE, 00);
            textInputEditText.setText(timeFormat());
        }
        else if(textInputEditText.getTag().toString().equals("checkOut"))
        {
            mCurrentTime = Calendar.getInstance();
            mCurrentTime.set(Calendar.HOUR_OF_DAY, 11);
            mCurrentTime.set(Calendar.MINUTE, 00);
            textInputEditText.setText(timeFormat());
        }
        else{
            if (mCurrentTime != null) {
                hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                min = mCurrentTime.get(Calendar.MINUTE);
                timePicker(context, textInputEditText);
            }
            else {
                mCurrentTime = Calendar.getInstance();
                hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                min = mCurrentTime.get(Calendar.MINUTE);
                timePicker(context, textInputEditText);
            }
        }

    }
    private String timeFormat()
    {
        return DateFormat.getTimeInstance(DateFormat.SHORT).format(mCurrentTime.getTime());
    }
    private void timePicker(Context context,final TextInputEditText textInputEditText)
    {
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog( context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                mCurrentTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                mCurrentTime.set(Calendar.MINUTE, selectedMinute);
                textInputEditText.setText(timeFormat());
            }
        }, hour, min, false);
        mTimePicker.show();
    }
}
