package com.it.testtelephonyservice;

import android.app.Application;
import android.content.Intent;

/**
 * Created by je on 17-3-1.
 */

public class MyAppcontion extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Intent startIntent = new Intent(this, TelephonyService.class);
        startService(startIntent);
    }
}
