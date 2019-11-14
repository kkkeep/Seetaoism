package com.seetaoism.data.repositories;

import com.mr.k.mvp.base.BaseRepository;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.data.entity.HttpResult;
import com.seetaoism.data.entity.User;
import com.seetaoism.data.okhttp.JDDataService;
import com.seetaoism.home.phone.PhoneContract;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class PhoneRepository extends BaseRepository implements PhoneContract.IPhoneModel {
    @Override
    public void getPhoneM(LifecycleProvider provider, HashMap<String, String> map, IBaseCallBack<User> callBack) {
        observer(provider, JDDataService.service().editmobile(map), new Function<HttpResult<User>, ObservableSource<User>>() {
            @Override
            public ObservableSource<User> apply(HttpResult<User> newsColumnDataHttpResult) throws Exception {
                if (newsColumnDataHttpResult.code == 1 && newsColumnDataHttpResult.data != null) {
                    return Observable.just(newsColumnDataHttpResult.data);
                }
                return Observable.error(new ResultException(ResultException.SERVER_ERROR));
            }
        }, callBack);
    }
    @Override
    public void getPhonecodeM(LifecycleProvider provider, HashMap<String, String> map, IBaseCallBack<String> callBack) {
        observer(provider, JDDataService.service().getSmsCode(map), new Function<HttpResult<String>, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(HttpResult<String> stringHttpResult) throws Exception {
                if (stringHttpResult.code == 1) {
                    return Observable.just(stringHttpResult.data);
                }
                return Observable.error(new ResultException(ResultException.SERVER_ERROR, stringHttpResult.message));
            }
        }, callBack);
    }
}
