package com.seetaoism.home.topic;

import com.mr.k.mvp.base.BasePresenter;
import com.mr.k.mvp.base.ICacheBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.AppConstant;
import com.seetaoism.data.entity.TopicData;
import com.seetaoism.data.repositories.TopRepository;

import java.util.HashMap;

public class TopicPresenter extends BasePresenter<TopicContract.ITopicView> implements TopicContract.ITopicPresnter {


    private TopicContract.ITopModel mMode;


    public TopicPresenter() {
        this.mMode = new TopRepository();
    }

    @Override
    public void getTopic(int start, int point_time,int number) {
        HashMap<String, String> params = new HashMap<>();

        params.put(AppConstant.RequestParamsKey.VIDEOTIME, String.valueOf(point_time));
        params.put(AppConstant.RequestParamsKey.VIDEOSTART, String.valueOf(start));
        params.put(AppConstant.RequestParamsKey.NEWS_NUMBER, String.valueOf(number));


        mMode.getTopData(getLifecycleProvider(), params, new ICacheBaseCallBack<TopicData>() {
            @Override
            public void onSuccess(TopicData data) {
                if (mView != null) {
                    mView.onTopicSuccess(data);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if (mView != null) {
                    mView.onTopicTabFail(e.getMessage());
                }

            }

            @Override
            public void onMemoryCacheBack(TopicData data) {

            }

            @Override
            public void onDiskCacheBack(TopicData data) {

            }
        });
    }
}