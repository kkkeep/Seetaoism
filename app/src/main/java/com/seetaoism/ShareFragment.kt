package com.seetaoism

import android.view.View
import android.widget.Button
import android.widget.Toast

import com.mr.k.mvp.base.BaseFragment
import com.mr.k.mvp.base.IBasePresenter
import com.mr.k.mvp.utils.Logger
import com.seetaoism.base.JDShareNewsBaseMvpFragment
import com.seetaoism.data.entity.NewsData
import com.seetaoism.home.detail.DetailsContract
import com.seetaoism.home.detail.page.DetailPagePresenter
import com.seetaoism.home.detail.vp.DetailVpPresenter
import com.seetaoism.utils.ShareUtils
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA

/*
 * created by Cherry on 2019-12-30
 **/
class ShareFragment : JDShareNewsBaseMvpFragment<DetailsContract.IDetailPagePresenter>() {
    override fun createPresenter(): DetailsContract.IDetailPagePresenter {
        return DetailPagePresenter()
    }

    private var mShareWeibo: Button? = null


    override fun getLayoutId(): Int {
        return R.layout.activity_test_share
    }

    public override fun initView(root: View) {

        mShareWeibo = root.findViewById(R.id.share_btn_sina)

        mShareWeibo!!.setOnClickListener {
            val newsBean = NewsData.NewsBean()
            newsBean.id = "dddd"
            newsBean.share_link = "https://www.seetao.com/m_details/12379.html"
            newsBean.theme = "191公里即将完工，阿富汗和伊朗将通过铁路连接起来"
            newsBean.imageUrl = "https://s.seetaoism.com/Public/Uploads/thumbnail/2019-12-30/y_s_2vbe9xu4w31am.png"
            newsBean.description = "阿富汗有望从伊朗铁路网的发展中获得巨大利益，特别是通过阿曼海上的港口城市恰巴哈与阿富汗边境附近的扎赫丹之间的一条关键线路。同时它将帮助阿富汗人利用通过伊朗的各种运输通道扩大与其他国家的业务"
            shareNewsDirect(newsBean, SHARE_MEDIA.SINA)
            // ShareUtils.openShareNewsPanel(getActivity(), newsBean, ShareFragment.this, SHARE_MEDIA.SINA);
        }

    }

    override fun cancelRequest() {

    }


    override fun isHidePreFragment(): Boolean {
        return false
    }

    override fun enter(): Int {
        return 0
    }

    override fun popEnter(): Int {
        return 0
    }


    companion object {


        private val TAG = "ShareFragment"
    }
}
