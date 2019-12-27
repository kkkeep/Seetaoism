package com.seetaoism.home.topic;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mr.k.mvp.base.MvpBaseFragment;
import com.mr.k.mvp.kotlin.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.seetaoism.R;
import com.seetaoism.data.entity.DetailExclusiveData;
import com.seetaoism.data.entity.FROM;
import com.seetaoism.home.detail.vp.DetailVPFragment;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;
import java.util.Objects;
import com.seetaoism.data.entity.TopicData;


public class TopicFragment extends MvpBaseFragment<TopicContract.ITopicPresnter> implements TopicContract.ITopicView, OnRefreshLoadMoreListener {


    private SmartRefreshLayout topic_smart;
    private RecyclerView topic_rec;
    private int start = 0;
    private int time = 0;
    private ArrayList<TopicData.Bannerlist> mBannerlist = new ArrayList<>();
    private ArrayList<TopicData.Topiclist> mlist = new ArrayList<>();
    private TopicAdapter adapter;

    private LinearLayoutManager mLinearLayoutManager;


    @Override
    protected void initData() {
        mPresenter.getTopic(start, time);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_topic;
    }

    @Override
    protected void initView(View root) {

        topic_smart = root.findViewById(R.id.topic_smart);
        topic_rec = root.findViewById(R.id.topic_rec);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        topic_rec.setLayoutManager(mLinearLayoutManager);
        adapter = new TopicAdapter(getContext(), mBannerlist, mlist);

        adapter.setOnItemClickListener((list,index)->{
            DetailExclusiveData data = new DetailExclusiveData(FROM.TOPIC,list,index);
            data.setStart(start);
            data.setTime(time);
            DetailVPFragment.Launcher.open((BaseActivity) Objects.requireNonNull(getActivity()), data, null);
        });
        topic_rec.setAdapter(adapter);

        topic_rec.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int firstVisibleItem, lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
                //大于0说明有播放
                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals(adapter.VIDEO_TAG)
                            && (position < firstVisibleItem || position > lastVisibleItem)) {
                        //如果滑出去了上面和下面就是否，和今日头条一样
                        if(!GSYVideoManager.isFullState(getActivity())) {
                            GSYVideoManager.releaseAllVideos();
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });

        topic_smart.setOnRefreshLoadMoreListener(this);

    }


    @Override
    public void onTopicSuccess(TopicData data) {
        if (data != null) {
            //记录上一次的位置
            start=data.getStart();
            time=data.getPoint_time();
            topic_smart.setNoMoreData(data.getMore() == 0);
            if (data.getList() != null) {
                adapter.addAriticleData(data.getList());
            }
            if (data.getBanner_list().size() > 0) {
                adapter.addBannerData(data.getBanner_list());
            }
        } else {
            showToast("数据异常");
        }
    }

    @Override
    public void onTopicTabFail(String msg) {

    }

    @Override
    public TopicContract.ITopicPresnter createPresenter() {
        return new TopicPresenter();
    }

    @Override
    public boolean isNeedAnimation() {

        return false;
    }

    @Override
    public boolean isNeedAddToBackStack() {

        return false;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        topic_smart.finishLoadMore(2000);       //2s加载结束
        mPresenter.getTopic(start,time);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        topic_smart.finishRefresh(2000);    //2s刷新结束
        start = 0;
        time = 0;
        adapter.mBannerlist.clear();
        adapter.mlist.clear();
        mPresenter.getTopic(start,time);
    }
}
