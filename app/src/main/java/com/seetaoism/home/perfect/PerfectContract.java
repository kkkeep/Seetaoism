package com.seetaoism.home.perfect;

import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.base.IBasePresenter;
import com.mr.k.mvp.base.IBaseView;
import com.seetaoism.data.entity.SocialBindData;
import com.seetaoism.data.entity.User;
import com.seetaoism.data.entity.VideoData;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.HashMap;

import okhttp3.MultipartBody;

public interface PerfectContract {

    public interface IPerfectView extends IBaseView<IPerfectPresenter> {

        void onPerfectSuccess(User s, String msg);
        void getUpdateNameSuccess(User user, String msg);
        //获取用户信息
        void onUserSuccess(User user);
        //社交绑定
        void onSocialbindSuccess(SocialBindData data);
        void onSocialbindFail(String msg);


    }

    public interface IPerfectPresenter extends IBasePresenter<IPerfectView> {
        void getPerfect(MultipartBody.Part file);
        void getUpdateNameP(String name);
        void getUser();
        //社交绑定
        void getSocialbind(String type,String openid,String nickname,String head_url,String unionid);
    }

    public interface IPerfectModel {
        void getSearch(LifecycleProvider provider, MultipartBody.Part part, IBaseCallBack<User> callBack);
        void getUpdateName(LifecycleProvider provider, HashMap<String, String> hashMap, IBaseCallBack<User> callBack);
        void getUser(LifecycleProvider provider, HashMap<String, String> hashMap, IBaseCallBack<User> callBack);

        //社交绑定
        void getSocialbind(LifecycleProvider provider, HashMap<String, String> hashMap, IBaseCallBack<SocialBindData> callBack);

    }
}
