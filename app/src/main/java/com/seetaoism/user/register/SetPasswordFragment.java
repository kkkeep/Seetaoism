package com.seetaoism.user.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mr.k.mvp.utils.SPUtils;
import com.mr.k.mvp.utils.SystemFacade;
import com.seetaoism.AppConstant.RequestParamsKey;
import com.seetaoism.R;
import com.seetaoism.data.entity.User;
import com.seetaoism.home.HomeActivity;
import com.seetaoism.libloadingview.LoadingView;
import com.seetaoism.user.BaseUserFragment;
import com.seetaoism.user.login.LoginContract;
import com.seetaoism.user.login.LoginVerifyFragment;
import com.seetaoism.user.login.PasswordLoginFragment;
import com.seetaoism.widgets.CleanEditTextButton;
import com.seetaoism.widgets.TogglePasswordButton;

/*
 * created by Cherry on 2019-08-29
 **/
public class SetPasswordFragment extends BaseUserFragment<LoginContract.IRegisterSetPsdPresenter> implements View.OnClickListener,LoginContract.IRegisterSetPsdView {


    private static final String TAG = "SetPasswordFragment";


    private ImageView mIvClose;
    private EditText mEdtPsd;
    private CleanEditTextButton mIvCleanPsd;
    private TogglePasswordButton mIvShowPsd;


    private EditText mEdtConfirmPsd;
    private CleanEditTextButton mIvCleanCPsd;
    private TogglePasswordButton mIvShowConfirmPsd;

    private TextView mTvLicense;

    private Button mBtnRegister;

    private TextView mTvGotoSmsLogin;
    private TextView mTvGotoPsdLogin;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_set_password;
    }

    @Override
    protected void initView(View root) {
        mEdtPsd = bindViewAndSetListener(R.id.register_setting_psd_edt_psd, this);
        mEdtConfirmPsd = bindViewAndSetListener(R.id.register_setting_psd_edt_confirm_psd, this);
        mIvShowPsd = bindViewAndSetListener(R.id.register_setting_psd_iv_show_psd, null);
        mIvShowConfirmPsd = bindViewAndSetListener(R.id.register_setting_psd_iv_show_confirm_psd, null);
        mIvCleanPsd = bindViewAndSetListener(R.id.register_setting_psd_iv_clean_psd, null);
        mIvCleanCPsd = bindViewAndSetListener(R.id.register_setting_psd_iv_clean_cpsd, null);
        mTvLicense = bindViewAndSetListener(R.id.register_setting_psd_tv_license, null);

        mBtnRegister = bindViewAndSetListener(R.id.register_setting_psd_btn_register, this);

        mTvGotoSmsLogin = bindViewAndSetListener(R.id.register_setting_psd_tv_goto_sms_login, this);
        mTvGotoPsdLogin = bindViewAndSetListener(R.id.register_setting_psd_tv_goto_psd_login, this);


        mIvShowPsd.bindPasswordEditText(mEdtPsd);
        mIvShowConfirmPsd.bindPasswordEditText(mEdtConfirmPsd);

        mIvCleanPsd.bindEditText(mEdtPsd);
        mIvCleanCPsd.bindEditText(mEdtConfirmPsd);

        mBtnRegister.setEnabled(false);

        mEdtPsd.requestFocus();


        final String str = getString(R.string.text_user_license);
        SpannableStringBuilder spannableString = new SpannableStringBuilder(str);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_2 )),9,13, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_2 )),14,str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        mTvLicense.setText(spannableString);



        mEdtConfirmPsd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mBtnRegister.setEnabled(mEdtConfirmPsd.getText().toString().trim().length() > 0);
            }
        });
    }

    @Override
    public LoginContract.IRegisterSetPsdPresenter createPresenter() {
        return new SetPasswordPresenter();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.register_setting_psd_btn_register: {
                handClickRegister();
                break;
            }
            case R.id.user_iv_left_close: {
                back();
                break;
            }



            case R.id.register_setting_psd_tv_license: {
                break;
            }

            case R.id.register_setting_psd_tv_goto_sms_login: {
                backToOrAdd(LoginVerifyFragment.class, R.id.login_fragment_container, null);
                break;
            }

            case R.id.register_setting_psd_tv_goto_psd_login: {
                backToOrAdd(PasswordLoginFragment.class, R.id.login_fragment_container, null);
                break;
            }


        }

    }

    private void handClickRegister() {

        if (mPresenter == null) {
            return;
        }
        String psd = mEdtPsd.getText().toString().trim();

        String cpsd = mEdtConfirmPsd.getText().toString().trim();

        if (TextUtils.isEmpty(psd) || TextUtils.isEmpty(cpsd)) {
            showToast(R.string.text_error_null_password);
            return;
        }


        if (!psd.equals(cpsd)) {
            showToast(R.string.text_error_tow_password_not_same);
            return;
        }

        Bundle bundle = getArguments();
        String phoneNum = null;
        if (bundle != null) {
            phoneNum = bundle.getString(RequestParamsKey.MOBILE);
        }

        if (TextUtils.isEmpty(phoneNum)) {
            return;
        }

        showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG);
        mPresenter.register(phoneNum, psd, cpsd);
    }

    @Override
    public void onRegisterResultSuccess(User user) {
        closeLoading();
        login(user);

    }

    @Override
    public void onRegisterResultFail(String msg) {
        showToast(msg);
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
