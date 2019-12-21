package com.bignerdranch.android.weatherforecast;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForecastActivity extends AppCompatActivity {
    public static  Map<String,Integer> IconMap = new HashMap<String,Integer>();
    private boolean isTwoPane;
    private GoogleApiClient mClient;
    FragmentManager fragmentManager;
    public static String CurrentLat;
    public static String CurrentLon;
    public static boolean LocationIsChanged = false;
    private static final int REQUEST_LOCATION_PERMISSIONS = 0;
    public static MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitIconMap();
        setContentView(R.layout.activity_forecast);
        //MyService.setServiceAlarm(this,true);
        IsTwopane();
       initToolbar();
       fragmentManager = getSupportFragmentManager();
       mClient = new GoogleApiClient.Builder(this).
               addApi(LocationServices.API).
               addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
           @RequiresApi(api = Build.VERSION_CODES.M)
           @Override
           public void onConnected(@Nullable Bundle bundle) {
               if (hasLocationPermission()&&!LocationIsChanged) {
                   findLocation();
               }
               else if (!hasLocationPermission()) requestPermissions(LOCATION_PERMISSIONS,REQUEST_LOCATION_PERMISSIONS);
           }
           @Override
           public void onConnectionSuspended(int i) {
           }
       }).build();
        dbHelper = new MyDatabaseHelper(this,"NewWeather",null,1);
    }
    @Override
    protected void onStart() {
        super.onStart();
        this.invalidateOptionsMenu();
        mClient.connect();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mClient.disconnect();
    }
    public void IsTwopane()
    {
        if (findViewById(R.id.forecast_content_layout)!=null) isTwoPane = true;
        else isTwoPane = false;
    }
    public Toolbar initToolbar()
    {
        Toolbar toolbar = findViewById(R.id.toolbar_mlist);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isTwoPane)
            getMenuInflater().inflate(R.menu.toolbar_istwopane,menu);
        else getMenuInflater().inflate(R.menu.toolbar_list,menu);
        return true;
    }
    public static Intent ShareWeather()
    {
        Intent textIntent = new Intent(Intent.ACTION_SEND);
        textIntent.setType("text/plain");
        String weatherMessage = new String("Today Weather: "+ForecastListFragment.TodayWeather.getWeather()+"\n"
                +"Max Temperature:"+ForecastListFragment.TodayWeather.getTempMaxCe()+"\n"+"Min Temperature:"+ForecastListFragment.TodayWeather.getTempMinCe()
                );
        textIntent.putExtra(Intent.EXTRA_TEXT,weatherMessage);
        return textIntent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (isTwoPane){
            switch (item.getItemId()){
                case R.id.share:
                    startActivity(Intent.createChooser(ShareWeather(), "分享"));
                    break;
                case R.id.map_location:
                    startNaviGoogle(CurrentLat,CurrentLon);
                    break;
                case R.id.setting:
                    Intent intent =SettingActivity.newIntent(this,CurrentLat,CurrentLon);
                    startActivityForResult(intent,1);
                    break;
                default:
            }
        }
        else {
            switch (item.getItemId()){
                case R.id.map_location:
                    startNaviGoogle(CurrentLat,CurrentLon);
                    break;
                case R.id.setting:
                    Intent intent =SettingActivity.newIntent(this,CurrentLat,CurrentLon);
                    startActivityForResult(intent,1);
                    break;
                default:
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1)
        {
            if (resultCode == RESULT_OK) {
                CurrentLat = data.getStringExtra("lat");
                CurrentLon = data.getStringExtra("lon");
                ForecastListFragment forecastListFragment =
                        (ForecastListFragment) fragmentManager.findFragmentById(R.id.list_fragment);
                try {
                    forecastListFragment.setNewWeather(CurrentLat,CurrentLon);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                LocationIsChanged = true;
            }
        }
        if (requestCode==3)
        {
            if (resultCode==RESULT_OK)
            {
                ForecastListFragment forecastListFragment =
                        (ForecastListFragment) fragmentManager.findFragmentById(R.id.list_fragment);
               forecastListFragment.setList();
            }
        }
        ForecastListFragment forecastListFragment =
                (ForecastListFragment) fragmentManager.findFragmentById(R.id.list_fragment);
        forecastListFragment.setList();
        if (isTwoPane) forecastListFragment.setTodayWeatherContent();
    }


    protected void InitIconMap()
    {
        IconMap.put("01d",R.drawable.m01d); IconMap.put("01n",R.drawable.m01n);
        IconMap.put("02d",R.drawable.m02d); IconMap.put("02n",R.drawable.m02n);
        IconMap.put("03d",R.drawable.m03d); IconMap.put("03n",R.drawable.m03n);
        IconMap.put("04d",R.drawable.m04d); IconMap.put("04n",R.drawable.m04n);
        IconMap.put("09d",R.drawable.m09d); IconMap.put("09n",R.drawable.m09n);
        IconMap.put("10d",R.drawable.m10d); IconMap.put("10n",R.drawable.m10n);
        IconMap.put("11d",R.drawable.m11d); IconMap.put("11n",R.drawable.m11n);
        IconMap.put("13d",R.drawable.m13d); IconMap.put("13n",R.drawable.m13n);
        IconMap.put("50d",R.drawable.m50d); IconMap.put("50n",R.drawable.m50n);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    private void findLocation()
    {
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);
        LocationServices.FusedLocationApi
                .requestLocationUpdates(mClient, request, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        CurrentLat = String.valueOf(location.getLatitude()) ;
                        CurrentLon = String.valueOf(location.getLongitude());
                        Log.d("test",CurrentLat);
                        ForecastListFragment forecastListFragment =
                                (ForecastListFragment) fragmentManager.findFragmentById(R.id.list_fragment);
                        try {
                            forecastListFragment.setNewWeather(CurrentLat,CurrentLon);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    private static final String[] LOCATION_PERMISSIONS = new String[] {
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    private boolean hasLocationPermission() {
        int result = ContextCompat
                .checkSelfPermission(this, LOCATION_PERMISSIONS[0]);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSIONS:
                if (hasLocationPermission()) {
                    findLocation();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }
    public void startNaviGoogle(String lat,String lon) {
        if (isAvilible(this, "com.google.android.apps.maps")) {
            Uri gmmIntentUri = Uri.parse("geo:0,0?z=100&q="+lat+","+lon+"(阁下在此)");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        } else {
            Toast.makeText(this,"您尚未安装谷歌地图或者谷歌地图版本过低！",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean NotificationIsOpen = sharedPreferences.getBoolean("notification",true);
        if (NotificationIsOpen)
        MyService.setServiceAlarm(this,true);
        super.onBackPressed();
    }
}
