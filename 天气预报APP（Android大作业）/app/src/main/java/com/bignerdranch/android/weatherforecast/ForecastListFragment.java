package com.bignerdranch.android.weatherforecast;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 杨文卓 on 2018/11/29.
 */

public class ForecastListFragment extends Fragment {
    private boolean isTwoPane;
    private EditText ShowDate;
    private EditText ShowTempMax;
    private EditText ShowTempMin;
    private EditText ShowWeather;
    private ImageView WeatherIcon;
    RecyclerView weatherListRecyclerView;
    ForecastActivity mActivity;
    SharedPreferences spf;
    String tempUint;
    boolean OnNotification;
//    public static MyDatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        mActivity = (ForecastActivity) getActivity();
        weatherListRecyclerView = view.findViewById(R.id.recyclerView4);
        spf= PreferenceManager.getDefaultSharedPreferences(getActivity());
        tempUint = spf.getString("temp","Celsiusl");
        OnNotification = spf.getBoolean("notification",true);
        ShowDate = view.findViewById(R.id.editText16);
        ShowTempMax = view.findViewById(R.id.editText14);
        ShowTempMin = view.findViewById(R.id.editText15);
        ShowWeather = view.findViewById(R.id.editText17);
        WeatherIcon = view.findViewById(R.id.imageView3);
//        dbHelper = new MyDatabaseHelper(getActivity(),"Weather_2",null,1);
        return view;
    }

    public void setTodayWeather(WeatherItem weather)
    {
        ShowDate.setText(weather.getWeekNumAndDateNum());
        if (tempUint.equals("Celsius1"))
        {
            ShowTempMax.setText(weather.getTempMaxCe());
            ShowTempMin.setText(weather.getTempMinCe());
        }
        else
        {
            ShowTempMax.setText(weather.getTempMaxFa());
            ShowTempMin.setText(weather.getTempMinFa());
        }
        ShowWeather.setText(weather.getWeather());
        WeatherIcon.setImageResource(weather.getImageID());
    }
    public static WeatherItem TodayWeather;
    public static WeatherItem CurrentWeather;
    public static List<WeatherItem> weatherItemList;
    public void setNewWeather(String lat,String lon) throws Exception {
        if (ForecastActivity.dbHelper.searchTodayWeather(WeatherFetchr.getTodayDate(),lat,lon)==null)
        {
            new FetchItemsTask(lat,lon).execute();
        }
        else {
            TodayWeather = ForecastActivity.dbHelper.searchTodayWeather(WeatherFetchr.getTodayDate(),lat,lon);
            weatherItemList = ForecastActivity.dbHelper.searchWeatherList(WeatherFetchr.getTodayDate(),lat,lon);
            setList();
            if (isTwoPane)
                setTodayWeatherContent();
        }
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
            WeatherFetchr newWeatherFetchr = new WeatherFetchr();
            TodayWeather =newWeatherFetchr.fetchTodayItems(lat,lon);
            weatherItemList = newWeatherFetchr.fetchItems(lat,lon);
            try {
                ForecastActivity.dbHelper.insertValues(WeatherFetchr.getTodayDate(),TodayWeather,weatherItemList,lat,lon);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return weatherItemList;
        }

        @Override
        protected void onPostExecute(List<WeatherItem> items) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
            WeatherAdapter weatherAdapter = new WeatherAdapter(weatherItemList);
            weatherListRecyclerView.setLayoutManager(layoutManager);
            weatherListRecyclerView.setAdapter(weatherAdapter);
            setTodayWeather(TodayWeather);
            if (isTwoPane)
            {
               setTodayWeatherContent();
            }
        }


    }
    public void setTodayWeatherContent()
    {
        ForecastFragment forecastFragment =
                (ForecastFragment) getFragmentManager().findFragmentById(R.id.content_fragment);
        String tempMax = new String() ;
        String tempMin = new String();
        if (tempUint.equals("Celsius1"))
        {
            tempMax = TodayWeather.getTempMaxCe();
            tempMin = TodayWeather.getTempMinCe();
        }
        else {
            tempMax = TodayWeather.getTempMaxFa();
            tempMin = TodayWeather.getTempMinFa();
        }
        forecastFragment.refresh(TodayWeather.getWeeknum(),TodayWeather.getDateNum(),tempMax,tempMin,
                TodayWeather.getWeather(),TodayWeather.getHumidity(),TodayWeather.getPressure(),
                TodayWeather.getWind(),TodayWeather.getImageID());
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.forecast_content_layout)!=null)
            isTwoPane = true;
        else
            isTwoPane = false;
    }

    class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder>
    {
        private List<WeatherItem> WeatherList;
        class ViewHolder extends RecyclerView.ViewHolder
        {
            private ImageView WeatherImage;
            private EditText weekNum;
            private EditText Weather;
            private EditText TempAverage;
            private EditText TempMin;
            public ViewHolder(View view)
            {
                super(view);
                WeatherImage = view.findViewById(R.id.imageView2);
                weekNum = view.findViewById(R.id.editText9);
                Weather = view.findViewById(R.id.editText10);
                TempAverage = view.findViewById(R.id.editText11);
                TempMin = view.findViewById(R.id.editText12);
            }
        }
        public WeatherAdapter(List<WeatherItem> newList)
        {
            WeatherList = newList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.forecast_item,parent,false);
            final ViewHolder holder = new ViewHolder(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WeatherItem weatherItem = WeatherList.get(holder.getAdapterPosition());
                    CurrentWeather = weatherItem;
                    if (tempUint.equals("Celsius1")) {
                        if (isTwoPane) {
                            ForecastFragment forecastFragment =
                                    (ForecastFragment) getFragmentManager().findFragmentById(R.id.content_fragment);
                            forecastFragment.refresh(weatherItem.getWeeknum(),
                                    weatherItem.getDateNum(), weatherItem.getTempMaxCe(),
                                    weatherItem.getTempMinCe(), weatherItem.getWeather(),
                                    weatherItem.getHumidity(), weatherItem.getPressure(),
                                    weatherItem.getWind(), weatherItem.getImageID());

                        } else {
                            ForecastContentActivity.actionStart(getActivity(), weatherItem.getWeeknum(),
                                    weatherItem.getDateNum(), weatherItem.getTempMaxCe(),
                                    weatherItem.getTempMinCe(), weatherItem.getWeather(),
                                    weatherItem.getHumidity(), weatherItem.getPressure(),
                                    weatherItem.getWind(), weatherItem.getImageID());
                        }
                    }
                    else {
                        if (isTwoPane) {
                            ForecastFragment forecastFragment =
                                    (ForecastFragment) getFragmentManager().findFragmentById(R.id.content_fragment);
                            forecastFragment.refresh(weatherItem.getWeeknum(),
                                    weatherItem.getDateNum(), weatherItem.getTempMaxFa(),
                                    weatherItem.getTempMinFa(), weatherItem.getWeather(),
                                    weatherItem.getHumidity(), weatherItem.getPressure(),
                                    weatherItem.getWind(), weatherItem.getImageID());

                        } else {
                            ForecastContentActivity.actionStart(getActivity(), weatherItem.getWeeknum(),
                                    weatherItem.getDateNum(), weatherItem.getTempMaxFa(),
                                    weatherItem.getTempMinFa(), weatherItem.getWeather(),
                                    weatherItem.getHumidity(), weatherItem.getPressure(),
                                    weatherItem.getWind(), weatherItem.getImageID());
                        }
                    }
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            WeatherItem weatherItem = WeatherList.get(position);
            holder.WeatherImage.setImageResource(weatherItem.getImageID());
            holder.Weather.setText(weatherItem.getWeather());
            tempUint = spf.getString("temp","Celsius1");
            if (tempUint.equals("Celsius1")){
                holder.TempMin.setText(weatherItem.getTempMaxCe());
                holder.TempAverage.setText(weatherItem.getTempMinCe());
            }
            else
            {
                holder.TempMin.setText(weatherItem.getTempMaxFa());
                holder.TempAverage.setText(weatherItem.getTempMinFa());
            }
            holder.weekNum.setText(weatherItem.getWeeknum());
            setTodayWeather(TodayWeather);
            if (isTwoPane)
            setTodayWeatherContent();
        }

        @Override
        public int getItemCount() {
            return WeatherList.size();
        }
    }

    public void setList()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        WeatherAdapter weatherAdapter = new WeatherAdapter(weatherItemList);
        weatherListRecyclerView.setLayoutManager(layoutManager);
        weatherListRecyclerView.setAdapter(weatherAdapter);
        setTodayWeather(TodayWeather);
        Log.d("1111","111111111");
    }

}
