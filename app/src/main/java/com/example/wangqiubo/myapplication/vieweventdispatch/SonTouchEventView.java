package com.example.wangqiubo.myapplication.vieweventdispatch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by wangqiubo on 2017/5/19.
 */

public class SonTouchEventView extends LinearLayout implements View.OnClickListener {
    public SonTouchEventView(Context context) {
        super(context);
    }

    public SonTouchEventView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SonTouchEventView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        Log.d("ViewMotinEvent: ", "son_dispatch_" + MotinEventUtil.getTouchEventName(event));
//        return true;
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event){
        Log.d("ViewMotinEvent: ", "son_intercept_" + MotinEventUtil.getTouchEventName(event));
//        return true;
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        Log.d("ViewMotinEvent: ", "son_touch_" + MotinEventUtil.getTouchEventName(event));
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        Log.d("ViewMotinEvent: ", "son_performClick_");
        return super.performClick();
    }

    @Override
    public void onClick(View v) {
        Log.d("ViewMotinEvent: ", "Son_onClick");
    }
}
