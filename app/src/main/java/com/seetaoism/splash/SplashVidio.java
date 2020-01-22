package com.seetaoism.splash;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/*
 * created by Cherry on 2020-01-17
 **/
public class SplashVidio extends StandardGSYVideoPlayer {
    public SplashVidio(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public SplashVidio(Context context) {
        super(context);
    }

    public SplashVidio(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    protected void setViewShowState(View view, int visibility) {
        if(view == mStartButton){
            view.setVisibility(GONE);
            return;
        }
        if (view != null) {
            view.setVisibility(GONE);
        }
    }
}
