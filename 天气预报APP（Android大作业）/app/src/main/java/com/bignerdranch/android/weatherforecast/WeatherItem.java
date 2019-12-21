package com.bignerdranch.android.weatherforecast;

import android.media.Image;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Created by 杨文卓 on 2018/11/29.
 */

public class WeatherItem implements Serializable {
    private static final long serialVersionUID = 3559533002594201715L;
    private String Weeknum;
    private String DateNum;
    private String WeekNumAndDateNum;
    private String TempAverage;
    private String TempMin;
    private String Weather;
    private String Humidity;
    private String Pressure;
    private String Wind;
    private int imageID;
    private String TempMaxFa;
    private String TempMinFa;
    private String TempMaxCe;
    private String TempMinCe;

    public String getWeekNumAndDateNum() {
        return WeekNumAndDateNum;
    }

    public void setWeekNumAndDateNum(String weekNumAndDateNum) {
        WeekNumAndDateNum = weekNumAndDateNum;
    }

    public void setTempMaxFa(String TempK) {
        double Kel = Double.valueOf(TempK);
        DecimalFormat df = new DecimalFormat("0.00");
        TempMaxFa = String.valueOf(df.format((Kel - 273.15)*1.8+32))+" ℉";
    }

    public void setTempMinFa(String TempK) {
        double Kel = Double.valueOf(TempK);
        DecimalFormat df = new DecimalFormat("0.00");
        TempMinFa =  String.valueOf(df.format((Kel - 273.15)*1.8+32))+" ℉";
    }

    public String getTempMaxFa() {
        return TempMaxFa;
    }

    public String getTempMinFa() {
        return TempMinFa;
    }

    public String getTempMaxCe() {
        return TempMaxCe;
    }

    public String getTempMinCe() {
        return TempMinCe;
    }

    public void setTempMaxCe(String TempK) {
        double Kel = Double.valueOf(TempK);
        DecimalFormat df = new DecimalFormat("0.00");
        TempMaxCe = String.valueOf(df.format(Kel - 273.15))+" ℃";
    }

    public void setTempMinCe(String TempK) {
        double Kel = Double.valueOf(TempK);
        DecimalFormat df = new DecimalFormat("0.00");
        TempMinCe =  String.valueOf(df.format(Kel - 273.15))+" ℃";
    }

    public WeatherItem()
    {

    }
    public void setWeeknum(String weeknum) {
        Weeknum = weeknum;
    }

    public void setDateNum(String dateNum) {
        DateNum = dateNum;
    }

    public void setTempAverage(String tempAverage) {
        TempAverage = tempAverage;
        setTempMaxCe(tempAverage);
        setTempMaxFa(tempAverage);
    }

    public void setTempMin(String tempMin) {
        TempMin = tempMin;
        setTempMinCe(tempMin);
        setTempMinFa(tempMin);
    }

    public void setWeather(String weather) {
        Weather = weather;
    }

    public void setHumidity(String humidity) {
        Humidity = humidity;
    }

    public void setPressure(String pressure) {
        Pressure = pressure;
    }

    public void setWind(String wind) {
        Wind = wind;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getWeeknum() {
        return Weeknum;
    }

    public String getDateNum() {
        return DateNum;
    }

    public String getTempAverage() {
        return TempAverage;
    }

    public String getTempMin() {
        return TempMin;
    }

    public String getWeather() {
        return Weather;
    }

    public String getHumidity() {
        return Humidity;
    }

    public String getPressure() {
        return Pressure;
    }

    public String getWind() {
        return Wind;
    }

    public int getImageID() {
        return imageID;
    }
}
