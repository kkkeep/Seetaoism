package com.seetaoism.home.message;

import com.mr.k.mvp.base.BasePresenter;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.AppConstant;
import com.seetaoism.data.entity.MessageData;
import com.seetaoism.data.repositories.MessageRepository;

import java.util.HashMap;


public class MessagePresenter extends BasePresenter<MessageContract.MessageView> implements MessageContract.MessagePresenter {

    private MessageContract.MessageModel mMode;


    public MessagePresenter() {
        this.mMode = new MessageRepository();
    }

    @Override
    public void getMessageList(int start, int time) {
        HashMap<String, String> params = new HashMap<>();

        params.put(AppConstant.RequestParamsKey.VIDEOSTART, String.valueOf(start));
        params.put(AppConstant.RequestParamsKey.VIDEOTIME, String.valueOf(time));

        mMode.getMessage(getLifecycleProvider(), params, new IBaseCallBack<MessageData>() {
            @Override
            public void onSuccess(MessageData data) {
                if (mView != null) {
                    mView.MessageSucceed(data);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if (mView != null) {
                    mView.MessageFail(e.getMessage());
                }
            }
        });

    }
}
