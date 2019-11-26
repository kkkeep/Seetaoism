package com.seetaoism.home.changepassword;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.seetaoism.R;
import com.seetaoism.base.JDMvpBaseActivity;
import com.seetaoism.home.HomeActivity;
import com.seetaoism.user.login.LoginContract;
import com.seetaoism.user.login.UpdatePswPresenter;
import com.seetaoism.widgets.CleanEditTextButton;
import com.seetaoism.widgets.TogglePasswordButton;

public class SetPswActivity extends JDMvpBaseActivity<LoginContract.IUpdatePresenter> implements LoginContract.IUpdateView, View.OnClickListener {
    private TogglePasswordButton update_close_eye;
    private TogglePasswordButton update_eye;
    private CleanEditTextButton iv_cleanPhone1;
    private CleanEditTextButton iv_cleanPhone2;
    private EditText et_phoneNumber;
    private EditText et_verify;
    private Button register_btn_next;
    private String phone;
    private String sms_code;
    private ImageView iv_close;


    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        phone=intent.getStringExtra("phone");
        sms_code=intent.getStringExtra("sms_code");


        update_close_eye = findViewById(R.id.update_close_eye);
        update_eye = findViewById(R.id.update_eye);
        iv_close = findViewById(R.id.iv_close);
        iv_cleanPhone1 = findViewById(R.id.iv_cleanPhone1);
        iv_cleanPhone2 = findViewById(R.id.iv_cleanPhone2);
        et_phoneNumber = findViewById(R.id.et_phoneNumber);
        et_verify = findViewById(R.id.et_verify);
        register_btn_next = findViewById(R.id.register_btn_next);


        register_btn_next.setOnClickListener(this);
        iv_close.setOnClickListener(this);

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
                register_btn_next.setEnabled(et_verify.getText().toString().trim().length() > 0);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_psw;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_btn_next: {
                String verify = et_verify.getText().toString();
                mPresenter.SetPsw(phone, sms_code,verify);
                break;
            }
            case R.id.iv_close:
                finish();
                break;

            default:
                break;
        }

    }

    @Override
    public void IUpdateSuccess(String user) {
        closeLoading();
        startActivity(new Intent(this, HomeActivity.class));

        showToast(user);
    }

    @Override
    public void IUpdatetFail(String msg) {

        showToast(msg);
    }

    @Override
    public LoginContract.IUpdatePresenter createPresenter() {
        return new UpdatePswPresenter();
    }
}
