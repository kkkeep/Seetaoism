package com.seetaoism.home.recommend;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mr.k.mvp.base.MvpBaseFragment;
import com.mr.k.mvp.kotlin.base.BaseActivity;
import com.mr.k.mvp.utils.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.seetaoism.R;
import com.seetaoism.data.entity.DetailExclusiveData;
import com.seetaoism.data.entity.FROM;
import com.seetaoism.data.entity.NewsData;
import com.seetaoism.data.repositories.NewsRepository;
import com.seetaoism.home.NewsViewModel;
import com.seetaoism.home.detail.vp.DetailVPFragment;
import com.seetaoism.home.recommend.RecommendContract.INewsPageModel;
import com.seetaoism.libloadingview.LoadingView;
import com.seetaoism.utils.ShareUtils;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.List;
import java.util.Objects;

public class NewsPageFragment extends MvpBaseFragment<RecommendContract.INewsPagePresenter> implements RecommendContract.INewsPageView {

    private static final String TAG = "NewsPageFragment";

    public static final String COLUMN_ID = "column_id";

    private RecyclerView mNewsRecyclerView;
    private NewsPageAdapter mNewsPageAdapter;
    private SmartRefreshLayout mSmartRefreshLayout;
    private NewsViewModel mNewViewModel;


    private String mColumnId = "";

    private int mNewsStart;
    private int mVideoStart;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page_news;
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        if (args != null) {
            mColumnId = args.getString(COLUMN_ID);
        }
    }

    @Override
    protected void initView(View root) {
        mNewsRecyclerView = root.findViewById(R.id.home_recommend_news_list);
        mSmartRefreshLayout = root.findViewById(R.id.home_recommend_news_refresh_layout);


        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refresh();
            }
        });


        mNewsPageAdapter = new NewsPageAdapter();
        mNewsPageAdapter.setOnItemClickListener(new NewsPageAdapter.OnItemClickListener() {
            @Override
            public void onClick(NewsData.NewsBean news, int position) {


                List<? extends NewsData.NewsBean> list = null;

                if(news instanceof NewsData.Banner){
                    list = mNewsPageAdapter.getBannerData();
                }else{
                    list= mNewsPageAdapter.getNewsData();
                }

                DetailExclusiveData data = new DetailExclusiveData(FROM.RECOMMEND,list,position);
                data.setStart(mNewsStart);
                data.setVideoStartForRecommend(mVideoStart);
                data.setMColumnId(mColumnId);



                DetailVPFragment.Launcher.open((BaseActivity) Objects.requireNonNull(getActivity()), data, null);
            }

            @Override
            public void onShareAction(NewsData.NewsBean news, int position) {
                ShareUtils.openShareNewsPanel(getActivity(), news, new UMShareListener() {
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
                });
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mNewsRecyclerView.setLayoutManager(layoutManager);
        mNewsRecyclerView.setAdapter(mNewsPageAdapter);
    }


    @Override
    protected void initData() {
        showLoadingForViewPager();
        mPresenter.getNewsData(mColumnId, mVideoStart, mNewsStart, INewsPageModel.REQUEST_TYPE_FIRST_LOAD);
    }


    private void refresh() {
        mPresenter.getNewsData(mColumnId, 0, 0, INewsPageModel.REQUEST_TYPE_REFRESH_LOAD);
    }

    private void loadMore() {
        mPresenter.getNewsData(mColumnId, mVideoStart, mNewsStart, INewsPageModel.REQUEST_TYPE_MORE_LOAD);
    }


    @Override
    public String getColumnId() {
        return mColumnId;
    }

    @Override
    public void onServerResult(NewsData data, int responseType, String msg) {

        if (responseType == INewsPageModel.REQUEST_TYPE_FIRST_LOAD) {
            if (data != null) {
                closeLoading();
                mNewsPageAdapter.setData(data.getBannerList(), data.getFlashList(), data.getArticleList());
                mVideoStart = data.getVideoStart();
                mNewsStart = data.getStart();
            } else {
                onError(msg, new LoadingView.RetryCallBack() {
                    @Override
                    public void onRetry() {
                        mPresenter.getNewsData(mColumnId, 0, 0, INewsPageModel.REQUEST_TYPE_FIRST_LOAD);
                    }
                });
                Logger.d("%s first load data error = %s", TAG, msg);
            }
        } else if (responseType == INewsPageModel.REQUEST_TYPE_REFRESH_LOAD) {
            mSmartRefreshLayout.finishRefresh();
            if (data != null) {
                mNewsPageAdapter.refresh(data.getBannerList(), data.getFlashList(), data.getArticleList());
                mVideoStart = data.getVideoStart();
                mNewsStart = data.getStart();
            } else {
                showToast(R.string.text_news_refresh_fail);

            }
        } else if (responseType == INewsPageModel.REQUEST_TYPE_MORE_LOAD) {
            mSmartRefreshLayout.finishLoadMore();
            if (data != null) {
                mNewsPageAdapter.loadMore(data.getArticleList());
                mVideoStart = data.getVideoStart();
                mNewsStart = data.getStart();
            } else {
                showToast(R.string.text_news_refresh_fail);
            }
        }
    }

    @Override
    public void onMemoryCacheResult(NewsData data) {
        closeLoading();
        mNewsPageAdapter.setData(data.getBannerList(), data.getFlashList(), data.getArticleList());
        mVideoStart = data.getVideoStart();
        mNewsStart = data.getStart();
    }


    @Override
    public void onDiskCacheResult(NewsData data) {

    }

    @Override
    public RecommendContract.INewsPagePresenter createPresenter() {
        return new NewsPagePresenter(NewsRepository.getInstance());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mNewsPageAdapter = null;
    }
}
