package com.seetaoism.widgets.bottomtablaout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

/*
 * created by Cherry on 2019-09-09
 **/
public class BottomTab extends LinearLayout {


    private TabIcon mTabIcon;
    private TextView mTabValue;


    private OnClickListener mOnClickListener;

    public BottomTab(Context context) {
        super(context);
    }

    public BottomTab(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomTab(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSelect(boolean select){

        mTabIcon.setChecked(select);
    }

    public boolean isSelect(){
       return mTabIcon.isChecked();
    }

    public void playAnimation(){
        mTabIcon.playAnimation();
    }

    public void setTitle(String value){
        mTabValue.setText(value);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
        mOnClickListener = l;
    }


    public OnClickListener getOnClickListener() {
        return mOnClickListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view;
        for(int i = 0; i < getChildCount(); i++){

            view = getChildAt(i);

            if(mTabIcon == null && view instanceof TabIcon){

                mTabIcon = (TabIcon) view;

            }

            if(mTabValue == null && view instanceof TextView){
                mTabValue = (TextView) view;
            }

        }
    }
}
