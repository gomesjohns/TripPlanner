package com.trip.planner.helper;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.trip.planner.NewLodgingActivity;
import com.trip.planner.R;

import java.util.ArrayList;

public class ClearButton
{
    ArrayList<Button> buttonArrayList;
    private ClearText clearText;
    private ArrayList<FrameLayout> inputLayoutChildren;

    public ClearButton()
    {
        clearText = new ClearText();
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

    public void generateButtons(Context context, ArrayList<TextInputLayout> textInputLayoutArrayList)
    {
        inputLayoutChildren = new ArrayList<>();
        //Create buttons from textInputLayout
        for(int i=0; i<textInputLayoutArrayList.size(); i++)
        {
            //----------------------------Button creation start-------------------------------------
            final Button mButton= new Button(context);
            //set button drawable, bg color, tag(same as inputLayout)
            mButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_clear_text,0,0,0);
            mButton.setBackgroundColor(context.getResources().getColor(R.color.transparent));
            mButton.setTag(textInputLayoutArrayList.get(i).getTag());
            //create layout for button
            LinearLayout.LayoutParams btnParam= new LinearLayout.LayoutParams(70, 70);
            btnParam.gravity = Gravity.RIGHT;
            btnParam.setMargins(0,-140,50,0);
            //set layout parameters to the button
            mButton.setLayoutParams(btnParam);
            //add button to textInputLayout
            textInputLayoutArrayList.get(i).addView(mButton);
            buttonArrayList.add(mButton);
            //hide the button
            mButton.setVisibility(View.INVISIBLE);
            //-----------------------------Button creation end--------------------------------------

            //Get children of textInputLayout
            for(int x=0; x<textInputLayoutArrayList.get(i).getChildCount(); x++)
            {
                //Add children to inputLayoutChildren array list to find children
                if(textInputLayoutArrayList.get(i).getChildAt(x) instanceof FrameLayout)
                {
                    inputLayoutChildren.add((FrameLayout) textInputLayoutArrayList.get(i).getChildAt(x));
                }
            }

            //Set click listener to the clear text buttons
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Run clear text method on click, passing button tag and view list
                    clearText.clear(mButton.getTag().toString(), inputLayoutChildren);
                }
            });
        }
    }
}
