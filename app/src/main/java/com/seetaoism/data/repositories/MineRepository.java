package com.seetaoism.data.repositories;

import com.mr.k.mvp.UserManager;
import com.mr.k.mvp.base.BaseRepository;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.data.entity.HttpResult;
import com.seetaoism.data.entity.IntergrelData;
import com.seetaoism.data.entity.User;
import com.seetaoism.data.okhttp.JDDataService;
import com.seetaoism.home.mine.MineContract;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class MineRepository extends BaseRepository implements MineContract.MineModel {
    @Override
    public void getMine(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<String> callBack) {
        observer(provider, JDDataService.service().checkinaddintegral(params), new Function<HttpResult<String>, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(HttpResult<String> newsColumnDataHttpResult) throws Exception {
                if (newsColumnDataHttpResult.code == 1 && newsColumnDataHttpResult.data != null) {
                    return Observable.just(newsColumnDataHttpResult.data);
                }
                return Observable.error(new ResultException(ResultException.SERVER_ERROR));
            }
        }, callBack);
    }

    @Override
    public void getMineUser(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<User> callBack) {
        observer(provider, JDDataService.service().getUserinfoByToken(params), new Function<HttpResult<User>, ObservableSource<User>>() {
            @Override
            public ObservableSource<User> apply(HttpResult<User> newsColumnDataHttpResult) throws Exception {
                if (newsColumnDataHttpResult.code == 1 && newsColumnDataHttpResult.data != null) {
                    User user = newsColumnDataHttpResult.data;

                    User local = (User) UserManager.getUser();
                    if(user.getToken() == null){
                        user.setToken(local.getToken());
                    }

                    UserManager.login(newsColumnDataHttpResult.data);
                    return Observable.just(newsColumnDataHttpResult.data);
                }
                return Observable.error(new ResultException(ResultException.SERVER_ERROR));
            }
        }, callBack);
    }
}

