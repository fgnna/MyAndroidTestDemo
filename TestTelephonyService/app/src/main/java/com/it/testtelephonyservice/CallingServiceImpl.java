package com.it.testtelephonyservice;

import android.util.Log;


/**
 * Created by je on 17-3-6.
 */

public class CallingServiceImpl extends BaseCallingService
{
    private static final String TAG = "CallingServiceImpl";



    @Override
    protected void onEndCallInComingNotAnswer(CallInfoEntity infoEntity) {

    }

    @Override
    protected void onEndCallInComingIsAnswer(CallInfoEntity infoEntity) {

    }

    @Override
    protected void onEndCallOutNotAnswer(CallInfoEntity infoEntity) {

    }

    @Override
    protected void onEndCallOutIsAnswer(CallInfoEntity infoEntity) {

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

    }

    @Override
    protected void onCallStateIdel() {

    }
}
