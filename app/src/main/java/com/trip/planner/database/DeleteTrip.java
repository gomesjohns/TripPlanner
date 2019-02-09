package com.trip.planner.database;

import com.google.firebase.database.FirebaseDatabase;

public class DeleteTrip
{
    public DeleteTrip(String id)
    {
        //Get database
        FirebaseDatabase.getInstance().getReference("TripDatabase")
                .child(id)//Get id of the item clicked
                .removeValue();//Remove value from db

    }
}
