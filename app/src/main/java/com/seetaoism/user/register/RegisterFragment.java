package com.seetaoism.user.register;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mr.k.mvp.utils.Logger;
import com.mr.k.mvp.utils.SystemFacade;
import com.seetaoism.AppConstant.RequestParamsKey;
import com.seetaoism.R;
import com.seetaoism.data.entity.User;
import com.seetaoism.libloadingview.LoadingView;
import com.seetaoism.user.BaseUserFragment;
import com.seetaoism.user.login.LoginContract;
import com.seetaoism.user.login.LoginVerifyFragment;
import com.seetaoism.user.login.PasswordLoginFragment;

import java.util.Locale;

/*
 * created by taofu on 2019-08-29
 **/
public class RegisterFragment extends BaseUserFragment<LoginContract.IRegisterPresenter> implements View.OnClickListener, LoginContract.IRegisterView {


    private static final String TAG = "RegisterFragment";


    private ImageView mIvClearPhoneNumber;
    private EditText mEdtPhoneNumber;
    private EditText mEdtSmsCode;
    private TextView mTvGetCode;
    private Button mBtnNext;

    private TextView mTvGotoSmsLogin;
    private TextView mTvGotoPsdLogin;




    private CountDownTimer mCountDownTimer;


    private int mCountDown;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView(View root) {

        mIvClearPhoneNumber = bindViewAndSetListener(R.id.register_iv_clean, this);
        mTvGotoSmsLogin = bindViewAndSetListener(R.id.register_setting_psd_tv_goto_sms_login, this);
        mTvGotoPsdLogin = bindViewAndSetListener(R.id.register_setting_psd_tv_goto_psd_login, this);
        mTvGetCode = bindViewAndSetListener(R.id.register_tv_get_code, this);
        mBtnNext = bindViewAndSetListener(R.id.register_btn_next, this);
        mEdtPhoneNumber = bindViewAndSetListener(R.id.register_edt_phone_number, this);
        mEdtSmsCode = bindViewAndSetListener(R.id.register_edt_sms_code, this);
        mIvClearPhoneNumber.setVisibility(View.INVISIBLE);
        mBtnNext.setEnabled(false);
        mEdtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mEdtPhoneNumber.getText() != null && mEdtPhoneNumber.getText().toString().length() > 0) {
                    if (mEdtPhoneNumber.getText().toString().trim().length() == 11) {
                        mTvGetCode.setTextColor(getResources().getColor(R.color.red_2));
                    } else {
                        if (mTvGetCode.getText().length() > 4) {
                            mTvGetCode.setTextColor(getResources().getColor(R.color.gray_6));
                        }
                    }
                    if (mIvClearPhoneNumber.getVisibility() != View.VISIBLE) {
                        mIvClearPhoneNumber.setVisibility(View.VISIBLE);
                        return;
                    } else {
                        return;
                    }
                }
                mIvClearPhoneNumber.setVisibility(View.INVISIBLE);
                if (mTvGetCode.getText().length() > 4) {
                    mTvGetCode.setTextColor(getResources().getColor(R.color.gray_6));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        mEdtSmsCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(mEdtSmsCode.getText().toString())) {
                    mBtnNext.setEnabled(true);
                } else {
                    mBtnNext.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mEdtSmsCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    handVerifySmsCode();
                }
                return false;
            }
        });
    }


    @Override
    public LoginContract.IRegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_iv_left_close: {
                back();
                break;
            }

            case R.id.register_iv_clean: {
                mEdtPhoneNumber.setText(null);
                break;
            }

            case R.id.register_setting_psd_tv_goto_sms_login: {
                addFragment(getFragmentManager(), LoginVerifyFragment.class, R.id.login_fragment_container, null);
                break;
            }

            case R.id.user_iv_wechat_login: {
                loginSocial(SOCIAL_LOGIN_TYPE_WE_CHAT);
                break;
            }

            case R.id.user_iv_qq_login: {
                loginSocial(SOCIAL_LOGIN_TYPE_QQ);
                break;
            }
            case R.id.user_iv_sina_login: {
                loginSocial(SOCIAL_LOGIN_TYPE_WE_CHAT);
                break;
            }

            case R.id.register_setting_psd_tv_goto_psd_login: {
                addFragment(getFragmentManager(), PasswordLoginFragment.class, R.id.login_fragment_container, null);
                break;
            }

            case R.id.register_btn_next: {
                handVerifySmsCode();

              //  addFragment(getFragmentManager(), SetPasswordFragment.class, R.id.login_fragment_container, null);

                break;
            }

            case R.id.register_tv_get_code: {

                handGetSmsCode();
                break;
            }

        }
    }


    private void handVerifySmsCode() {

        if (mPresenter == null) {
            return;
        }


        String smsCode = mEdtSmsCode.getText().toString().trim();

        if (!SystemFacade.isValidSmsCodeNumber(smsCode)) {
            showToast(R.string.text_error_input_sms_code);
            return;
        }


        String phoneNumber = mEdtPhoneNumber.getText().toString().trim();

        if (!SystemFacade.isValidPhoneNumber(phoneNumber)) {
            showToast(R.string.text_error_input_phone_number);
            return;
        }


        showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG);

        mPresenter.verifySmsCode(phoneNumber, smsCode);

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
                mTvGetCode.setText(String.format(Locale.CHINA, "%ds", mCountDown--));
            }

            @Override
            public void onFinish() {

                resetGetCodeTextView();
            }
        };

        mCountDownTimer.start();
        mTvGetCode.setTextColor(getResources().getColor(R.color.red_2));
        mTvGetCode.setEnabled(false);


        mPresenter.getSmsCode(phoneNumber);


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
    public void onVerifySmsCodeResult(String msg, boolean success) {
        closeLoading();
        if (success) {
            Bundle bundle = new Bundle();
            bundle.putString(RequestParamsKey.MOBILE, mEdtPhoneNumber.getText().toString().trim());
            addFragment(getFragmentManager(), SetPasswordFragment.class, R.id.login_fragment_container, bundle);

        } else {
            showToast(msg);
        }
    }


    private void resetGetCodeTextView() {
        mTvGetCode.setTextColor(getResources().getColor(R.color.gray_6));
        mTvGetCode.setText(R.string.text_register_get_code);
        mTvGetCode.setEnabled(true);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    public boolean isNeedAddToBackStack() {
        return false;
    }

    @Override
    public boolean isNeedAnimation() {
        return false;
    }
}
