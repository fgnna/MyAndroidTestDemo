package com.it.testtelephonyservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by je on 17-3-1.
 */

public abstract class TelephonyCallingService extends Service
{
    private static final String TAG = "MyPhoneListerner";

    private TelephonyManager mTelephonyManager;
    private MyPhoneListerner mMyPhoneListerner;
    private Handler mHandler = new Handler();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d(TAG, "监听服务启动");
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mMyPhoneListerner = new MyPhoneListerner();
        mTelephonyManager.listen(mMyPhoneListerner, PhoneStateListener.LISTEN_CALL_STATE);
        getContentResolver().registerContentObserver(CallLog.Calls.CONTENT_URI, false, new CallLogContentObserver(mHandler));
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mTelephonyManager.listen(mMyPhoneListerner,PhoneStateListener.LISTEN_NONE);
    }

    private boolean mIsCallIn = false;//是否来电
    /**
     * 当前电话通话状态
     * @see TelephonyManager#CALL_STATE_IDLE
     */
    private int mCallState ;
    private String mNumber1;//主叫号码
    private String mNumber2;//可能出现的呼叫等待号码

    class MyPhoneListerner extends PhoneStateListener
    {

        @Override
        public void onCallStateChanged(int state, String incomingNumber)
        {
            mCallState = state;
            switch (state)
            {
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.d(TAG, "IDLE-" + incomingNumber);
                    mIsCallIn = false;
                    mNumber1 = null;
                    mNumber2 = null;

                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.d(TAG, "RINGING-" + incomingNumber);

                    mNumber1 = incomingNumber;

                    mIsCallIn = true;
                    onComeingCallRinging();
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.d(TAG, "OFFHOOK-" + incomingNumber);
                    if (mIsCallIn)
                        onAnswerComeingCall();
                    else
                        onOutCall();
                    break;

            }
        }
    }


    /**
     * 通话结束，并产生系统通话记录后处理业务分发
     * @param infoEntity
     */
    private void handlerCallLogChange(CallInfoEntity infoEntity)
    {
        if(BuildConfig.DEBUG)
            Log.d(TAG,infoEntity.toString());


        if(infoEntity.type == CallLog.Calls.OUTGOING_TYPE)
        {
            //去电
            if(infoEntity.duration > 0)
                onEndCallOutIsAnswer();
            else
                onEndCallOutNotAnswer();
        }
        else
        {
            //来电
            if(infoEntity.duration > 0)
                onEndCallInComingIsAnswer();
            else
                onEndCallInComingNotAnswer();
        }

    }

    /**
     * 来电结束-未接听
     */
    protected abstract void onEndCallInComingNotAnswer();

    /**
     * 来电结束-已接听
     */
    protected abstract void onEndCallInComingIsAnswer();

    /**
     * 呼出结束-未接听
     */
    protected abstract void onEndCallOutNotAnswer();

    /**
     * 呼出结束-已接听
     */
    protected abstract void onEndCallOutIsAnswer();


    /**
     * 来电-待接听
     */
    protected abstract void onComeingCallRinging();


    /**
     * 来电-正在通话
     */
    protected abstract void onAnswerComeingCall();

    /**
     * 呼出-正在通话
     */
    protected abstract void onOutCall();


    /**
     * 在挂断后需要通过监听 CallLog 的变化来获取正确的通话记录
     * 及判断是否接听
     */
    class CallLogContentObserver extends ContentObserver
    {
        private String[] projection =  new String[]{
                CallLog.Calls.DATE, CallLog.Calls.DURATION, CallLog.Calls.NUMBER,
                CallLog.Calls._ID, CallLog.Calls.TYPE,CallLog.Calls.IS_READ};

        public CallLogContentObserver(Handler handler) {super(handler);}

        @Override
        public void onChange(boolean selfChange)
        {
            // TODO Auto-generated method stub
            super.onChange(selfChange);
            Log.d(TAG, "callLog记录变化通知");
            CallInfoEntity callInfoEntity = null;
            Cursor cursor = getContentResolver().query(
                    CallLog.Calls.CONTENT_URI,
                    null, null, null,
                    "_ID DESC");
            try
            {
                if (cursor.moveToNext())
                {
                    callInfoEntity = new CallInfoEntity();
                    callInfoEntity._id = cursor.getString(cursor.getColumnIndex(CallLog.Calls._ID));
                    callInfoEntity.number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    callInfoEntity.type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
                    callInfoEntity.duration = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DURATION));
                    callInfoEntity.date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                    callInfoEntity.isNew = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NEW));
//                    if(BuildConfig.DEBUG)
//                    {
//                        String lastname = "";
//                        StringBuilder stringBuilder =  new StringBuilder();
//                        int count = 1;
//                        for(String name:cursor.getColumnNames())
//                        {
//                            stringBuilder.append(cursor.getString(cursor.getColumnIndex(name))).append(",");
//                            if(count == 4)
//                                lastname = name;
//                            count++;
//                        }
//                        Log.d(TAG,stringBuilder.toString());
//                        Log.d(TAG,lastname);
//                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                callInfoEntity = null;
                Log.e("TelephonyCallingService","查询通话记录出错了"+e.getMessage());
            }
            finally
            {
                if(null != cursor)
                    cursor.close();

            }

            /**
             * 对像存在  并且 isNew是 "1" 即第一条插入数据库，而非更新的通知
             */
            if(null != callInfoEntity && "1".equals(callInfoEntity.isNew))
                handlerCallLogChange(callInfoEntity);
        }

    }

}
