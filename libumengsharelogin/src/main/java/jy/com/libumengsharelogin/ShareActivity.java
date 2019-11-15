package jy.com.libumengsharelogin;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/*
 * created by Cherry on 2019/5/7
 **/
public class ShareActivity  extends AppCompatActivity implements View.OnClickListener,UMShareListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_share);

        findViewById(R.id.btn_umeng_share_text).setOnClickListener(this);
        findViewById(R.id.btn_umeng_share_pic).setOnClickListener(this);
        findViewById(R.id.btn_umeng_share_link).setOnClickListener(this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        if(id == R.id.btn_umeng_share_text){
            shareText();
        }else if(id == R.id.btn_umeng_share_pic){
            sharePic();
        }else if(id == R.id.btn_umeng_share_link){
            shareLink();
        }

    }


    public void shareText(){
        new ShareAction(this).withText("hello").setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                .setCallback(this).open();
    }


    public void sharePic(){
        UMImage image = new UMImage(ShareActivity.this, R.drawable.umeng_socialize_qq);//资源文件
        new ShareAction(this).withMedia(image).setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                .setCallback(this).open();

    }


    public void shareLink(){
        UMImage thumb = new UMImage(ShareActivity.this, R.drawable.umeng_socialize_wechat);

        UMWeb web = new UMWeb("https://blog.csdn.net/Just_keep/article/details/78962586");
        web.setTitle("Implementation vs API dependency");//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription("Android Studio Gradle: Implementation vs API dependency");//描述

        new ShareAction(this).withMedia(web).setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                .setCallback(this).open();
    }
    @Override
    public void onStart(SHARE_MEDIA share_media) {
        Log.d("Test", "onStart = " + share_media.getName());
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        Log.d("Test", "onResult = " + share_media.getName());
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        Log.d("Test", "onError = " + share_media.getName() + " error + " + throwable.getMessage());
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        Log.d("Test", "onCancel = " + share_media.getName());
    }
}
