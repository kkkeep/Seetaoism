package com.seetaoism;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.mr.k.mvp.kotlin.base.MvpFragmentManager;
import com.seetaoism.data.entity.NewsData;
import com.seetaoism.utils.ShareUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/*
 * created by Cherry on 2019-11-27
 **/
public class ShareActivity extends AppCompatActivity  implements UMShareListener {

    private static final String TAG = "ShareActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_share);

        //MvpFragmentManager.addFragment(getSupportFragmentManager(), ShareFragment.class, android.R.id.content);

        if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this,mPermissionList,123);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    public void onClick(View view){

        switch (view.getId()){

            case  R.id.share_btn_wechat:{
                new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                        .withText("hello")//分享内容
                        .setCallback(this)//回调监听器
                        .share();

                // 带面板分享
                new ShareAction(this).withText("hello").setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                        .setCallback(this).open();
                break;
            }
            case  R.id.share_btn_qq: {


                new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QQ)//传入平台
                        .withMedia(new UMImage(this,  R.drawable.umeng_socialize_back_icon))
                        .withText("hello")//分享内容
                        .setCallback(this)//回调监听器
                        .share();
                break;
            }

            case  R.id.share_btn_sina:{
                NewsData.NewsBean newsBean = new NewsData.NewsBean();
                newsBean.setId("dddd");
                newsBean.setShare_link("https://www.seetao.com/m_details/12379.html");
                newsBean.setTheme("191公里即将完工，阿富汗和伊朗将通过铁路连接起来");
                newsBean.setImageUrl("https://s.seetaoism.com/Public/Uploads/thumbnail/2019-12-30/y_s_2vbe9xu4w31am.png");
                newsBean.setDescription("阿富汗有望从伊朗铁路网的发展中获得巨大利益，特别是通过阿曼海上的港口城市恰巴哈与阿富汗边境附近的扎赫丹之间的一条关键线路。同时它将帮助阿富汗人利用通过伊朗的各种运输通道扩大与其他国家的业务");
                ShareUtils.shareNewsDirect(this, newsBean, SHARE_MEDIA.SINA, this);
                break;
            }

            /*case  R.id.login_btn_wechat :{
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
            }
            case  R.id.login_btn_qq :{
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            }
            case R.id.login_btn_sina :{
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.SINA, umAuthListener);
                break;
            }*/

        }

    }


    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {

    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {

    }
}
