package com.mr.k.mvp.base;

import io.reactivex.disposables.Disposable;

/*
 * created by Cherry on 2019-08-26
 **/
public interface IBasePresenter<T extends IBaseView> {

    void attachView(T view);

    void detachView();


      void cacheRequest(Disposable disposable);

     void cancelRequest();

}
