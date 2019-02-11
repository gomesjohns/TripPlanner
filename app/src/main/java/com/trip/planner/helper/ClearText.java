package com.trip.planner.helper;

import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;

import java.util.ArrayList;

public class ClearText
{
    public void clear(String buttonTag, ArrayList<FrameLayout> inputLayoutChildrenList)
    {
        ArrayList<View> textViewChildList= new ArrayList<>();
        //Get child from inputLayoutChildren (AutoCompleteEditText, TextInputEditText)
        for(int z=0; z<inputLayoutChildrenList.size();z++)
        {
            for(int i=0; i<inputLayoutChildrenList.get(z).getChildCount(); i++)
            {
                //Add the text view child
                textViewChildList.add(inputLayoutChildrenList.get(z).getChildAt(i));
            }
        }

        //For every views in viewArrayList, if AutoComplete or TextInputEditText, cast, then set text null
        for(int i=0; i<textViewChildList.size(); i++)
        {
            if(buttonTag.equals(textViewChildList.get(i).getTag()))
            {
                if(textViewChildList.get(i) instanceof AutoCompleteTextView) {
                    ((AutoCompleteTextView) textViewChildList.get(i)).setText(null);
                }else {
                    ((TextInputEditText) textViewChildList.get(i)).setText(null);
                }
            }
        }
    }

}
