package com.seetaoism.data.repositories;

import com.mr.k.mvp.UserManager;
import com.mr.k.mvp.base.BaseRepository;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.data.entity.HttpResult;
import com.seetaoism.data.entity.SocialBindData;
import com.seetaoism.data.entity.User;
import com.seetaoism.data.entity.VideoData;
import com.seetaoism.data.okhttp.JDDataService;
import com.seetaoism.home.perfect.PerfectContract;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.MultipartBody;

public class PerfectRepository extends BaseRepository implements PerfectContract.IPerfectModel {
    //头像修改
    @Override
    public void getSearch(LifecycleProvider provider, MultipartBody.Part part, IBaseCallBack<User> callBack) {
        observer(provider, JDDataService.service().uploadHead(part), new Function<HttpResult<User>, ObservableSource<User>>() {
            @Override
            public ObservableSource<User> apply(HttpResult<User> newsColumnDataHttpResult) throws Exception {
                if (newsColumnDataHttpResult.code == 1 && newsColumnDataHttpResult.data != null) {
                    User user = newsColumnDataHttpResult.data;
                    User localUser = (User) UserManager.getUser();

                    user.setToken(localUser.getToken());
                    UserManager.login(user);
                    return Observable.just(newsColumnDataHttpResult.data);
                }
                return Observable.error(new ResultException(ResultException.SERVER_ERROR));
            }
        }, callBack);
    }
   //昵称修改
    @Override
    public void getUpdateName(LifecycleProvider provider, HashMap<String, String> hashMap, IBaseCallBack<User> callBack) {
        observer(provider, JDDataService.service().updatename(hashMap), new Function<HttpResult<User>, ObservableSource<User>>() {
            @Override
            public ObservableSource<User> apply(HttpResult<User> newsColumnDataHttpResult) throws Exception {
                if (newsColumnDataHttpResult.code == 1 && newsColumnDataHttpResult.data != null) {
                    User user = newsColumnDataHttpResult.data;
                    User localUser = (User) UserManager.getUser();
                    user.setToken(localUser.getToken());
                    UserManager.login(user);
                    return Observable.just(newsColumnDataHttpResult.data);
                }
                return Observable.error(new ResultException(ResultException.SERVER_ERROR));
            }
        }, callBack);
    }


    //获取用户信息
    @Override
    public void getUser(LifecycleProvider provider, HashMap<String, String> hashMap, IBaseCallBack<User> callBack) {
        observer(provider, JDDataService.service().getUserinfoByToken(hashMap), new Function<HttpResult<User>, ObservableSource<User>>() {
            @Override
            public ObservableSource<User> apply(HttpResult<User> newsColumnDataHttpResult) throws Exception {
                if (newsColumnDataHttpResult.code == 1 && newsColumnDataHttpResult.data != null) {
                    return Observable.just(newsColumnDataHttpResult.data);
                }
                return Observable.error(new ResultException(ResultException.SERVER_ERROR));
            }
        }, callBack);
    }
    //社交绑定
    @Override
    public void getSocialbind(LifecycleProvider provider, HashMap<String, String> hashMap, IBaseCallBack<SocialBindData> callBack) {
        observer(provider, JDDataService.service().socialbind(hashMap), newsColumnDataHttpResult -> {
            if (newsColumnDataHttpResult.code == 1 && newsColumnDataHttpResult.data != null) {
                return Observable.just(newsColumnDataHttpResult.data);
            }
            return Observable.error(new ResultException(ResultException.SERVER_ERROR));
        }, callBack);
    }
    //社交解除绑定
    @Override
    public void getSocialunbind(LifecycleProvider provider, HashMap<String, String> hashMap, IBaseCallBack<SocialBindData> callBack) {
        observer(provider, JDDataService.service().socialunbind(hashMap), newsColumnDataHttpResult -> {
            if (newsColumnDataHttpResult.code == 1 && newsColumnDataHttpResult.data != null) {
                return Observable.just(newsColumnDataHttpResult.data);
            }
            return Observable.error(new ResultException(ResultException.SERVER_ERROR));
        }, callBack);
    }


}
