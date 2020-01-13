package com.seetaoism.home.video;

import com.mr.k.mvp.base.BasePresenter;
import com.mr.k.mvp.base.ICacheBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.AppConstant;
import com.seetaoism.data.entity.VideoData;
import com.seetaoism.data.repositories.VideoRepository;

import java.util.HashMap;

public class VidePresenter extends BasePresenter<VideoContract.VideoView> implements VideoContract.VideoPresenter {

    private VideoContract.VideoModel mMode;


    public VidePresenter() {
        this.mMode = new VideoRepository();
    }

    @Override
    public void video(int start, int point_time,int number) {

        HashMap<String, String> params = new HashMap<>();

        params.put(AppConstant.RequestParamsKey.VIDEOTIME, String.valueOf(point_time));
        params.put(AppConstant.RequestParamsKey.VIDEOSTART, String.valueOf(start));
        params.put(AppConstant.RequestParamsKey.NEWS_NUMBER, String.valueOf(number));


        mMode.getVideo(getLifecycleProvider(), params, new ICacheBaseCallBack<VideoData>() {
            @Override
            public void onSuccess(VideoData data) {
                if (mView!=null){
                    mView.onVideoSuccess(data,null);
                }

            }

            @Override
            public void onFail(ResultException e) {
                if (mView!=null){
                    mView.onVideoSuccess(null,e.getMessage());
                }

            }

            @Override
            public void onMemoryCacheBack(VideoData data) {
                mView.onVideoCacheResult(data);
            }

            @Override
            public void onDiskCacheBack(VideoData data) {

            }
        });

    }
}
