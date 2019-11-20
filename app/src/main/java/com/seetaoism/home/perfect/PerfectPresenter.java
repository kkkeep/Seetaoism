package com.seetaoism.home.perfect;

import androidx.annotation.NonNull;

import com.mr.k.mvp.base.BasePresenter;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.data.entity.SocialBindData;
import com.seetaoism.data.entity.User;
import com.seetaoism.data.entity.VideoData;
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
            }
        });
    }

    @Override
    public void getSocialbind(String type, String openid, String nickname, String head_url, String unionid) {

        HashMap<String, String> map = new HashMap<>();
        map.put("type",type);
        map.put("openid",openid);
        map.put("nickname",nickname);
        map.put("head_url",head_url);
        map.put("unionid",unionid);


        mMode.getSocialbind(getLifecycleProvider(), map, new IBaseCallBack<SocialBindData>() {
            @Override
            public void onSuccess(@NonNull SocialBindData data) {
                if (mView!=null){
                    mView.onSocialbindSuccess(data);
                }
            }

            @Override
            public void onFail(@NonNull ResultException e) {
                if (mView!=null){
                    mView.onSocialbindFail(e.getMessage());
                }
            }
        });

    }

    @Override
    public void getSocialunbind(String type, String openid, String unionid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("type",type);
        map.put("openid",openid);
        map.put("unionid",unionid);

        mMode.getSocialunbind(getLifecycleProvider(), map, new IBaseCallBack<SocialBindData>() {
            @Override
            public void onSuccess(@NonNull SocialBindData data) {
                if (mView!=null){
                    mView.onSocialunbindSuccess(data);
                }
            }

            @Override
            public void onFail(@NonNull ResultException e) {
                if (mView!=null){
                    mView.onSocialunbindFail(e.getMessage());
                }
            }
        });
    }


}
