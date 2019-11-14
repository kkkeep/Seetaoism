package com.seetaoism.home.topic;

import com.mr.k.mvp.base.IBasePresenter;
import com.mr.k.mvp.base.IBaseView;
import com.mr.k.mvp.base.ICacheBaseCallBack;
import com.seetaoism.data.entity.TopicData;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

public interface TopicContract {

    public interface ITopicView extends IBaseView<ITopicPresnter> {


        void onTopicSuccess(TopicData data);

        void onTopicTabFail(String msg);

    }

    public interface ITopicPresnter extends IBasePresenter<ITopicView> {
        void getTopic(int start, int point_time);
    }

    public interface ITopModel {


        void getTopData(LifecycleProvider provider, Map<String, String> params, ICacheBaseCallBack<TopicData> callBack);
    }


}
