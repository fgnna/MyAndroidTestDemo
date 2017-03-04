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
    public void onCreate()
    {
        super.onCreate();
        startService(new Intent(this,CallService.class));


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
