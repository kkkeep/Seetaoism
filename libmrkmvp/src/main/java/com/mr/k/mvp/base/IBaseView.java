package com.mr.k.mvp.base;

import android.app.Activity;

/*
 * created by Cherry on 2019-08-26
 **/
public interface IBaseView<T extends IBasePresenter> {


    T createPresenter();


    void closeLoading();

    Activity getActivityObj();
}
