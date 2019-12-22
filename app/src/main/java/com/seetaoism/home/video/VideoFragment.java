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
import com.seetaoism.libloadingview.LoadingView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

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
    LinearLayoutManager mLinearLayoutManager;
    public ArrayList<VideoData.NewList> mlist = new ArrayList<>();
    private VideoAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }


    @Override
    protected void initData() {
        showLoading(LoadingView.LOADING_MODE_WHITE_BG);
        mPresenter.video(start,time);
    }

    @Override
    protected void initView(View root) {
        video_rec = root.findViewById(R.id.video_rec);
        video_smart = root.findViewById(R.id.video_smart);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        video_rec.setLayoutManager(mLinearLayoutManager);
        adapter = new VideoAdapter(mlist, getContext());
        video_rec.setAdapter(adapter);

        video_rec .addOnScrollListener(new RecyclerView.OnScrollListener() {

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
                    if (GSYVideoManager.instance().getPlayTag().equals(VideoAdapter.VIDEO_TAG)
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
        closeLoading();
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
