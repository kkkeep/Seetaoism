package com.seetaoism.user.login;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mr.k.mvp.base.MvpBaseFragment;
import com.seetaoism.AppConstant;
import com.seetaoism.R;
import com.seetaoism.home.HomeActivity;
import com.seetaoism.widgets.CleanEditTextButton;
import com.seetaoism.widgets.TogglePasswordButton;


public class UpdatePswFragment extends MvpBaseFragment<LoginContract.IUpdatePresenter> implements LoginContract.IUpdateView, View.OnClickListener {


    private TogglePasswordButton update_close_eye;
    private TogglePasswordButton update_eye;
    private CleanEditTextButton iv_cleanPhone1;
    private CleanEditTextButton iv_cleanPhone2;
    private EditText et_phoneNumber;
    private EditText et_verify;
    private Button register_btn_next;
    private String phone;
    private String sms_code;


    @Override
    protected int getLayoutId() {

        return R.layout.fragment_update_psw;
    }

    @Override
    protected void initView(View root) {
        update_close_eye = bindViewAndSetListener(R.id.update_close_eye, null);
        update_eye = bindViewAndSetListener(R.id.update_eye, null);
        iv_cleanPhone1 = bindViewAndSetListener(R.id.iv_cleanPhone1, null);
        iv_cleanPhone2 = bindViewAndSetListener(R.id.iv_cleanPhone2, null);
        et_phoneNumber = bindViewAndSetListener(R.id.et_phoneNumber, this);
        et_verify = bindViewAndSetListener(R.id.et_verify, this);
        register_btn_next = bindViewAndSetListener(R.id.register_btn_next, this);



        //获取传过来的数据
        Bundle bundle = getArguments();
        phone = bundle.getString(AppConstant.RequestParamsKey.MOBILE);
        sms_code = bundle.getString(AppConstant.RequestParamsKey.SMS_CODE);


       iv_cleanPhone1.bindEditText(et_phoneNumber);
       iv_cleanPhone2.bindEditText(et_verify);
       update_close_eye.bindPasswordEditText(et_phoneNumber);
       update_eye.bindPasswordEditText(et_verify);
       //按钮的状态
       et_verify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                register_btn_next .setEnabled(et_verify.getText().toString().trim().length() > 0);
            }
        });

    }

    @Override
    public LoginContract.IUpdatePresenter createPresenter() {

        return new UpdatePswPresenter();
    }

    @Override
    public void IUpdateSuccess(String user) {
        closeLoading();
        startActivity(new Intent(getActivity(), HomeActivity.class));
    }

    @Override
    public void IUpdatetFail(String msg) {
        showToast(msg);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.register_btn_next: {
                mPresenter.IUpdatePsw(phone,sms_code,et_verify.getText().toString());
                break;
            }

            default:
                break;
        }
    }
}
