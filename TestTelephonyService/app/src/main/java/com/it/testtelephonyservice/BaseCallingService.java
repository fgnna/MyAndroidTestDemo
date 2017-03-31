package com.it.testtelephonyservice;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.provider.CallLog;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by je on 17-3-1.
 */
public abstract class BaseCallingService extends Service
{
    private static final String TAG = "cac_MyPhoneListerner";

    public static final String APP_OUTCALL_INTENT = "APP_OUTCALL_INTENT";

    private TelephonyManager mTelephonyManager;
    private MyPhoneListerner mMyPhoneListerner;
    private Handler mHandler = new Handler();


    /**
     * 用于判断是否由这个APP呼叫电话
     * 如果是由于其他APP呼出的电话，不对这一通电话进行管理
     * 所有本APP呼出的电话呼出前必须先调用{@link Context#startService(Intent)}
     * 传入{@link #APP_OUTCALL_INTENT} = true
     * @see #onStartCommand
     */
    private boolean isAppOutCall = false;
    //是否来电
    private boolean mIsCallIn = false;
    //是否未接来电
    private boolean isMissCallIn = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "监听服务启动");
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mMyPhoneListerner = new MyPhoneListerner();
        mTelephonyManager.listen(mMyPhoneListerner, PhoneStateListener.LISTEN_CALL_STATE);
        getContentResolver().registerContentObserver(CallLog.Calls.CONTENT_URI, false, new CallLogContentObserver(mHandler));
    }

    public static void startServiceForOutCall(Context context) {
        Intent intent = new Intent(context, CallingServiceImpl.class);
        intent.putExtra(APP_OUTCALL_INTENT, true);
        context.startService(intent);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null != intent) {
            isAppOutCall = intent.getBooleanExtra(APP_OUTCALL_INTENT, false);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTelephonyManager.listen(mMyPhoneListerner, PhoneStateListener.LISTEN_NONE);
    }


    /**
     * 当前电话通话状态
     * @see TelephonyManager#CALL_STATE_IDLE
     */
    protected int mCallState;
    private String mNumber1;//主叫号码
    private String mNumber2;//可能出现的呼叫等待号码

    class MyPhoneListerner extends PhoneStateListener {

        @Override
        public void onCallStateChanged(int state, String number) {
            mCallState = state;
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    mNumber1 = number;
                    mIsCallIn = true;
                    isMissCallIn = true;
                    onComeingCallRinging(number);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (mIsCallIn) {
                        onAnswerComeingCall(number);
                        isMissCallIn = false;
                    } else if (isAppOutCall) {
                        onOutCall(number);
                    }
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    onCallStateIdel();
                    mIsCallIn = false;
                    mNumber1 = null;
                    mNumber2 = null;

                    break;

            }
        }
    }

    /**
     * 在挂断后需要通过监听 CallLog 的变化来获取正确的通话记录
     * 及判断是否接听
     */
    class CallLogContentObserver extends ContentObserver {
        private String[] projection = new String[]{
                CallLog.Calls.DATE, CallLog.Calls.DURATION, CallLog.Calls.NUMBER,
                CallLog.Calls._ID, CallLog.Calls.TYPE, CallLog.Calls.IS_READ,
                CallLog.Calls.NEW};
        private String mLastid = "";

        public CallLogContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Log.d(TAG, "callLog记录变化通知");
            CallInfoEntity callInfoEntity = null;
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CALL_LOG) !=
                    PackageManager.PERMISSION_GRANTED) {
                onErrorNoCalllog();
                return;
            }
            Cursor cursor = getContentResolver().query(
                    CallLog.Calls.CONTENT_URI,
                    projection, null, null,
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

                    if(BuildConfig.DEBUG)
                    {
                        String lastname = "";
                        StringBuilder stringBuilder =  new StringBuilder();
                        int count = 1;
                        for(String name:cursor.getColumnNames())
                        {
                            stringBuilder.append(cursor.getString(cursor.getColumnIndex(name))).append(",");
                            if(count == 4)
                                lastname = name;
                            count++;
                        }
                        Log.d(TAG,stringBuilder.toString());
                        Log.d(TAG,lastname);
                    }

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                callInfoEntity = null;
                Log.e("BaseCallingService","查询通话记录出错了"+e.getMessage());
            }
            finally
            {
                if(null != cursor)
                    cursor.close();

            }

            /**
             * 对像存在  并且 isNew是 "1" 即插入新数据，而非更新的通知
             * 因为如果在原生通话里直接拨打电话，还是会出条两条重复的新增通知，
             * 所以还要在最后多加一个LastId，作为是否重复数据的判断
             *  by shaojunjie at 2017.03.07
             */
            if(null != callInfoEntity && "1".equals(callInfoEntity.isNew) && !mLastid.equals(callInfoEntity._id))
            {
                mLastid = callInfoEntity._id;
                handlerCallLogChange(callInfoEntity);
            }

            if(null == callInfoEntity)
            {
                //如果没查出记录，可能 是因为权限被拒绝了
                onErrorNoCalllog();
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

        infoEntity.duration = infoEntity.duration * 1000;//秒数转毫秒数 后台需要
        if(infoEntity.type == CallLog.Calls.OUTGOING_TYPE )
        {
            if(!isAppOutCall)
                return;
            isAppOutCall = false;

            //去电
            if(infoEntity.duration > 0)
                onEndCallOutIsAnswer(infoEntity);
            else
                onEndCallOutNotAnswer(infoEntity);
        }
        else
        {
            /**
             * 来电判断
             */
            if(isMissCallIn)
            {
                infoEntity.duration = 0;
                onEndCallInComingNotAnswer(infoEntity);
            }
            else
            {
                onEndCallInComingIsAnswer(infoEntity);
            }
        }

    }

    /**
     * 来电结束-未接听
     * @param infoEntity
     */
    protected abstract void onEndCallInComingNotAnswer(CallInfoEntity infoEntity);

    /**
     * 来电结束-已接听
     * @param infoEntity
     */
    protected abstract void onEndCallInComingIsAnswer(CallInfoEntity infoEntity);

    /**
     * 呼出结束-未接听
     * @param infoEntity
     */
    protected abstract void onEndCallOutNotAnswer(CallInfoEntity infoEntity);

    /**
     * 呼出结束-已接听
     * @param infoEntity
     */
    protected abstract void onEndCallOutIsAnswer(CallInfoEntity infoEntity);


    /**
     * 来电-待接听
     * @param number
     */
    protected abstract void onComeingCallRinging(String number);


    /**
     * 来电-正在通话
     */
    protected abstract void onAnswerComeingCall(String number);

    /**
     * 呼出-正在通话
     * @param number
     */
    protected abstract void onOutCall(String number);

    /**
     * 没有通话记录异常 ，通常是由于没权限
     */
    protected abstract void onErrorNoCalllog();

    /**
     * 话机进入空闲状态，此方法用于处理一些因系统原因，
     * 通话结束后没有产生calllog而导致问题，
     * 比如小米手机出现过，在头闭wifi和4g网络情况下，不产生calllog的奇怪问题
     * 在这方法里做一些清理工作
     */
    protected abstract void onCallStateIdel();

}
