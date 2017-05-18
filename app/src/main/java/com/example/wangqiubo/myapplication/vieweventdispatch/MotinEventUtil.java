package com.example.wangqiubo.myapplication.vieweventdispatch;

import android.view.MotionEvent;

/**
 * Created by wangqiubo on 2017/5/19.
 */

public class MotinEventUtil {

    public static String getTouchEventName(MotionEvent motionEvent){
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                return "action_domn";
            case MotionEvent.ACTION_MOVE:
                return "action_move";
            case MotionEvent.ACTION_UP:
                return "action_up";
            case MotionEvent.ACTION_CANCEL:
                return "action_cancle";
            default:
                return "";
        }

    }
}
