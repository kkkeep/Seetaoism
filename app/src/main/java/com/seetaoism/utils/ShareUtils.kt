package com.seetaoism.utils

import android.app.Activity
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


/*
 * created by taofu on 2019-11-13
**/

fun shareNews(activity: FragmentActivity, news: NewsData.NewsBean, listener: UMShareListener) {
    val permissionUtils = PermissionUtils(activity)
    permissionUtils.checkPermission(activity, object : PermissionUtils.OnPermissionCallBack{
        override fun onAllMustAccept() {
            val thumb = UMImage(activity, R.drawable.ic_drawer_logo)
            val web = UMWeb(news.share_link)
            web.title = news.theme
            web.setThumb(thumb)  //缩略图
            web.description = news.description
            ShareAction(activity).withMedia(web).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QQ, SHARE_MEDIA.SINA)
                    .setCallback(listener).open();
        }

        override fun shouldShowRationale(call: PermissionUtils.PermissionCall?) {
        }

        override fun shouldShowPermissionSetting() {
        }

        override fun onDenied() {
            Toast.makeText(activity,activity.getString(R.string.text_share_qq_need_permission),Toast.LENGTH_LONG).show()
        }
    },arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),null)


}



fun shareNewsToWechat(activity: FragmentActivity, news: NewsData.NewsBean, listener: UMShareListener) {

    share(activity, news, SHARE_MEDIA.WEIXIN, listener)
}

fun shareNewsToQQ(activity: FragmentActivity, news: NewsData.NewsBean, listener: UMShareListener) {
    val permissionUtils = PermissionUtils(activity)
    permissionUtils.checkPermission(activity, object : PermissionUtils.OnPermissionCallBack{
        override fun onAllMustAccept() {
            share(activity, news, SHARE_MEDIA.QQ, listener)
        }

        override fun shouldShowRationale(call: PermissionUtils.PermissionCall?) {
        }

        override fun shouldShowPermissionSetting() {
        }

        override fun onDenied() {
            Toast.makeText(activity,activity.getString(R.string.text_share_qq_need_permission),Toast.LENGTH_LONG).show()
        }
    },arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),null)

    
}

fun shareNewsToSina(activity: FragmentActivity, news: NewsData.NewsBean, listener: UMShareListener) {
    share(activity, news, SHARE_MEDIA.SINA, listener)
}

fun shareNewsToMoment(activity: FragmentActivity, news: NewsData.NewsBean, listener: UMShareListener) {
    share(activity, news, SHARE_MEDIA.WEIXIN_CIRCLE, listener)
}



private fun share(activity: FragmentActivity, news: NewsData.NewsBean, platform: SHARE_MEDIA, listener: UMShareListener) {
    val thumb = UMImage(activity, R.drawable.ic_drawer_logo)
    val web = UMWeb(news.share_link)
    web.title = news.theme
    web.setThumb(thumb)  //缩略图
    web.description = news.description
    ShareAction(activity).withMedia(web).setPlatform(platform).setCallback(listener).share()
}