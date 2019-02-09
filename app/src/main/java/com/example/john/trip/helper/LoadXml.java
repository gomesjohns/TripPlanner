package com.example.john.trip.helper;

import android.content.Context;
import android.content.res.Resources;

import com.example.john.trip.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LoadXml {
    private ArrayList<String> arrayList;

    public LoadXml()
    {}

    public ArrayList<String> xmlLoad(Context context, int resources) throws XmlPullParserException, IOException
    {
        arrayList = new ArrayList<>();
        //Create ResourceParser for XML file
        XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
        XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
        InputStream inputStream = context.getResources().openRawResource(resources);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        xmlPullParser.setInput(reader);

        //Check state
        int eventType = xmlPullParser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            switch (eventType)
            {
                case XmlPullParser.START_TAG:
                    String name = xmlPullParser.getName();

                    if(name.equals("name"))
                    {
                        String names= xmlPullParser.nextText();
                        arrayList.add(names);
                    }
                case XmlPullParser.END_TAG:
            }
            eventType = xmlPullParser.next();
        }
        return arrayList;
    }
}

