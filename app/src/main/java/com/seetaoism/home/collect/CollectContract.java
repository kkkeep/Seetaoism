package com.seetaoism.home.collect;

import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.base.IBasePresenter;
import com.mr.k.mvp.base.IBaseView;

import com.seetaoism.data.entity.VideoData;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

public interface CollectContract {

    public interface ICollectView extends IBaseView<ICollectPresenter> {
        void onICollectSucceed(VideoData data);

        void onICollectFail(String s);

        void onICollectVideoSucceed(VideoData data);

        void onICollectVideoFail(String s);

        void onICollectDelete(String data);

        void onICollectDeleteFail(String data);
    }

    public interface ICollectPresenter extends IBasePresenter<ICollectView> {
        void getCollect(int start, int time);

        void getCollectVideo(int start, int time);

        void getCollectDelet(String id);
    }

    public interface ICollectModel {
        void getCollectM(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<VideoData> callBack);
        //void getCollectVideoM(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<VideoData> callBack);
        void getCollectDeleteM(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<String> callBack);

    }
}
