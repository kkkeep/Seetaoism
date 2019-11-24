package com.seetaoism.user.login;

import androidx.annotation.NonNull;

import com.mr.k.mvp.base.BasePresenter;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.base.ICancelBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.AppConstant.RequestParamsKey;
import com.seetaoism.data.entity.User;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class PasswordLoginPresenter extends BasePresenter<LoginContract.ILoginPsView> implements LoginContract.ILoginPsPresenter {


    private LoginContract.ILoginModel mModel;


    public PasswordLoginPresenter(LoginContract.ILoginModel mModel) {
        this.mModel = mModel;
    }

    @Override
    public void login(String phoneNum, String ps) {

        HashMap<String,String> params = new HashMap<>();

        params.put(RequestParamsKey.PASSWORD, ps);
        params.put(RequestParamsKey.USER_NAME, phoneNum);


        mModel.loginByPs(getLifecycleProvider(), params,  new ICancelBaseCallBack<User>() {

            @Override
            public void onStart(Disposable disposable) {
                cacheRequest(disposable);
            }

            @Override
            public void onSuccess(User data) {
                if(mView != null){
                    mView.onLoginSuccess(data);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if(mView != null){
                    mView.onLoginFail(e.getMessage());
                }
            }
        });

    }

    @Override
    public void socialLogin(String type, String openId, String head_url, String nickname, String unionid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("type",type);
        map.put("openid",openId);
        map.put("nickname",nickname);
        map.put("head_url",head_url);
        map.put("unionid",unionid);

        mModel.socialLogin(getLifecycleProvider(), map, new IBaseCallBack<User>() {
            @Override
            public void onSuccess(@NonNull User data) {
                if(mView != null){
                    mView.onSocialLoginResult(data,null);
                }
            }

            @Override
            public void onFail(@NonNull ResultException e) {
                if(mView != null){
                    mView.onSocialLoginResult(null,e.getMessage());
                }
            }
        });
    }
}
