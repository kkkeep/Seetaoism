package com.seetaoism.user.register;

import com.mr.k.mvp.base.BasePresenter;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.base.ICancelBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.AppConstant.RequestParamsKey;
import com.seetaoism.R;
import com.seetaoism.data.repositories.UserRepository;
import com.seetaoism.user.login.LoginActivity;
import com.seetaoism.user.login.LoginContract;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/*
 * created by Cherry on 2019-08-29
 **/
public class RegisterPresenter extends BasePresenter<LoginContract.IRegisterView> implements LoginContract.IRegisterPresenter {



    private LoginContract.ILoginModel mRepository;


    public RegisterPresenter() {
        mRepository = UserRepository.getInstance();
    }

    @Override
    public void getSmsCode(String phoneNumber) {

        Map<String,String> params = new HashMap<>();

        params.put(RequestParamsKey.MOBILE, phoneNumber);
        params.put(RequestParamsKey.SMS_CODE_TYPE, LoginActivity.SMS_CODE_TYPE_REGISTER);

        mRepository.getSmsCode(getLifecycleProvider(), params, new IBaseCallBack<String>() {



            @Override
            public void onSuccess(String data) {
                    mView.onSmsCodeResult(data, true);
            }

            @Override
            public void onFail(ResultException exception) {
                mView.onSmsCodeResult(exception.getMessage(), false);
            }
        });
    }


    @Override
    public void verifySmsCode(String phoneNumber, String code) {
        Map<String,String> params = new HashMap<>();
        params.put(RequestParamsKey.MOBILE, phoneNumber);
        params.put(RequestParamsKey.SMS_CODE_TYPE, LoginActivity.SMS_CODE_TYPE_REGISTER);
        params.put(RequestParamsKey.SMS_CODE, code);


        mRepository.verifySmsCode(getLifecycleProvider(), params, new ICancelBaseCallBack<String>() {


            @Override
            public void onStart(Disposable disposable) {
                cacheRequest(disposable);
            }

            @Override
            public void onSuccess(String data) {
                mView.onVerifySmsCodeResult(data, true);
            }

            @Override
            public void onFail(ResultException e) {
                mView.onVerifySmsCodeResult(getString(R.string.text_error_get_sms_code_fail), false);
            }
        });
    }


}
