package com.example.wangqiubo.myapplication.vieweventdispatch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.wangqiubo.myapplication.R;

/**
 * Created by wangqiubo on 2017/5/18.
 */

public class ViewEventActivity extends Activity implements View.OnClickListener{
    FatherTouchEventView fatherTouchEventView = null;
    SonTouchEventView sonTouchEventView = null;
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.touch_event_main);

        fatherTouchEventView = (FatherTouchEventView)findViewById(R.id.father_event);
        sonTouchEventView = (SonTouchEventView)findViewById(R.id.son_touchevent);

//        fatherTouchEventView.setOnClickListener(this);
//        sonTouchEventView.setOnClickListener(this);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
//        if (event.getAction() == MotionEvent.ACTION_DOWN){
//
//        }
//
//        if (getWindow().superDispatchTouchEvent(event)){
//            return true;
//        }

//        return onTouchEvent(event);

        Log.d("ViewMotinEvent: ", "Activity_dispatch_" + MotinEventUtil.getTouchEventName(event));
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        Log.d("ViewMotinEvent: ", "Activity_touchEvent_" + MotinEventUtil.getTouchEventName(event));
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        Log.d("ViewMotinEvent: ", "Activity_onClick");
        if(v.getId() == R.id.father_event){
            Log.d("ViewMotinEvent: ", "Activity_Father_onClickListenler");
        }else if (v.getId() == R.id.son_touchevent){
            Log.d("ViewMotinEvent: ", "Activity_Son_onClickListenler");
        }
    }
}
