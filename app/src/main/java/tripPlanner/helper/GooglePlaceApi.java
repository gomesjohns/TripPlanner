package tripPlanner.helper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import tripPlanner.adapter.PlaceListAdapter;
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

import java.util.ArrayList;

public class GooglePlaceApi implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks
{
    //Google Place API
    private static final String TAG = "GOOGLE PLACE ACTIVITY";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;
    private PlaceListAdapter placeListAdapter;
    int filter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(new LatLng(0, -0), new LatLng(0, -0));

    CloseKeyboard closeKeyboardHelper;
    ClearText clearText;
    ClearButton clearButton;

    final private AutoCompleteTextView autoCompleteTextView;
    private Context context;
    private Activity activity;
    private ArrayList<Button> buttonArrayList;


    public GooglePlaceApi(Context context, Activity activity, AutoCompleteTextView autoCompleteTextView,
                          ArrayList<Button> buttonArrayList, int filter)
    {
        this.context = context;
        this.activity = activity;
        this.autoCompleteTextView = autoCompleteTextView;
        this.buttonArrayList = buttonArrayList;
        this.filter = filter;
        initHelperClasses();
        initGoogleApi();
    }

    public GooglePlaceApi(Context context, Activity activity, AutoCompleteTextView autoCompleteTextView, int filter)
    {
        this.context = context;
        this.activity = activity;
        this.autoCompleteTextView = autoCompleteTextView;
        this.filter = filter;
        initHelperClasses();
        initGoogleApi();
    }


    public void initHelperClasses()
    {
        closeKeyboardHelper = new CloseKeyboard();
        clearText= new ClearText();
        //clearButton = new ClearButton(buttonArrayList);
    }

    //Initializes google place api
    public PlaceListAdapter initGoogleApi()
    {
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(filter)
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage((FragmentActivity) context, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();

        autoCompleteTextView.setOnItemClickListener(clickListener);
        placeListAdapter = new PlaceListAdapter(context, android.R.layout.simple_expandable_list_item_1, BOUNDS_MOUNTAIN_VIEW, typeFilter);
        autoCompleteTextView.setAdapter(placeListAdapter);
        return placeListAdapter;
    }

    public AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            final PlaceListAdapter.PlaceAutocomplete item = placeListAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(placeDetails);
            //If you want to trim place name do it here

            closeKeyboardHelper.close(activity, view);
            clearButton.showBtn(autoCompleteTextView.getTag().toString());
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
        placeListAdapter.setGoogleApiClient(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {
        placeListAdapter.setGoogleApiClient(null);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

       Toast.makeText(context, "Google Places API connection failed with error code:" + connectionResult.getErrorCode(), Toast.LENGTH_LONG).show();
    }

}
