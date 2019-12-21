package com.bignerdranch.android.weatherforecast;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by 杨文卓 on 2018/12/1.
 */

public class WeatherFetchr {
    private static final String API_KEY = "4a12e47b1cf57a63d1209b1f300f09e5";
    private static final String Google_API_KEY = "AIzaSyDnohcPnAdrHXpY75J-pFNG7RP04ichliw";

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }
    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }
    public String fetchAdrress(String lat,String lon) throws IOException, JSONException {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + ","
                + lon + "&sensor=true"+"&key=AIzaSyDnohcPnAdrHXpY75J-pFNG7RP04ichliw";
        String jsonString = getUrlString(url);
        JSONObject jsonObject = new JSONObject(jsonString);
        String Status = jsonObject.getString("status");
        Log.d("1234",jsonString);
        if (Status.equalsIgnoreCase("OK")){
            JSONArray Results = jsonObject.getJSONArray("results");
            JSONObject location = Results.getJSONObject(0);
            return location.getString("formatted_address");
        }
        else return null;
    }
    public List<WeatherItem> fetchItems(String lat,String lon){
        List<WeatherItem> items = new ArrayList<>();
        try{
            String url = Uri.parse("https://api.openweathermap.org/data/2.5/forecast")
                    .buildUpon()
                    .appendQueryParameter("lat",lat)
                    .appendQueryParameter("lon",lon)
                    .appendQueryParameter("appid",API_KEY)
                    .build().toString();
            String jsonString = getUrlString(url);
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                parseItem(items,jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
                e.printStackTrace();
            }
            return items;
        }
    public WeatherItem fetchTodayItems(String lat,String lon){
        WeatherItem weatherItem = new WeatherItem();
        try{
            String url = Uri.parse("https://api.openweathermap.org/data/2.5/weather")
                    .buildUpon()
                    .appendQueryParameter("lat",lat)
                    .appendQueryParameter("lon",lon)
                    .appendQueryParameter("appid",API_KEY)
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.d("1234",jsonString);
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                String dt = jsonObject.getString("dt");
                String CSTTime = dtToCST(dt);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = simpleDateFormat.parse(CSTTime);
                String[] strings1 = date.toString().split(" ");
                weatherItem.setWeekNumAndDateNum(strings1[0]+" "+strings1[1]+" "+strings1[2]);
                weatherItem.setWeeknum(strings1[0]);
                weatherItem.setDateNum(strings1[1]+" "+strings1[2]);
                JSONObject WeatherJsonObject = jsonObject.getJSONArray("weather").getJSONObject(0);
                weatherItem.setWeather(WeatherJsonObject.getString("main"));
                weatherItem.setImageID(ForecastActivity.IconMap.get(WeatherJsonObject.getString("icon")));
                JSONObject mainJsonObject = jsonObject.getJSONObject("main");
                weatherItem.setHumidity("Humidity: "+mainJsonObject.getString("humidity")+" %");
                weatherItem.setPressure("Pressure: "+mainJsonObject.getString("pressure")+" hPa");
                weatherItem.setTempAverage(mainJsonObject.getString("temp_max"));
                weatherItem.setTempMin(mainJsonObject.getString("temp_min"));
                JSONObject windJsonObject = jsonObject.getJSONObject("wind");
                weatherItem.setWind("Wind: "+windJsonObject.getString("speed")+" m/s " +windJsonObject.getString("deg")+" degeree");

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weatherItem;
    }
     private void parseItem(List<WeatherItem> items,JSONObject jsonObject) throws JSONException {
        JSONArray WeatherJsonArray = jsonObject.getJSONArray("list");
        for (int i = 0;i<WeatherJsonArray.length();i++)
        {
            WeatherItem weatherItem = new WeatherItem();
            JSONObject dayJsonObject = WeatherJsonArray.getJSONObject(i);
            String dt = dayJsonObject.getString("dt");
            String CSTTime = dtToCST(dt);
            String[] strings = CSTTime.split(" ");
            if (!strings[1].equals("11:00:00")||strings[0].equals(getTodayDate())) continue;
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = spf.parse(strings[0]);
                String[] strings1 = date.toString().split(" ");
                weatherItem.setWeekNumAndDateNum(strings1[0]+" "+strings1[1]+" "+strings1[2]);
                weatherItem.setWeeknum(strings1[0]);
                weatherItem.setDateNum(strings1[1]+" "+strings1[2]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            JSONObject mainJsonObject = dayJsonObject.getJSONObject("main");
            JSONObject weatherJsonObject = dayJsonObject.getJSONArray("weather").getJSONObject(0);
            JSONObject windJsonObject = dayJsonObject.getJSONObject("wind");
//            weatherItem.setWeeknum(dt);
//            weatherItem.setDateNum(dt);
            Log.d("test2",getTodayDate());
            //weatherItem.setWeekNumAndDateNum(dtToCST(dt));

            weatherItem.setHumidity("Humidity: "+mainJsonObject.getString("humidity")+" %");
            weatherItem.setPressure("Pressure: "+mainJsonObject.getString("pressure")+" hPa");
            weatherItem.setTempAverage(mainJsonObject.getString("temp_max"));
            weatherItem.setTempMin(mainJsonObject.getString("temp_min"));

            weatherItem.setWind("Wind: "+windJsonObject.getString("speed")+" m/s " +windJsonObject.getString("deg")+" degeree");
            weatherItem.setWeather(weatherJsonObject.getString("main"));

            weatherItem.setImageID(ForecastActivity.IconMap.get(weatherJsonObject.getString("icon")));
            items.add(weatherItem);
        }
    }
    public String dtToCST(String dt)
    {
        long unixSecond = Long.parseLong(dt)*1000L;
        Date date = new Date(unixSecond);
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        spf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return spf.format(date);
    }
    public static String getTodayDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date = new Date();
        return sdf.format(date);
    }

}
