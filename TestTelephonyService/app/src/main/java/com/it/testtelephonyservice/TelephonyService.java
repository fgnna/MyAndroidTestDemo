package com.it.testtelephonyservice;

import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by je on 17-3-1.
 */

public class TelephonyService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyPhoneListerner", "监听服务启动");
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(new MyPhoneListerner(this),
                PhoneStateListener.LISTEN_CALL_STATE);


    }


    public static class TelephonyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }


    public static class MyPhoneListerner extends PhoneStateListener {
        private boolean mIsCallIn = false;
        private boolean mIsAnswer = false;
        private Context mContext;
        private Handler mHandler = new Handler();

        public MyPhoneListerner(Context context) {
            mContext = context;
        }


        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.d("MyPhoneListerner", "IDLE-" + incomingNumber);

                    if (null == incomingNumber || "".equals(incomingNumber)) {
                        return;
                    }
                    if (mIsAnswer) {
                        if (mIsCallIn)
                            onComeingCallIsEnd();
                        else
                        {
                            mContext.getContentResolver().registerContentObserver(CallLog.Calls.CONTENT_URI, true,
                                    new SMSContentObserver(mContext,mHandler)
                                    {
                                        @Override
                                        public void message()
                                        {
                                            onCallOutIsEnd();
                                        }
                                    }
                            );
                        }
                    } else {
                        onComeingCallIsNotAnswer();
                    }

                    mIsCallIn = false;
                    mIsAnswer = false;
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.d("MyPhoneListerner", "RINGING-" + incomingNumber);
                    mIsCallIn = true;
                    onComeingCall();
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.d("MyPhoneListerner", "OFFHOOK-" + incomingNumber);
                    if (mIsCallIn)
                        onAnswerComeingCall();
                    else
                        onOutCall();


                    mIsAnswer = true;
                    break;

            }

        }

        private void onComeingCall() {
            Log.d("MyPhoneListerner", "来电-待接听");
        }

        private void onComeingCallIsNotAnswer() {
            Log.d("MyPhoneListerner", "来电-不接听");
        }

        private void onComeingCallIsEnd() {
            Log.d("MyPhoneListerner", "来电-通话结束");
        }

        private void onAnswerComeingCall() {
            Log.d("MyPhoneListerner", "来电-正在通话");
        }


        private void onOutCall() {
            Log.d("MyPhoneListerner", "呼出-正在通话");
        }

        private void onCallOutIsEnd() {
            Log.d("MyPhoneListerner", "呼出-通话结束");
            getLatestCallHistory();

        }

        static abstract class SMSContentObserver extends ContentObserver {

            Handler handler;
            Context mContext;
            private boolean isUnregister = false;

            public SMSContentObserver(Context context, Handler handler) {
                super(handler);
                // TODO Auto-generated constructor stub
                mContext = context;
                this.handler = handler;
            }

            abstract void message();


            @Override
            public void onChange(boolean selfChange) {
                // TODO Auto-generated method stub
                super.onChange(selfChange);
                Log.d("MyPhoneListerner", "callLog记录变化通知");
                message();
                if(!isUnregister)
                    isUnregister = true;
                mContext.getContentResolver().unregisterContentObserver(this);
            }

        }

        private void getLatestCallHistory() {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_CALL_LOG) !=
                    PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Cursor cursor = mContext.getContentResolver().query(
                    CallLog.Calls.CONTENT_URI,
                    new String[]{CallLog.Calls.DATE, CallLog.Calls.DURATION, CallLog.Calls.NUMBER, CallLog.Calls._ID, CallLog.Calls.TYPE}, null, null,
                    "_ID DESC");

            if (cursor.moveToNext()) {
                String date = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
                Long duration = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DURATION));
                String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                String id = cursor.getString(cursor.getColumnIndex(CallLog.Calls._ID));
                String type = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));

                JSONObject obj = new JSONObject();

                try {
                    obj.put("date", date);
                    obj.put("duration", duration);
                    obj.put("number", number);
                    obj.put("id", id);
                    obj.put("type", type);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Log.d("MyPhoneListerner", obj.toString());

            }
            cursor.close();
        }

    }



}
