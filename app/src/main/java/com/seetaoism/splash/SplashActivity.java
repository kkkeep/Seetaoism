package com.seetaoism.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.mr.k.mvp.UserManager;
import com.mr.k.mvp.base.MvpBaseActivity;
import com.mr.k.mvp.utils.Logger;
import com.mr.k.mvp.utils.SPUtils;
import com.mr.k.mvp.utils.SystemFacade;
import com.seetaoism.BuildConfig;
import com.seetaoism.GlideApp;
import com.seetaoism.R;
import com.seetaoism.data.entity.Ad;
import com.seetaoism.data.entity.User;
import com.seetaoism.home.HomeActivity;
import com.seetaoism.user.login.LoginContract;
import com.seetaoism.user.login.LoginGetUserPresenter;
import com.seetaoism.widgets.CountDownView;
import com.seetaoism.widgets.SplashPopWindow;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.io.File;

import retrofit2.http.Url;


/*
 * created by Cherry on 2019-09-02
 **/
public class SplashActivity extends MvpBaseActivity<LoginContract.ILoginGetUserInfoPresenter> implements LoginContract.ILoginGetUserInfoView {

    private static final String TAG = "SplashActivity";
    private ViewPager mViewPager;

    private int[] mGuideImages;
    ImageView imageView;

    private static Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         imageView   = new ImageView(this);

         imageView.setBackgroundResource(R.drawable.splash_bg);

        // setContentView(imageView);

       /* if(true){
            return;
        }
*/
        String isFirstLaunch = SPUtils.getValue("isFirst");
        int version = SPUtils.getIntValue("version");








        // todo 第一步判断是否为第一次启动
        if (TextUtils.isEmpty(isFirstLaunch) || BuildConfig.VERSION_CODE  > version) {

            getWindow().getDecorView().post(new Runnable() {
                @Override
                public void run() {
                   showPopWindow();
                }
            });

        } else {

           showSplashPage();

        }


    }



    private void showPopWindow(){
        SplashPopWindow splashPopWindow = new SplashPopWindow(this);

        splashPopWindow.show( getWindow().getDecorView(), new SplashPopWindow.OnPopButtonClickListener() {
            @Override
            public void onClick(int position) {

                Intent intent = new Intent(SplashActivity.this, SplashProtocolPolicyActivity.class);
                intent.putExtra("from", position);
                startActivity(intent);
            }

            @Override
            public void onStop() {
                finish();
            }

            @Override
            public void onAgree() {
                showGuidePage();
            }
        });
    }

    private void showSplashPage(){



      //  setContentView(imageView);
        new Thread() {
            @Override
            public void run() {
                User user = UserManager.loginLocal(User.class);
                if (user != null && user.getToken() != null && !TextUtils.isEmpty(user.getToken().getValue())) {
                    mPresenter.getUserInfoByToken(user.getToken().getValue());
                }

            }
        }.start();

        handler.postDelayed(mJumpTask, 3000);
        showAdView();

        // 倒计时5秒，然后去获取用户

    }

    private Runnable mJumpTask = new Runnable() {
        @Override
        public void run() {
            if (!isFinishing()) {
                startMainActivity();
            }
        }
    };

    private void showAdView(){

        Ad ad = SplashAdManager.INSTANCE.getLocalAd();
        if(ad == null){
            Logger.d("%s ad is null", TAG);
            return;
        }


        setContentView(R.layout.layout_splash_pic_ad);


        View decorView = getWindow().getDecorView();

        int option = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(option);


        if (Build.VERSION.SDK_INT >= 28) {
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(params);
        }


      /*  if(SystemFacade.hasP()){
            WindowManager.LayoutParams lp =getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);

        }
*/


        CountDownView countDownView = findViewById(R.id.splash_pic_ad_tv_jump);

        countDownView.setFinishListener(new CountDownView.OnCountDownFinishListener() {
            @Override
            public void onFinish() {
                if (!isFinishing()) {
                    startMainActivity();
                }
            }
        });

        countDownView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFinishing()) {
                    startMainActivity();
                }
            }
        });
        countDownView.start();


        if(ad.getLayout() == 1){ // 图片广告
            ImageView mask = findViewById(R.id.splash_pic_ad_video_mask);
            mask.setVisibility(View.GONE);
            ImageView imageView = findViewById(R.id.splash_pic_ad_iv_pic);
            GlideApp.with(this).load(ad.getFilePath()).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


            getWindow().getDecorView().removeCallbacks(mJumpTask);
        }else{
            SplashVidio videoPlayer = findViewById(R.id.splash_pic_ad_video);
            videoPlayer.setUp( Uri.fromFile(new File(ad.getFilePath())).toString(), true, "");
            videoPlayer.startPlayLogic();
            GSYVideoManager.instance().setNeedMute(true);
            //Bitmap bitmap = getVideoThumb(ad.getFilePath());
            ImageView mask = findViewById(R.id.splash_pic_ad_video_mask);
            //mask.setImageBitmap(bitmap);
           // mask.setScaleType(ImageView.ScaleType.FIT_XY);
            handler.postDelayed(() ->{
                mask.setVisibility(View.GONE);
            }, 800);

        }

        handler.removeCallbacks(mJumpTask);
        handler.postDelayed(mJumpTask, 4800);

    }

    public  static Bitmap getVideoThumb(String path) {

        MediaMetadataRetriever media = new MediaMetadataRetriever();

        media.setDataSource(path);

        return  media.getFrameAtTime();

    }


    private void showGuidePage(){

        setContentView(R.layout.activity_splash);
        mViewPager = findViewById(R.id.splash_guild_page);

        mGuideImages = new int[]{R.mipmap.splash_guild1, R.mipmap.splash_guild2, R.mipmap.splash_guild3};

        mViewPager.setAdapter(new SplashGuidePageAdapter());
        SPUtils.saveValueToDefaultSpByApply("isFirst", "true");
        SPUtils.saveIntValueToDefaultSpByApply("version", BuildConfig.VERSION_CODE);


    }

    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }

    @Override
    public void onUserInfoSuccess(User user) {

        //startMainActivity();
    }

    @Override
    public void onUserInfoFail(String msg) {
        //startMainActivity();
    }

    @Override
    public LoginContract.ILoginGetUserInfoPresenter createPresenter() {
        return new LoginGetUserPresenter();
    }

    private class SplashGuidePageAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return mGuideImages.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }


        @SuppressLint("ResourceType")
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            /*if (position != mGuideImages.length - 1) {*/
                /*ImageView imageView = new ImageView(container.getContext());

                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(layoutParams);

                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setBackgroundResource(mGuideImages[position]);
                container.addView(imageView);
                return imageView;*/



                ConstraintLayout constraintLayout = new ConstraintLayout(container.getContext());

                constraintLayout.setBackgroundResource(mGuideImages[position]);
                constraintLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


                ConstraintSet constraintSet = new ConstraintSet();

                constraintSet.clone(constraintLayout);



                TextView tvJump = new TextView(container.getContext());

                tvJump.setOnClickListener(new SplashOnClickListener());
                tvJump.setId(100);

                tvJump.setText(R.string.text_jump);

                tvJump.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

                tvJump.setTextColor(Color.parseColor("#9B9B9B"));

                constraintSet.connect(tvJump.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, SystemFacade.dp2px(container.getContext(), 30));
                constraintSet.connect(tvJump.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, SystemFacade.dp2px(container.getContext(), 20));

                constraintSet.constrainWidth(tvJump.getId(), ConstraintSet.WRAP_CONTENT);
                constraintSet.constrainHeight(tvJump.getId(), ConstraintSet.WRAP_CONTENT);

                constraintLayout.addView(tvJump);


                if(position == mGuideImages.length -1){
                    Button btnStart = new Button(container.getContext());

                    btnStart.setId(101);
                    btnStart.setBackgroundResource(R.drawable.login_button_rounder_bg_selector);
                    btnStart.setText(R.string.text_immediate_experience);
                    btnStart.setTextColor(Color.parseColor("#ffffff"));

                    constraintSet.constrainWidth(btnStart.getId(), SystemFacade.dp2px(container.getContext(), 128));
                    constraintSet.constrainHeight(btnStart.getId(), SystemFacade.dp2px(container.getContext(), 44));

                    constraintSet.connect(btnStart.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
                    constraintSet.connect(btnStart.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);

                    float height  =  SystemFacade.getScreenHeight(SplashActivity.this);
                    float width  =  SystemFacade.getScreenWidth(SplashActivity.this);

                    float dp = SystemFacade.getDensity(SplashActivity.this);



                    float r;
                    int bottom = 0;
                    if(dp == 4){
                        r= 1;
                        bottom = 74;
                    }
                    else if(height >= 1700 && dp == 3){
                        r = height / 1920;
                        bottom = 74;
                    }else if(height >= 1280 && dp == 2){
                        r = height /1280;
                        bottom = 65;
                    }else{
                        bottom = 65;
                        r = 1;
                    }

                    constraintSet.connect(btnStart.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, SystemFacade.dip2px(container.getContext(), bottom * r));
                    btnStart.setOnClickListener(new SplashOnClickListener());

                    constraintLayout.addView(btnStart);
                }



                constraintSet.applyTo(constraintLayout);

                container.addView(constraintLayout);



                return constraintLayout;

            //}

        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }


    private class SplashOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            startMainActivity();

        }
    }


    private void startMainActivity() {
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        finish();
    }
}
