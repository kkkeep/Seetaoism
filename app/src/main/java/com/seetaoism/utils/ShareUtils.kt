package com.seetaoism.utils

import android.app.Activity
import android.text.TextUtils
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.mr.k.mvp.utils.PermissionUtils
import com.seetaoism.Manifest
import com.seetaoism.R
import com.seetaoism.data.entity.NewsData
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb
import kotlin.random.Random


/*
 * created by Cherry on 2019-11-13
**/


object ShareUtils {



    @JvmStatic
    fun openShareNewsPanel(activity: FragmentActivity, news: NewsData.NewsBean, listener: UMShareListener, vararg shareMedias : SHARE_MEDIA) {

        val permissionUtils = PermissionUtils(activity)
        permissionUtils.checkPermission(activity, object : PermissionUtils.OnPermissionCallBack {
            override fun onAllMustAccept() {
               // if(platform == SHARE_MEDIA.SINA){
                   // news.description = news.description + System.currentTimeMillis()
               // }
                val web = UMWeb(news.share_link)
                web.title = news.theme
                //web.mText = "" + Math.random();
                if(!TextUtils.isEmpty(news.imageUrl)){
                    val thumb = UMImage(activity, news.imageUrl)
                    web.setThumb(thumb)  //缩略图
                }else {
                    val thumb = UMImage(activity, "https://www.seetao.com/Public/static/share_logo.png")
                }

                web.description = news.description
                ShareAction(activity).withMedia(web).setDisplayList(*shareMedias)
                        .setCallback(listener).open();
            }

            override fun shouldShowRationale(call: PermissionUtils.PermissionCall?) {
            }

            override fun shouldShowPermissionSetting() {
            }

            override fun onDenied() {
                Toast.makeText(activity, activity.getString(R.string.text_share_qq_need_permission), Toast.LENGTH_LONG).show()
            }
        }, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), null)
    }


    @JvmStatic
    fun shareApp(activity: FragmentActivity, listener: UMShareListener) {

        val permissionUtils = PermissionUtils(activity)
        permissionUtils.checkPermission(activity, object : PermissionUtils.OnPermissionCallBack {
            override fun onAllMustAccept() {
                val thumb = UMImage(activity,"https://www.seetao.com/Public/static/share_logo.png")
                val web = UMWeb("https://www.seetao.com/m_greet")
                web.title = activity.getString(R.string.app_name)
                web.setThumb(thumb)  //缩略图
                web.description = activity.getString(R.string.text_share_app_desciption)
                ShareAction(activity).withMedia(web).setDisplayList( SHARE_MEDIA.QQ,SHARE_MEDIA.SINA, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(listener).open()
            }

            override fun shouldShowRationale(call: PermissionUtils.PermissionCall?) {
            }

            override fun shouldShowPermissionSetting() {
            }

            override fun onDenied() {
                Toast.makeText(activity, activity.getString(R.string.text_share_qq_need_permission), Toast.LENGTH_LONG).show()
            }
        }, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), null)


    }


    @JvmStatic
    fun shareNewsDirect(activity: FragmentActivity, news: NewsData.NewsBean, platform: SHARE_MEDIA, listener: UMShareListener){
        if(platform == SHARE_MEDIA.QQ || platform == SHARE_MEDIA.QZONE){

            val permissionUtils = PermissionUtils(activity)
            permissionUtils.checkPermission(activity, object : PermissionUtils.OnPermissionCallBack {
                override fun onAllMustAccept() {
                    shareNewsToSpecialPlatform(activity, news, platform, listener)
                }

                override fun shouldShowRationale(call: PermissionUtils.PermissionCall?) {
                }

                override fun shouldShowPermissionSetting() {
                }

                override fun onDenied() {
                    Toast.makeText(activity, activity.getString(R.string.text_share_qq_need_permission), Toast.LENGTH_LONG).show()
                }
            }, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), null)
        }else{
            shareNewsToSpecialPlatform(activity, news, platform, listener)
        }


    }

    private fun shareNewsToSpecialPlatform(activity: FragmentActivity, news: NewsData.NewsBean, platform: SHARE_MEDIA, listener: UMShareListener) {

        val web = UMWeb(news.share_link)
        web.title = news.theme

        /*if(platform == SHARE_MEDIA.SINA){
            news.description = news.description + System.currentTimeMillis()
        }*/
        web.mText = "" + Math.random();
        if(!TextUtils.isEmpty(news.imageUrl)){
            val thumb = UMImage(activity,news.imageUrl)
            web.setThumb(thumb)  //缩略图
        }else{
            if(platform == SHARE_MEDIA.SINA){
                val thumb = UMImage(activity,"https://www.seetao.com/Public/static/share_logo.png")
                web.setThumb(thumb)  //缩略图
            }
        }
        web.description = news.description
        ShareAction(activity).withMedia(web).setPlatform(platform).setCallback(listener).share()
    }



}

