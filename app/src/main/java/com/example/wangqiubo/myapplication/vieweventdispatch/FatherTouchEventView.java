package com.example.wangqiubo.myapplication.vieweventdispatch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by wangqiubo on 2017/5/19.
 */

public class FatherTouchEventView extends LinearLayout {
    public FatherTouchEventView(Context context) {
        super(context);
    }

    public FatherTouchEventView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FatherTouchEventView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        Log.d("ViewMotinEvent: ", "father_dispatch_" + MotinEventUtil.getTouchEventName(event));
        return false;
        //return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event){
        Log.d("ViewMotinEvent: ", "father_intercept_" + MotinEventUtil.getTouchEventName(event));
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        Log.d("ViewMotinEvent: ", "father_touch_" + MotinEventUtil.getTouchEventName(event));
        return super.onTouchEvent(event);
    }

}
