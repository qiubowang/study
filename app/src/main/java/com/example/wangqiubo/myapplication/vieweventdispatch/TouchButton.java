package com.example.wangqiubo.myapplication.vieweventdispatch;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by wangqiubo on 2017/5/21.
 */

public class TouchButton extends android.support.v7.widget.AppCompatButton {
    public TouchButton(Context context) {
        super(context);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }


    public boolean dispatchTouchEvent(MotionEvent event){
        Log.d("ViewMotinEvent: ", "son_dispatch_" + MotinEventUtil.getTouchEventName(event));
//        return true;
        return super.dispatchTouchEvent(event);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        Log.d("ViewMotinEvent: ", "son_touch_" + MotinEventUtil.getTouchEventName(event));
        return super.onTouchEvent(event);
    }
}
