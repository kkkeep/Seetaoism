package com.seetaoism.home.email;

import com.mr.k.mvp.base.BasePresenter;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.AppConstant;
import com.seetaoism.data.entity.SearchData;
import com.seetaoism.data.entity.User;
import com.seetaoism.data.repositories.EmailRepository;
import com.seetaoism.data.repositories.SearchRepository;
import com.seetaoism.home.SearchContract;

import java.util.HashMap;
import java.util.Map;

public class EmailPresenter extends BasePresenter<EmailContract.IEmailView> implements EmailContract.IEmailPresenter {

    private EmailContract.IEmailModel mMode;


    public EmailPresenter() {
        this.mMode = new EmailRepository();
    }

    @Override
    public void getIEmail(String email) {
        Map<String, String> map = new HashMap<>();

        map.put(AppConstant.RequestParamsKey.EMAIL, email);

        mMode.getEmailM(getLifecycleProvider(), map, new IBaseCallBack<User>() {
            @Override
            public void onSuccess(User data) {
                if (mView != null) {
                    mView.IEmailonSucceed(data);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if (mView != null) {
    mView.IEmailonFail(e.getMessage());
                }
            }
        });
    }
}
