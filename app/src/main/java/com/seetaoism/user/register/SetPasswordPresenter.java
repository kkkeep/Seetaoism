package com.seetaoism.user.register;

import androidx.annotation.NonNull;

import com.mr.k.mvp.base.BasePresenter;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.base.ICancelBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.AppConstant;
import com.seetaoism.data.entity.User;
import com.seetaoism.data.repositories.UserRepository;
import com.seetaoism.user.login.LoginContract;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/*
 * created by Cherry on 2019-08-30
 **/
public class SetPasswordPresenter extends BasePresenter<LoginContract.IRegisterSetPsdView> implements LoginContract.IRegisterSetPsdPresenter {


    private LoginContract.ILoginModel mRepository;


    public SetPasswordPresenter() {

        mRepository = UserRepository.getInstance();
    }

    @Override
    public void register(String phoneNumber, String psd, String confirmPsd) {

        Map<String,String> params = new HashMap<>();

        params.put(AppConstant.RequestParamsKey.MOBILE, phoneNumber);
        params.put(AppConstant.RequestParamsKey.PASSWORD, psd);
        params.put(AppConstant.RequestParamsKey.AFFIRM_PASSWORD, confirmPsd);

        mRepository.register(getLifecycleProvider(), params, new ICancelBaseCallBack<User>() {

            @Override
            public void onStart(Disposable disposable) {
                cacheRequest(disposable);
            }

            @Override
            public void onSuccess(User data) {
                if(mView != null){
                    mView.onRegisterResultSuccess(data);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if(mView != null){
                    mView.onRegisterResultFail(e.getMessage());
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

        mRepository.socialLogin(getLifecycleProvider(), map, new IBaseCallBack<User>() {
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
