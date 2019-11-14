package com.seetaoism.user.login;

import com.mr.k.mvp.base.BasePresenter;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.data.entity.User;
import com.seetaoism.data.repositories.UserRepository;

import java.util.HashMap;
import java.util.Map;


public class LoginGetUserPresenter extends BasePresenter<LoginContract.ILoginGetUserInfoView> implements LoginContract.ILoginGetUserInfoPresenter {

    private LoginContract.ILoginModel mLoginRepository;

    public LoginGetUserPresenter() {

        mLoginRepository = UserRepository.getInstance();
    }

    @Override
    public void getUserInfoByToken(String token) {

        Map<String,String> params = new HashMap<>();

      //  params.put(RequestParamsKey.TOKEN, token);

        mLoginRepository.getUserByToken(getLifecycleProvider(), params, new IBaseCallBack<User>() {


            @Override
            public void onSuccess(User data) {
                if(mView != null){
                    mView.onUserInfoSuccess(data);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if(mView != null){
                    mView.onUserInfoFail(e.getMessage());
                }

            }
        });
    }
}
