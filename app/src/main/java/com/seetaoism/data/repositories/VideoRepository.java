package com.seetaoism.data.repositories;

import com.mr.k.mvp.base.BaseRepository;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.data.entity.HttpResult;
import com.seetaoism.data.entity.VideoData;
import com.seetaoism.data.okhttp.JDDataService;
import com.seetaoism.home.video.VideoContract;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class VideoRepository extends BaseRepository implements VideoContract.VideoModel {



    @Override
    public void getVideo(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<VideoData> callBack) {
        observer(provider, JDDataService.service().getVideoData(params), new Function<HttpResult<VideoData>, ObservableSource<VideoData>>() {
            @Override
            public ObservableSource<VideoData> apply(HttpResult<VideoData> newsColumnDataHttpResult) throws Exception {
                if (newsColumnDataHttpResult.code == 1 && newsColumnDataHttpResult.data != null) {
                    return Observable.just(newsColumnDataHttpResult.data);
                }
                return Observable.error(new ResultException(ResultException.SERVER_ERROR));
            }
        }, callBack);
    }
}
