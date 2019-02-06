package com.example.john.trip.helper;

import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import java.util.ArrayList;

public class ClearButton
{
    ArrayList<Button> buttonArrayList;

    public ClearButton(ArrayList<Button> buttonArrayList)
    {
        this.buttonArrayList = new ArrayList<>();
        this.buttonArrayList = buttonArrayList;
    }


    //Method to show clear text button
    public void showBtn(String tag)
    {
        for(int i= 0; i<buttonArrayList.size(); i++)
        {
            if(tag.equals(buttonArrayList.get(i).getTag()))
            {
                buttonArrayList.get(i).setVisibility(View.VISIBLE);
            }
        }
    }

    //Method to show clear text button
    public void hideBtn(String tag)
    {
        for(int i= 0; i<buttonArrayList.size(); i++)
        {
            if(tag.equals(buttonArrayList.get(i).getTag()))
            {
                buttonArrayList.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }
}
