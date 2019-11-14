package com.seetaoism.data.repositories;

import com.mr.k.mvp.base.BaseRepository;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.data.entity.HttpResult;
import com.seetaoism.data.entity.VideoData;
import com.seetaoism.data.okhttp.JDDataService;
import com.seetaoism.home.collect.CollectContract;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class CollectRepository extends BaseRepository implements CollectContract.ICollectModel {
    @Override
    public void getCollectM(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<VideoData> callBack) {

        observer(provider, JDDataService.service().collect(params), new Function<HttpResult<VideoData>, ObservableSource<VideoData>>() {
            @Override
            public ObservableSource<VideoData> apply(HttpResult<VideoData> newsColumnDataHttpResult) throws Exception {
                if (newsColumnDataHttpResult.code == 1 && newsColumnDataHttpResult.data != null) {
                    return Observable.just(newsColumnDataHttpResult.data);
                }
                return Observable.error(new ResultException(ResultException.SERVER_ERROR));
            }
        }, callBack);

    }

    @Override
    public void getCollectDeleteM(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<String> callBack) {
        observer(provider, JDDataService.service().deletecollect(params), new Function<HttpResult<String>, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(HttpResult<String> newsColumnDataHttpResult) throws Exception {
                if (newsColumnDataHttpResult.code == 1 && newsColumnDataHttpResult.data != null) {
                    return Observable.just(newsColumnDataHttpResult.data);
                }
                return Observable.error(new ResultException(ResultException.SERVER_ERROR));
            }
        }, callBack);
    }


}
