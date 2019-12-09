package com.seetaoism.home.detail.vp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
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
import com.mr.k.mvp.UserState
import com.mr.k.mvp.getUser
import com.mr.k.mvp.kotlin.base.BaseActivity
import com.mr.k.mvp.registerUserBroadcastReceiver
import com.mr.k.mvp.unRegisterUserBroadcastReceiver
import com.mr.k.mvp.utils.Logger
import com.seetaoism.AppConstant
import com.seetaoism.R
import com.seetaoism.base.JDShareNewsBaseMvpFragment
import com.seetaoism.data.entity.DetailExclusiveData
import com.seetaoism.data.entity.FROM
import com.seetaoism.data.entity.NewsAttribute
import com.seetaoism.data.entity.NewsData
import com.seetaoism.home.NewsViewModel
import com.seetaoism.home.SsearchActivity
import com.seetaoism.home.collect.CollectViewModel
import com.seetaoism.home.detail.page.DetailPageFragment
import com.seetaoism.home.detail.DetailsContract
import com.seetaoism.libloadingview.LoadingView
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import kotlinx.android.synthetic.main.fragment_detail_vp.*


/*
 * created by Cherry on 2019-11-03
**/



class DetailVPFragment : JDShareNewsBaseMvpFragment<DetailsContract.IDetailVpPresenter>(), DetailsContract.IDetailVpView, View.OnClickListener, UMShareListener {


    private val TAG = "DetailVPFragment"

    private var mReceiver: BroadcastReceiver? = null

    private var mColumnId: String? = null

    private var mIndex: Int = 0
    private lateinit var mViewModel: NewsViewModel

    private lateinit var mNewsDetailAdapter: NewsDetailAdapter

    private var detailExclusiveData: DetailExclusiveData? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (detailExclusiveData == null || detailExclusiveData!!.from != FROM.INNER) {
            mViewModel = ViewModelProviders.of(activity as FragmentActivity).get(NewsViewModel::class.java)

            mViewModel.getNewsDataLiveData().observe(this, Observer {
                //mNewsDetailAdapter.addData(it.articleList)
                detailExclusiveData = it
                mNewsDetailAdapter.addData(arrayListOf(it.list[it.index]))
                mNewsDetailAdapter.notifyDataSetChanged()
                newsDetailVp.currentItem = it.index
                mPresenter.getArticleAttribute(mNewsDetailAdapter.getCurrentNew().id)
                // refreshArticleAttr(it.list[it.index])

            })
        }


        mReceiver = registerUserBroadcastReceiver { user, userState ->
            if(userState == UserState.Login){
                if (user == null) {
                    refreshArticleAttr(mNewsDetailAdapter.getCurrentNew())
                } else {
                    mPresenter.getArticleAttribute(mNewsDetailAdapter.getCurrentNew().id)
                }
            }

        }
    }

    fun setNewsListData(data: DetailExclusiveData) {
        mIndex = data.index
        detailExclusiveData = data;

    }

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)

        /* args?.run {
             mColumnId = getString(AppConstant.BundleParamsKeys.DETAIL_NEWS_COLUMN_ID)
             mIndex = getInt(AppConstant.BundleParamsKeys.ARTICLE_POSITION)

         }*/


    }

    override fun initView(root: View?) {

        root!!.run { setOnTouchListener { _, _ -> return@setOnTouchListener true } }

        newsDetailVp.offscreenPageLimit = 1;
        mNewsDetailAdapter = NewsDetailAdapter(childFragmentManager, mutableListOf()).apply {
            detailExclusiveData?.let {
                //this.addData(it.articleList)
                it.list[mIndex].run {
                    this@apply.addData(arrayListOf(this))
                    //  refreshArticleAttr(this)
                    mPresenter.getArticleAttribute(this.id)
                }
            }

            newsDetailVp.adapter = this;
            newsDetailVp.currentItem = mIndex

        }

        newsDetailVp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                mPresenter.getArticleAttribute(mNewsDetailAdapter.getCurrentNew().id)
            }

        })

        newsDetailBottomIv.setOnClickListener(this)


        newsDetailBack.setOnClickListener(this)
        newsDetailCollect.setOnClickListener(this)
        newsDetailLike.setOnClickListener(this)
        newsDetailSearch.setOnClickListener(this)
        newsDetailShare.setOnClickListener(this)


    }

    private fun refreshArticleAttr(news: NewsData.NewsBean) {


        newsDetailLike.isChecked = (news.is_good == 1 && getUser() != null)
        newsDetailCollect.isChecked = (news.is_collect == 1 && getUser() != null)
    }

    override fun createPresenter() = DetailVpPresenter()

    override fun getLayoutId() = R.layout.fragment_detail_vp


    override fun isHidePreFragment() = false
    override fun exit() = 0;
    override fun popEnter() = 0;

    override fun onDetach() {
        super.onDetach()

        mReceiver?.run {
            unRegisterUserBroadcastReceiver(this)
            mReceiver = null;
        }

    }

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
                openShareNewsPanel(mNewsDetailAdapter.getCurrentNew(),SHARE_MEDIA.QQ, SHARE_MEDIA.SINA, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)

            }
            R.id.newsDetailSearch -> {
                //搜索页面
                val intent = Intent(activity, SsearchActivity::class.java)
                activity!!.startActivity(intent)
            }
        }

    }

    override fun onArticleAttributeResult(newsAttribute: NewsAttribute?, errorMsg: String?) {
        if (newsAttribute != null) {
            val news = mNewsDetailAdapter.getCurrentNew()
            news.is_good = newsAttribute.is_good ?: 0
            news.is_collect = newsAttribute.is_collect ?: 0
            refreshArticleAttr(news)
        }
    }

    override fun onDoArticleLikeResult(data: String?, msg: String?) {
        closeLoading()
        if (data != null && msg == null) {
            newsDetailLike.isChecked = true
            mNewsDetailAdapter.getCurrentNew().is_good = 1
            showToast("操作成功")
        } else {
            msg?.run {
                showToast(this)
            }

        }

    }

    override fun onDoArticleCollectResult(data: String?, msg: String?) {
        closeLoading()
        if (data != null && msg == null) {
            newsDetailCollect.isChecked = !newsDetailCollect.isChecked

            if (newsDetailCollect.isChecked) {
                mNewsDetailAdapter.getCurrentNew().is_collect = 1
                showToast("操作成功")
            } else {
                mNewsDetailAdapter.getCurrentNew().is_collect = 0
                if (detailExclusiveData?.from == FROM.COLLECT) {
                    val viewModel = ViewModelProviders.of(activity!!).get(CollectViewModel::class.java)
                    viewModel.unCollect(mNewsDetailAdapter.getCurrentNew())
                }
            }

        } else {
            msg?.run {
                showToast(this)
            }
        }
    }


    object Launcher {

        @JvmStatic
        fun open(activity: BaseActivity, data: DetailExclusiveData, args: Bundle?): DetailVPFragment? {
            val newsViewModel = ViewModelProviders.of(activity).get(NewsViewModel::class.java)
            newsViewModel.setData(data)
            return activity.addFragment(activity.supportFragmentManager, DetailVPFragment::class.java, android.R.id.content, args)
        }


        @JvmStatic
         fun openInner(activity: BaseActivity, data: DetailExclusiveData): DetailVPFragment? {

            val tag = activity.getFragmentTag(DetailVPFragment::class.java) + "_" + data.hashCode()

            return activity.addFragment(activity.supportFragmentManager, DetailVPFragment::class.java, android.R.id.content, tag = tag) {
                it.setNewsListData(data)
            }

        }

    }

    fun NewsDetailAdapter.getCurrentNew(): NewsData.NewsBean {
        return getNews(newsDetailVp.currentItem)
    }

    override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
        Logger.d("%s onError = %s", TAG, p0?.getName());
        if (p0 != SHARE_MEDIA.WEIXIN) {
            closeLoading()
        }
    }

    override fun onStart(p0: SHARE_MEDIA?) {
        Logger.d("%s onStart = %s", TAG, p0?.getName())
        if (p0 != SHARE_MEDIA.WEIXIN) {
            showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG)
        }
    }

    override fun onResult(p0: SHARE_MEDIA?) {
        Logger.d("%s onResult = %s", TAG, p0?.getName())
        if (p0 != SHARE_MEDIA.WEIXIN) {
            closeLoading()
        }
    }

    override fun onCancel(p0: SHARE_MEDIA?) {
        Logger.d("%s onCancel = %s", TAG, p0?.getName())
        if (p0 != SHARE_MEDIA.WEIXIN) {
            closeLoading()
        }
    }


}


class NewsDetailAdapter(fm: FragmentManager, val news: MutableList<NewsData.NewsBean>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val newsData = news[position]
        val fragment = DetailPageFragment();
        val bundle = Bundle()

        bundle.putString(AppConstant.BundleParamsKeys.ARTICLE_ID, newsData.id)
        bundle.putString(AppConstant.BundleParamsKeys.ARTICLE_LINK_URL, newsData.link)
        bundle.putString(AppConstant.BundleParamsKeys.ARTICLE_SHARE_LINK_URL, newsData.share_link)
        bundle.putString(AppConstant.BundleParamsKeys.ARTICLE_DESCRIPTION, newsData.description)
        bundle.putString(AppConstant.BundleParamsKeys.ARTICLE_TITLE, newsData.theme)
        bundle.putString(AppConstant.BundleParamsKeys.ARTICLE_IMAGE_URL, newsData.imageUrl)

        fragment.arguments = bundle;

        return fragment
    }

    fun getNews(position: Int): NewsData.NewsBean {
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

    fun addData(newDataList: List<NewsData.NewsBean>) {
        news.addAll(newDataList)
    }


    fun getDataFromPosition(position: Int): NewsData.NewsBean {
        return news[position]
    }


}

