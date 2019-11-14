package com.seetaoism.home.perfect;

import com.mr.k.mvp.base.BasePresenter;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.data.entity.User;
import com.seetaoism.data.repositories.PerfectRepository;

import java.util.HashMap;

import okhttp3.MultipartBody;

public class PerfectPresenter extends BasePresenter<PerfectContract.IPerfectView> implements PerfectContract.IPerfectPresenter {

    private PerfectContract.IPerfectModel mMode;

    public PerfectPresenter() {
        this.mMode = new PerfectRepository();
    }

    @Override
    public void getPerfect(MultipartBody.Part file) {


        mMode.getSearch(getLifecycleProvider(), file, new IBaseCallBack<User>() {
            @Override
            public void onSuccess(User data) {
                if (mView != null) {
                    mView.onPerfectSuccess(data, null);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if (mView != null) {
                    mView.onPerfectSuccess(null, e.getMessage());
                }
            }
        });

    }

    @Override
    public void getUpdateNameP(String name) {
        HashMap<String, String> map = new HashMap<>();
        map.put("nickname", name);

        mMode.getUpdateName(getLifecycleProvider(), map, new IBaseCallBack<User>() {
            @Override
            public void onSuccess(User data) {
                if (mView != null) {
                    mView.getUpdateNameSuccess(data, null);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if (mView != null) {
                    mView.getUpdateNameSuccess(null, e.getMessage());
                }
            }
        });
    }

    @Override
    public void getUser() {
        HashMap<String, String> map = new HashMap<>();
        mMode.getUser(getLifecycleProvider(), map, new IBaseCallBack<User>() {
            @Override
            public void onSuccess(User data) {
                mView.onUserSuccess(data);
            }

            @Override
            public void onFail(ResultException e) {
                //mView.onUserSuccess(e.getMessage());
            }
        });
    }


}
