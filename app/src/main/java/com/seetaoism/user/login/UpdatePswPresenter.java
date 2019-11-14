package com.seetaoism.user.login;

import com.mr.k.mvp.base.BasePresenter;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.base.ICancelBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.AppConstant;
import com.seetaoism.data.repositories.UserRepository;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public class UpdatePswPresenter extends BasePresenter<LoginContract.IUpdateView> implements LoginContract.IUpdatePresenter {



    private LoginContract.ILoginModel mRepository;


    public UpdatePswPresenter() {
        mRepository = UserRepository.getInstance();
    }

    @Override
    public void IUpdatePsw(String phone, String verif, String psw) {

        Map<String, String> params = new HashMap<>();

        params.put(AppConstant.RequestParamsKey.MOBILE, phone);
        params.put(AppConstant.RequestParamsKey.SMS_CODE, verif);
        params.put(AppConstant.RequestParamsKey.PASSWORD, psw);


        mRepository.updatePsw(getLifecycleProvider(), params, new ICancelBaseCallBack<String>() {
            @Override
            public void onStart(Disposable disposable) {
                cacheRequest(disposable);
            }

            @Override
            public void onSuccess(String data) {
                mView.IUpdateSuccess(data);
            }

            @Override
            public void onFail(ResultException e) {
                mView.IUpdatetFail(e.getMessage());
            }
        });


    }

    @Override
    public void SetPsw(String phone, String verif, String psw) {
        Map<String, String> params = new HashMap<>();

        params.put(AppConstant.RequestParamsKey.MOBILE, phone);
        params.put(AppConstant.RequestParamsKey.SMS_CODE, verif);
        params.put(AppConstant.RequestParamsKey.PASSWORD, psw);


        mRepository.editpassword(getLifecycleProvider(), params, new IBaseCallBack<String>() {

            @Override
            public void onSuccess(String data) {
                /////////////////////////
                mView.IUpdateSuccess(data);
            }

            @Override
            public void onFail(ResultException e) {
                mView.IUpdatetFail(e.getMessage());
            }
        });
    }

}
