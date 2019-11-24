package com.seetaoism.base

import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.mr.k.mvp.base.BaseRepository
import com.mr.k.mvp.base.IBaseCallBack
import com.mr.k.mvp.base.IBasePresenter
import com.mr.k.mvp.base.MvpBaseFragment
import com.mr.k.mvp.exceptions.ResultException
import com.mr.k.mvp.utils.Logger
import com.mr.k.mvp.utils.MapBuilder
import com.seetaoism.AppConstant
import com.seetaoism.data.entity.HttpResult
import com.seetaoism.data.entity.NewsData
import com.seetaoism.data.okhttp.JDDataService
import com.seetaoism.libloadingview.LoadingView
import com.seetaoism.utils.ShareUtils
import com.seetaoism.widgets.IntegralWidget
import com.trello.rxlifecycle2.LifecycleProvider
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import java.lang.ref.SoftReference


private const val TAG = "JDShareNewsBaseMvpFragment"

/*
 * created by Cherry on 2019-11-23
**/
@Suppress("FINITE_BOUNDS_VIOLATION_IN_JAVA")
abstract class JDShareNewsBaseMvpFragment<P : IBasePresenter<*>> : MvpBaseFragment<P>() {


    private var mNewsID: String = ""

    private var isNeedShow = false;

    fun onShareArticleSuccess() {
        val repository = BaseRepository();
        val params = MapBuilder<String, String>().put(AppConstant.RequestParamsKey.DETAIL_ARTICLE_ID, mNewsID).builder()

        repository.observer(this as LifecycleProvider<*>, JDDataService.service().shareAddIntegral(params), Function<HttpResult<String>, ObservableSource<String>> {
            if (it.code == 1) {
                return@Function Observable.just(it.data)
            } else {
                return@Function Observable.error(ResultException(it.message))
            }
        }, object : IBaseCallBack<String> {
            override fun onSuccess(data: String) {
                if (isResumed) {
                    IntegralWidget.show(activity!!, 20)
                    Logger.d("%s request success show  IntegralWidget",TAG)
                    Log.d("Test", "show IntegralWidget")
                } else {
                    isNeedShow = true;
                    Logger.d("%s request success but do not need to show  IntegralWidget",TAG)
                }
            }

            override fun onFail(e: ResultException) {
            }
        })
    }


    override fun onResume() {
        super.onResume()
        if (isNeedShow) {
            isNeedShow = false;
            IntegralWidget.show(activity!!, 20)
            Logger.d("%s  show  IntegralWidget onResume",TAG)
        }
    }

    fun openShareNewsPanel(news: NewsData.NewsBean, vararg shareMedias: SHARE_MEDIA) {
        mNewsID = news.id
        ShareUtils.openShareNewsPanel(activity as FragmentActivity, news, JDShareListener(this), *shareMedias)
    }


    fun shareNewsDirect(news: NewsData.NewsBean, shareMedias: SHARE_MEDIA) {
        mNewsID = news.id;
        ShareUtils.shareNewsDirect(activity as FragmentActivity, news, shareMedias, JDShareListener(this))
    }


}


class JDShareListener(fragment: JDShareNewsBaseMvpFragment<*>) : UMShareListener {
    private val softReference: SoftReference<JDShareNewsBaseMvpFragment<*>> = SoftReference(fragment)
    override fun onResult(p0: SHARE_MEDIA?) {
        Logger.d("%s onResult = %s", TAG, p0?.getName())
        getFragment()?.run {
            if (p0 != SHARE_MEDIA.WEIXIN && p0 != SHARE_MEDIA.WEIXIN_CIRCLE) {
                closeLoading()
            }
            onShareArticleSuccess()
        }

    }

    override fun onCancel(p0: SHARE_MEDIA?) {
        Logger.d("%s onCancel = %s", TAG, p0?.getName())

        if (p0 != SHARE_MEDIA.WEIXIN && p0 != SHARE_MEDIA.WEIXIN_CIRCLE) {
            getFragment()?.run {
                closeLoading()
            }
        }
    }

    override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
        Logger.d("%s onError = %s", TAG, p0?.getName())
        if (p0 != SHARE_MEDIA.WEIXIN && p0 != SHARE_MEDIA.WEIXIN_CIRCLE) {
            getFragment()?.run {
                closeLoading()
            }
        }
    }

    override fun onStart(p0: SHARE_MEDIA?) {
        Logger.d("%s onStart = %s", TAG, p0?.getName())
        if (p0 != SHARE_MEDIA.WEIXIN && p0 != SHARE_MEDIA.WEIXIN_CIRCLE) {
            getFragment()?.run {
                showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG)
            }
        }

    }

    private fun getFragment(): JDShareNewsBaseMvpFragment<*>? {
        return softReference.get()
    }
}