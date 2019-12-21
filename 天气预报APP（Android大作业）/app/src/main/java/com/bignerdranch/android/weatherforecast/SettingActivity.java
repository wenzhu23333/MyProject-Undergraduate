package com.bignerdranch.android.weatherforecast;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SettingActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener
        ,Preference.OnPreferenceClickListener {
    public static MyDatabaseHelper dbHelper;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final  String LOCATION = "location";
    public static boolean isFirstOpen = true;
    public static String LAT = "Lat";
    public static String LON = "Lon";
    private Preference location;
    private ListPreference tempUnit;
    private CheckBoxPreference notification;
    private SharedPreferences spf;
    public static String CurrentLocation = "Changsha";
    boolean isCheaked;
    String currentlat;
    String currentlon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.settings);
        spf = PreferenceManager.getDefaultSharedPreferences(this);
        isCheaked = spf.getBoolean("notification",true);
        addPreferencesFromResource(R.layout.settings);
        location = findPreference("pre");
        location.setSummary(CurrentLocation);
        //location.setSummary(CurrentLocation);
        tempUnit = (ListPreference) findPreference("temp");
        notification = (CheckBoxPreference)findPreference("notification");
        location.setOnPreferenceClickListener(this);
        tempUnit.setOnPreferenceChangeListener(this);
        notification.setOnPreferenceChangeListener(this);
        currentlat = getIntent().getStringExtra(LAT);
        currentlon = getIntent().getStringExtra(LON);
        if (isFirstOpen)
        //new SetCity().execute();
        isFirstOpen = false;
    }

    private class SetCity extends AsyncTask
    {

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                CurrentLocation = new WeatherFetchr().fetchAdrress(ForecastActivity.CurrentLat,ForecastActivity.CurrentLon);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Object o) {
            location.setSummary(CurrentLocation);
        }
    }
    private void latlonToAddress()
    {
//        List<Address> addresses;
//        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//        try {
//            addresses = geocoder.getFromLocation(Double.valueOf(ForecastActivity.CurrentLat),
//                    Double.valueOf(ForecastActivity.CurrentLon),1);
//            String address = addresses.get(0).getAddressLine(0);
//            Log.d("1234",address);
//            CurrentLocation = address;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    public static Intent newIntent(Context packageContext,String lat,String lon) {
        Intent intent = new Intent(packageContext, SettingActivity.class);
        intent.putExtra(LAT, lat);
        intent.putExtra(LON,lon);
        return intent;
    }
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (tempUnit.equals(preference))
        {
           tempUnit.setValue(newValue.toString());
        }
        if (notification.equals(preference))
        {
            isCheaked = !isCheaked;
            notification.setChecked(isCheaked);
        }
        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference == location)
        {
            try {
                Intent intent =
                        new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                .build(this);
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
            } catch (GooglePlayServicesRepairableException e) {
                // TODO: Handle the error.
            } catch (GooglePlayServicesNotAvailableException e) {
                //Log.i("1234", "sdfklsdfj");
                // TODO: Handle the error.
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                CurrentLocation = place.getName().toString();
                location.setSummary(place.getName());
               setNewLocation(String.valueOf(place.getLatLng().latitude),String.valueOf(place.getLatLng().longitude));
               //Log.i("1234", "Place: " +place.getLatLng().latitude);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                //Log.i("1234", "sdfklsdfj");
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
    private void setNewLocation(String lat,String lon)
    {
        Intent intent = new Intent();
        intent.putExtra("lat",lat);
        intent.putExtra("lon",lon);
        setResult(RESULT_OK,intent);
    }
}
