package com.bignerdranch.android.weatherforecast;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by 杨文卓 on 2018/12/13.
 */

public class MyService extends IntentService {

    private static final int POLL_INTERVAL_MS = 1000*5;
    private static final String TAG = "MyService";
    public static Intent newIntent(Context context) {
        return new Intent(context, MyService.class);
    }
    public MyService() {
        super(TAG);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
//        if (!isNetworkAvailableAndConnected()) {
//            return;
//        }
        showNotification();
        Log.d("12345","hello");
    }
    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;
    }
    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent i = MyService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
        AlarmManager alarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);
        if (isOn) {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(), POLL_INTERVAL_MS, pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showNotification(){
        String id = "my_channel_01";
        String name="我是渠道名字";
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
        notificationManager.createNotificationChannel(mChannel);
        Intent resultIntent = new Intent(this,ForecastActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, 0);
        WeatherItem weatherItem = new WeatherFetchr().fetchTodayItems(ForecastActivity.CurrentLat,ForecastActivity.CurrentLon);
        //Log.d("1111",weatherItem.getDateNum());
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(weatherItem.getImageID())
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), weatherItem.getImageID()))
                        .setContentTitle("Today Weather:"+weatherItem.getWeather())
                        .setContentText("Max: "+weatherItem.getTempMaxCe()+" Min: "+weatherItem.getTempMinCe())
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setChannelId(id);
        notificationManager.notify(1, mBuilder.build());
    }
}
