package com.seetaoism.base;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;


import com.mr.k.mvp.base.IBasePresenter;
import com.mr.k.mvp.base.IBaseView;
import com.mr.k.mvp.base.MvpBaseActivity;
import com.mr.k.mvp.statusbar.StatusBarUtils;


public abstract class JDMvpBaseActivity<P extends IBasePresenter> extends MvpBaseActivity<P> {


    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        // 4.4 ~ 5.0 的手机必须写在setContentView 之后
        StatusBarUtils.setStatusBarLightMode(this,Color.WHITE);
        
        doOnCreate(savedInstanceState);
    }

   protected abstract void doOnCreate(@Nullable Bundle savedInstanceState);

   protected abstract int getLayoutId();
}
