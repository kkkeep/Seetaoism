package com.seetaoism.home.perfect;

import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.base.IBasePresenter;
import com.mr.k.mvp.base.IBaseView;
import com.seetaoism.data.entity.User;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.HashMap;

import okhttp3.MultipartBody;

public interface PerfectContract {

    public interface IPerfectView extends IBaseView<IPerfectPresenter> {

        void onPerfectSuccess(User s, String msg);
        void getUpdateNameSuccess(User user, String msg);
        //获取用户信息
        void onUserSuccess(User user);


    }

    public interface IPerfectPresenter extends IBasePresenter<IPerfectView> {
        void getPerfect(MultipartBody.Part file);
        void getUpdateNameP(String name);
        void getUser();
    }

    public interface IPerfectModel {
        void getSearch(LifecycleProvider provider, MultipartBody.Part part, IBaseCallBack<User> callBack);
        void getUpdateName(LifecycleProvider provider, HashMap<String, String> hashMap, IBaseCallBack<User> callBack);
        void getUser(LifecycleProvider provider, HashMap<String, String> hashMap, IBaseCallBack<User> callBack);

    }
}
