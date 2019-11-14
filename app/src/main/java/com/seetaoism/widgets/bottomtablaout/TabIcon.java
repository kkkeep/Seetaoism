package com.seetaoism.widgets.bottomtablaout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

/*
 * created by taofu on 2019-09-09
 **/
@SuppressLint("AppCompatCustomView")
public class TabIcon extends ImageView implements Checkable {

    int[] CHECKED_STATE_SET = { android.R.attr.state_checked };

    private boolean mChecked = false;

    public TabIcon(Context context) {
        super(context);
    }

    public TabIcon(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TabIcon(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void playAnimation(){
        if(isChecked()){
            // 播放从选中到未选中的动画
        }else{
            // 播放 从未选中到选中的动画
        }
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            refreshDrawableState();
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }
}
