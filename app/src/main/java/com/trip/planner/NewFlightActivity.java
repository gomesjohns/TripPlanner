package com.trip.planner;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.trip.planner.helper.ClearButton;
import com.trip.planner.helper.CloseKeyboard;
import com.trip.planner.helper.DatePickerUtil;
import com.trip.planner.helper.InputValidation;
import com.trip.planner.helper.LoadXml;
import com.trip.planner.helper.TimePickerUtil;
import com.trip.planner.model.Flight;
import com.trip.planner.model.SelectedTrip;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class NewFlightActivity extends AppCompatActivity {
    private TextInputLayout airlineNameLayout, flightNumLayout, seatLayout, confirmationNumLayout,
    departureCityAirportLayout, departureDateLayout, departureTimeLayout, departureTermLayout,
    departureGateLayout,arrivalCityAirportLayout, arrivalDateLayout, arrivalTimeLayout, arrivalTermLayout,
            arrivalGateLayout;
    private TextInputEditText departureDate, flightNumber, seats, confirmationNum, departureTime, departureTerminal, departureGate,
            arrivalDate, arrivalTime, arrivalTerminal, arrivalGate;
    private AutoCompleteTextView airlineAutoComplete, departureCityAirport, arrivalCityAirport;
    private Flight flight;
    private DatabaseReference databaseReference;
    private SelectedTrip myTrip;
    private InputValidation inputValidation;
    private DatePickerUtil datePickerUtil;
    private TimePickerUtil timePickerUtil;
    private CloseKeyboard closeKeyboardHelper;
    private ArrayList<String> airlineArrayList;
    private ArrayList<String> airportArrayList;
    private LoadXml loadXml;
    private ClearButton clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_flight);

        //Init views
        initViews();
        //Init helpers
        initHelperClasses();
        //Load XML
        try {
            setAirlineArrayAdapter();
            setAirportArrayAdapter();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        //Ger extras
        getExtras();
        //Init Db
        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_REFERENCE);
        //Init listeners
        initListeners();
        //Generate clear buttons
        generateClearBtn();
        //Back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //----------------------------------------------------------------------------------------------
    //Override methods
    //----------------------------------------------------------------------------------------------
    //Action bar menu
    @Override //Create save trip menu from XML
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override //Back button
    public boolean onSupportNavigateUp() {
        Intent myIntent = new Intent(NewFlightActivity.this, TripDetailsActivity.class);
        myIntent.putExtra("tripObj", myTrip);
        NewFlightActivity.this.startActivity(myIntent);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getTitle() != null) {
            if (menuItem.getTitle().equals("save")) {
                addFlight();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    //----------------------------------------------------------------------------------------------
    //Private methods
    //----------------------------------------------------------------------------------------------
    //Initialize views
    private void initViews() {
        //Views
        airlineAutoComplete = findViewById(R.id.addFlight_textInputAutocomplete_airline);
        flightNumber = findViewById(R.id.addFlight_textInputEditText_flightNumber);
        seats = findViewById(R.id.addFlight_textInputEditText_seats);
        confirmationNum = findViewById(R.id.addFlight_textInputEditText_confirmationNum);
        departureCityAirport = findViewById(R.id.addFlight_textInputAutocomplete_departureCityAirport);
        departureDate = findViewById(R.id.addFlight_textInputEditText_departureDate);
        departureTime = findViewById(R.id.addFlight_textInputEditText_departureTime);
        departureTerminal = findViewById(R.id.addFlight_textInputEditText_departureTerminal);
        departureGate = findViewById(R.id.addFlight_textInputEditText_departureGate);
        arrivalCityAirport = findViewById(R.id.addFlight_textInputAutocomplete_arrivalCityAirport);
        arrivalDate = findViewById(R.id.addFlight_textInputEditText_arrivalDate);
        arrivalTime = findViewById(R.id.addFlight_textInputEditText_arrivalTime);
        arrivalTerminal = findViewById(R.id.addFlight_textInputEditText_arrivalTerminal);
        arrivalGate = findViewById(R.id.addFlight_textInputEditText_arrivalGate);
        //Layout
        airlineNameLayout =findViewById(R.id.addFlight_textInputLayout_airline);
        flightNumLayout = findViewById(R.id.addFlight_textInputLayout_flightNumber);
        seatLayout = findViewById(R.id.addFlight_textInputLayout_seats);
        confirmationNumLayout = findViewById(R.id.addFlight_textInputLayout_confirmationNum);
        departureCityAirportLayout = findViewById(R.id.addFlight_textInputLayout_departureCityAirport);
        departureDateLayout = findViewById(R.id.addFlight_textInputLayout_departureDate);
        departureTimeLayout = findViewById(R.id.addFlight_textInputLayout_departureTime);
        departureTermLayout = findViewById(R.id.addFlight_textInputLayout_departureTerminal);
        departureGateLayout = findViewById(R.id.addFlight_textInputLayout_departureGate);
        arrivalCityAirportLayout = findViewById(R.id.addFlight_textInputLayout_arrivalCityAirport);
        arrivalDateLayout = findViewById(R.id.addFlight_textInputLayout_arrivalDate);
        arrivalTimeLayout = findViewById(R.id.addFlight_textInputLayout_arrivalTime);
        arrivalTermLayout = findViewById(R.id.addFlight_textInputLayout_arrivalTerminal);
        arrivalGateLayout = findViewById(R.id.addFlight_textInputLayout_arrivalGate);
    }

    //Get extras
    private void getExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //Get
            myTrip = extras.getParcelable("tripObj");
        }
    }

    //Init all the helper classes
    private void initHelperClasses() {
        closeKeyboardHelper = new CloseKeyboard();
        datePickerUtil = new DatePickerUtil();
        timePickerUtil = new TimePickerUtil();
        inputValidation = new InputValidation();
        clearButton = new ClearButton();
        loadXml = new LoadXml();
    }

    //Listeners
    private void initListeners() {
        final ArrayList<View> listenerList = new ArrayList<>();
        listenerList.add(airlineAutoComplete);
        listenerList.add(flightNumber);
        listenerList.add(seats);
        listenerList.add(confirmationNum);
        listenerList.add(departureCityAirport);
        listenerList.add(departureTerminal);
        listenerList.add(departureGate);
        listenerList.add(arrivalCityAirport);
        listenerList.add(arrivalTerminal);
        listenerList.add(arrivalGate);

        for(int i=0; i<listenerList.size(); i++)
        {
            final int finalI = i;
            if(listenerList.get(i) instanceof AutoCompleteTextView)
            {
                ((AutoCompleteTextView)listenerList.get(i)).addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        clearButton.showBtn(listenerList.get(finalI).getTag().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }else{
                ((TextInputEditText)listenerList.get(i)).addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        clearButton.showBtn(listenerList.get(finalI).getTag().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        }
        //Date pickers
        departureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboardHelper.close(NewFlightActivity.this, v);
                //Departure date picker- uses DatePickerUtil
                datePickerUtil.datePickerDialog(NewFlightActivity.this, departureDate);
                //Listener to show clear text button on text change
                departureDate.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        clearButton.showBtn(departureDate.getTag().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });
        arrivalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboardHelper.close(NewFlightActivity.this, v);
                //Departure date picker- uses DatePickerUtil
                datePickerUtil.datePickerDialog(NewFlightActivity.this, arrivalDate);
                //Listener to show clear text button on text change
                departureDate.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        clearButton.showBtn(arrivalDate.getTag().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });
        //Time pickers
        departureTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboardHelper.close(NewFlightActivity.this, v);
                timePickerUtil.timePickerDialog(NewFlightActivity.this, departureTime);
                departureTime.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        clearButton.showBtn(departureTime.getTag().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });
        arrivalTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboardHelper.close(NewFlightActivity.this, v);
                timePickerUtil.timePickerDialog(NewFlightActivity.this, arrivalTime);
                arrivalTime.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        clearButton.showBtn(arrivalTime.getTag().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });
    }

    //Generate clear text button
    private void generateClearBtn()
    {
        ArrayList<TextInputLayout> inputLayoutList = new ArrayList<>();
        inputLayoutList.add(airlineNameLayout);
        inputLayoutList.add(flightNumLayout);
        inputLayoutList.add(seatLayout);
        inputLayoutList.add(confirmationNumLayout);
        inputLayoutList.add(departureCityAirportLayout);
        inputLayoutList.add(departureDateLayout);
        inputLayoutList.add(departureTimeLayout);
        inputLayoutList.add(departureTermLayout);
        inputLayoutList.add(departureGateLayout);
        inputLayoutList.add(arrivalCityAirportLayout);
        inputLayoutList.add(arrivalDateLayout);
        inputLayoutList.add(arrivalTimeLayout);
        inputLayoutList.add(arrivalTermLayout);
        inputLayoutList.add(arrivalGateLayout);


        clearButton.generateButtons(NewFlightActivity.this, inputLayoutList);
    }

    //-------------------------------Load airport/airline list XML----------------------------------
    //Set airline array adapter
    private void setAirlineArrayAdapter() throws IOException, XmlPullParserException {

        ArrayList<String> airlineNameList = new ArrayList<>();
        airlineArrayList = new ArrayList<>();
        airlineArrayList = loadXml.xmlLoad(NewFlightActivity.this, R.raw.airline_list);

        for (int i = 0; i < airlineArrayList.size(); i++) {
            airlineNameList.add(airlineArrayList.get(i).trim());
        }

        ArrayAdapter<String> airlineArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_expandable_list_item_1, airlineNameList);
        airlineAutoComplete.setAdapter(airlineArrayAdapter);
    }

    //Set airport array adapter
    private void setAirportArrayAdapter() throws IOException, XmlPullParserException {

        ArrayList<String> airportList = new ArrayList<>();
        airportArrayList = new ArrayList<>();
        airportArrayList = loadXml.xmlLoad(NewFlightActivity.this, R.raw.airport_list);

        for (int i = 0; i < airportArrayList.size(); i++) {
            airportList.add(airportArrayList.get(i).trim());
        }

        ArrayAdapter<String> airPortArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_expandable_list_item_1, airportList);
        departureCityAirport.setAdapter(airPortArrayAdapter);
        arrivalCityAirport.setAdapter(airPortArrayAdapter);
    }

    //-------------------------------------Add flight to DB-----------------------------------------
    //Method to add flight to the database
    private void addFlight() {
        String airline_text = airlineAutoComplete.getText().toString();
        String flightNum_text = flightNumber.getText().toString();
        String seats_text = seats.getText().toString();
        String confirmationNum_text = confirmationNum.getText().toString();
        String dCityAirport_text = departureCityAirport.getText().toString();
        String dDate_text = departureDate.getText().toString();
        String dTime_text = departureTime.getText().toString();
        String dTerm_text = departureTerminal.getText().toString();
        String dGate_text = departureGate.getText().toString();
        String aCityAirport_text = arrivalCityAirport.getText().toString();
        String aDate_text = arrivalDate.getText().toString();
        String aTime_text = arrivalTime.getText().toString();
        String aTerm_text = arrivalTerminal.getText().toString();
        String aGate_text = arrivalGate.getText().toString();

        inputValidation.setErrorCount();
        inputValidation.validateInputAutoComplete(airlineAutoComplete, airline_text);
        inputValidation.validateInputAutoComplete(departureCityAirport, dCityAirport_text);
        inputValidation.validateInputAutoComplete(arrivalCityAirport, aCityAirport_text);
        inputValidation.validateTextViewInput(flightNumber, flightNum_text);
        inputValidation.validateTextViewInput(departureDate, dDate_text);
        inputValidation.validateTextViewInput(departureTime, dTime_text);
        inputValidation.validateTextViewInput(arrivalDate, aDate_text);
        inputValidation.validateTextViewInput(arrivalTime, aTime_text);

        if (inputValidation.getErrorCount() == 0) {
            String id = databaseReference.push().getKey();
            flight = new Flight(id, airline_text, flightNum_text, seats_text, confirmationNum_text
                    , dCityAirport_text, dDate_text, dTime_text, dTerm_text, dGate_text, aCityAirport_text,
                    aDate_text, aTime_text, aTerm_text, aGate_text);


            databaseReference.child(myTrip.getTripId()).child("flight" + id).setValue(flight);
            Toast.makeText(NewFlightActivity.this, "Flight Added Successfully",
                    Toast.LENGTH_LONG).show();
            //Start TripDetailsActivity
            Intent myIntent = new Intent(NewFlightActivity.this, TripDetailsActivity.class);
            myIntent.putExtra("tripObj", myTrip);
            NewFlightActivity.this.startActivity(myIntent);
        }
    }

}
