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
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.trip.planner.helper.ClearButton;
import com.trip.planner.helper.CloseKeyboard;
import com.trip.planner.helper.DatePickerUtil;
import com.trip.planner.helper.GooglePlaceApi;
import com.trip.planner.helper.InputValidation;
import com.trip.planner.helper.LoadXml;
import com.trip.planner.helper.TimePickerUtil;
import com.trip.planner.model.Car;
import com.trip.planner.model.Flight;
import com.trip.planner.model.SelectedTrip;

import java.util.ArrayList;

public class NewCarActivity extends AppCompatActivity {

    private AutoCompleteTextView companyName, pickUpLocation, dropOffLocation;
    private TextInputEditText confirmationNum, pickUpDate, pickUpTime, dropOffDate, dropOffTime, notes;
    private TextInputLayout companyNameLayout, confirmationNumLayout,
            pickUpLocationLayout, pickUpDateLayout, pickUpTimeLayout,
            dropOffLocationLayout, dropOffDateLayout, dropOffTimeLayout, notesLayout;
    private Switch sameLocationSwitch;
    private DatabaseReference databaseReference;
    private SelectedTrip myTrip;
    private InputValidation inputValidation;
    private DatePickerUtil datePickerUtil;
    private TimePickerUtil timePickerUtil;
    private CloseKeyboard closeKeyboardHelper;
    private Car car;
    private ClearButton clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car);

        //Init views
        initViews();
        //Init helpers
        initHelperClasses();
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
        Intent myIntent = new Intent(NewCarActivity.this, TripDetailsActivity.class);
        myIntent.putExtra("tripObj", myTrip);
        NewCarActivity.this.startActivity(myIntent);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getTitle() != null) {
            if (menuItem.getTitle().equals("save")) {
                addCar();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }
    //----------------------------------------------------------------------------------------------
    //Private methods
    //----------------------------------------------------------------------------------------------
    //Initialize views
    private void initViews() {
        companyName = findViewById(R.id.addCar_autoCompleteTextView_companyName);
        confirmationNum = findViewById(R.id.addCar_textInputEditText_confirmationNum);
        pickUpLocation = findViewById(R.id.addCar_textInputAutoComplete_pickUpLocation);
        pickUpDate = findViewById(R.id.addCar_textInputEditText_pickUpDate);
        pickUpTime = findViewById(R.id.addCar_textInputEditText_pickUpTime);
        dropOffLocation = findViewById(R.id.addCar_textInputAutoComplete_dropOffLocation);
        dropOffDate = findViewById(R.id.addCar_textInputEditText_dropOffDate);
        dropOffTime = findViewById(R.id.addCar_textInputEditText_dropOffTime);
        sameLocationSwitch = findViewById(R.id.addCar_sameLocation_switch);
        notes = findViewById(R.id.addCar_textInputEditText_notes);

        companyNameLayout= findViewById(R.id.addCar_textInputLayout_companyName);
        confirmationNumLayout = findViewById(R.id.addCar_textInputLayout_confirmationNum);
        pickUpLocationLayout = findViewById(R.id.addCar_textInputLayout_pickUpLocation);
        pickUpDateLayout = findViewById(R.id.addCar_textInputLayout_pickUpDate);
        pickUpTimeLayout = findViewById(R.id.addCar_textInputLayout_pickUpTime);
        dropOffLocationLayout = findViewById(R.id.addCar_textInputLayout_dropOffLocation);
        dropOffDateLayout = findViewById(R.id.addCar_textInputLayout_dropOffDate);
        dropOffTimeLayout = findViewById(R.id.addCar_textInputLayout_dropOffTime);
        notesLayout = findViewById(R.id.addCar_textInputLayout_notes);

        if(sameLocationSwitch.isChecked())
        {
            //((ViewManager)dropOffLayout.getParent()).removeView(dropOffLayout);
            dropOffLocationLayout.setVisibility(View.GONE);
        }
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
        new GooglePlaceApi(this, NewCarActivity.this, pickUpLocation, dropOffLocation, AutocompleteFilter.TYPE_FILTER_ADDRESS); //Init Google Place Api
    }
    //Listeners
    private void initListeners() {
        final ArrayList<View> listenerList = new ArrayList<>();
        listenerList.add(companyName);
        listenerList.add(confirmationNum);
        listenerList.add(pickUpLocation);
        listenerList.add(dropOffLocation);
        listenerList.add(notes);

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
        pickUpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboardHelper.close(NewCarActivity.this, v);
                datePickerUtil.datePickerDialog(NewCarActivity.this, pickUpDate);
                //Listener to show clear text button on text change
                pickUpDate.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        clearButton.showBtn(pickUpDate.getTag().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            }
        });
        dropOffDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboardHelper.close(NewCarActivity.this, v);
                //Departure date picker- uses DatePickerUtil
                datePickerUtil.datePickerDialog(NewCarActivity.this, dropOffDate);
                //Listener to show clear text button on text change
                dropOffDate.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        clearButton.showBtn(dropOffDate.getTag().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });
        //Time pickers
        pickUpTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboardHelper.close(NewCarActivity.this, v);
                timePickerUtil.timePickerDialog(NewCarActivity.this, pickUpTime);
                pickUpTime.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        clearButton.showBtn(pickUpTime.getTag().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });
        dropOffTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboardHelper.close(NewCarActivity.this, v);
                timePickerUtil.timePickerDialog(NewCarActivity.this, dropOffTime);
                dropOffTime.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        clearButton.showBtn(dropOffTime.getTag().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });

        sameLocationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    dropOffLocationLayout.setVisibility(View.GONE);
                }
                else {
                    dropOffLocationLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    //Generate clear text button
    private void generateClearBtn()
    {
        ArrayList<TextInputLayout> inputLayoutList = new ArrayList<>();
        inputLayoutList.add(companyNameLayout);
        inputLayoutList.add(confirmationNumLayout);
        inputLayoutList.add(pickUpLocationLayout);
        inputLayoutList.add(pickUpDateLayout);
        inputLayoutList.add(pickUpTimeLayout);
        inputLayoutList.add(dropOffLocationLayout);
        inputLayoutList.add(dropOffDateLayout);
        inputLayoutList.add(dropOffTimeLayout);
        inputLayoutList.add(notesLayout);




        clearButton.generateButtons(NewCarActivity.this, inputLayoutList);
    }
    //Add car to db
    private void addCar()
    {
        String company = companyName.getText().toString();
        String confirmation = confirmationNum.getText().toString();
        String pickUpD = pickUpDate.getText().toString();
        String pickUpT= pickUpTime.getText().toString();
        String dropOffD= dropOffDate.getText().toString();
        String dropOffT= dropOffTime.getText().toString();
        String dropOffL = dropOffLocation.getText().toString();
        String pickUpL= pickUpLocation.getText().toString();
        String note= notes.getText().toString();

        inputValidation.setErrorCount();
        inputValidation.validateInputAutoComplete(companyName, company);
        inputValidation.validateInputAutoComplete(pickUpLocation, pickUpL);
        inputValidation.validateTextViewInput(pickUpDate, pickUpD);
        inputValidation.validateTextViewInput(pickUpTime, pickUpT);
        inputValidation.validateTextViewInput(dropOffDate, dropOffD);
        inputValidation.validateTextViewInput(dropOffTime, dropOffT);

        if (inputValidation.getErrorCount() == 0) {
            String id = databaseReference.push().getKey();
            car = new Car(company, confirmation, pickUpL,dropOffL, pickUpD, pickUpT,
                    dropOffD, dropOffT, note);
            databaseReference.child(myTrip.getTripId()).child("car" + id).setValue(car);
            Toast.makeText(NewCarActivity.this, "Car Added Successfully",
                    Toast.LENGTH_LONG).show();
            //Start TripDetailsActivity
            Intent myIntent = new Intent(NewCarActivity.this, TripDetailsActivity.class);
            myIntent.putExtra("tripObj", myTrip);
            NewCarActivity.this.startActivity(myIntent);
        }
    }

}
