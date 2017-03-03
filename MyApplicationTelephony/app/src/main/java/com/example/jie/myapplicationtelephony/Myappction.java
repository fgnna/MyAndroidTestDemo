package com.example.jie.myapplicationtelephony;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by jie on 17-2-24.
 */

public class Myappction extends Application
{
    @Override
    public void onCreate() {
        Log.d("Myappction","onCreate");
        Intent intent2 = new Intent(this,TestAlarmManager.class);
        PendingIntent pd =PendingIntent.getBroadcast(getApplicationContext(), 0, intent2,PendingIntent.FLAG_ONE_SHOT);
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        long triggerTime = 0;
        am.set(AlarmManager.ELAPSED_REALTIME,triggerTime, pd);
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        Log.d("Myappction","onTerminate");
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}
