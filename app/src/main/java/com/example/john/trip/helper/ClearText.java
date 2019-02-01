package com.example.john.trip.helper;

import android.support.design.widget.TextInputEditText;
import android.widget.AutoCompleteTextView;

public class ClearText
{
    HideClearButton hideClearButton;

    public ClearText()
    {
        initHelperClasses();
    }

    //Init all the helper classes
    private void initHelperClasses()
    {
        hideClearButton= new HideClearButton();
    }

    public void ClearTextEditText(TextInputEditText textInputEditText)
    {
        textInputEditText.setText(null);
        hideClearButton.hideBtn(textInputEditText.getHint().toString().toLowerCase());
    }
    public void ClearTextAutoComplete(AutoCompleteTextView autoCompleteTextView)
    {
        autoCompleteTextView.setText(null);
        hideClearButton.hideBtn("destination");
    }
}
