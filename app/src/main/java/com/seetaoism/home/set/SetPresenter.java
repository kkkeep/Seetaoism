package com.seetaoism.home.set;

import com.mr.k.mvp.base.BasePresenter;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.data.entity.User;
import com.seetaoism.data.repositories.SetRepository;

import java.util.HashMap;


public class SetPresenter extends BasePresenter<SetContract.ISetView> implements SetContract.ISetPresenter {

    private SetContract.ISetModel mMode;


    public SetPresenter() {
        this.mMode = new SetRepository();
    }

    @Override
    public void getOutLogin() {
        HashMap<String, String> map = new HashMap<>();

        mMode.getOutLogin(getLifecycleProvider(), map, new IBaseCallBack<String>() {

            @Override
            public void onSuccess(String data) {
                if (mView != null) {
                    mView.onOutLoginSuccess(data);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if (mView != null) {
                    mView.onOutLoginFail(e.getMessage());
                }
            }
        });

    }

    @Override
    public void getPushid(String register_id) {
        HashMap<String, String> map = new HashMap<>();

        map.put("register_id", register_id);

        mMode.getPushid(getLifecycleProvider(), map, new IBaseCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                mView.onPushidSuccess(data);
            }

            @Override
            public void onFail(ResultException e) {
                mView.onPushidFail(e.getMessage());
            }
        });
    }
}
