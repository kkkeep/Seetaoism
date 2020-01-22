package com.seetaoism.data.repositories;


import com.mr.k.mvp.base.BaseRepository;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.mr.k.mvp.utils.DataCacheUtils;
import com.mr.k.mvp.utils.SPUtils;
import com.seetaoism.AppConstant;
import com.seetaoism.data.entity.Ad;
import com.seetaoism.data.entity.HttpResult;
import com.seetaoism.data.entity.NewsColumnData;
import com.seetaoism.data.okhttp.JDDataService;
import com.seetaoism.home.recommend.RecommendContract;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


public class RecommendRepository extends BaseRepository implements RecommendContract.IRecommendModel {


    @Override
    public void getNewsColumn(LifecycleProvider provider, IBaseCallBack<NewsColumnData> callBack) {

        observer(provider, JDDataService.service().getNewsColumnList(new HashMap<String, String>()), new Function<HttpResult<NewsColumnData>, ObservableSource<NewsColumnData>>() {
            @Override
            public ObservableSource<NewsColumnData> apply(HttpResult<NewsColumnData> newsColumnDataHttpResult) throws Exception {
                if(newsColumnDataHttpResult.code == 1 && newsColumnDataHttpResult.data != null){

                    SPUtils.saveValueToDefaultSpByCommit(AppConstant.SPKeys.COLUMNS, DataCacheUtils.convertToJsonFromObject(newsColumnDataHttpResult.data));

                    return Observable.just(newsColumnDataHttpResult.data);
                }
                return Observable.error(new ResultException(ResultException.SERVER_ERROR));
            }
        }, callBack);
    }

    @Override
    public void uploadColumn(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<String> callBack) {

        observer(provider, JDDataService.service().uploadColumn(params), new Function<HttpResult<String>, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(HttpResult<String> stringHttpResult) throws Exception {
                if(stringHttpResult.code == 1){
                   return  Observable.just(stringHttpResult.data);
                }
                return Observable.error(new ResultException(stringHttpResult.message));
            }
        },callBack);
    }
    @Override
    public void getSplashAd(LifecycleProvider provider, IBaseCallBack<List<Ad>> callBack) {
        observer(provider, JDDataService.service().getSplashAd(), listHttpResult -> {
            if(listHttpResult.code == 1 && listHttpResult.data != null && listHttpResult.data.size() > 0){
                return Observable.just(listHttpResult.data);
            }else{
                return Observable.error(new ResultException(listHttpResult.message));
            }
        }, callBack);
    }
}
