package com.seetaoism.user.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mr.k.mvp.kotlin.widget.EditTextButton;
import com.mr.k.mvp.utils.SPUtils;
import com.mr.k.mvp.utils.SystemFacade;
import com.seetaoism.AppConstant;
import com.seetaoism.R;
import com.seetaoism.data.entity.User;
import com.seetaoism.data.repositories.UserRepository;
import com.seetaoism.libloadingview.LoadingView;
import com.seetaoism.user.BaseUserFragment;
import com.seetaoism.user.register.RegisterFragment;
import com.seetaoism.widgets.CleanEditTextButton;
import com.seetaoism.widgets.TogglePasswordButton;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;


public class PasswordLoginFragment extends BaseUserFragment<LoginContract.ILoginPsPresenter> implements LoginContract.ILoginPsView, View.OnClickListener {

    private TextView mTvGotoRegister;
    private TextView login_goto_sms_login;

    private EditText mEdtPhoneNumber;
    private EditText mEdtPassword;

    private CleanEditTextButton mBtnCleanPhoneNumber;
    private CleanEditTextButton mBtnCleanPassword;

    private TogglePasswordButton mBtnShowPassword;

    private TextView mTvmGotoForgetPassword;



    private EditTextButton mBtnLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pwd_login;
    }

    @Override
    protected void initView(View root) {
        mTvGotoRegister = bindViewAndSetListener(R.id.login_tv_goto_register, this);
        mEdtPhoneNumber = bindViewAndSetListener(R.id.login_edt_phone_number, this);
        mEdtPassword = bindViewAndSetListener(R.id.login_edt_password, this);

        mBtnCleanPhoneNumber = bindViewAndSetListener(R.id.login_iv_clean_account, null);
        mBtnCleanPassword = bindViewAndSetListener(R.id.login_pwd_iv_clean_psw, null);


        mTvmGotoForgetPassword = bindViewAndSetListener(R.id.login_tv_forget_psw, this);
        mBtnShowPassword = bindViewAndSetListener(R.id.login_iv_show_password, null);
        login_goto_sms_login = bindViewAndSetListener(R.id.login_goto_sms_login, this);

        mEdtPhoneNumber.requestFocus();
        mBtnCleanPhoneNumber.bindEditText(mEdtPhoneNumber);


        mBtnCleanPassword.bindEditText(mEdtPassword);
        mBtnShowPassword.bindPasswordEditText(mEdtPassword);


        mBtnLogin = bindViewAndSetListener(R.id.login_btn_login, this);

        mBtnLogin.bindEditText(mEdtPassword);

        String value = SPUtils.getValue("loginnumber");
        if (value!=null){
            mEdtPhoneNumber.setText(value);
        }
    }


    @Override
    public LoginContract.ILoginPsPresenter createPresenter() {
        return new PasswordLoginPresenter(UserRepository.getInstance());
    }


    @Override
    public void onLoginSuccess(User user) {
        closeLoading();
        login(user);
        //保存上次的登录账号
        SPUtils.saveValueToDefaultSpByCommit("loginnumber",mEdtPhoneNumber.getText().toString());

    }

    @Override
    public void onLoginFail(String msg) {
        closeLoading();
        showToast(msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            // 去注册
            case R.id.login_tv_goto_register: {
                //addFragment(getFragmentManager(), RegisterFragment.class, R.id.login_fragment_container, null);
                backToOrAdd(RegisterFragment.class, R.id.login_fragment_container, null);
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
            // 验证码登录
            case R.id.login_goto_sms_login: {
                backToOrAdd(LoginVerifyFragment.class, R.id.login_fragment_container, null);
                break;
            }
            case R.id.login_tv_forget_psw:{
                String phoneNumber = mEdtPhoneNumber.getText().toString().trim();
                if (!SystemFacade.isValidPhoneNumber(phoneNumber)) {
                    showToast(R.string.text_error_input_phone_number);
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putString(AppConstant.BundleParamsKeys.ACCOUNT, mEdtPhoneNumber.getText().toString());

                backToOrAdd(ForgetPsFragment.class, R.id.login_fragment_container, bundle);
                break;
            }

        }
    }


    @Override
    protected void handBindSocial(String type, String openId, String head_url, String nickname, String unionid) {
        showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG, true);
        mPresenter.socialLogin(type, openId, head_url, nickname, unionid);
    }

    @Override
    public void onSocialLoginResult(User user, String msg) {
        closeLoading();
        if(user != null){
            login(user);

        }else{
            showToast(msg);
        }
    }
}
