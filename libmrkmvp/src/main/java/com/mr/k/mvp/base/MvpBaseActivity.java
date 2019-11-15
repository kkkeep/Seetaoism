package com.mr.k.mvp.base;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.mr.k.mvp.kotlin.base.BaseActivity;

/*
 * created by Cherry on 2019-08-26
 **/
@SuppressWarnings("ALL")
public abstract class MvpBaseActivity<P extends IBasePresenter> extends BaseActivity implements IBaseView<P> {

    protected P mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = createPresenter();

        if(mPresenter != null){
            mPresenter.attachView(this);
        }


    }


    @Override
    public void closeLoading() {
        super.closeLoading();
    }

    @Override
    public Activity getActivityObj() {
        return this;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mPresenter != null){
            mPresenter.detachView();
        }
    }
}
