package com.seetaoism.data.repositories;

import android.content.Context;

import com.mr.k.mvp.UserManager;
import com.mr.k.mvp.base.BaseRepository;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.data.entity.HttpResult;
import com.seetaoism.data.entity.User;
import com.seetaoism.data.okhttp.JDDataService;
import com.seetaoism.home.set.SetContract;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.io.File;
import java.util.HashMap;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class SetRepository extends BaseRepository implements SetContract.ISetModel {
    //退出登录
    @Override
    public void getOutLogin(LifecycleProvider provider, HashMap<String, String> map, IBaseCallBack<String> callBack) {
        observer(provider, JDDataService.service().getlogout(map), new Function<HttpResult<String>, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(HttpResult<String> newsColumnDataHttpResult) throws Exception {
                if (newsColumnDataHttpResult.code == 1 ) {
                    UserManager.loginOut();
                    return Observable.just(newsColumnDataHttpResult.data);
                }
                return Observable.error(new ResultException(ResultException.SERVER_ERROR));
            }
        }, callBack);
    }

    @Override
    public void getPushid(LifecycleProvider provider, HashMap<String, String> map, IBaseCallBack<String> callBack) {
        observer(provider, JDDataService.service().setpushid(map), new Function<HttpResult<String>, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(HttpResult<String> newsColumnDataHttpResult) throws Exception {
                if (newsColumnDataHttpResult.code == 1 ) {
                    return Observable.just(newsColumnDataHttpResult.data);
                }
                return Observable.error(new ResultException(ResultException.SERVER_ERROR));
            }
        }, callBack);
    }


}
