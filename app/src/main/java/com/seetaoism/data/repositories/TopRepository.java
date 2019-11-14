package com.seetaoism.data.repositories;

import com.mr.k.mvp.base.BaseRepository;
import com.mr.k.mvp.base.ICacheBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.data.entity.HttpResult;
import com.seetaoism.data.entity.TopicData;
import com.seetaoism.data.okhttp.JDDataService;
import com.seetaoism.home.topic.TopicContract;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class TopRepository extends BaseRepository implements TopicContract.ITopModel {
    @Override
    public void getTopData(LifecycleProvider provider, Map<String, String> params, ICacheBaseCallBack<TopicData> callBack) {



        observer(provider, JDDataService.service().getTopicData(params), new Function<HttpResult<TopicData>, ObservableSource<TopicData>>() {
            @Override
            public ObservableSource<TopicData> apply(HttpResult<TopicData> newsColumnDataHttpResult) throws Exception {
                if (newsColumnDataHttpResult.code == 1 && newsColumnDataHttpResult.data != null) {

                    return Observable.just(newsColumnDataHttpResult.data);
                }
                return Observable.error(new ResultException(ResultException.SERVER_ERROR));
            }
        }, callBack);
    }
}

