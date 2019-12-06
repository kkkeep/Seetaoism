package com.kkk.libbottomtablayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

/*
 * created by Cherry on 2019-09-09
 **/
public class BottomTabLayout extends ConstraintLayout {


    private OnTabSelectedChangeListener mTabSelectedChangeListener;

    private SparseArray<BottomTab> mChilds = new SparseArray<>();

    public BottomTabLayout(Context context) {
        super(context);
    }

    public BottomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


        for (int i = 0; i < getChildCount(); i++) {

            View v = getChildAt(i);

            if (!(v instanceof BottomTab)) {
                continue;
            }
            mChilds.put(mChilds.size() + 1, (BottomTab) v);
            ((BottomTab) v).setIndex(mChilds.size());

            // 为每一个 tab 设置点击事件
            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    BottomTab bottomTab = (BottomTab) v;
                    if (bottomTab.isSelect()) {
                        if (mTabSelectedChangeListener != null) {
                            mTabSelectedChangeListener.onSelectedAgain(bottomTab.getIndex());
                        }
                    } else {
                        bottomTab.setSelect(true);
                        unSelectOther(bottomTab);

                        bottomTab.playAnimation();// 播放帧动画

                        if (mTabSelectedChangeListener != null) {
                            mTabSelectedChangeListener.onSelect(bottomTab.getIndex());
                        }
                    }

                }
            });
        }

    }

    /**
     * 除了当前 tab 以外，把其他的设置为选中
     *
     * @param selectedChild
     */
    private void unSelectOther(BottomTab selectedChild) {

        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if ((v instanceof BottomTab) && v != selectedChild) {
                if (((BottomTab) v).isSelect()) {
                    ((BottomTab) v).setSelect(false);
                    ((BottomTab) v).playAnimation();
                }
            }
        }

    }

    public void setTabSelectedChangeListener(OnTabSelectedChangeListener tabSelectedChangeListener) {
        this.mTabSelectedChangeListener = tabSelectedChangeListener;
    }


    // position  从第 1 开始，而不是 0
    public void selectTab(int position) {

        View childView = mChilds.get(position);

        OnClickListener listener = ((BottomTab) childView).getOnClickListener();
        listener.onClick(childView);
    }

    public void setTabTitle(int position, String title) {
        View childView = getChildAt(position);

        if (childView instanceof BottomTab) {
            ((BottomTab) childView).setTitle(title);
        }
    }

    public interface OnTabSelectedChangeListener {

        void onSelect(int position);

        void onSelectedAgain(int position);
    }
}
