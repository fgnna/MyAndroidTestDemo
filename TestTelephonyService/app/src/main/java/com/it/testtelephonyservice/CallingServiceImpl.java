package com.it.testtelephonyservice;

import android.util.Log;


/**
 * Created by je on 17-3-6.
 */

public class CallingServiceImpl extends TelephonyCallingService
{
    private static final String TAG = "CallingServiceImpl";
    /**
     * 来电结束-未接听
     */
    protected  void onEndCallInComingNotAnswer()
    {
        Log.d(TAG, "来电结束-未接听");
    }

    /**
     * 来电结束-已接听
     */
    protected  void onEndCallInComingIsAnswer()
    {
        Log.d(TAG, "来电结束-已接听");
    }

    /**
     * 呼出结束-未接听
     */
    protected  void onEndCallOutNotAnswer()
    {
        Log.d(TAG, "呼出结束-未接听");
    }

    /**
     * 呼出结束-已接听
     */
    protected  void onEndCallOutIsAnswer()
    {
        Log.d(TAG, "呼出结束-已接听");
    }


    /**
     * 来电-待接听
     */
    protected  void onComeingCallRinging()
    {
        Log.d(TAG, "来电-待接听");
    }


    /**
     * 来电-正在通话
     */
    protected  void onAnswerComeingCall()
    {
        Log.d(TAG, "来电-正在通话");
    }

    /**
     * 呼出-正在通话
     */
    protected  void onOutCall()
    {
        Log.d(TAG, "呼出-正在通话");
    }
}
