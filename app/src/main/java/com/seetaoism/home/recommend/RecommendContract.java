package com.seetaoism.home.recommend;

import androidx.annotation.IntDef;

import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.base.IBasePresenter;
import com.mr.k.mvp.base.IBaseView;
import com.mr.k.mvp.base.ICacheBaseCallBack;
import com.seetaoism.data.entity.NewsColumnData;
import com.seetaoism.data.entity.NewsData;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;


public interface RecommendContract {


    public interface IRecommendView extends IBaseView<IRecommendPresenter> {

        void onNewsColumnSuccess(NewsColumnData data);

        void onNewsColumnFail(String msg);
    }

    public interface IRecommendPresenter extends IBasePresenter<IRecommendView> {
        void getNewsColumn();
    }

    public interface IRecommendModel {

        void getNewsColumn(LifecycleProvider provider, IBaseCallBack<NewsColumnData> callBack);
    }


    public interface INewsPageView extends IBaseView<INewsPagePresenter> {

        void onServerResult(NewsData data, int responseType, String msg);

        void onMemoryCacheResult(NewsData data);

        void onDiskCacheResult(NewsData data);
    }

    public interface INewsPagePresenter extends IBasePresenter<INewsPageView> {


        void getNewsData(String id, int videoStart, int newsStart, int requestType);

    }


    public interface INewsPageModel {

        int REQUEST_TYPE_FIRST_LOAD = 1;
        int REQUEST_TYPE_REFRESH_LOAD = 2;
        int REQUEST_TYPE_MORE_LOAD = 3;

        void getNewsData(LifecycleProvider provider, Map<String, String> params, @RequestType int requestType, ICacheBaseCallBack<NewsData> callBack);

    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({INewsPageModel.REQUEST_TYPE_FIRST_LOAD, INewsPageModel.REQUEST_TYPE_REFRESH_LOAD, INewsPageModel.REQUEST_TYPE_MORE_LOAD})
    @interface RequestType {
    }

}
