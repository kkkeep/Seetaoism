package com.mr.k.mvp.base;

import android.app.Activity;

/*
 * created by taofu on 2019-08-26
 **/
public interface IBaseView<T extends IBasePresenter> {


    T createPresenter();


    void closeLoading();

    Activity getActivityObj();
}
