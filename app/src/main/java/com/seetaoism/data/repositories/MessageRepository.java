package com.seetaoism.data.repositories;

import com.mr.k.mvp.base.BaseRepository;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.data.entity.HttpResult;
import com.seetaoism.data.entity.MessageData;
import com.seetaoism.data.entity.User;
import com.seetaoism.data.okhttp.JDDataService;
import com.seetaoism.home.message.MessageContract;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class MessageRepository  extends BaseRepository implements MessageContract.MessageModel {
    @Override
    public void getMessage(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<MessageData> callBack) {
        observer(provider, JDDataService.service().notice(params), new Function<HttpResult<MessageData>, ObservableSource<MessageData>>() {
            @Override
            public ObservableSource<MessageData> apply(HttpResult<MessageData> newsColumnDataHttpResult) throws Exception {
                if (newsColumnDataHttpResult.code == 1 && newsColumnDataHttpResult.data != null) {
                    return Observable.just(newsColumnDataHttpResult.data);
                }
                return Observable.error(new ResultException(ResultException.SERVER_ERROR));
            }
        }, callBack);
    }
}
