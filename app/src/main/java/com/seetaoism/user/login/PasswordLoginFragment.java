package com.seetaoism.user.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mr.k.mvp.utils.SPUtils;
import com.mr.k.mvp.utils.SystemFacade;
import com.seetaoism.R;
import com.seetaoism.data.entity.User;
import com.seetaoism.data.repositories.UserRepository;
import com.seetaoism.home.HomeActivity;
import com.seetaoism.libloadingview.LoadingView;
import com.seetaoism.user.BaseUserFragment;
import com.seetaoism.user.register.RegisterFragment;
import com.seetaoism.widgets.CleanEditTextButton;
import com.seetaoism.widgets.TogglePasswordButton;


public class PasswordLoginFragment extends BaseUserFragment<LoginContract.ILoginPsPresenter> implements LoginContract.ILoginPsView, View.OnClickListener {

    private TextView mTvGotoRegister;
    private TextView login_goto_sms_login;

    private EditText mEdtPhoneNumber;
    private EditText mEdtPassword;

    private CleanEditTextButton mBtnCleanPhoneNumber;

    private TogglePasswordButton mBtnShowPassword;

    private Button mBtnLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView(View root) {
        mTvGotoRegister = bindViewAndSetListener(R.id.login_tv_goto_register, this);
        mEdtPhoneNumber = bindViewAndSetListener(R.id.login_edt_phone_number, this);
        mEdtPassword = bindViewAndSetListener(R.id.login_edt_password, this);

        mBtnCleanPhoneNumber = bindViewAndSetListener(R.id.login_iv_clean, null);
        mBtnShowPassword = bindViewAndSetListener(R.id.login_iv_show_password, null);
        login_goto_sms_login = bindViewAndSetListener(R.id.login_goto_sms_login, this);


        mBtnCleanPhoneNumber.bindEditText(mEdtPhoneNumber);
        mBtnShowPassword.bindPasswordEditText(mEdtPassword);


        mBtnLogin = bindViewAndSetListener(R.id.login_btn_login, this);
    }


    @Override
    public LoginContract.ILoginPsPresenter createPresenter() {
        return new PasswordLoginPresenter(UserRepository.getInstance());
    }


    @Override
    public void onLoginSuccess(User user) {
        closeLoading();
//        //保存头像
//        SPUtils.saveValueToDefaultSpByCommit("pic", user.getUserInfo().getHeadUrl());
//        //保存用户名
//        SPUtils.saveValueToDefaultSpByCommit("name", user.getUserInfo().getNickname());
//        //保存手机号
//        SPUtils.saveValueToDefaultSpByCommit("editphone", user.getUserInfo().getMobile());
//        //保存电子邮箱
//        SPUtils.saveValueToDefaultSpByCommit("editemail", user.getUserInfo().getEmail());
        login(user);
        startActivity(new Intent(getActivity(), HomeActivity.class));
        getActivity().finish();
    }

    @Override
    public void onLoginFail(String msg) {
        onError(msg, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.login_tv_goto_register: {
                addFragment(getFragmentManager(), RegisterFragment.class, R.id.login_fragment_container, null);
                break;
            }

            case R.id.login_btn_login: {
                String phoneNumber = mEdtPhoneNumber.getText().toString().trim();

                if (!SystemFacade.isValidPhoneNumber(phoneNumber)) {
                    showToast(R.string.text_error_input_phone_number);
                    return;
                }

                String password = mEdtPassword.getText().toString().trim();

                if (TextUtils.isEmpty(password)) {
                    showToast(R.string.text_error_null_password);
                    return;
                }

                showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG, true);
                mPresenter.login(phoneNumber, password);
                break;
            }
            case R.id.login_goto_sms_login:
                addFragment(getFragmentManager(), LoginVerifyFragment.class, R.id.login_fragment_container, null);
                break;
        }
    }

    @Override
    public boolean isNeedAddToBackStack() {
        return true;
    }

    @Override
    public boolean isNeedAnimation() {
        return true;
    }


}
