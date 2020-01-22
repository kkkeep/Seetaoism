package com.seetaoism;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.mr.k.mvp.base.BaseActivity;
import com.seetaoism.data.entity.User;
import com.seetaoism.user.login.LoginContract;
import com.seetaoism.user.login.PasswordLoginPresenter;


public class TestViewActivity extends BaseActivity implements LoginContract.ILoginPsView {


    private LoginContract.ILoginPsPresenter mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.test);
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(option);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        if (Build.VERSION.SDK_INT >= 28) {
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(params);
        }
        mPresenter = createPresenter();


        mPresenter.attachView(this);

    }


    @Override
    public LoginContract.ILoginPsPresenter createPresenter() {

        return new PasswordLoginPresenter(null);
    }

    @Override
    public void closeLoading() {

    }

    @Override
    public Activity getActivityObj() {
        return null;
    }


    @Override
    public void onLoginSuccess(User user) {

    }

    @Override
    public void onLoginFail(String msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mPresenter.detachView();
    }

    @Override
    public void onSocialLoginResult(User user, String msg) {

    }
}
