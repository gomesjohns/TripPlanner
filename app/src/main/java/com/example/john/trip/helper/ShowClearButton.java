package com.example.john.trip.helper;

import android.view.View;

import static com.example.john.trip.NewTripActivity.buttonClearText1;
import static com.example.john.trip.NewTripActivity.buttonClearText2;
import static com.example.john.trip.NewTripActivity.buttonClearText3;

public class ShowClearButton
{
    public ShowClearButton()
    {}

    //Method to show clear text button
    public void showBtn(String textBox)
    {
        if( textBox.equals("destination"))
        {
            buttonClearText1.setVisibility(View.VISIBLE);
        }
        else if (  textBox.equals("depart"))
        {
            buttonClearText2.setVisibility(View.VISIBLE);
        }
        else if ( textBox.equals("return"))
        {
            buttonClearText3.setVisibility(View.VISIBLE);
        }
        else if(textBox.equals("allBtn"))
        {
            buttonClearText1.setVisibility(View.VISIBLE);
            buttonClearText2.setVisibility(View.VISIBLE);
            buttonClearText3.setVisibility(View.VISIBLE);
        }
    }
}
