package com.it.testtelephonyservice;

import android.telephony.PhoneStateListener;
import android.util.Log;


/**
 * Created by je on 17-3-6.
 */

public class CallingServiceImpl extends BaseCallingService
{
    private static final String TAG = "CallingServiceImpl";



    @Override
    protected void onEndCallInComingNotAnswer(CallInfoEntity infoEntity) {

        Log.d(TAG, "来电，未接听");
    }

    @Override
    protected void onEndCallInComingIsAnswer(CallInfoEntity infoEntity) {
        Log.d(TAG, "来电，已接听");
    }

    @Override
    protected void onEndCallOutNotAnswer(CallInfoEntity infoEntity) {
        Log.d(TAG, "去电，未接听");
    }

    @Override
    protected void onEndCallOutIsAnswer(CallInfoEntity infoEntity) {
        Log.d(TAG, "去电，已接听");
    }

    /**
     * 来电-待接听
     * @param number
     */
    protected  void onComeingCallRinging(String number)
    {
        Log.d(TAG, "来电-待接听");
    }

    @Override
    protected void onAnswerComeingCall(String number) {
        Log.d(TAG, "来电，接听");
    }

    /**
     * 呼出-正在通话
     * @param number
     */
    protected  void onOutCall(String number)
    {
        Log.d(TAG, "呼出-正在通话");
    }

    @Override
    protected void onErrorNoCalllog() {
        Log.d(TAG, "无log");
    }

    @Override
    protected void onCallStateIdel() {
        Log.d(TAG, "电话回归空闲");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
