package com.seetaoism.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import com.mr.k.mvp.base.IBasePresenter;
import com.mr.k.mvp.base.MvpBaseFragment;
import com.seetaoism.R;
import com.seetaoism.data.entity.User;
import com.seetaoism.home.HomeActivity;
import com.seetaoism.user.login.LoginActivity;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;


public abstract class BaseUserFragment<T extends IBasePresenter> extends MvpBaseFragment<T> implements UMAuthListener {

    public static final int SOCIAL_LOGIN_TYPE_QQ = 0X100;
    public static final int SOCIAL_LOGIN_TYPE_WE_CHAT = 0X200;
    public static final int SOCIAL_LOGIN_TYPE_SINA = 0X300;


    @IntDef({SOCIAL_LOGIN_TYPE_QQ, SOCIAL_LOGIN_TYPE_SINA, SOCIAL_LOGIN_TYPE_WE_CHAT})
    @interface SocialType {
    }


    private ImageView mIvClose;
    private ImageView mIvWeChatLogin;
    private ImageView mIvQQLogin;
    private ImageView mIvSinaLogin;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


    }

    protected void login(User user) {
        startActivity(new Intent(getActivity(), HomeActivity.class));
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mIvClose = bindViewAndSetListener(R.id.user_iv_left_close, mSocialOnClickListener);
        mIvWeChatLogin = bindViewAndSetListener(R.id.user_iv_wechat_login, mSocialOnClickListener);
        mIvQQLogin = bindViewAndSetListener(R.id.user_iv_qq_login, mSocialOnClickListener);
        mIvSinaLogin = bindViewAndSetListener(R.id.user_iv_sina_login, mSocialOnClickListener);


    }


    protected void loginSocial(@SocialType int type) {

    }

    private View.OnClickListener mSocialOnClickListener = v -> {

        switch (v.getId()) {
            case R.id.user_iv_left_close: {
                back();
                break;

            }

            case R.id.user_iv_wechat_login: {
                UMShareAPI umShareAPI = UMShareAPI.get(getActivity());
                // wx 的 有效期是1个月。过了1个月就必须重新授权登录。没有过期则不用跳转到授权页面，直接返回用户相关信息。
                umShareAPI.getPlatformInfo(getActivity(), SHARE_MEDIA.WEIXIN, this);
                break;
            }

            case R.id.user_iv_qq_login: {
                qqLogin(this);
                break;
            }
            case R.id.user_iv_sina_login: {

                break;
            }

        }
    };

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        Log.d("Test", "onStart");
    }

    @Override
    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {


        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();

            Log.d("Test", entry.getKey() + " = " + entry.getValue());
        }

    }

    @Override
    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
        Log.d("Test", "onError" + throwable.getMessage());
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media, int i) {
        Log.d("Test", "onCancel");
    }

    public void qqLogin(UMAuthListener listener) {
        UMShareAPI umShareAPI = UMShareAPI.get(getActivity());
        // wx 的 有效期是1个月。过了1个月就必须重新授权登录。没有过期则不用跳转到授权页面，直接返回用户相关信息。
        umShareAPI.getPlatformInfo(getActivity(), SHARE_MEDIA.QQ, listener);
    }

}
