package com.example.jie.myapplicationtelephony;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by jie on 17-2-24.
 */

public class PhoneStatReceiver extends BroadcastReceiver {

    private static final String TAG = "PhoneStatReceiver";

//        private static MyPhoneStateListener phoneListener = new MyPhoneStateListener();

    private static boolean incomingFlag = false;

    private static String incoming_number = null;
    private static int count = 1;
    @Override
    public void onReceive(Context context, Intent intent) {
        //如果是拨打电话
        if(intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){
            incomingFlag = false;
            String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.i(TAG, "call OUT:"+phoneNumber);
        }else{
            //如果是来电
            TelephonyManager tm =
                    (TelephonyManager)context.getSystemService(Service.TELEPHONY_SERVICE);

            switch (tm.getCallState()) {
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.i(TAG, String.valueOf(count++));
                    incomingFlag = true;//标识当前是来电
                    incoming_number = intent.getStringExtra("incoming_number");
                    Log.i(TAG, "RINGING :"+ incoming_number);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.i(TAG, String.valueOf(count++));
                    Log.i(TAG, "CALL_STATE_OFFHOOK :"+ incoming_number);
                    if(incomingFlag){
                        Log.i(TAG, "incoming ACCEPT :"+ incoming_number);
                    }
                    break;

                case TelephonyManager.CALL_STATE_IDLE:
                    Log.i(TAG, String.valueOf(count++));
                    if(incomingFlag){
                        Log.i(TAG, "incoming IDLE");
                    }
                    break;
            }
        }
    }
}