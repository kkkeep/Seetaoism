package com.seetaoism.user.login;

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

import com.mr.k.mvp.base.MvpBaseFragment;
import com.mr.k.mvp.utils.Logger;
import com.mr.k.mvp.utils.SystemFacade;
import com.seetaoism.AppConstant;
import com.seetaoism.R;
import com.seetaoism.data.repositories.UserRepository;
import com.seetaoism.libloadingview.LoadingView;
import com.seetaoism.user.register.RegisterFragment;
import com.seetaoism.widgets.CleanEditTextButton;

import org.w3c.dom.Text;

import java.util.Locale;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ForgetPsFragment extends MvpBaseFragment<LoginContract.IForgetPresenter> implements LoginContract.IForgetPsView, View.OnClickListener {

    private EditText mEdtPhoneNumber;
    private EditText mEtVerify;
    private TextView mTvGetVerify;
    private CleanEditTextButton mIvAdminClean;
    private ImageView mIvClose;
    private Button mBtnNext;

    private String mAccount;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forgetpsd;
    }

    @Override
    protected void initView(View root) {
        mTvGetVerify = bindViewAndSetListener(R.id.tv_getVerify, this);
        mBtnNext = bindViewAndSetListener(R.id.register_btn_next, this);
        mEtVerify = bindViewAndSetListener(R.id.et_verify, this);
        mEdtPhoneNumber = bindViewAndSetListener(R.id.et_phoneNumber, this);
        mIvAdminClean = bindViewAndSetListener(R.id.iv_cleanPhone, this);
        mIvClose = bindViewAndSetListener(R.id.iv_close, this);
        mIvAdminClean.bindEditText(mEdtPhoneNumber);

        if (mAccount != null && mAccount.length() == 11) {
            mEdtPhoneNumber.setText(SystemFacade.hidePhoneNum(mAccount));
        }

        mBtnNext.setEnabled(false);
        //验证码

        mEtVerify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(mEtVerify.getText().toString())) {
                    mBtnNext.setEnabled(true);
                } else {
                    mBtnNext.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        if (args != null) {
            mAccount = args.getString(AppConstant.BundleParamsKeys.ACCOUNT);
        }

    }

    @Override
    public LoginContract.IForgetPresenter createPresenter() {

        return new ForgetPsdPresenter(UserRepository.getInstance());
    }

    @Override
    public void onLoginSuccess(String msg, boolean success) {
        Logger.d("%s msg = %s , success = %s", TAG, msg, success);
        if (!success) {
            showToast(msg);
            mCountDownTimer.cancel();
            resetGetCodeTextView();
        }
    }

    @Override
    public void onLoginFail(String msg, boolean success) {
        //成功关闭loding
        closeLoading();
        if (success) {

            Bundle bundle = new Bundle();
            //传用户名密码
            bundle.putString(AppConstant.RequestParamsKey.MOBILE, mEdtPhoneNumber.getText().toString().trim());
            bundle.putString(AppConstant.RequestParamsKey.SMS_CODE, mEtVerify.getText().toString().trim());
            //显示修改界面
            addFragment(getFragmentManager(), UpdatePswFragment.class, R.id.login_fragment_container, bundle);
        } else {
            showToast(msg);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.login_tv_goto_register: {
                addFragment(getFragmentManager(), RegisterFragment.class, R.id.login_fragment_container, null);
                break;
            }
            case R.id.tv_getVerify: {
                //获取验证码
                handGetSmsCode();
                break;
            }
            case R.id.iv_close: {
                addFragment(getFragmentManager(), PasswordLoginFragment.class, R.id.login_fragment_container, null);

                break;
            }
            case R.id.iv_cleanPhone: {
                mEdtPhoneNumber.setText("");
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

    private void handVerifySmsCode() {

        if (mPresenter == null) {
            return;
        }
        String smsCode = mEtVerify.getText().toString().trim();

        if (!SystemFacade.isValidSmsCodeNumber(smsCode)) {
            showToast(R.string.text_error_input_sms_code);
            return;
        }

        String phoneNumber = getPhoneNumber();

        if (TextUtils.isEmpty(phoneNumber)) {
            showToast(R.string.text_error_input_phone_number);
            return;
        }


        showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG);

        mPresenter.ForgetSmsCode(phoneNumber, smsCode);

    }

    private void handGetSmsCode() {

        String phoneNumber = getPhoneNumber();

        if (TextUtils.isEmpty(phoneNumber)) {
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

    private String getPhoneNumber() {

        String phoneNumber = mEdtPhoneNumber.getText().toString().trim();

        if (mAccount != null && mAccount.length() == 11) {
            String account = SystemFacade.hidePhoneNum(mAccount);
            if (account.equals(phoneNumber)) {
                phoneNumber = mAccount;
            }
        }


        if (!SystemFacade.isValidPhoneNumber(phoneNumber)) {
            return null;
        }

        return phoneNumber;
    }


    private void resetGetCodeTextView() {
        mTvGetVerify.setText(R.string.text_register_get_code);
        mTvGetVerify.setEnabled(true);
    }

    @Override
    public boolean isNeedAddToBackStack() {
        return true;
    }

    @Override
    public boolean isNeedAnimation() {
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }
}
