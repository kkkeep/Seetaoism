package com.seetaoism.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.mr.k.mvp.base.BaseFragment;
import com.mr.k.mvp.base.IBasePresenter;
import com.mr.k.mvp.base.MvpBaseFragment;
import com.mr.k.mvp.kotlin.base.MvpFragmentManager;
import com.seetaoism.R;
import com.seetaoism.data.entity.User;
import com.seetaoism.home.HomeActivity;
import com.seetaoism.user.login.LoginActivity;
import com.seetaoism.user.login.LoginVerifyFragment;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import static android.content.Context.INPUT_METHOD_SERVICE;


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
                if (BaseUserFragment.this instanceof LoginVerifyFragment) {
                    getActivity().finish();
                    //关闭软键盘
                    HideKeyboard(mIvClose);
                    return;
                }
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
                UMShareAPI umShareAPI = UMShareAPI.get(getActivity());
                // wx 的 有效期是1个月。过了1个月就必须重新授权登录。没有过期则不用跳转到授权页面，直接返回用户相关信息。
                umShareAPI.getPlatformInfo(getActivity(), SHARE_MEDIA.QQ, this);
                break;
            }
            case R.id.user_iv_sina_login: {
                UMShareAPI umShareAPI = UMShareAPI.get(getActivity());
                // wx 的 有效期是1个月。过了1个月就必须重新授权登录。没有过期则不用跳转到授权页面，直接返回用户相关信息。
                umShareAPI.getPlatformInfo(getActivity(), SHARE_MEDIA.SINA, this);
                break;
            }

        }
    };

    public static void HideKeyboard(View v)
    {
        InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
        if ( imm.isActive( ) ) {
            imm.hideSoftInputFromWindow( v.getApplicationWindowToken( ) , 0 );

        }
    }


    @Override
    public void onStart(SHARE_MEDIA share_media) {
        Log.d("Test", "onStart");
    }

    @Override
    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

        String type = "";
        String openId = "";
        String nickname = "";
        String head_url = " ";
        String unionid = "";


        String openIdKey = "openid";


        if (share_media == SHARE_MEDIA.SINA) {
            type = "sina";
            openIdKey = "uid";

        } else if (share_media == SHARE_MEDIA.WEIXIN) {
            type = "wechat";
        } else if (share_media == SHARE_MEDIA.QQ) {
            type = "qq";
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            //uid
            if (entry.getKey().equals(openIdKey)) {
                openId = entry.getValue();

            }
            //昵称
            if (entry.getKey().equals("name")) {
                nickname = entry.getValue();
            }
            //头像
            if (entry.getKey().equals("profile_image_url")) {
                head_url = entry.getValue();
                ;
            }

            if (entry.getKey().equals("unionid")) {
                unionid = entry.getValue();
            }

        }

        handBindSocial(type, openId, head_url, nickname, unionid);


    }

    @Override
    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
        Log.d("Test", "onError" + throwable.getMessage());
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media, int i) {
        Log.d("Test", "onCancel");
    }


    protected void handBindSocial(String type, String openId, String head_url, String nickname, String unionid) {

    }


}
