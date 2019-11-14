package com.mr.k.mvp.base;

import androidx.annotation.NonNull;

import io.reactivex.disposables.Disposable;

public interface ICancelBaseCallBack<T> extends IBaseCallBack<T> {

    void onStart(@NonNull Disposable disposable);
}
