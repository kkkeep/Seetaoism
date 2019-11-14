package com.seetaoism.home.set;

import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.base.IBasePresenter;
import com.mr.k.mvp.base.IBaseView;
import com.seetaoism.data.entity.User;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.HashMap;


public interface SetContract {


    public interface ISetView extends IBaseView<ISetPresenter> {
        void onOutLoginSuccess(String user);

        void onOutLoginFail(String msg);

        void onPushidSuccess(String user);

        void onPushidFail(String msg);


    }

    public interface ISetPresenter extends IBasePresenter<ISetView> {
        void getOutLogin();
        void getPushid(String register_id);

    }

    public interface ISetModel {
        void getOutLogin(LifecycleProvider provider, HashMap<String, String> map, IBaseCallBack<String> callBack);
        void getPushid(LifecycleProvider provider, HashMap<String, String> map, IBaseCallBack<String> callBack);
    }
}
