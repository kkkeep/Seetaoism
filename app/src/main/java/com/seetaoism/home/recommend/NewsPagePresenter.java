package com.seetaoism.home.recommend;

import com.mr.k.mvp.base.BasePresenter;
import com.mr.k.mvp.base.ICacheBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.AppConstant;
import com.seetaoism.data.entity.NewsData;
import com.seetaoism.home.recommend.RecommendContract.INewsPageModel;
import com.seetaoism.home.recommend.RecommendContract.RequestType;

import java.util.HashMap;


public class NewsPagePresenter extends BasePresenter<RecommendContract.INewsPageView> implements RecommendContract.INewsPagePresenter {

    private INewsPageModel mNewsMode;

    public NewsPagePresenter(INewsPageModel newsMode) {
        mNewsMode  = newsMode;
    }

    @Override
    public void getNewsData(String id, long pointTime, int newsStart, @RequestType final int requestType) {

        HashMap<String,String> params = new HashMap<>();
        params.put(AppConstant.RequestParamsKey.COLUMN_ID,id);
        params.put(AppConstant.RequestParamsKey.NEWS_START,String.valueOf(newsStart));
        params.put(AppConstant.RequestParamsKey.NEWS_POINT_TIME,String.valueOf(pointTime));

        mNewsMode.getNewsData(getLifecycleProvider(), params, requestType, new ICacheBaseCallBack<NewsData>() {
            @Override
            public void onMemoryCacheBack(NewsData data) {
                if(mView != null){
                    mView.onMemoryCacheResult(data);
                }
            }

            @Override
            public void onDiskCacheBack(NewsData data) {

            }

            @Override
            public void onSuccess(NewsData data) {
                if(mView != null){
                    mView.onServerResult(data,requestType,null);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if(mView != null){
                    mView.onServerResult(null,requestType,e.getMessage());
                }
            }
        });

    }
}
