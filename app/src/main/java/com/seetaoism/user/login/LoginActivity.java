package com.seetaoism.user.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.mr.k.mvp.MvpManager;
import com.seetaoism.R;
import com.seetaoism.base.JDBaseActivity;
import com.seetaoism.user.register.RegisterFragment;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;


public class LoginActivity extends JDBaseActivity {

    public static final String SMS_CODE_TYPE_REGISTER = "1"; // 注册
    public static final String SMS_CODE_TYPE_MODIFY_PSW = "2"; // 修改密码
    public static final String SMS_CODE_TYPE_MODIFY_PHONE_NUM = "3"; // 修改手机号
    public static final String SMS_CODE_TYPE_LOGIN = "4"; // 登录




    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {
        addFragment(getSupportFragmentManager(), LoginVerifyFragment.class, R.id.login_fragment_container, null);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    public void qqLogin(UMAuthListener listener){
        UMShareAPI umShareAPI = UMShareAPI.get(this);
        // wx 的 有效期是1个月。过了1个月就必须重新授权登录。没有过期则不用跳转到授权页面，直接返回用户相关信息。
        umShareAPI.getPlatformInfo(this, SHARE_MEDIA.QQ, listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    public static void start(){
        MvpManager.getContext().startActivity(new Intent(MvpManager.getContext(),LoginActivity.class));
    }
}
