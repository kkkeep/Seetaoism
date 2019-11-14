package com.mr.k.mvp.base;

import androidx.annotation.NonNull;

import com.mr.k.mvp.exceptions.ResultException;

/*
 * created by taofu on 2019-08-26
 **/
public interface IBaseCallBack<T> {

    void onSuccess(@NonNull T data);

    void onFail(@NonNull ResultException e);

}
