package com.example.john.trip.helper;

import android.view.View;

import static com.example.john.trip.NewTripActivity.buttonClearText1;
import static com.example.john.trip.NewTripActivity.buttonClearText2;
import static com.example.john.trip.NewTripActivity.buttonClearText3;

public class HideClearButton
{
    DatePickerHelper datePickerHelper;

    public HideClearButton()
    {
        initHelper();
    }

    //Init all the helper classes
    private void initHelper()
    {

    }

    public void hideBtn(String text)
    {
        datePickerHelper = new DatePickerHelper();

        if(text.equals("destination"))
        {
            buttonClearText1.setVisibility(View.GONE);
        }
        else if (text.equals("depart"))
        {
            buttonClearText2.setVisibility(View.GONE);
            datePickerHelper.setCalendarDepart();
        }
        else if (text.equals("return"))
        {
            buttonClearText3.setVisibility(View.GONE);
            datePickerHelper.setCalendarReturn();
        }
        else if(text.equals("allBtn")) {
            buttonClearText1.setVisibility(View.GONE);
            buttonClearText2.setVisibility(View.GONE);
            buttonClearText3.setVisibility(View.GONE);
        }
    }
}
