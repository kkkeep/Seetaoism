package com.seetaoism.home.phone;

import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.base.IBasePresenter;
import com.mr.k.mvp.base.IBaseView;
import com.seetaoism.data.entity.User;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.HashMap;


public interface PhoneContract {

    public interface IPhoneView extends IBaseView<IPhonePresnter> {
        void IPhoneonSuccess(User user);

        void IPhoneonFail(String s);


        void IPhonecodeSuccess(String user);
        void IPhonecodeFail(String s);
    }

    public interface IPhonePresnter extends IBasePresenter<IPhoneView> {
        void getPhone(String mobile, String sms_code);

        void getPhone(String mobile);
    }

    public interface IPhoneModel {
        void getPhoneM(LifecycleProvider provider, HashMap<String, String> map, IBaseCallBack<User> callBack);
        void getPhonecodeM(LifecycleProvider provider, HashMap<String, String> map, IBaseCallBack<String> callBack);

    }
}
