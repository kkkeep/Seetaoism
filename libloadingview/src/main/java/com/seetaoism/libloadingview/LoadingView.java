package com.seetaoism.libloadingview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntDef;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Group;
import androidx.viewpager.widget.ViewPager;

import com.cunoraz.gifview.library.GifView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
 * created by Cherry on 2019-09-04
 **/
public class LoadingView extends ConstraintLayout {

    public static final int LOADING_MODE_TRANSPARENT_BG = 1; // 背景透明

    public static final int LOADING_MODE_WHITE_BG = 2; // 背景不透明



    private RetryCallBack mRetryCallBack;

    private OnCancelListener mCloseListener;

    private ViewGroup mParentViewGroup;

    private LinearLayout mLoadingLayout;

    private Group mErrorGroup;

    private GifView mGifView;

    private TextView mTvErrorMsg;

    private Button mBtnRetry;

    private int mCurMode;

    private boolean isCancelAble;


    @IntDef({LOADING_MODE_TRANSPARENT_BG,LOADING_MODE_WHITE_BG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoadingMode{ }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mLoadingLayout = findViewById(R.id.loading_load_layout);

        mErrorGroup = findViewById(R.id.loading_error_layout);

        mGifView = findViewById(R.id.loading_gif_view);

        mTvErrorMsg = findViewById(R.id.loading_tv_error_msg);

        mBtnRetry = findViewById(R.id.loading_btn_retry);


        mBtnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRetryCallBack != null){
                    mRetryCallBack.onRetry();
                    if(mCurMode == LOADING_MODE_WHITE_BG){
                        show(mCurMode);
                    }
                }
            }
        });
    }

    public static  LoadingView inject(Context context, ViewGroup group){

        for(int i = 0; i < group.getChildCount(); i++){
            if(group.getChildAt(i) instanceof  LoadingView){
                return (LoadingView) group.getChildAt(i);
            }
        }

       LoadingView loadingView =  (LoadingView) LayoutInflater.from(context).inflate(R.layout.layout_loading, null);

       loadingView.mParentViewGroup = group;

       return loadingView;

    }

    public void setCancelListener(OnCancelListener closeListener) {


        this.mCloseListener = closeListener;

    }
    public void show(@LoadingMode int mode){

        show(mode, false);
    }


    @SuppressLint("ResourceType")
    public void show(@LoadingMode int mode,boolean cancelAble){

        isCancelAble = cancelAble;

        if(this.getParent() == null) {
            String pName =  mParentViewGroup.getClass().getSimpleName();
            if (pName.equals(FrameLayout.class.getSimpleName()) || pName.equals(RelativeLayout.class.getSimpleName()) ) {
                mParentViewGroup.addView(this);

            } else if (pName.equals(ConstraintLayout.class.getSimpleName())) {
                ConstraintSet constraintSet = new ConstraintSet();
                this.setId(100000);
                constraintSet.clone((ConstraintLayout) mParentViewGroup);

                constraintSet.connect(this.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                constraintSet.connect(this.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
                constraintSet.connect(this.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
                constraintSet.connect(this.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);

                constraintSet.constrainWidth(this.getId(), ConstraintSet.MATCH_CONSTRAINT);
                constraintSet.constrainHeight(this.getId(), ConstraintSet.MATCH_CONSTRAINT);

                mParentViewGroup.addView(this);
                constraintSet.applyTo((ConstraintLayout) mParentViewGroup);
            } else {


                ViewGroup parent = (ViewGroup) mParentViewGroup.getParent();
                FrameLayout root = new FrameLayout(parent.getContext());
                root.setLayoutParams(mParentViewGroup.getLayoutParams());
                parent.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                parent.removeView(mParentViewGroup);
                parent.addView(root);

                parent.invalidate();
                root.addView(mParentViewGroup);
                parent.requestLayout();
                root.addView(this);
            }
        }


        mCurMode = mode;
        if(mode == LOADING_MODE_WHITE_BG){
            setBackgroundColor(Color.WHITE);
            mLoadingLayout.setBackground(null);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(this);
            constraintSet.constrainWidth(mLoadingLayout.getId(), ViewGroup.LayoutParams.WRAP_CONTENT);
            constraintSet.constrainHeight(mLoadingLayout.getId(), ViewGroup.LayoutParams.WRAP_CONTENT);
            constraintSet.applyTo(this);


        }else{
            mLoadingLayout.setBackgroundResource(R.drawable.loading_bg);
            int width = dip2px(getContext(), 100);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(this);
            constraintSet.constrainWidth(mLoadingLayout.getId(), width);
            constraintSet.constrainHeight(mLoadingLayout.getId(), width);
            constraintSet.applyTo(this);
            setBackgroundColor(Color.TRANSPARENT);
        }
        mErrorGroup.setVisibility(GONE);
        mLoadingLayout.setVisibility(VISIBLE);



        setFocusableInTouchMode(true);
        requestFocus();
        setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    if(isShown()){
                        if(isCancelAble && mCurMode == LOADING_MODE_TRANSPARENT_BG){
                            close();
                        }
                        return true;
                    }
                }
                return false;
            }
        });
        play();
    }

    // 播放帧动画 或者 gif 动画
    private void play(){
            mGifView.play();
    }

    public void close(){
        mGifView.pause();

        ViewGroup parent = (ViewGroup) getParent();
        if(parent != null){
            parent.removeView(this);
        }
    }


    public void onError(String msg ,RetryCallBack callBack){

        if(mCurMode == LOADING_MODE_TRANSPARENT_BG){
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            close();
            return;
        }

        mLoadingLayout.setVisibility(GONE);
        mErrorGroup.setVisibility(VISIBLE);

        mTvErrorMsg.setText(msg);

        mRetryCallBack = callBack;


    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mCloseListener != null){
            mCloseListener.onCancel();
        }
    }

    public boolean isShow(){
        return getParent() != null;
    }

    private  int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public interface  RetryCallBack{
        void onRetry();
    }


    public interface OnCancelListener {

        void onCancel();

    }


}
