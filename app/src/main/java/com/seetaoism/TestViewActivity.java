package com.seetaoism;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.mr.k.mvp.base.BaseActivity;
import com.seetaoism.data.entity.User;
import com.seetaoism.user.login.LoginContract;
import com.seetaoism.user.login.PasswordLoginPresenter;


public class TestViewActivity extends BaseActivity implements LoginContract.ILoginPsView {


    private LoginContract.ILoginPsPresenter mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
