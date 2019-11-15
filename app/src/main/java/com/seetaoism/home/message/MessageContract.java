package com.seetaoism.home.message;

import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.base.IBasePresenter;
import com.mr.k.mvp.base.IBaseView;
import com.seetaoism.data.entity.MessageData;
import com.seetaoism.data.entity.NoticedetailsBean;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

public interface MessageContract {

    public interface MessageView extends IBaseView<MessagePresenter> {

        void MessageSucceed(MessageData data);

        void MessageFail(String s);

        //我的消息详情
        void MessagedetailsSucceed(NoticedetailsBean data);

        void MessagedetailsFail(String s);

        //编辑删除消息
        void MeaasgeDeleteSucceed(String msg);

        void MeaasgeDeleteFail(String msg);

    }

    public interface MessagePresenter extends IBasePresenter<MessageView> {
        void getMessageList(int start,int time);

        //我的消息详情
        void getMessagedetails(int id);

        //编辑删除消息
        void getMeaasgeDelete(String id);
    }

    public interface MessageModel {

        void getMessage(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<MessageData> callBack);

        //我的消息详情
        void getMessagedetails(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<NoticedetailsBean> callBack);

        //编辑删除消息
        void getMeaasgeDelete(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<String> callBack);

    }
}
