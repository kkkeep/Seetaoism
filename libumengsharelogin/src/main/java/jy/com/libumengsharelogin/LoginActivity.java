package jy.com.libumengsharelogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Iterator;
import java.util.Map;

/*
 * created by taofu on 2019/5/7
 **/
public class LoginActivity extends AppCompatActivity implements UMAuthListener {

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        findViewById(R.id.btn_umeng_wx_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UMShareAPI umShareAPI = UMShareAPI.get(LoginActivity.this);
                // wx 的 有效期是1个月。过了1个月就必须重新授权登录。没有过期则不用跳转到授权页面，直接返回用户相关信息。
                umShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, LoginActivity.this);
            }
        });

        findViewById(R.id.btn_umeng_qq_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UMShareAPI umShareAPI = UMShareAPI.get(LoginActivity.this);
                // qq 的 access_token_有效期是三个月。过了三个月就必须重新授权登录。没有过期则不用跳转到授权页面，直接返回用户相关信息。
                umShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, LoginActivity.this);
            }
        });
    }


    @Override
    protected void onRestart() {
        super.onRestart();

    }

    // 必须重写该方法才行，不然没有回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        Log.d("Test", "onStart");
    }

    @Override
    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String,String> entry = it.next();

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

}
