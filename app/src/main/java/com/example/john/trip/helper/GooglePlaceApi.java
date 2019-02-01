package com.example.john.trip.helper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.john.trip.adapter.PlaceArrayAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import static com.example.john.trip.NewTripActivity.destinationAutocomplete;

public class GooglePlaceApi implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks
{
    //Google Place API
    private static final String TAG = "MainActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter placeArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(new LatLng(0, -0), new LatLng(0, -0));

    DatePickerHelper datePickerHelper;
    CloseKeyboard closeKeyboardHelper;
    ClearText clearText;
    HideClearButton hideClearButton;
    ShowClearButton showClearButton;

    private Context context;
    private Activity activity;


    public GooglePlaceApi(Context context, Activity activity)
    {
        this.context = context;
        this.activity = activity;
        initHelperClasses();
        initGoogleApi();
    }

    public void initHelperClasses()
    {
        closeKeyboardHelper = new CloseKeyboard();
        clearText= new ClearText();
        hideClearButton  = new HideClearButton();
        showClearButton = new ShowClearButton();
    }

    //Initializes google place api
    public void initGoogleApi()
    {
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS)
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage((FragmentActivity) context, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();

        destinationAutocomplete.setOnItemClickListener(clickListener);
        placeArrayAdapter = new PlaceArrayAdapter(context, android.R.layout.simple_expandable_list_item_1, BOUNDS_MOUNTAIN_VIEW, typeFilter);
        destinationAutocomplete.setAdapter(placeArrayAdapter);
    }

    private AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            closeKeyboardHelper.close(activity, view);
            showClearButton.showBtn("destination");
            final PlaceArrayAdapter.PlaceAutocomplete item = placeArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(placeDetails);
            //If you want to trim place name do it here

            //Auto complete clear text button listener
            destinationAutocomplete.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    showClearButton.showBtn("destination");
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    };

    private ResultCallback<PlaceBuffer> placeDetails= new ResultCallback<PlaceBuffer>()
    {
        @Override
        public void onResult(@NonNull PlaceBuffer places)
        {
            if(!places.getStatus().isSuccess())
            {
                Log.e(TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            final Place place = places.get(0);
            CharSequence attibutions = places.getAttributions();
        }
    };

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        placeArrayAdapter.setGoogleApiClient(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {
        placeArrayAdapter.setGoogleApiClient(null);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

       Toast.makeText(context, "Google Places API connection failed with error code:" + connectionResult.getErrorCode(), Toast.LENGTH_LONG).show();
    }

}
