package com.seetaoism.user.login;

import android.util.Log;

import androidx.annotation.NonNull;

import com.mr.k.mvp.base.BasePresenter;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.base.ICancelBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.AppConstant;
import com.seetaoism.data.entity.User;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;


public class LoginVerifyPresenter extends BasePresenter<LoginContract.ILoginCodeView> implements LoginContract.ILoginCodePresenter {


    private LoginContract.ILoginModel mModel;


    public LoginVerifyPresenter(LoginContract.ILoginModel mModel) {
        this.mModel = mModel;
    }

    @Override
    public void getSmsCode(String phoneNumber) {
        Map<String,String> params = new HashMap<>();

        params.put(AppConstant.RequestParamsKey.MOBILE, phoneNumber);
        params.put(AppConstant.RequestParamsKey.SMS_CODE_TYPE, LoginActivity.SMS_CODE_TYPE_LOGIN);

        mModel.getSmsCode(getLifecycleProvider(), params, new IBaseCallBack<String>() {

            @Override
            public void onSuccess(String data) {
                Log.d("onSuccess" ,"onSuccess: "+data);
                mView.onSmsCodeResult(data, true);
            }

            @Override
            public void onFail(ResultException exception) {
                mView.onSmsCodeResult(exception.getMessage(), false);
            }
        });
    }

    @Override
    public void loginByCode(String phoneNumber,String verify) {
        Map<String,String> params = new HashMap<>();
        params.put(AppConstant.RequestParamsKey.MOBILE, phoneNumber);
        params.put(AppConstant.RequestParamsKey.SMS_CODE, verify);
        mModel.loginByCode(getLifecycleProvider(), params, new ICancelBaseCallBack<User>() {
            @Override
            public void onStart(Disposable disposable) {
                cacheRequest(disposable);
            }
            @Override
            public void onSuccess(User data) {
                Log.d("onSuccess", "onSuccess: "+data.toString());
                if(mView != null){
                    mView.onLoginSuccess(data);
                }
            }
            @Override
            public void onFail(ResultException exception) {
                if (mView != null){
                    mView.onLoginFail(exception.getMessage(),false);
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
