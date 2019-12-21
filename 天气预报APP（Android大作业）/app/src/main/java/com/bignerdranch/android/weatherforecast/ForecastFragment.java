package com.bignerdranch.android.weatherforecast;

import android.graphics.drawable.Icon;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by 杨文卓 on 2018/11/28.
 */

public class ForecastFragment extends Fragment {
    private EditText WeekNumText;
    private EditText DateText;
    private EditText TempAverageText;
    private EditText TempMinText;
    private EditText WeatherText;
    private EditText HumidityText;
    private EditText PressureText;
    private EditText WindText;
    private ImageView WeatherImage;
    private ConstraintLayout constraintLayout_content;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_forecast,container,false);
        WeekNumText = v.findViewById(R.id.editText);
        DateText = v.findViewById(R.id.editText2);
        TempAverageText = v.findViewById(R.id.editText3);
        TempMinText = v.findViewById(R.id.editText4);
        WeatherText = v.findViewById(R.id.editText5);
        HumidityText = v.findViewById(R.id.editText6);
        PressureText = v.findViewById(R.id.editText7);
        WindText = v.findViewById(R.id.editText8);
        WeatherImage = v.findViewById(R.id.imageView);
        constraintLayout_content = v.findViewById(R.id.constraintLayout_content);

        return v;
    }

    //更新天气的详细信息
    public void refresh(String weeknum, String dateNum, String tempAverage,
                        String tempMin, String weather, String humidity,
                        String pressure, String wind, int imageID)
    {
        WeekNumText.setText(weeknum);
        DateText.setText(dateNum);
        TempAverageText.setText(tempAverage);
        TempMinText.setText(tempMin);
        WeatherText.setText(weather);
        HumidityText.setText(humidity);
        PressureText.setText(pressure);
        WindText.setText(wind);
        WeatherImage.setImageResource(imageID);
    }


}
