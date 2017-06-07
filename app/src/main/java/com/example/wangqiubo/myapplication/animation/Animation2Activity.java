package com.example.wangqiubo.myapplication.animation;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.wangqiubo.myapplication.R;

/**
 * Created by wangqiubo on 2017/5/31.
 */

public class Animation2Activity extends Activity implements View.OnClickListener{
    private Button mEndAnimButton = null;
    private Button mStartAnimButton = null;
    private LinearLayout mLinearLayout = null;
    private AnimationDrawable mAnimationDrawable = null;
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.animation_layout);
        mEndAnimButton = (Button)findViewById(R.id.end_anim_button);
        mEndAnimButton.setOnClickListener(this);
        mStartAnimButton = (Button)findViewById(R.id.start_anim_button);
        mStartAnimButton.setOnClickListener(this);

        mLinearLayout = (LinearLayout)findViewById(R.id.frame_layout);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_anim_button){
            if (null != mAnimationDrawable){
                if (mAnimationDrawable.isRunning())
                    return;
                mAnimationDrawable.run();
            }else {
//            Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_view);
//            mButton.setAnimation(animation);
                mLinearLayout.setBackgroundResource(R.drawable.frame_animation);
                mAnimationDrawable = (AnimationDrawable) mLinearLayout.getBackground();
                mAnimationDrawable.setOneShot(false);
                mAnimationDrawable.start();
            }
        }else if(v.getId() == R.id.end_anim_button){
            if (null != mAnimationDrawable && mAnimationDrawable.isRunning()){
                mAnimationDrawable.stop();;
            }
        }
    }
}
