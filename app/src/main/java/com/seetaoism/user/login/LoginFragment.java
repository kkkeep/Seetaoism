package com.seetaoism.user.login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mr.k.mvp.base.MvpBaseFragment;
import com.seetaoism.R;
import com.seetaoism.data.entity.User;


public class LoginFragment extends MvpBaseFragment<LoginContract.ILoginPsPresenter> implements LoginContract.ILoginPsView, View.OnClickListener {


    private EditText mEdtPhoneNumber;
    private EditText mEdtPassword;

    private Button mBtnLogin;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView(View root) {

       mEdtPhoneNumber =  bindViewAndSetListener(R.id.login_edt_phone_number,this);
       mEdtPassword = bindViewAndSetListener(R.id.login_edt_password, this);

       mBtnLogin = bindViewAndSetListener(R.id.login_btn_login, this);
    }


    @Override
    public void onLoginSuccess(User user) {

    }

    @Override
    public void onLoginFail(String msg) {

    }

    @Override
    public LoginContract.ILoginPsPresenter createPresenter() {
        return null;
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){


        }
    }
}
