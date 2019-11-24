package com.mr.k.mvp.base;


import android.content.Context;

import com.mr.k.mvp.MvpManager;
import com.mr.k.mvp.exceptions.ResultException;
import com.mr.k.mvp.utils.Logger;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class BaseRepository {



    protected boolean isListNotEmpty(List list){

        return list != null && list.size() != 0;
    }

    public  <T, R> void observer(LifecycleProvider provider, Observable<T> observable, Function<T, ObservableSource<R>> flatMap, final IBaseCallBack<R> callBack) {
        if(provider == null){
            return;
        }
         observable.flatMap(flatMap).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .compose(provider instanceof RxFragment ? ((RxFragment) provider).<R>bindUntilEvent(FragmentEvent.DESTROY_VIEW) : ((RxAppCompatActivity) (provider)).<R>bindUntilEvent(ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<R>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (callBack instanceof ICancelBaseCallBack) {
                            ((ICancelBaseCallBack<R>) callBack).onStart(d);
                        }
                    }

                    @Override
                    public void onNext(R r) {
                        if (Logger.isDebug()) {
                            Logger.d("BaseRepository observer onNext %s ", r.toString());
                        }
                        callBack.onSuccess(r);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("BaseRepository observer onError %s", e.getMessage());
                        if (e instanceof ResultException) {
                            callBack.onFail((ResultException) e);
                        } else {
                            callBack.onFail(new ResultException(e));
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    protected <T, R> void observerNoMap(LifecycleProvider provider, Observable<R> observable,  final IBaseCallBack<R> callBack) {


        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .compose(provider instanceof RxFragment ? ((RxFragment) provider).<R>bindUntilEvent(FragmentEvent.DESTROY_VIEW) : ((RxAppCompatActivity) (provider)).<R>bindUntilEvent(ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<R>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (callBack instanceof ICancelBaseCallBack) {
                            ((ICancelBaseCallBack<R>) callBack).onStart(d);
                        }
                    }

                    @Override
                    public void onNext(R r) {
                        if (Logger.isDebug()) {
                            Logger.d("BaseRepository observer onNext %s ", r.toString());
                        }
                        callBack.onSuccess(r);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("BaseRepository observer onError %s", e.getMessage());
                        if (e instanceof ResultException) {
                            callBack.onFail((ResultException) e);
                        } else {
                            callBack.onFail(new ResultException(e));
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
