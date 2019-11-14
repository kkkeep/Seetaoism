package com.seetaoism.data.repositories;

import android.content.Context;

import com.mr.k.mvp.UserManager;
import com.mr.k.mvp.base.BaseRepository;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.mr.k.mvp.utils.DataCacheUtils;
import com.seetaoism.data.entity.HttpResult;
import com.seetaoism.data.entity.User;
import com.seetaoism.data.okhttp.JDDataService;
import com.seetaoism.user.login.LoginContract;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;



public class UserRepository extends BaseRepository implements LoginContract.ILoginModel {


    private volatile static UserRepository mInstance;



    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if (mInstance == null) {
            synchronized (UserRepository.class) {
                if (mInstance == null) {
                    mInstance = new UserRepository();
                }
            }
        }
        return mInstance;
    }


    @Override
    public void loginByPs(LifecycleProvider provider, Map<String, String> params, final IBaseCallBack<User> callBack) {
        observer(provider, JDDataService.service().loginByPs(params), userHttpResult -> {


                if (userHttpResult.code == 1 && userHttpResult.data != null) { // 表示请求成功
                    // 保存User 和 token

                    saveUser(userHttpResult.data);


                    return Observable.just(userHttpResult.data);
                }

            return Observable.error(new ResultException(userHttpResult.message));
        }, callBack);


    }

    @Override
    public void loginByCode(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<User> callBack) {

        observer(provider, JDDataService.service().loginByCode(params), userHttpResult -> {
            if (userHttpResult.code == 1 && userHttpResult.data != null) { // 表示请求成功

                saveUser(userHttpResult.data);

                return Observable.just(userHttpResult.data);
            }
            return Observable.error(new ResultException(ResultException.SERVER_ERROR));
        }, callBack);

    }

    @Override
    public void getSmsCode(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<String> callBack) {
        observer(provider, JDDataService.service().getSmsCode(params), stringHttpResult -> {
            if (stringHttpResult.code == 1) {
                return Observable.just(stringHttpResult.data);
            }
            return Observable.error(new ResultException(ResultException.SERVER_ERROR, stringHttpResult.message));
        }, callBack);
    }

    @Override
    public void verifySmsCode(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<String> callBack) {
        observer(provider, JDDataService.service().verifySmsCode(params), stringHttpResult -> {
            if (stringHttpResult.code == 1) {
                return Observable.just(stringHttpResult.data);
            }
            return Observable.error(new ResultException(stringHttpResult.message));
        }, callBack);
    }

    @Override
    public void register(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<User> callBack) {


        observer(provider, JDDataService.service().register(params), userHttpResult -> {

            if (userHttpResult.code == 1 && userHttpResult.data != null) { // 表示请求成功

                // 保存User 和 token
                saveUser(userHttpResult.data);
                return Observable.just(userHttpResult.data);
            }
            return Observable.error(new ResultException(ResultException.SERVER_ERROR));
        }, callBack);
    }

    @Override
    public void getUserByToken(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<User> callBack) {
        observer(provider, JDDataService.service().getUserinfoByToken(params), userHttpResult -> {

            if (userHttpResult.code == 1 && userHttpResult.data != null) { // 表示请求成功


                // 通过 toke 获取的 user 里面不带用 token。所有我们需要把原来保存的用户信息里面的toke 重新这只给新的user 里面
                User user = userHttpResult.data;
                if (user.getToken() == null) {

                    User old = (User) UserManager.getUserFromSdcard(User.class);

                    if (old != null && old.getToken() != null) {
                        user.setToken(old.getToken());
                    }
                }

                // 再次保存用户信息
                saveUser(userHttpResult.data);

                return Observable.just(userHttpResult.data);
            }
            return Observable.error(new ResultException(ResultException.SERVER_ERROR));
        }, callBack);
    }

    @Override
    public void updatePsw(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<String> callBack) {
        observer(provider, JDDataService.service().updatepaw(params), stringHttpResult -> {
            if (stringHttpResult.code == 1) {
                return Observable.just(stringHttpResult.data);
            }
            return Observable.error(new ResultException(stringHttpResult.message));
        }, callBack);
    }

    @Override
    public void editpassword(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<String> callBack) {
        observer(provider, JDDataService.service().editpassword(params), stringHttpResult -> {
            if (stringHttpResult.code == 1) {
                return Observable.just(stringHttpResult.data);
            }
            return Observable.error(new ResultException(stringHttpResult.message));
        }, callBack);
    }

    public  Disposable getCacheUser(IBaseCallBack<User> callBack) {
        return Observable.create((ObservableOnSubscribe<User>) emitter -> {
            User user = (User) UserManager.getUserFromSdcard(User.class);
            if (user != null) {
                emitter.onNext(user);
            } else {
                emitter.onError(new NullPointerException(" user cache is null"));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack::onSuccess, e -> callBack.onFail(new ResultException(e)));

    }





    public static void destroy() {
        if (mInstance != null) {
            mInstance = null;
        }
    }




    // 保存用户信息到本地
    private void saveUser(User user) {
        UserManager.login(user);

    }


    // 当调用退出登录接口成功时需要调用这个方法
    public static void cleanUser(Context context){

        Disposable subscribe = Observable.create(emitter -> {
            UserManager.loginOut();
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe();
    }



}
