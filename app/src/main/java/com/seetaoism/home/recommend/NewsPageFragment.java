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
import com.seetaoism.AppConstant;
import com.seetaoism.R;
import com.seetaoism.data.entity.NewsData;
import com.seetaoism.data.repositories.NewsRepository;
import com.seetaoism.home.NewsViewModel;
import com.seetaoism.home.detail.vp.DetailVPFragment;
import com.seetaoism.home.recommend.RecommendContract.INewsPageModel;
import com.seetaoism.libloadingview.LoadingView;

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
                NewsData newsData = new NewsData(); // 先new 一个 newsData 对象
                List<? extends NewsData.NewsBean> list = null;

                if(news instanceof NewsData.Banner){
                    newsData.setBannerList(mNewsPageAdapter.getBannerData());
                }else{
                    newsData.setArticleList(mNewsPageAdapter.getNewsData());
                }


                 // 把 新闻集合传进去
                newsData.setStart(mNewsStart); // 设置 下一次加载更多新闻的 start
                newsData.setVideoStart(mVideoStart); // 设置 下一次加载更多新闻的 video start
                Bundle bundle = new Bundle(); //
                bundle.putString(AppConstant.IntentParamsKeys.DETAIL_NEWS_COLUMN_ID, mColumnId); // 推荐页跳转详情需要传
                bundle.putInt(AppConstant.IntentParamsKeys.ARTICLE_POSITION, position); // 点击的item 的 position，注意这个不一定是item 的position


                DetailVPFragment.Launcher.open((BaseActivity) Objects.requireNonNull(getActivity()), newsData, bundle);
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
