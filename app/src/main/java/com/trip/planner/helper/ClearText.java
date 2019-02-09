package com.trip.planner.helper;

import android.support.design.widget.TextInputEditText;
import android.widget.AutoCompleteTextView;

public class ClearText
{
    public ClearText()
    {

    }

    public void clear(String tag, TextInputEditText textInputEditTexts)
    {
        if(tag.equals(textInputEditTexts.getTag()))
        {
            textInputEditTexts.setText(null);
        }
    }
    public void clear(String tag, AutoCompleteTextView textInputEditTexts)
    {
        if(tag.equals(textInputEditTexts.getTag()))
        {
            textInputEditTexts.setText(null);
        }
    }
}
