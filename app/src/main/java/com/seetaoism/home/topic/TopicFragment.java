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
import com.seetaoism.data.entity.TopicData;
import com.seetaoism.home.detail.vp.DetailVPFragment;

import java.util.ArrayList;
import java.util.Objects;


public class TopicFragment extends MvpBaseFragment<TopicContract.ITopicPresnter> implements TopicContract.ITopicView, OnRefreshLoadMoreListener {


    private SmartRefreshLayout topic_smart;
    private RecyclerView topic_rec;
    private int start = 0;
    private int time = 0;

    private ArrayList<TopicData.Bannerlist> mBannerlist = new ArrayList<>();
    private ArrayList<TopicData.Topiclist> mlist = new ArrayList<>();
    private TopicAdapter adapter;


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

        topic_rec.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TopicAdapter(getContext(), mBannerlist, mlist);

        adapter.setOnItemClickListener((list,index)->{
            DetailExclusiveData data = new DetailExclusiveData(FROM.TOPIC,list,index);
            data.setStart(start);
            data.setTime(time);
            DetailVPFragment.Launcher.open((BaseActivity) Objects.requireNonNull(getActivity()), data, null);
        });
        topic_rec.setAdapter(adapter);


        topic_smart.setOnRefreshLoadMoreListener(this);

    }


    @Override
    public void onTopicSuccess(TopicData data) {
        if (data != null) {
            //记录上一次的位置
            start=data.getStart();
            time=data.getPoint_time();
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
