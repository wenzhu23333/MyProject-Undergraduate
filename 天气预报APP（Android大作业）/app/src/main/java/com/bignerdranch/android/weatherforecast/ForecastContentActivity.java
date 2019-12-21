package com.bignerdranch.android.weatherforecast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;


public class ForecastContentActivity extends AppCompatActivity {

    private List<WeatherItem> newList;
    private WeatherItem newTodayWeather;
    private WeatherItem weatherContent;
    private String CurrentLat;
    private String CurrentLon;
    String tempUint;
    SharedPreferences spf;
    public static void actionStart(Activity activity, String weeknum,
                                   String dateNum, String tempAverage,
                                   String tempMin, String weather, String humidity,
                                   String pressure, String wind, int imageID)
    {
        Intent intent = new Intent(activity,ForecastContentActivity.class);
        intent.putExtra("weeknum",weeknum);
        intent.putExtra("dataNum",dateNum);
        intent.putExtra("tempAverage",tempAverage);
        intent.putExtra("tempMin",tempMin);
        intent.putExtra("weather",weather);
        intent.putExtra("humidity",humidity);
        intent.putExtra("pressure",pressure);
        intent.putExtra("wind",wind);
        intent.putExtra("imageID",imageID);
        activity.startActivityForResult(intent,3);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_content);
        String weeknum = getIntent().getStringExtra("weeknum");
        String dataNum = getIntent().getStringExtra("dataNum");
        String tempAverage = getIntent().getStringExtra("tempAverage");
        String tempMin = getIntent().getStringExtra("tempMin");
        String weather = getIntent().getStringExtra("weather");
        String humidity = getIntent().getStringExtra("humidity");
        String pressure = getIntent().getStringExtra("pressure");
        String wind = getIntent().getStringExtra("wind");
        int imageID = getIntent().getIntExtra("imageID",0);
        ForecastFragment forecastFragment = (ForecastFragment)
                getSupportFragmentManager().findFragmentById(R.id.forecast_content_fragment);
        forecastFragment.refresh(weeknum,dataNum,tempAverage,tempMin,weather,humidity,pressure,wind,imageID);
        initToolbar();

        //刷新fragment界面
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1)
        {
            if (resultCode == RESULT_OK) {
                CurrentLat = data.getStringExtra("lat");
                CurrentLon = data.getStringExtra("lon");
                ForecastActivity.CurrentLat = CurrentLat;
                ForecastActivity.CurrentLon = CurrentLon;
                ForecastActivity.LocationIsChanged = true;
                try {
                    if (ForecastActivity.dbHelper.searchTodayWeather(WeatherFetchr.getTodayDate(),CurrentLat,CurrentLon)==null){
                        new FetchItemsTask(CurrentLat,CurrentLon).execute();
                    }
                    else {
                        ForecastListFragment.weatherItemList = ForecastActivity.dbHelper.searchWeatherList(WeatherFetchr.getTodayDate(),
                                CurrentLat,CurrentLon);
                        ForecastListFragment.TodayWeather = ForecastActivity.dbHelper.searchTodayWeather(WeatherFetchr.getTodayDate(),
                                CurrentLat,CurrentLon);
                        newList = ForecastListFragment.weatherItemList;
                        weatherContent = ForecastListFragment.TodayWeather;
                        setTodaycontent();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        ChangeUnit();
    }
    public  void  ChangeUnit()
    {
        spf= PreferenceManager.getDefaultSharedPreferences(this);
        tempUint = spf.getString("temp","Celsiusl");
        WeatherItem weatherItem = ForecastListFragment.CurrentWeather;
        ForecastFragment forecastFragment = (ForecastFragment)
                getSupportFragmentManager().findFragmentById(R.id.forecast_content_fragment);
        if (tempUint.equals("Celsius1"))
        {
            forecastFragment.refresh(weatherItem.getWeeknum(),
                    weatherItem.getDateNum(),weatherItem.getTempMaxCe(),weatherItem.getTempMinCe(),
                    weatherItem.getWeather(),weatherItem.getHumidity(),weatherItem.getPressure(),weatherItem.getWind(),
                    weatherItem.getImageID());
        }
        else
        {
            forecastFragment.refresh(weatherItem.getWeeknum(),
                    weatherItem.getDateNum(),weatherItem.getTempMaxFa(),weatherItem.getTempMinFa(),
                    weatherItem.getWeather(),weatherItem.getHumidity(),weatherItem.getPressure(),weatherItem.getWind(),
                    weatherItem.getImageID());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public Toolbar initToolbar()
    {
        Toolbar toolbar = findViewById(R.id.toolbar_mlist);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_content,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.share:
                startActivity(Intent.createChooser(ForecastActivity.ShareWeather(), "分享"));
                break;
            case R.id.setting:
                Intent intent = new Intent(this,SettingActivity.class);
                startActivityForResult(intent,1);
                break;
        }
        return true;
    }
    private class FetchItemsTask extends AsyncTask<Void,Void,List<WeatherItem>>
    {
        private String lat;
        private String lon;

        FetchItemsTask(String lat,String lon)
        {
            this.lat = lat;
            this.lon = lon;
        }

        @Override
        protected List<WeatherItem> doInBackground(Void... voids) {
            WeatherFetchr weatherFetchr = new WeatherFetchr();
            newList = weatherFetchr.fetchItems(CurrentLat,CurrentLon);
            newTodayWeather = weatherFetchr.fetchTodayItems(CurrentLat,CurrentLon);
            try {
                ForecastActivity.dbHelper.insertValues(WeatherFetchr.getTodayDate(),newTodayWeather,newList,lat,lon);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<WeatherItem> items) {
            ForecastListFragment.weatherItemList = newList;
            ForecastListFragment.TodayWeather = newTodayWeather;
            setTodaycontent();
            super.onPostExecute(items);
        }
    }

    public void setTodaycontent()
    {
        ForecastFragment forecastFragment = (ForecastFragment)
                getSupportFragmentManager().findFragmentById(R.id.forecast_content_fragment);
        if (tempUint.equals("Celsius1"))
        {
            forecastFragment.refresh(newTodayWeather.getWeeknum(),newTodayWeather.getDateNum(),newTodayWeather.getTempMaxCe(),
                    newTodayWeather.getTempMinCe(),newTodayWeather.getWeather(),newTodayWeather.getHumidity(),newTodayWeather.getPressure(),
                    newTodayWeather.getWind(),newTodayWeather.getImageID());
        }
        else
        {
            forecastFragment.refresh(newTodayWeather.getWeeknum(),newTodayWeather.getDateNum(),newTodayWeather.getTempMaxFa(),
                    newTodayWeather.getTempMinFa(),newTodayWeather.getWeather(),newTodayWeather.getHumidity(),newTodayWeather.getPressure(),
                    newTodayWeather.getWind(),newTodayWeather.getImageID());
        }
    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}
