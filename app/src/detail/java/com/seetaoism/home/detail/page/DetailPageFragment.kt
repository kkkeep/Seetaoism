@file:Suppress("DEPRECATION")

package com.seetaoism.home.detail.page

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mr.k.mvp.base.MvpBaseFragment
import com.mr.k.mvp.kotlin.widget.CommentDecoration
import com.mr.k.mvp.kotlin.base.BaseActivity
import com.mr.k.mvp.utils.Logger
import com.mr.k.mvp.utils.SystemFacade
import com.seetaoism.AppConstant
import com.seetaoism.R
import com.seetaoism.data.entity.*
import com.seetaoism.home.detail.DetailsContract
import com.seetaoism.home.detail.vp.DetailVPFragment
import com.seetaoism.home.detail.vp.NewsDetailAdapter
import com.seetaoism.libloadingview.LoadingView
import com.seetaoism.utils.shareNewsToMore
import com.seetaoism.utils.shareNewsToQQ
import com.seetaoism.utils.shareNewsToSina
import com.seetaoism.utils.shareNewsToWechat
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import kotlinx.android.synthetic.main.fragment_detail_vp.*
import kotlinx.android.synthetic.main.fragment_details.*


/*
 * created by Cherry on 2019-11-03
**/
class DetailPageFragment : MvpBaseFragment<DetailsContract.IDetailPagePresenter>(), DetailsContract.IDetailPageView,UMShareListener {

    companion object{
        private  const val TAG = "DetailPageFragment"
    }


    private val mListAdapter = DetailPageNewsAdapter()
    private var mArticleId: String? = null
    private var mArticleLinkUrl: String? = null
    private var mArticleDescription: String? = null
    private var mArticleTtile: String? = null

    private var mIndex: Int? = null
    private var mLastInputContent : String? = null

    private var mIsFinish = false

    private var mItemPosition = -1;
    private var mReplyItemPosition = -1;

    private var mCommentStart = 0
    private var mCommentStartPoint = 0L

    private var mHasMoreComment = false



    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        args?.run {
            mArticleId = getString(AppConstant.BundleParamsKeys.ARTICLE_ID)
            mArticleLinkUrl = getString(AppConstant.BundleParamsKeys.ARTICLE_LINK_URL)
            mArticleDescription = getString(AppConstant.BundleParamsKeys.ARTICLE_DESCRIPTION)
            mArticleTtile = getString(AppConstant.BundleParamsKeys.ARTICLE_TITLE)

        }
    }

    override fun createPresenter() = DetailPagePresenter()

    override fun onArticleListResult(data: MutableList<NewsData.News>?, msg: String?) {
        closeLoading()
        data?.run {
            mListAdapter.run {
                setNews(data)
                detailRelativeList.addItemDecoration(CommentDecoration(data.size - 1))
                notifyDataSetChanged()
            }

        }
    }
    override fun onCommentResult(comment: CommentData.Comment?, msg: String?) {
        if(comment != null && msg == null){
            mLastInputContent = null
            mListAdapter.addComment(comment);

        }else{
            showToast(msg ?: getString(R.string.text_detail_comment_error))
        }


        closeLoading()
    }

    override fun onReplyCommentResult(reply: CommentData.Reply?, msg: String?) {

        closeLoading()
        if(reply != null && msg == null){
            mLastInputContent = null
            mListAdapter.addReply(arrayListOf(reply),mItemPosition)
        }else{
            showToast("评论失败 "+ msg)
        }
        mItemPosition = -1;
    }

    override fun onArticleUserCommentResult(commentData: CommentData?, msg: String?) {

       if(mCommentStart != 0 || mListAdapter.hasComments()){
            detailSrl.finishLoadMore()
        }

        commentData?.run {
            mCommentStart = start
            mCommentStartPoint = point_time
            mHasMoreComment = more == 1
            comment_list?.run {
                mListAdapter.addComments(this);


            }
        }



    }

    override fun onArticleUserCommentReplyResult(commentReplyData: CommentReplyData?, msg: String?) {
        closeLoading()
        commentReplyData?.run {
            val comment = mListAdapter.getCommentByPosition(mItemPosition);

            comment.reply_start = start
            comment.replyPointTime = point_time
            comment.reply_more = more

            if(SystemFacade.isListEmpty(commentReplyData.reply_list)){
                  mListAdapter.notifyItemChanged(mItemPosition)
                return
            }

            commentReplyData.reply_list.run {
                mListAdapter.addReply(this,mItemPosition)
            }

        }
    }


    override fun onDoLikeResult(content: String?, msg: String?) {
        if(content != null && msg == null){
            mListAdapter.doLike(mItemPosition);
        }else{
            if (msg != null) {
                showToast(msg)
            }
        }
        closeLoading()

    }


    override fun getLayoutId() = R.layout.fragment_details

    override fun initView(root: View?) {

        detailSrl.run {
            setEnableRefresh(false)
            setEnableLoadMore(false)
            setEnableAutoLoadMore(false)

            setOnLoadMoreListener {
                if(mHasMoreComment){
                    getComments()
                }else{
                    setNoMoreData(true)
                    finishLoadMore(1000)
                }
            }
        }


        detailWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        detailWebView.run {
            initSetting()

            webChromeClient = object : WebChromeClient() {

                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    if (newProgress == 100 && !mIsFinish) {
                        mPresenter.getRelatedArticleList(mArticleId)
                        getComments()

                        mIsFinish = true;
                    }
                }
            }
        }
        detailRelativeList.run {
            layoutManager = LinearLayoutManager(context)
            adapter = mListAdapter.apply {
                setItemOnClickListener(object : DetailPageNewsAdapter.DetailItemOnClickListener {
                    override fun onNewsClick( newsList : MutableList<out NewsData.NewsBean>,realPosition: Int) {

                        DetailVPFragment.Launcher.openInner(activity as BaseActivity, DetailExclusiveData(FROM.INNER,newsList,realPosition))
                    }

                    override fun onCommentClick(comment: CommentData.Comment,itemPostion : Int) {
                        mItemPosition = itemPostion
                        showReplyDialog(comment)
                    }

                    override fun onCommentReplayClick(reply: CommentData.Reply,itemPostion : Int,replyItemPostion : Int) {
                        mItemPosition = itemPostion
                        mReplyItemPosition = replyItemPostion
                        showReplyDialog(reply)
                    }

                    override fun onLoadMoreReplyClick(comment: CommentData.Comment, itemPosition: Int) {
                        mItemPosition = itemPosition;
                        getSomeoneReplyList(comment)
                    }

                    override fun onCommentLikeClick(comment: CommentData.Comment, itemPosition: Int) {
                        showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG,view as ViewGroup,false)
                        mItemPosition = itemPosition;
                        mPresenter.doCommentLike(comment.comment_id)

                    }
                })
            }

        }

        detailRelativeList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if(newState == RecyclerView.SCROLL_STATE_IDLE && isResumed){
                    if(isBottom() ){
                        //if(mHasMoreComment){
                        Logger.d("%s set load more enable ",TAG)
                            detailSrl.setEnableLoadMore(true)
                       // }else{

                       // }
                    }else{
                        Logger.d("%s set load more not enable ",TAG)
                        detailSrl.setEnableLoadMore(false)
                    }
                }
            }
        })


        detailPageShareWechat.setOnClickListener {
            shareNewsToWechat(activity!!,buildNews(),this);
        }
        detailPageShareQQ.setOnClickListener {
            shareNewsToQQ(activity!!,buildNews(),this);
        }

        detailPageShareSina.setOnClickListener {
            shareNewsToSina(activity!!,buildNews(),this);
        }

        detailPageShareMoments.setOnClickListener {
            shareNewsToMore(activity!!,buildNews(),this);
        }
    }


    private fun buildNews() : NewsData.News{

        val news =  NewsData.News()
        news.share_link = mArticleLinkUrl
        news.description = mArticleDescription
        news.theme = mArticleTtile
        return news
    }


    private fun getComments(start : Int = mCommentStart,pointTime : Long = mCommentStartPoint) {
        mPresenter.getArticleUserCommentList(mArticleId, start, pointTime)
    }


    private fun getSomeoneReplyList(comment: CommentData.Comment){
        showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG,view as ViewGroup,false)
        mPresenter.getArticleUserCommentReplyList(mArticleId,comment.comment_id,comment.reply_start,comment.replyPointTime)
    }

    private fun isBottom(): Boolean {
        if(detailRelativeList == null){
            return false
        }
        val layoutManager = detailRelativeList.layoutManager as LinearLayoutManager

        val position = layoutManager.findLastVisibleItemPosition()

        if (position == mListAdapter.getItemCount() - 1) {
            val view = layoutManager.findViewByPosition(position) ?: return false
            if (view.bottom == detailRelativeList.height) {
                return true
            }
        }

        return false

    }

    override fun initData() {
        showLoadingForViewPager()
        detailWebView.loadUrl(mArticleLinkUrl)
    }



    internal fun commitArticle(news : NewsData.NewsBean){
        showCommentDialog(news)
    }


    /**
     * 显示评论新闻的Pop
     */
    private fun showCommentDialog(news : NewsData.NewsBean){
        CommentPopView(context!!).show(detailWebView,CommentPopView.TYPE_COMMENT, mLastInputContent , getString(R.string.text_detail_write_comment), object : OnActionListener {
            override fun onAction(type: Int, content: String) {
                if(type == CommentPopView.TYPE_COMMENT){
                    mLastInputContent = content
                    showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG,view as ViewGroup,true)
                    mPresenter.commentArticle(news.id,content)
                }
            }
        })
    }

    /**
     * 显示回复一级评论pop
     */
    private fun showReplyDialog(comment: CommentData.Comment){

        CommentPopView(context!!).show(detailWebView,CommentPopView.TYPE_REPLY,mLastInputContent, getString(R.string.text_detail_relay_someone,comment.username),object : OnActionListener {
            override fun onAction(type: Int, content: String) {
                if(type == CommentPopView.TYPE_REPLY){
                    mLastInputContent = content
                    showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG,view as ViewGroup,true)
                    mPresenter.replyComment(mArticleId,comment.comment_id,content,comment.user_id,1,"0")
                }
            }
        })
    }
    /**
     * 显示回复别人的回复的pop
     */
    private fun showReplyDialog(reply: CommentData.Reply){
        CommentPopView(context!!).show(detailWebView,CommentPopView.TYPE_REPLY,mLastInputContent,getString(R.string.text_detail_relay_someone,reply.from_name), object : OnActionListener {
            override fun onAction(type: Int, content: String) {
                if(type == CommentPopView.TYPE_REPLY){
                    mLastInputContent = content;
                    showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG,view as ViewGroup,true)
                    mPresenter.replyComment(mArticleId,reply.comment_id,content,reply.from_id,2,reply.reply_id);
                }
            }
        })
    }


    fun NewsDetailAdapter.getCurrentNew() : NewsData.NewsBean{
        return getNews(newsDetailVp.currentItem)
    }

    override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
        if(p0 != SHARE_MEDIA.WEIXIN && p0 != SHARE_MEDIA.WEIXIN_CIRCLE){
            closeLoading()
        }
    }

    override fun onStart(p0: SHARE_MEDIA?) {
        if(p0 != SHARE_MEDIA.WEIXIN && p0 != SHARE_MEDIA.WEIXIN_CIRCLE){
            showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG,view as ViewGroup,false)
        }
    }

    override fun onResult(p0: SHARE_MEDIA?) {
        if(p0 != SHARE_MEDIA.WEIXIN && p0 != SHARE_MEDIA.WEIXIN_CIRCLE){
            closeLoading()
        }
    }
    override fun onCancel(p0: SHARE_MEDIA?) {
        if(p0 != SHARE_MEDIA.WEIXIN && p0 != SHARE_MEDIA.WEIXIN_CIRCLE){
            closeLoading()
        }
    }
}


@SuppressLint("SetJavaScriptEnabled")
fun WebView.initSetting() {

    val webSetting = settings
    webSetting.javaScriptEnabled = true
    webSetting.javaScriptCanOpenWindowsAutomatically = true
    webSetting.allowFileAccess = true
    webSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
    webSetting.setSupportZoom(true)
    webSetting.useWideViewPort = true
    webSetting.displayZoomControls = false
    webSetting.builtInZoomControls = true
    webSetting.setSupportMultipleWindows(true)
    webSetting.setAppCacheEnabled(true)
    webSetting.domStorageEnabled = true
    webSetting.setAppCacheMaxSize(java.lang.Long.MAX_VALUE)
    webSetting.pluginState = WebSettings.PluginState.ON_DEMAND
    webSetting.cacheMode = WebSettings.LOAD_DEFAULT
    webSetting.loadWithOverviewMode = true
    webSetting.setGeolocationEnabled(true)

    val userAgent = webSetting.userAgentString
    webSetting.userAgentString = userAgent
    webSetting.setAppCacheEnabled(true)
    webSetting.databaseEnabled = true
    webSetting.mediaPlaybackRequiresUserGesture = false

}