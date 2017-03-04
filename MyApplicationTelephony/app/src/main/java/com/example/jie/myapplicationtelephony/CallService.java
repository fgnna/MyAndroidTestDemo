package com.example.jie.myapplicationtelephony;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by mac on 17/3/4.
 */

public class CallService extends Service {

    private TelephonyManager mTelephonyManager;
    private MyPhoneStateListener mMyPhoneStateListener;
    private CallLogInsertContentObserver mCallLogInstertContentObserver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mTelephonyManager = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        mMyPhoneStateListener = new MyPhoneStateListener();
        mCallLogInstertContentObserver = new CallLogInsertContentObserver(new Handler());
        mTelephonyManager.listen(mMyPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        getContentResolver().registerContentObserver(CallLog.Calls.CONTENT_URI, false, mCallLogInstertContentObserver);
        Log.d("call_thread","main="+String.valueOf(Thread.currentThread().getId()));
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mTelephonyManager.listen(mMyPhoneStateListener,PhoneStateListener.LISTEN_NONE);
        getContentResolver().unregisterContentObserver(mCallLogInstertContentObserver);
    }

    /**
     * 监听电话状态变化
     */
    class MyPhoneStateListener extends PhoneStateListener
    {
        @Override
        public void onCallStateChanged(int state, String incomingNumber)
        {
            switch (state)
            {
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                default:
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }

    }

    /*

    formatted_number,photo_uri,numbertype,duration,presentation,geocoded_location,lookup_uri,countryiso,transcription,subscription_id,
    null,            null,     null,      0,       1,           California,       null,      US,        null,         89014103211118510720,
    photo_id,is_read,    voicemail_uri,features,new,phone_account_address,date,         data_usage,numberlabel,subscription_component_name,
    0,       0,          null,         0,       1,  15555215554,          1488615215440,null,      null,       com.android.phone/com.android.services.telephony.TelephonyConnectionService,
    name,matched_number,normalized_number,
    null,null,          +16505551212,

    _id | type | number       | duration(通话时长),
    4   | 3    | 6505551212   | 0

    type
    1：来电（接听或主动挂断），
    2：去电
    3：来电（未接听被动挂断)

     */

    /**
     * 监听通话记录变化
     */
    class CallLogInsertContentObserver extends ContentObserver
    {
        String[] columnNames = new String[]{"_id","type","number","duration"};

        @Override
        public void onChange(boolean selfChange, Uri uri)
        {
            super.onChange(selfChange, uri);
            Log.d("call_log_uri",uri.toString());
            Log.d("call_log_selfChange2",String.valueOf(selfChange));

            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED)
            {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Log.d("call","没有权限");
            }
            Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI,columnNames, null, null, "_id DESC limit 1");
            if(cursor.moveToNext())
            {
                StringBuilder valueLogString = new StringBuilder();
                StringBuilder columnNamesLogString = new StringBuilder();
                for (String columnName : columnNames)
                {
                    columnNamesLogString.append(columnName).append(",");
                    valueLogString.append(cursor.getString(cursor.getColumnIndex(columnName))).append(",");
                }
                Log.d("call",columnNamesLogString.toString());
                Log.d("call",valueLogString.toString());
            }
        }

        public CallLogInsertContentObserver(Handler handler)
        {
            super(handler);
            Log.d("call_thread","calllog="+String.valueOf(Thread.currentThread().getId()));

        }
    }

}
