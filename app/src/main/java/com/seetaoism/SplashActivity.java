package com.seetaoism;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.mr.k.mvp.UserManager;
import com.mr.k.mvp.base.MvpBaseActivity;
import com.mr.k.mvp.utils.DataCacheUtils;
import com.mr.k.mvp.utils.Logger;
import com.mr.k.mvp.utils.SPUtils;
import com.mr.k.mvp.utils.SystemFacade;
import com.seetaoism.data.entity.User;
import com.seetaoism.home.HomeActivity;
import com.seetaoism.user.login.LoginActivity;
import com.seetaoism.user.login.LoginContract;
import com.seetaoism.user.login.LoginGetUserPresenter;

import java.util.ArrayList;


/*
 * created by Cherry on 2019-09-02
 **/
public class SplashActivity extends MvpBaseActivity<LoginContract.ILoginGetUserInfoPresenter> implements LoginContract.ILoginGetUserInfoView {

    private static final String TAG = "SplashActivity";

    private ViewPager mViewPager;

    private int[] mGuideImages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String isFirstLaunch = SPUtils.getValue("isFirst");
        // todo 第一步判断是否为第一次启动
        if (TextUtils.isEmpty(isFirstLaunch)) {
            SPUtils.saveValueToDefaultSpByApply("isFirst", "true");
            setContentView(R.layout.activity_splash);
            mViewPager = findViewById(R.id.splash_guild_page);

            mGuideImages = new int[]{R.drawable.splash_guild1, R.drawable.splash_guide2, R.drawable.splash_guide3};

            mViewPager.setAdapter(new SplashGuidePageAdapter());

        } else {

            new Thread() {
                @Override
                public void run() {
                    User user = UserManager.loginLocal(User.class);
                    if (user != null && user.getToken() != null && !TextUtils.isEmpty(user.getToken().getValue())) {
                        mPresenter.getUserInfoByToken(user.getToken().getValue());
                    }

                }
            }.start();

            // 倒计时5秒，然后去获取用户信息
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!isFinishing()) {
                        startMainActivity();
                    }
                }
            }, 3000);


        }


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

            if (position != mGuideImages.length - 1) {
                ImageView imageView = new ImageView(container.getContext());

                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(layoutParams);

                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setBackgroundResource(mGuideImages[position]);
                container.addView(imageView);
                return imageView;

            } else {

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


                Button btnStart = new Button(container.getContext());

                btnStart.setId(101);
                btnStart.setBackgroundResource(R.drawable.login_button_rounder_bg_selector);
                btnStart.setText(R.string.text_immediate_experience);
                btnStart.setTextColor(Color.parseColor("#ffffff"));

                constraintSet.constrainWidth(btnStart.getId(), SystemFacade.dp2px(container.getContext(), 128));
                constraintSet.constrainHeight(btnStart.getId(), SystemFacade.dp2px(container.getContext(), 44));

                constraintSet.connect(btnStart.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
                constraintSet.connect(btnStart.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
                constraintSet.connect(btnStart.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, SystemFacade.dip2px(container.getContext(), 65));


                constraintLayout.addView(btnStart);


                constraintSet.applyTo(constraintLayout);

                container.addView(constraintLayout);

                btnStart.setOnClickListener(new SplashOnClickListener());

                return constraintLayout;

            }

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

    private void test(){

        HttpResult httpResult = new HttpResult();

        httpResult.code = 1;
        httpResult.msg = "";

        ArrayList<HttpResult.Goods> arrayList = new ArrayList<>();


        HttpResult.Goods good1 = new HttpResult.Goods();

        good1.id = 1;
        good1.name = "茄子";
        good1.buySpeciName = "袋（3斤）";

        good1.buySpeciTotalPrice = 9.30f;
        good1.buySpeciUnitPrice = 3.1f;
        good1.itemUnitName = "斤";

        good1.image_url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574255264775&di=9422503684a0a71dd94cefff61acc4c2&imgtype=0&src=http%3A%2F%2Fd.51240.com%2Fshipu%2F4%2F20090318155%2F2009031815583910361.jpg";

        arrayList.add(good1);

        // -----
        good1 = new HttpResult.Goods();
        good1.id = 2;
        good1.name = "土豆";
        good1.buySpeciName = "袋（2斤）";

        good1.buySpeciTotalPrice = 2.60f;
        good1.buySpeciUnitPrice = 1.30f;
        good1.itemUnitName = "斤";

        good1.image_url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574255398638&di=6a9a92c30d8e81f22ed894a3670bc3ca&imgtype=0&src=http%3A%2F%2Fdpic.tiankong.com%2Fxz%2Ftk%2FQJ6980951486.jpg%3Fx-oss-process%3Dstyle%2Fshow";

        arrayList.add(good1);

        // -----
        good1 = new HttpResult.Goods();
        good1.id = 3;
        good1.name = "萝卜";
        good1.buySpeciName = "袋（4斤）";

        good1.buySpeciTotalPrice = 5.60f;
        good1.buySpeciUnitPrice = 1.4f;
        good1.itemUnitName = "斤";

        good1.image_url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574255514810&di=4e0e9c1a79ea0df3b32a7f04379861a4&imgtype=0&src=http%3A%2F%2Fupload.taihainet.com%2F2016%2F1011%2F1476186770328.jpeg";

        arrayList.add(good1);

        // ----
        good1 = new HttpResult.Goods();
        good1.id = 4;
        good1.name = "白菜";
        good1.buySpeciName = "袋（5斤）";

        good1.buySpeciTotalPrice = 5.50f;
        good1.buySpeciUnitPrice = 1.1f;
        good1.itemUnitName = "斤";

        good1.image_url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574255573984&di=f92857f1f0a618bc36fcaab380469cec&imgtype=0&src=http%3A%2F%2Fbpic.588ku.com%2Felement_origin_min_pic%2F08%2F04%2F20%2F165707b46952dcf.jpg";

        arrayList.add(good1);

// ----
        good1 = new HttpResult.Goods();
        good1.id = 5;
        good1.name = "香菜";
        good1.buySpeciName = "把（3斤）";

        good1.buySpeciTotalPrice = 15.30f;
        good1.buySpeciUnitPrice = 5.1f;
        good1.itemUnitName = "斤";

        good1.image_url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574850353&di=d627ac7f2e15922fe7cd2b19d916422f&imgtype=jpg&er=1&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn09%2F284%2Fw640h444%2F20180826%2F7889-hifuvpf9029392.jpg";

        arrayList.add(good1);


        // --
        good1 = new HttpResult.Goods();
        good1.id = 6;
        good1.name = "茄子";
        good1.buySpeciName = "袋（3斤）";

        good1.buySpeciTotalPrice = 9.30f;
        good1.buySpeciUnitPrice = 3.1f;
        good1.itemUnitName = "斤";

        good1.image_url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574255264775&di=9422503684a0a71dd94cefff61acc4c2&imgtype=0&src=http%3A%2F%2Fd.51240.com%2Fshipu%2F4%2F20090318155%2F2009031815583910361.jpg";

        arrayList.add(good1);

        good1 = new HttpResult.Goods();
        good1.id = 7;
        good1.name = "胡萝卜";
        good1.buySpeciName = "袋（3斤）";

        good1.buySpeciTotalPrice = 7.20f;
        good1.buySpeciUnitPrice = 2.4f;
        good1.itemUnitName = "斤";

        good1.image_url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574255695995&di=5598926eef5772b0205e6387c74f31f2&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20140305%2FImg396082544.jpg";

        arrayList.add(good1);


        // ----
        good1 = new HttpResult.Goods();
        good1.id = 8;
        good1.name = "生姜";
        good1.buySpeciName = "袋（3斤）";

        good1.buySpeciTotalPrice = 27.00f;
        good1.buySpeciUnitPrice = 9.0f;
        good1.itemUnitName = "斤";

        good1.image_url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574850585&di=5862452fdc597f48b4de8dee1deab3f6&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.wtq.gov.cn%2FUploadFiles%2F2012-06%2Fxy%2F2012062016583132675.jpg";

        arrayList.add(good1);

        good1 = new HttpResult.Goods();
        good1.id = 9;
        good1.name = "生菜";
        good1.buySpeciName = "袋（3斤）";

        good1.buySpeciTotalPrice = 18.30f;
        good1.buySpeciUnitPrice = 6.1f;
        good1.itemUnitName = "斤";

        good1.image_url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574255807006&di=ad9d7982cdbe65599496d22d7fa90d72&imgtype=0&src=http%3A%2F%2Fwww.xn--b6qt1d706g.com%2Fupfile%2Fimg%2F161124%2Fproduct_bigimg_5836a8fd276ef.jpg";

        arrayList.add(good1);


        //---
        good1 = new HttpResult.Goods();
        good1.id = 10;
        good1.name = "豆角";
        good1.buySpeciName = "袋（2斤）";

        good1.buySpeciTotalPrice = 14.20f;
        good1.buySpeciUnitPrice = 7.1f;
        good1.itemUnitName = "斤";

        good1.image_url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574255756920&di=7d273f46f370cab80f7a844dd4a48fc3&imgtype=0&src=http%3A%2F%2Fwww.nzjsw.com%2Fwp-content%2Fuploads%2F2018%2F07%2F21%2F20180721184956.jpg";

        arrayList.add(good1);



        httpResult.datas = arrayList;

        Gson gson = new Gson();
      Log.d("Test", gson.toJson(httpResult));



    }
}
