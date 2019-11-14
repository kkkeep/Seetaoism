package com.seetaoism.data.repositories;

import com.mr.k.mvp.base.BaseRepository;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.data.entity.HttpResult;
import com.seetaoism.data.entity.SearchData;
import com.seetaoism.data.okhttp.JDDataService;
import com.seetaoism.home.SearchContract;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class SearchRepository extends BaseRepository implements SearchContract.ISearchModel {


    @Override
    public void getSearch(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<SearchData> callBack) {
        observer(provider, JDDataService.service().getSeachData(params), new Function<HttpResult<SearchData>, ObservableSource<SearchData>>() {
            @Override
            public ObservableSource<SearchData> apply(HttpResult<SearchData> newsColumnDataHttpResult) throws Exception {
                if (newsColumnDataHttpResult.code == 1 && newsColumnDataHttpResult.data != null) {
                    return Observable.just(newsColumnDataHttpResult.data);
                }
                return Observable.error(new ResultException(ResultException.SERVER_ERROR));
            }
        }, callBack);
    }
}
