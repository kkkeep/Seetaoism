package com.seetaoism.home.recommend;

import com.mr.k.mvp.base.BasePresenter;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.data.entity.NewsColumnData;
import com.seetaoism.data.repositories.RecommendRepository;


public class RecommendPresenter extends BasePresenter<RecommendContract.IRecommendView> implements RecommendContract.IRecommendPresenter {

    private RecommendContract.IRecommendModel mMode;


    public RecommendPresenter() {
        this.mMode = new RecommendRepository();
    }

    @Override
    public void getNewsColumn() {
            mMode.getNewsColumn(getLifecycleProvider(), new IBaseCallBack<NewsColumnData>() {
                @Override
                public void onSuccess(NewsColumnData data) {
                    if(mView != null){
                        mView.onNewsColumnSuccess(data);
                    }
                }

                @Override
                public void onFail(ResultException e) {
                    if(mView != null){
                        mView.onNewsColumnFail(e.getMessage());
                    }
                }
            });
    }
}
