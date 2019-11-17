package com.mr.k.mvp.base;

import android.app.Activity;

import androidx.annotation.StringRes;

import com.mr.k.mvp.utils.Logger;
import com.trello.rxlifecycle2.LifecycleProvider;

import io.reactivex.disposables.Disposable;

/*
 * created by Cherry on 2019-08-26
 **/
public  class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {

    private Disposable mRecentRequest;

    protected V mView;

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        mRecentRequest = null;
    }


    protected LifecycleProvider getLifecycleProvider(){

        if(mView != null){
            return (LifecycleProvider) mView;
        }
        return null;
    }

    protected String getString(@StringRes int id){

        if(mView != null){
            Activity activity = mView.getActivityObj();
            if(activity != null){
                return activity.getString(id);
            }
        }

        return "";
    }

    public void cacheRequest(Disposable disposable){
        Logger.d("%s +++++++  ", getClass().getSimpleName());
        mRecentRequest = disposable;
    }

    public void cancelRequest(){
        if(mRecentRequest != null){
            mRecentRequest.dispose();
            Logger.d("%s ------  ", getClass().getSimpleName());
        }
    }


}
