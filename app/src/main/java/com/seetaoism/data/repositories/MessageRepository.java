package com.seetaoism.data.repositories;

import com.mr.k.mvp.base.BaseRepository;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.data.entity.HttpResult;
import com.seetaoism.data.entity.MessageData;
import com.seetaoism.data.entity.NoticedetailsBean;
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

    //我的消息详情
    @Override
    public void getMessagedetails(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<NoticedetailsBean> callBack) {
        observer(provider, JDDataService.service().noticedetails(params), new Function<HttpResult<NoticedetailsBean>, ObservableSource<NoticedetailsBean>>() {
            @Override
            public ObservableSource<NoticedetailsBean> apply(HttpResult<NoticedetailsBean> newsColumnDataHttpResult) throws Exception {
                if (newsColumnDataHttpResult.code == 1 && newsColumnDataHttpResult.data != null) {
                    return Observable.just(newsColumnDataHttpResult.data);
                }
                return Observable.error(new ResultException(ResultException.SERVER_ERROR));
            }
        }, callBack);
    }
    //编辑删除
    @Override
    public void getMeaasgeDelete(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<String> callBack) {
        observer(provider, JDDataService.service().deletenotice(params), new Function<HttpResult<String>, ObservableSource<String>>() {
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
