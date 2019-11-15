package com.seetaoism.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;

/*
 * created by Cherry on 2019-09-15
 **/


public class MatchScrollParentCLayout extends ConstraintLayout {

    private int mPHeight;
    private int mHeight;

    public MatchScrollParentCLayout(Context context) {
        super(context);
    }

    public MatchScrollParentCLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MatchScrollParentCLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if(mHeight > 0){
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY));
        }else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        if(mPHeight == 0){
            ViewGroup parent = (ViewGroup) getParent();
            mPHeight = parent.getMeasuredHeight();

            int mMyHeight = getHeight();

            if(mMyHeight < mPHeight){
                mHeight = mPHeight;
                post(new Runnable() {
                    @Override
                    public void run() {
                        requestLayout();
                    }
                });
                return;
            }
        }
        super.onLayout(changed, left, top, right, bottom);


    }
}
