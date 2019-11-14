package com.seetaoism.data.repositories;

import com.mr.k.mvp.base.BaseRepository;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.data.entity.HttpResult;
import com.seetaoism.data.entity.IntergrelData;
import com.seetaoism.data.okhttp.JDDataService;
import com.seetaoism.home.integral.IntegrelContract;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class IntegrelRepository extends BaseRepository implements IntegrelContract.IntegrelModel {
    @Override
    public void getIntegrel(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<IntergrelData> callBack) {
        observer(provider, JDDataService.service().myintegral(params), new Function<HttpResult<IntergrelData>, ObservableSource<IntergrelData>>() {
            @Override
            public ObservableSource<IntergrelData> apply(HttpResult<IntergrelData> newsColumnDataHttpResult) throws Exception {
                if (newsColumnDataHttpResult.code == 1 && newsColumnDataHttpResult.data != null) {
                    return Observable.just(newsColumnDataHttpResult.data);
                }
                return Observable.error(new ResultException(ResultException.SERVER_ERROR));
            }
        }, callBack);
    }
}
