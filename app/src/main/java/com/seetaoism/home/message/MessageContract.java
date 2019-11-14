package com.seetaoism.home.message;

import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.base.IBasePresenter;
import com.mr.k.mvp.base.IBaseView;
import com.seetaoism.data.entity.MessageData;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

public interface MessageContract {

    public interface MessageView extends IBaseView<MessagePresenter> {

        void MessageSucceed(MessageData data);

        void MessageFail(String s);


      /*  void MessagedetailsSucceed(MessageData data);

        void MessagedetailsFail(String s);*/

    }

    public interface MessagePresenter extends IBasePresenter<MessageView> {
        void getMessageList(int start,int time);
    }

    public interface MessageModel {

        void getMessage(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<MessageData> callBack);

    }
}
