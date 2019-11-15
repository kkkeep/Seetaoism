package com.seetaoism.home.detail.vp

import com.mr.k.mvp.base.BasePresenter
import com.mr.k.mvp.base.IBaseCallBack
import com.mr.k.mvp.base.ICacheBaseCallBack
import com.mr.k.mvp.base.ICancelBaseCallBack
import com.mr.k.mvp.exceptions.ResultException
import com.mr.k.mvp.utils.MapBuilder
import com.seetaoism.AppConstant
import com.seetaoism.AppConstant.RequestParamsKey.DETAIL_ARTICLE_ID
import com.seetaoism.AppConstant.RequestParamsKey.DETAIL_DO_ARTICLE_LICK_TYPE
import com.seetaoism.data.entity.NewsAttribute
import com.seetaoism.data.repositories.DetailRepository
import com.seetaoism.home.detail.DetailsContract
import io.reactivex.disposables.Disposable


/*
 * created by Cherry on 2019-11-05
**/
class DetailVpPresenter : BasePresenter<DetailsContract.IDetailVpView>(), DetailsContract.IDetailVpPresenter {


    private val mRepository = DetailRepository()


    override fun doArticleLike(id: String, type: Int) {

        val  params = MapBuilder<String,String>().put(DETAIL_ARTICLE_ID,id).put(DETAIL_DO_ARTICLE_LICK_TYPE,type.toString()).builder()


        mRepository.doArticleLike(lifecycleProvider,params,object : ICancelBaseCallBack<String>{

            override fun onSuccess(data: String) {
                mView?.run {
                    onDoArticleLikeResult(data,null)
                }
            }

            override fun onFail(e: ResultException) {
                mView?.run {
                    onDoArticleLikeResult(null,e.message)
                }
            }

            override fun onStart(disposable: Disposable) {
                cacheRequest(disposable)
            }

        })
    }

    override fun doArticleCollect(id: String, type: Int) {

        val  params = MapBuilder<String,String>().put(DETAIL_ARTICLE_ID,id).put(DETAIL_DO_ARTICLE_LICK_TYPE,type.toString()).builder()

        mRepository.doArticleCollect(lifecycleProvider,params,object : ICancelBaseCallBack<String>{

            override fun onSuccess(data: String) {
                mView?.run {
                    onDoArticleCollectResult(data,null)
                }
            }
            override fun onFail(e: ResultException) {
                mView?.run {
                    onDoArticleCollectResult(null,e.message)
                }
            }
            override fun onStart(disposable: Disposable) {
                cacheRequest(disposable)
            }

        })
    }

    override fun getArticleAttribute(id: String) {
        val  params = MapBuilder<String,String>().put(DETAIL_ARTICLE_ID,id).builder()

        mRepository.getArticleAttribute(lifecycleProvider,params,object : IBaseCallBack<NewsAttribute>{

            override fun onSuccess(data: NewsAttribute) {
                mView?.run {
                    onArticleAttributeResult(data,null)
                }
            }
            override fun onFail(e: ResultException) {
                mView?.run {
                    onArticleAttributeResult(null,e.message)
                }
            }

        })
    }


}