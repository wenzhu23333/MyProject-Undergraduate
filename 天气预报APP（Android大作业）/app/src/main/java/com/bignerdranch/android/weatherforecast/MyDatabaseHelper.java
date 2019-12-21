package com.bignerdranch.android.weatherforecast;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by 杨文卓 on 2018/12/8.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_WEATHERFORECAST = "create table Weather " +
            "(id integer primary key autoincrement," +
            "date text, "+
            "TodayWeather BLOB, " +
            "ForecastList BLOB,"+
            "lat text, " +
            "lon text)";
    private Context mContext;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WEATHERFORECAST);
    }

    public static byte[] objectToBytes(Object obj) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream sOut = new ObjectOutputStream(out);
        sOut.writeObject(obj);
        sOut.flush();
        byte[] bytes = out.toByteArray();
        sOut.close();
        out.close();
        return bytes;
    }
    public static Object bytesToObject(byte[] bytes) throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream sIn = new ObjectInputStream(in);
        Object object = sIn.readObject();
        in.close();
        sIn.close();
        return object;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insertValues(String date, WeatherItem TodayWeather, List<WeatherItem> ForecastWeather,String lat,String lon) throws Exception {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date",date);
        contentValues.put("TodayWeather",objectToBytes(TodayWeather));
        contentValues.put("ForecastList",objectToBytes(ForecastWeather));
        contentValues.put("lat",lat);
        contentValues.put("lon",lon);
        db.insert("Weather",null,contentValues);
        db.close();
    }
    public WeatherItem searchTodayWeather(String date,String lat,String lon) throws Exception {
        WeatherItem weatherItem = null;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query("Weather",new String[]{"TodayWeather"},"date=? AND lat=? AND lon=?",new String[]{date,lat,lon},null,null,null);
        if (cursor.moveToFirst()&&cursor!=null){
            byte[] TodayWeatherByte = cursor.getBlob(cursor.getColumnIndex("TodayWeather"));
            Log.d("cursorOut",String.valueOf(TodayWeatherByte==null));
            weatherItem = (WeatherItem) bytesToObject(TodayWeatherByte);
        }
        cursor.close();
        return  weatherItem;
    }
    public List<WeatherItem> searchWeatherList(String date,String lat,String lon) throws Exception {
        List<WeatherItem> ForecastList = null;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query("Weather",new String[]{"ForecastList"},"date=? AND lat=? AND lon=?",new String[]{date,lat,lon},null,null,null);
        if (cursor.moveToFirst()&&cursor!=null)
        {
            byte[] ForecastListByte = cursor.getBlob(cursor.getColumnIndex("ForecastList"));
            ForecastList = (List<WeatherItem>)bytesToObject(ForecastListByte);
        }
        cursor.close();
        return  ForecastList;
    }

}
