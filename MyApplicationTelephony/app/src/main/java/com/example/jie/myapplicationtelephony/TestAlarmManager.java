package com.example.jie.myapplicationtelephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by jie on 17-2-24.
 */

public class TestAlarmManager extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d("Myappction","TestAlarmManager"+ SystemClock.currentThreadTimeMillis());
    }



}
