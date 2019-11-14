package com.seetaoism.home.detail.vp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.mr.k.mvp.base.MvpBaseFragment
import com.mr.k.mvp.getUser
import com.mr.k.mvp.kotlin.base.BaseActivity
import com.seetaoism.AppConstant
import com.seetaoism.R
import com.seetaoism.data.entity.NewsAttribute
import com.seetaoism.data.entity.NewsData
import com.seetaoism.home.NewsViewModel
import com.seetaoism.home.detail.page.DetailPageFragment
import com.seetaoism.home.detail.DetailsContract
import com.seetaoism.libloadingview.LoadingView
import com.seetaoism.utils.shareNews
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb
import kotlinx.android.synthetic.main.fragment_detail_vp.*


/*
 * created by taofu on 2019-11-03
**/
class DetailVPFragment : MvpBaseFragment<DetailsContract.IDetailVpPresenter>(), DetailsContract.IDetailVpView, View.OnClickListener,UMShareListener {
    override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
    }

    override fun onStart(p0: SHARE_MEDIA?) {
    }

    override fun onResult(p0: SHARE_MEDIA?) {
    }

    override fun onCancel(p0: SHARE_MEDIA?) {
    }


    private var mColumnId: String? = null

    private var mIndex: Int = 0
    private lateinit var mViewModel: NewsViewModel

    private lateinit var mNewsDetailAdapter: NewsDetailAdapter

    private var mNewsData: NewsData? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (mNewsData == null) {
            mViewModel = ViewModelProviders.of(activity as FragmentActivity).get(NewsViewModel::class.java)

            mViewModel.getNewsDataLiveData().observe(this, Observer {
                //mNewsDetailAdapter.addData(it.articleList)
                mNewsDetailAdapter.addData(arrayListOf(it.articleList[mIndex]))
                mNewsDetailAdapter.notifyDataSetChanged()
                newsDetailVp.currentItem = mIndex
                refreshArticleAttr(it.articleList[mIndex])

            })
        }
    }

    fun setNewsListData(data: NewsData, position: Int) {

        mIndex = position;
        mNewsData = data;

    }

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)

        args?.run {
            mColumnId = getString(AppConstant.IntentParamsKeys.DETAIL_NEWS_COLUMN_ID)
            mIndex = getInt(AppConstant.IntentParamsKeys.ARTICLE_POSITION)

        }


    }

    override fun initView(root: View?) {

        root!!.run { setOnTouchListener { _, _ -> return@setOnTouchListener true } }

        newsDetailVp.offscreenPageLimit = 1;
        mNewsDetailAdapter = NewsDetailAdapter(childFragmentManager, mutableListOf()).apply {
            mNewsData?.let {
                //this.addData(it.articleList)
                this.addData(arrayListOf(it.articleList[mIndex]))
                refreshArticleAttr(it.articleList[mIndex])
            }

            newsDetailVp.adapter = this;
            newsDetailVp.currentItem = mIndex

        }

        newsDetailVp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
               refreshArticleAttr(mNewsDetailAdapter.getCurrentNew())
            }

        })

        newsDetailBottomIv.setOnClickListener(this)


        newsDetailBack.setOnClickListener(this)
        newsDetailCollect.setOnClickListener(this)
        newsDetailLike.setOnClickListener(this)
        newsDetailSearch.setOnClickListener(this)
        newsDetailShare.setOnClickListener(this)


    }

    private fun refreshArticleAttr(news: NewsData.News) {

        newsDetailLike.isChecked = (news.is_good == 1 && getUser() != null)
        newsDetailCollect.isChecked =( news.is_collect == 1 && getUser() != null)
    }

    override fun createPresenter() = DetailVpPresenter()

    override fun getLayoutId() = R.layout.fragment_detail_vp


    override fun isHidePreFragment() = false
    override fun exit() = 0;
    override fun popEnter() = 0;


    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.newsDetailBottomIv -> {


                val page = newsDetailVp.adapter!!.instantiateItem(newsDetailVp, newsDetailVp.currentItem) as DetailPageFragment
                page.commitArticle(mNewsDetailAdapter.getDataFromPosition(newsDetailVp.currentItem))

            }

            R.id.newsDetailBack -> {
                back()
            }

            R.id.newsDetailLike -> {
                if (newsDetailLike.isChecked) {
                    showToast(R.string.text_detail_do_lick_toast)
                    return
                }
                showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG, true);
                mPresenter.doArticleLike(mNewsDetailAdapter.getCurrentNew().id, 1)

            }

            R.id.newsDetailCollect -> {
                var type = 1
                if (newsDetailCollect.isChecked) {
                    type = 2
                }
                showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG, true);
                mPresenter.doArticleCollect(mNewsDetailAdapter.getCurrentNew().id, type)
            }

            R.id.newsDetailShare -> {
                shareNews(activity!!,mNewsDetailAdapter.getCurrentNew(),this)
            }
        }

    }

    override fun onArticleAttributeResult(newsAttribute: NewsAttribute?, errorMsg: String?) {

    }

    override fun onDoArticleLikeResult(data: String?, msg: String?) {
        closeLoading()
        if (data != null && msg == null) {
            newsDetailLike.isChecked = true
            mNewsDetailAdapter.getCurrentNew().is_good = 1
        } else {
            msg?.run {
                showToast(this)
            }

        }

    }

    override fun onDoArticleCollectResult(data: String?, msg: String?) {
        closeLoading()
        closeLoading()
        if (data != null && msg == null) {
            mNewsDetailAdapter.getCurrentNew().is_collect = 1
            newsDetailCollect.isChecked = true
        } else {
            msg?.run {
                showToast(this)
            }
        }
    }


    object Launcher {

        @JvmStatic
        fun open(activity: BaseActivity, data: NewsData, args: Bundle): DetailVPFragment? {
            val newsViewModel = ViewModelProviders.of(activity).get(NewsViewModel::class.java)
            newsViewModel.setData(data)
            return activity.addFragment(activity.supportFragmentManager, DetailVPFragment::class.java, android.R.id.content, args)
        }

        @JvmStatic
        internal fun openInner(activity: BaseActivity, data: NewsData, position: Int): DetailVPFragment? {

            val tag = activity.getFragmentTag(DetailVPFragment::class.java) + "_" + data.hashCode()
            return activity.addFragment(activity.supportFragmentManager, DetailVPFragment::class.java, android.R.id.content, tag = tag) {
                it.setNewsListData(data, position)
            }

        }

    }

    fun NewsDetailAdapter.getCurrentNew() : NewsData.News{
        return getNews(newsDetailVp.currentItem)
    }

}



class NewsDetailAdapter(fm: FragmentManager, val news: MutableList<NewsData.News>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val newsData = news[position]
        val fragment = DetailPageFragment();
        val bundle = Bundle()

        bundle.putString(AppConstant.IntentParamsKeys.ARTICLE_ID, newsData.id)
        bundle.putString(AppConstant.IntentParamsKeys.ARTICLE_LINK_URL, newsData.link)
        bundle.putString(AppConstant.IntentParamsKeys.ARTICLE_DESCRIPTION, newsData.description)
        bundle.putString(AppConstant.IntentParamsKeys.ARTICLE_TITLE, newsData.theme)

        fragment.arguments = bundle;

        return fragment
    }

    fun getNews(position: Int): NewsData.News {
        return news[position]
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return super.instantiateItem(container, position)
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun getCount(): Int {
        return news.size
    }

    fun addData(newDataList: List<NewsData.News>) {
        news.addAll(newDataList)
    }


    fun getDataFromPosition(position: Int): NewsData.News {
        return news[position]
    }

}

