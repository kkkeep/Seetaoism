package com.seetaoism.base;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.mr.k.mvp.kotlin.base.BaseActivity;
import com.mr.k.mvp.statusbar.StatusBarUtils;

/*
 * created by taofu on 2019-10-28
 **/
public abstract class JDBaseActivity extends BaseActivity {


    @Override

    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());


        StatusBarUtils.setStatusBarLightMode(this,Color.WHITE);


        doOnCreate(savedInstanceState);
    }

   protected abstract void doOnCreate(@Nullable Bundle savedInstanceState);

   protected abstract int getLayoutId();
}
