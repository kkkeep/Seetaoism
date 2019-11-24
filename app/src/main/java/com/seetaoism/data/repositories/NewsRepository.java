package com.seetaoism.data.repositories;

import com.mr.k.mvp.base.BaseRepository;
import com.mr.k.mvp.base.ICacheBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.AppConstant;
import com.seetaoism.data.entity.HttpResult;
import com.seetaoism.data.entity.NewsData;
import com.seetaoism.data.okhttp.JDDataService;
import com.seetaoism.home.recommend.RecommendContract;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


public class NewsRepository extends BaseRepository implements RecommendContract.INewsPageModel {

    private static volatile NewsRepository mInstance;


    private Map<String, NewsData> mMemoryCache;

    private NewsRepository(){
        mMemoryCache = new HashMap<>();
    }


    public static NewsRepository getInstance(){
        if(mInstance == null){
            synchronized (NewsRepository.class){
                if(mInstance == null){
                    mInstance = new NewsRepository();
                }
            }
        }

        return mInstance;
    }


    @Override
    public void getNewsData(LifecycleProvider provider, final Map<String, String> params, @RecommendContract.RequestType final int requestType, ICacheBaseCallBack<NewsData> callBack) {

        if(requestType == RecommendContract.INewsPageModel.REQUEST_TYPE_FIRST_LOAD){
            NewsData data = mMemoryCache.get(params.get(AppConstant.RequestParamsKey.COLUMN_ID));
            if(data != null){
                callBack.onMemoryCacheBack(data);
                return;
            }

        }






        observer(provider, JDDataService.service().getNewsData(params), new Function<HttpResult<NewsData>, ObservableSource<NewsData>>() {
            @Override
            public ObservableSource<NewsData> apply(HttpResult<NewsData> newsDataHttpResult) throws Exception {
                if(newsDataHttpResult.code == 1 && newsDataHttpResult.data != null){
                    cacheToMemory(requestType, newsDataHttpResult.data,params.get(AppConstant.RequestParamsKey.COLUMN_ID));
                    return Observable.just(newsDataHttpResult.data);
                }
                return Observable.error(new ResultException(newsDataHttpResult.code, newsDataHttpResult.message));
            }
        },callBack);
    }




    private void cacheToMemory(@RecommendContract.RequestType int requestType,NewsData data,String key){


        if(requestType != RecommendContract.INewsPageModel.REQUEST_TYPE_MORE_LOAD){
            mMemoryCache.remove(key);
            NewsData newsData = new NewsData();

            newsData.setStart(data.getStart());
            newsData.setVideoStart(data.getVideoStart());
            newsData.setMore(data.getMore());

            newsData.setBannerList(data.getBannerList());
            newsData.setFlashList(data.getFlashList());
            newsData.setArticleList(data.getArticleList());
            mMemoryCache.put(key,newsData);
        }else{
            NewsData newsData = mMemoryCache.get(key);

            newsData.setStart(data.getStart());
            newsData.setVideoStart(data.getVideoStart());
            newsData.setMore(data.getMore());

            List<NewsData.News> arrayList = newsData.getArticleList();

            arrayList.addAll(data.getArticleList());
        }

    }


    public static void destroy(){
        if(mInstance != null){
            mInstance.mMemoryCache.clear();
            mInstance.mMemoryCache = null;
            mInstance = null;
        }
    }



}
