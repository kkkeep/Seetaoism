package com.seetaoism.home.email;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.flyco.roundview.RoundTextView;
import com.seetaoism.R;
import com.seetaoism.base.JDMvpBaseActivity;
import com.seetaoism.data.entity.User;
import com.shehuan.niv.NiceImageView;

public class EmailActivity extends JDMvpBaseActivity<EmailContract.IEmailPresenter> implements View.OnClickListener, EmailContract.IEmailView {


    private NiceImageView close;
    private RoundTextView ok;
    private EditText email;

    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {
        close = findViewById(R.id.close);
        ok = findViewById(R.id.ok);
        email = findViewById(R.id.email);

        close.setOnClickListener(this);
        ok.setOnClickListener(this);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_email;
    }

    @Override
    public void IEmailonSucceed(User user) {

        //showToast("成功");
        Intent intent = new Intent();
        intent.putExtra("email",user.getUserInfo().getEmail());
        setResult(300,intent);
        finish();
        finish();
    }

    @Override
    public void IEmailonFail(String message) {

        showToast("失败");

    }

    @Override
    public EmailContract.IEmailPresenter createPresenter() {
        return new EmailPresenter();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                finish();
                break;
            case R.id.ok:
                String smgcodeStr = email.getText().toString();
                if (!smgcodeStr.isEmpty()) {
                    if (smgcodeStr.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")) {

                        mPresenter.getIEmail(smgcodeStr);
                    } else {
                        showToast("邮箱格式错误");
                    }
                } else {
                    showToast("邮箱不能为空");
                }
                break;
        }
    }
}
