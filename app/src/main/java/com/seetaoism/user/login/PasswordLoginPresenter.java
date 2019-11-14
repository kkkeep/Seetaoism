package com.seetaoism.user.login;

import com.mr.k.mvp.base.BasePresenter;
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
}
