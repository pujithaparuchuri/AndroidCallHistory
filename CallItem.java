package com.calllog;

/**
 * Created by Srik on 2/1/2017.
 */

/**
* This class is used for maintaining the time,duration and the call type to be maintaied for call log
*/

public class CallItem {
    String dtime;
    String duration;
    String callType;

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getDtime() {
        return dtime;
    }

    public void setDtime(String dtime) {
        this.dtime = dtime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
