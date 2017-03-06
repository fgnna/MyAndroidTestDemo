package com.it.testtelephonyservice;

/**
 * Created by je on 17-3-6.
 */

public class CallInfoEntity
{

    /**
     * calllog ID
     */
    public String _id;
    /**
     * 电话号码
     */
    public String number;

    /**
     * 通话时间 ，毫秒数
     */
    public long date;
    /**
     * 通话时长，毫秒数
     */
    public long duration;
    /**
     * 通话类型
     * @see android.provider.CallLog.Calls.INCOMING_TYPE
     * @see android.provider.CallLog.Calls.OUTGOING_TYPE
     * @see android.provider.CallLog.Calls.MISSED_TYPE
     * @see android.provider.CallLog.Calls.INCOMING_TYPE
     * @see android.provider.CallLog.Calls.REJECTED_TYPE
     */
    public int type;

    public String isNew;


    @Override
    public String toString() {
        return "CallInfoEntity{" +
                "_id='" + _id + '\'' +
                ", number='" + number + '\'' +
                ", date=" + date +
                ", duration=" + duration +
                ", type=" + type +
                ", isNew='" + isNew + '\'' +
                '}';
    }
}
