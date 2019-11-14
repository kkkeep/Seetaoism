package com.seetaoism.home.phone;

import android.util.Log;

import com.mr.k.mvp.base.BasePresenter;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.base.ICancelBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.AppConstant;
import com.seetaoism.data.entity.User;
import com.seetaoism.data.repositories.PerfectRepository;
import com.seetaoism.data.repositories.PhoneRepository;
import com.seetaoism.home.perfect.PerfectContract;
import com.seetaoism.user.login.LoginActivity;

import java.util.HashMap;
import java.util.Map;

public class PhonePresenter extends BasePresenter<PhoneContract.IPhoneView> implements PhoneContract.IPhonePresnter {

    private PhoneContract.IPhoneModel mMode;

    public PhonePresenter() {
        this.mMode = new PhoneRepository();
    }
    //这个是修改
    @Override
    public void getPhone(String mobile, String sms_code) {
        HashMap<String, String> map = new HashMap<>();
        map.put(AppConstant.RequestParamsKey.MOBILE, mobile);
        map.put(AppConstant.RequestParamsKey.SMS_CODE, sms_code);

        mMode.getPhoneM(getLifecycleProvider(), map, new IBaseCallBack<User>() {
            @Override
            public void onSuccess(User data) {
                if (mView != null) {
                    mView.IPhoneonSuccess(data);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if (mView != null) {
                    mView.IPhoneonFail(e.getMessage());
                }
            }
        });


    }
    //这个验证码
    @Override
    public void getPhone(String mobile) {
        HashMap<String, String> params = new HashMap<>();
        params.put(AppConstant.RequestParamsKey.MOBILE, mobile);
        params.put(AppConstant.RequestParamsKey.SMS_CODE_TYPE, LoginActivity.SMS_CODE_TYPE_MODIFY_PHONE_NUM);
        //找不到了呀。。。omg传的都对。。是不是后台的错 哈哈哈
        mMode.getPhonecodeM(getLifecycleProvider(), params, new IBaseCallBack<String>() {

            @Override
            public void onSuccess(String data) {
                mView.IPhonecodeSuccess(data);
            }

            @Override
            public void onFail(ResultException exception) {
                mView.IPhonecodeFail(exception.getMessage());
            }

        });
    }
}
