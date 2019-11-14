package com.seetaoism.home.changepassword;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.Nullable;

import com.mr.k.mvp.utils.SystemFacade;
import com.seetaoism.R;
import com.seetaoism.base.JDMvpBaseActivity;
import com.seetaoism.data.repositories.UserRepository;
import com.seetaoism.libloadingview.LoadingView;
import com.seetaoism.user.login.ForgetPsdPresenter;
import com.seetaoism.user.login.LoginContract;

import com.seetaoism.widgets.CleanEditTextButton;

import java.util.Locale;

public class VerificationPswActivity extends JDMvpBaseActivity<LoginContract.IForgetPresenter> implements LoginContract.IForgetPsView, View.OnClickListener {

    private ImageView mIvClose;
    private EditText mEtPhoneNumber;
    private TextView mIv1;
    private ImageView mImageView9;
    private CleanEditTextButton mIvCleanPhone;
    private TextView mLoginIvLine1;
    private EditText mEtVerify;
    private TextView mTvGetVerify;
    private TextView mLoginIvLine2;
    private Button mRegisterBtnNext;



    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {
        mIvClose = findViewById(R.id.iv_close);
        mEtPhoneNumber = findViewById(R.id.et_phoneNumber);
        mIv1 = findViewById(R.id.iv1);
        mImageView9 = findViewById(R.id.imageView9);
        mIvCleanPhone = findViewById(R.id.iv_cleanPhone);
        mLoginIvLine1 = findViewById(R.id.login_iv_line1);
        mEtVerify = findViewById(R.id.et_verify);
        mTvGetVerify = findViewById(R.id.tv_getVerify);
        mLoginIvLine2 = findViewById(R.id.login_iv_line2);
        mRegisterBtnNext = findViewById(R.id.register_btn_next);

        mIvCleanPhone.bindEditText(mEtPhoneNumber);
        mTvGetVerify.setOnClickListener(this);
        mRegisterBtnNext.setOnClickListener(this);

        //验证码
        mEtVerify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(mEtVerify.getText().toString())) {
                    mRegisterBtnNext.setEnabled(true);
                } else {
                    mRegisterBtnNext.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_verification_psw;
    }


    @Override
    public void onLoginSuccess(String msg, boolean success) {
        if (!success) {
            showToast(msg);
            mCountDownTimer.cancel();
            resetGetCodeTextView();
        }
    }

    @Override
    public void onLoginFail(String msg, boolean success) {
        closeLoading();
        if (success) {
            Intent intent = new Intent(VerificationPswActivity.this,SetPswActivity.class);
            intent.putExtra("phone",mEtPhoneNumber.getText().toString());
            intent.putExtra("sms_code",mEtVerify.getText().toString());
            startActivity(intent);

        } else {
            showToast(msg);
        }
    }

    @Override
    public LoginContract.IForgetPresenter createPresenter() {
        return new ForgetPsdPresenter(UserRepository.getInstance());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.login_tv_goto_register: {
                break;
            }
            case R.id.tv_getVerify: {
                //获取验证码
                handGetSmsCode();
                break;
            }
            case R.id.iv_close: {
                finish();
                break;
            }

            case R.id.register_btn_next:
                //判断验证码是否发给该手机
                handVerifySmsCode();
                break;
            default:
                break;
        }
    }

    private int mCountDown;
    private CountDownTimer mCountDownTimer;

    private void handGetSmsCode() {
        String phoneNumber = mEtPhoneNumber.getText().toString().trim();

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

        // 请求验证码
        mPresenter.ForgetCode(phoneNumber);


    }

    private void handVerifySmsCode() {

        if (mPresenter == null) {
            return;
        }


        String smsCode = mEtVerify.getText().toString().trim();

        if (!SystemFacade.isValidSmsCodeNumber(smsCode)) {
            showToast(R.string.text_error_input_sms_code);
            return;
        }


        String phoneNumber = mEtPhoneNumber.getText().toString().trim();

        if (!SystemFacade.isValidPhoneNumber(phoneNumber)) {
            showToast(R.string.text_error_input_phone_number);
            return;
        }


        showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG);

        mPresenter.ForgetSmsCode(phoneNumber, smsCode);

    }

    private void resetGetCodeTextView() {
        mTvGetVerify.setText(R.string.text_register_get_code);
        mTvGetVerify.setEnabled(true);
    }
}
