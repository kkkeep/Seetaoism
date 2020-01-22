package com.seetaoism.splash

import android.content.Context
import android.text.TextUtils
import cn.jpush.android.cache.Sp
import com.mr.k.mvp.MvpManager
import com.mr.k.mvp.utils.DataCacheUtils
import com.mr.k.mvp.utils.Logger
import com.mr.k.mvp.utils.SPUtils
import com.seetaoism.data.entity.Ad
import com.seetaoism.libdownlaod.DownLoadManager
import java.io.File


/*
 * created by Cherry on 2020-01-12
**/

object SplashAdManager {

    private const val TAG = "SplashAdManager"

    private const val AD_SP = "ad_sp"
    private const val SPLASH_AD = "splash_ad"
    private const val JPG_AD_FILE_NAME = "splash_jpg_ad.jpg"
    private const val VIDEO_AD_FILE_NAME = "splash_video_ad.mp4"
    private const val SPLASH_AD_NAME = "splash_ad.ad"

    var ad: Ad? = null;

    fun onNewAd(list: List<Ad>?) {
        if (list === null || list.isEmpty()) {
            Logger.d("%s new ad is null",TAG)
            return
        }
        val newAd = list[0]


        if (newAd == ad) { // 如果和之前广告一样，
            if(adResExist(newAd)){
                Logger.d("%s new ad equals old ad and ad res is exist so do nothing",TAG)
                return;
            }else{
                Logger.d("%s new ad equals old ad but ad res is not exist so start load res",TAG)
                startLoadRes(newAd);
            }

        }else{

            ad?.let { deleteAd(it) };
            SPUtils.applayValue(AD_SP, SPLASH_AD,DataCacheUtils.convertToJsonFromObject(newAd))
            startLoadRes(newAd)
            Logger.d("%s save new ad into sp and start load res",TAG)
        }


    }


    fun getLocalAd(): Ad? {
        val adString = SPUtils.getValue(AD_SP, SPLASH_AD)

        if (TextUtils.isEmpty(adString)) {
            Logger.d("%s local ad is null ",TAG)
            return null
        }
        ad = DataCacheUtils.convertDataFromJson(Ad::class.java, adString)
        ad?.run {
            Logger.d("%s local ad is not null ",TAG)
            if(adResExist(this)){
                Logger.d("%s local ad res exist then get file path and return  ",TAG)

                val file = File( DownLoadManager.getInstance(MvpManager.context).downloadFileDirectory, SPLASH_AD_NAME)
                this.filePath = file.absolutePath;
                return ad;
            }else{
                Logger.d("%s local ad res not  exist then do not show local ad  ",TAG)
            }
        }
        return null
    }

    private fun startLoadRes(ad: Ad) {
        if(ad.layout == 1){
            DownLoadManager.getInstance(MvpManager.context).startLoadAdRes(MvpManager.context,ad.ad_url, SPLASH_AD_NAME)
        }else{
            DownLoadManager.getInstance(MvpManager.context).startLoadAdRes(MvpManager.context,ad.ad_url, SPLASH_AD_NAME);
        }
    }

    private fun adResExist(ad : Ad) : Boolean{
       var name = SPLASH_AD_NAME
        if(ad.layout == 2){
            name = SPLASH_AD_NAME;
        }

      return  DownLoadManager.getInstance(MvpManager.context).isFileExist(name)
    }

    private fun deleteAd(ad : Ad){
        Logger.d("%s new ad  not equals old ad so delete old ad res",TAG)
        var name = SPLASH_AD_NAME
        if(ad.layout == 2){
            name = SPLASH_AD_NAME;
        }
        return  DownLoadManager.getInstance(MvpManager.context).deleteFile(name)
    }
}