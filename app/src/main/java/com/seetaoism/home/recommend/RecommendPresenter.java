package com.seetaoism.home.recommend;

import androidx.annotation.NonNull;

import com.mr.k.mvp.base.BasePresenter;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.mr.k.mvp.utils.MapBuilder;
import com.seetaoism.AppConstant;
import com.seetaoism.data.entity.NewsColumn;
import com.seetaoism.data.entity.NewsColumnData;
import com.seetaoism.data.repositories.RecommendRepository;

import java.util.List;
import java.util.Map;


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

    @Override
    public void uploadColumn(List<NewsColumn> newsColumns) {

        StringBuilder ids = new StringBuilder();
        StringBuilder sorts = new StringBuilder();

        for(int i = 0 ; i < newsColumns.size(); i++){
            ids.append(newsColumns.get(i).getId());
            sorts.append(i);
            if(i != newsColumns.size() -1){
                ids.append(",");
                sorts.append(",");
            }
        }

        Map<String,String> params = new MapBuilder<String,String>().put(AppConstant.RequestParamsKey.COLUMN_IDS, ids.toString())
                .put(AppConstant.RequestParamsKey.COLUMN_SORT, sorts.toString()).builder();

        mMode.uploadColumn(getLifecycleProvider(),params , new IBaseCallBack<String>() {
            @Override
            public void onSuccess(@NonNull String data) {

            }

            @Override
            public void onFail(@NonNull ResultException e) {

            }
        });
    }
}
