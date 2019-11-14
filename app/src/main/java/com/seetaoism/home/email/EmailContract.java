package com.seetaoism.home.email;

import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.base.IBasePresenter;
import com.mr.k.mvp.base.IBaseView;
import com.seetaoism.data.entity.User;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.HashMap;
import java.util.Map;

public interface EmailContract {


    public interface IEmailView extends IBaseView<IEmailPresenter> {
        void IEmailonSucceed(User user);

        void IEmailonFail(String message);
    }

    public interface IEmailPresenter extends IBasePresenter<IEmailView> {
        void getIEmail(String email);
    }

    public interface IEmailModel {
        void getEmailM(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<User> callBack);

    }
}
