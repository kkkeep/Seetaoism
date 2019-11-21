package com.seetaoism.home.video;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mr.k.mvp.base.MvpBaseFragment;
import com.mr.k.mvp.kotlin.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.seetaoism.AppConstant;
import com.seetaoism.R;
import com.seetaoism.data.entity.DetailExclusiveData;
import com.seetaoism.data.entity.FROM;
import com.seetaoism.data.entity.NewsData;
import com.seetaoism.data.entity.VideoData;
import com.seetaoism.home.detail.vp.DetailVPFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class VideoFragment extends MvpBaseFragment<VideoContract.VideoPresenter> implements VideoContract.VideoView, View.OnClickListener {
    private RecyclerView video_rec;
    private SmartRefreshLayout video_smart;
    private int more = 1;//默认可加载更多
    private int start = 0;
    private int time = 0;
    public ArrayList<VideoData.NewList> mlist = new ArrayList<>();
    private VideoAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }


    @Override
    protected void initData() {
        mPresenter.video(start,time);
    }

    @Override
    protected void initView(View root) {
        video_rec = root.findViewById(R.id.video_rec);
        video_smart = root.findViewById(R.id.video_smart);
        video_rec.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new VideoAdapter(mlist, getContext());
        video_rec.setAdapter(adapter);

        video_smart.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                video_smart.finishLoadMore(2000);       //2s加载结束
                Log.d(TAG, "onLoadMore: "+start+"----"+time);
                mPresenter.video(start,time);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                video_smart.finishRefresh(2000);    //2s刷新结束
                start = 0;
                time = 0;
                adapter.mlist.clear();
                mPresenter.video(start,time);
            }
        });

        adapter.setOnItemClickListener((list, position) ->{

            DetailExclusiveData data = new DetailExclusiveData(FROM.VIDEO,list,position);
            data.setStart(start);
            data.setTime(time);
            DetailVPFragment.Launcher.open((BaseActivity) Objects.requireNonNull(getActivity()), data, null);
        });
    }

    @Override
    public VideoContract.VideoPresenter createPresenter() {
        return new VidePresenter();
    }

    @Override
    public void onVideoCacheResult(VideoData data) {

    }

    @Override
    public void onVideoSuccess(VideoData data, String msg) {
        if (data!=null&&data.getList().size()>0){
            start = data.getStart();
            more=data.getMore();
            time = data.getPoint_time();
            adapter.addData(data.getList());
            //如果返回0就是没有更多数据
            if (more == 0) {
                video_smart.setNoMoreData(true);
            } else {
                video_smart.setNoMoreData(false);
            }
        }else {
           showToast(msg);
        }



    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    public boolean isNeedAnimation() {

        return false;
    }

    @Override
    public boolean isNeedAddToBackStack() {

        return false;
    }
}
