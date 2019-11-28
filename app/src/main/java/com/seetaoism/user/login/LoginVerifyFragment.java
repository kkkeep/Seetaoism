package com.seetaoism.user.login;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mr.k.mvp.utils.Logger;
import com.mr.k.mvp.utils.SystemFacade;
import com.seetaoism.R;
import com.seetaoism.data.entity.User;
import com.seetaoism.data.repositories.UserRepository;
import com.seetaoism.libloadingview.LoadingView;
import com.seetaoism.user.BaseUserFragment;
import com.seetaoism.user.register.RegisterFragment;
import com.seetaoism.user.register.SetPasswordFragment;

import java.util.Locale;

public class LoginVerifyFragment extends BaseUserFragment<LoginContract.ILoginCodePresenter> implements LoginContract.ILoginCodeView, View.OnClickListener {
    private static final Object TAG = "LoginVerifyFragment";
    private TextView mTvGetVerify;
    private TextView mTvGoToPsdLogin;
    private TextView mTvGoToPsdRegister;
    private Button mBtnLogin;
    private Button mBtnNext;
    private ImageView mIvClearPhoneNumber;
    private EditText mEdtPhoneNumber;
    private EditText mEtVerify;
    private int mCountDown;
    private CountDownTimer mCountDownTimer;
    private ImageView user_iv_left_close;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_verify;
    }

    @Override
    protected void initView(View root) {
        mIvClearPhoneNumber = bindViewAndSetListener(R.id.login_iv_clean_psw, this);
        mEdtPhoneNumber = bindViewAndSetListener(R.id.login_edt_phone_number, this);
        mTvGetVerify = bindViewAndSetListener(R.id.login_tv_getverify, this);
        mEtVerify = bindViewAndSetListener(R.id.login_edt_verify, this);
        mTvGoToPsdLogin = bindViewAndSetListener(R.id.login_goto_ps_login, this);
        mTvGoToPsdRegister = bindViewAndSetListener(R.id.login_tv_goto_register, this);
        mBtnLogin = bindViewAndSetListener(R.id.login_btn_login, this);
        user_iv_left_close = bindViewAndSetListener(R.id.user_iv_left_close, this);

        mEdtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(mEdtPhoneNumber.getText().toString())) {
                    mIvClearPhoneNumber.setVisibility(View.INVISIBLE);
                } else {
                    mIvClearPhoneNumber.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(mEdtPhoneNumber.getText().toString())) {
                    mIvClearPhoneNumber.setVisibility(View.INVISIBLE);
                } else {
                    mIvClearPhoneNumber.setVisibility(View.VISIBLE);
                }
            }
        });


        mEtVerify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(mEtVerify.getText().toString())) {
                    mBtnLogin.setEnabled(true);
                } else {
                    mBtnLogin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }


    @Override
    public LoginContract.ILoginCodePresenter createPresenter() {
        return new LoginVerifyPresenter(UserRepository.getInstance());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_iv_clean_psw: {
                mEdtPhoneNumber.setText("");
                break;
            }
            //获取验证码
            case R.id.login_tv_getverify: {
                handGetSmsCode();
                break;
            }
            //密码登录
            case R.id.login_goto_ps_login: {
                backToOrAdd(PasswordLoginFragment.class, R.id.login_fragment_container, null);
                //back();
                break;
            }
            //立即注册
            case R.id.login_tv_goto_register: {
                backToOrAdd(RegisterFragment.class, R.id.login_fragment_container, null);
                //backTo(RegisterFragment.class);
                break;
            }
            case R.id.login_btn_login: {
                login();
                break;
            }
            case R.id.user_iv_left_close: {
                break;
            }

            case R.id.register_setting_psd_tv_goto_sms_login: {

                break;
            }
            case R.id.register_btn_next: {
                // handVerifySmsCode();

                addFragment(getFragmentManager(), SetPasswordFragment.class, R.id.login_fragment_container, null);

                break;
            }

            case R.id.register_tv_get_code: {

                handGetSmsCode();
                break;
            }

            default:
                break;
        }
    }

    private void login() {
        String phoneNumber = mEdtPhoneNumber.getText().toString().trim();

        if(!SystemFacade.isValidPhoneNumber(phoneNumber)){
            showToast(R.string.text_error_input_phone_number);
            return;
        }

        String verify = mEtVerify.getText().toString().trim();

        if (!SystemFacade.isValidSmsCodeNumber(verify)) {
            showToast(R.string.text_error_input_sms_code);
            return;
        }

        showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG, true);
        mPresenter.loginByCode(phoneNumber, verify);
    }


    private void handGetSmsCode() {
        String phoneNumber = mEdtPhoneNumber.getText().toString().trim();

        if (!SystemFacade.isValidPhoneNumber(phoneNumber)) {
            showToast(R.string.text_error_input_phone_number);
            return;
        }


        mCountDown = 60;
        mCountDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTvGetVerify.setText(String.format(Locale.CHINA, "%ds", mCountDown--));
            }

            @Override
            public void onFinish() {

                resetGetCodeTextView();
            }
        };

        mCountDownTimer.start();
        mTvGetVerify.setEnabled(false);

        //获取验证码
        mPresenter.getSmsCode(phoneNumber);


    }

    private void resetGetCodeTextView() {
        mTvGetVerify.setText(R.string.text_register_get_code);
        mTvGetVerify.setEnabled(true);
    }




    @Override
    public boolean isNeedAddToBackStack() {
        return false;
    }

    @Override
    public void onSmsCodeResult(String msg, boolean success) {
        Logger.d("%s msg = %s , success = %s", TAG, msg, success);
        if (!success) {
            showToast(msg);
            mCountDownTimer.cancel();
            resetGetCodeTextView();
        }
    }

    @Override
    public void onLoginSuccess(User user) {
        closeLoading();
        login(user);

    }

    @Override
    public void onLoginFail(String msg, boolean success) {
        closeLoading();
        showToast(msg);
    }


    @Override
    public boolean isNeedAnimation() {
        return false;
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
