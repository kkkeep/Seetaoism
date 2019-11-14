package com.seetaoism.user.login;

import com.mr.k.mvp.base.BasePresenter;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.base.ICancelBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.AppConstant.RequestParamsKey;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;


public class ForgetPsdPresenter extends BasePresenter<LoginContract.IForgetPsView> implements LoginContract.IForgetPresenter {


    private LoginContract.ILoginModel mModel;


    public ForgetPsdPresenter(LoginContract.ILoginModel mModel) {
        this.mModel = mModel;
    }


    @Override
    public void ForgetCode(String phoneNumber) {
        Map<String,String> params = new HashMap<>();

        params.put(RequestParamsKey.MOBILE, phoneNumber);
        params.put(RequestParamsKey.SMS_CODE_TYPE, LoginActivity.SMS_CODE_TYPE_MODIFY_PSW);

        mModel.getSmsCode(getLifecycleProvider(), params, new IBaseCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                //是因为这里回调了两个参数  成功返回true  失败返回false
                mView.onLoginSuccess(data,true);
            }

            @Override
            public void onFail(ResultException e) {
               mView.onLoginFail(e.getMessage(),false);
            }
        });

    }

    @Override
    public void ForgetSmsCode(String phoneNumber, String code) {

        Map<String,String> params = new HashMap<>();
        params.put(RequestParamsKey.MOBILE, phoneNumber);
        params.put(RequestParamsKey.SMS_CODE_TYPE, LoginActivity.SMS_CODE_TYPE_MODIFY_PSW);
        params.put(RequestParamsKey.SMS_CODE, code);


        mModel.verifySmsCode(getLifecycleProvider(), params, new ICancelBaseCallBack<String>() {


            @Override
            public void onStart(Disposable disposable) {
                cacheRequest(disposable);
            }

            @Override
            public void onSuccess(String data) {
                mView.onLoginFail(data,true);
            }

            @Override
            public void onFail(ResultException e) {
                mView.onLoginFail(e.getMessage(),false);
            }
        });

    }
}
