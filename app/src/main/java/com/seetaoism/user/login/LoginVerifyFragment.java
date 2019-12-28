package com.seetaoism.user.login;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.mr.k.mvp.utils.Logger;
import com.mr.k.mvp.utils.SPUtils;
import com.mr.k.mvp.utils.SystemFacade;
import com.seetaoism.R;
import com.seetaoism.data.entity.User;
import com.seetaoism.data.repositories.UserRepository;
import com.seetaoism.libloadingview.LoadingView;
import com.seetaoism.user.BaseUserFragment;
import com.seetaoism.user.register.PrivacyPolicyFragment;
import com.seetaoism.user.register.RegisterFragment;
import com.seetaoism.user.register.SetPasswordFragment;
import com.seetaoism.user.register.UserProtocolFragment;

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
    public EditText mEtVerify;
    private int mCountDown;
    private CountDownTimer mCountDownTimer;
    private ImageView user_iv_left_close;
    private TextView register_setting_psd_tv_license;


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
        register_setting_psd_tv_license = bindViewAndSetListener(R.id.register_setting_psd_tv_license, this);
        mEdtPhoneNumber.requestFocus();

        String value = SPUtils.getValue("loginnumber");
        if (value!=null){
            mEdtPhoneNumber.setText(value);
            mEdtPhoneNumber.setSelection(value.length());
        }

        final String str = getString(R.string.text_user_license);
        SpannableStringBuilder spannableString = new SpannableStringBuilder(str);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_2 )),9,13, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                addFragment(getFragmentManager(), UserProtocolFragment.class, R.id.login_fragment_container, null);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.RED);
            }
        }, 9, 13, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_2 )),14,str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                addFragment(getFragmentManager(), PrivacyPolicyFragment.class, R.id.login_fragment_container, null);
            }
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.RED);
            }

        }, 14, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        register_setting_psd_tv_license.setText(spannableString);
        register_setting_psd_tv_license.setMovementMethod(LinkMovementMethod.getInstance());


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

                if (mEdtPhoneNumber.getText().length() > 10) {
                    mTvGetVerify.setTextColor(getResources().getColor(R.color.black_1));
                } else if (mEdtPhoneNumber.getText().length() < 2) {
                    mTvGetVerify.setTextColor(getResources().getColor(R.color.gray_1));

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

        if (!SystemFacade.isValidPhoneNumber(phoneNumber)) {
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
        mTvGetVerify.setTextColor(getResources().getColor(R.color.black_1));
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
        //保存上次的登录账号
        SPUtils.saveValueToDefaultSpByCommit("loginnumber",mEdtPhoneNumber.getText().toString());
        hideKeyboard(mEdtPhoneNumber);


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

        if (user != null) {
            login(user);

        } else {
            showToast(msg);
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param :上下文
     * @param view :一般为EditText
     */
    public void hideKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
