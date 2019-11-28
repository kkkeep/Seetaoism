package com.seetaoism.home.phone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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

import com.mr.k.mvp.utils.SystemFacade;
import com.seetaoism.R;
import com.seetaoism.base.JDMvpBaseActivity;
import com.seetaoism.data.entity.User;
import com.seetaoism.widgets.CleanEditTextButton;

import java.util.Locale;

public class PhoneActivity extends JDMvpBaseActivity<PhoneContract.IPhonePresnter> implements View.OnClickListener, PhoneContract.IPhoneView {
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
        mIvClose.setOnClickListener(this);

        mEtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mEtPhoneNumber.getText().length() > 10) {
                    mTvGetVerify.setTextColor(getResources().getColor(R.color.black_1));

                }else {
                    mTvGetVerify.setTextColor(getResources().getColor(R.color.gray_1));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }


        });

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
        return R.layout.activity_phone;
    }

    @Override
    public void IPhoneonSuccess(User user) {
        Intent intent = new Intent();
        intent.putExtra("phone", user.getUserInfo().getMobile());
        setResult(200, intent);
        finish();
    }

    @Override
    public void IPhoneonFail(String s) {

    }

    @Override
    public void IPhonecodeSuccess(String user) {

    }

    @Override
    public void IPhonecodeFail(String s) {
        showToast(s);
    }

    @Override
    public PhoneContract.IPhonePresnter createPresenter() {
        return new PhonePresenter();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_getVerify: {
                //获取验证码
                handGetSmsCode();
                break;
            }
            case R.id.iv_close:
                finish();
                break;

            case R.id.register_btn_next:
                updatephone();
                break;
        }
    }

    private void updatephone() {
        String phone = mEtPhoneNumber.getText().toString();
        String sms = mEtVerify.getText().toString();
        mPresenter.getPhone(phone, sms);
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
        mTvGetVerify.setTextColor(getResources().getColor(R.color.black_1));
        mTvGetVerify.setEnabled(false);

        // 请求验证码
        mPresenter.getPhone(phoneNumber);
    }

    private void resetGetCodeTextView() {
        mTvGetVerify.setText(R.string.text_register_get_code);

        mTvGetVerify.setEnabled(true);
    }
}
