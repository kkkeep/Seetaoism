package com.seetaoism.home.collect;

import com.mr.k.mvp.base.BasePresenter;
import com.mr.k.mvp.base.ICacheBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.AppConstant;
import com.seetaoism.data.entity.VideoData;
import com.seetaoism.data.repositories.CollectRepository;

import java.util.HashMap;

public class CollectPresenter extends BasePresenter<CollectContract.ICollectView> implements CollectContract.ICollectPresenter {

    private CollectContract.ICollectModel collectModel;

    public CollectPresenter() {

        collectModel = new CollectRepository();
    }

    //全部
    @Override
    public void getCollect(int start, int time) {
        HashMap<String, String> params = new HashMap<>();

        params.put(AppConstant.RequestParamsKey.VIDEOTIME, String.valueOf(time));
        params.put(AppConstant.RequestParamsKey.VIDEOSTART, String.valueOf(start));
        params.put(AppConstant.RequestParamsKey.SMS_CODE_TYPE, CollectActivity.COLLECTALL);


        collectModel.getCollectM(getLifecycleProvider(), params, new ICacheBaseCallBack<VideoData>() {
            @Override
            public void onSuccess(VideoData data) {

                if (mView != null) {
                    mView.onICollectSucceed(data);
                }
            }

            @Override
            public void onFail(ResultException e) {

                if (mView != null) {
                    mView.onICollectFail(e.getMessage());
                }
            }

            @Override
            public void onMemoryCacheBack(VideoData data) {

            }

            @Override
            public void onDiskCacheBack(VideoData data) {

            }
        });
    }


    //视频
    @Override
    public void getCollectVideo(int start, int time) {
        HashMap<String, String> params = new HashMap<>();

        params.put(AppConstant.RequestParamsKey.VIDEOTIME, String.valueOf(time));
        params.put(AppConstant.RequestParamsKey.VIDEOSTART, String.valueOf(start));
        params.put(AppConstant.RequestParamsKey.SMS_CODE_TYPE, CollectActivity.COLLECT_VIDEO);


        collectModel.getCollectM(getLifecycleProvider(), params, new ICacheBaseCallBack<VideoData>() {
            @Override
            public void onSuccess(VideoData data) {
                if (mView != null) {
                    mView.onICollectVideoSucceed(data);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if (mView != null) {
                    mView.onICollectVideoFail(e.getMessage());
                }
            }

            @Override
            public void onMemoryCacheBack(VideoData data) {

            }

            @Override
            public void onDiskCacheBack(VideoData data) {

            }
        });
    }

    @Override
    public void getCollectDelet(String id) {
        HashMap<String, String> params = new HashMap<>();
        params.put(AppConstant.RequestParamsKey.DETAIL_ARTICLE_ID, id);
        collectModel.getCollectDeleteM(getLifecycleProvider(), params, new ICacheBaseCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                if (mView != null) {
                    mView.onICollectDelete(data);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if (mView != null) {
                    mView.onICollectVideoFail(e.getMessage());
                }
            }

            @Override
            public void onMemoryCacheBack(String data) {

            }

            @Override
            public void onDiskCacheBack(String data) {

            }
        });
    }
}
