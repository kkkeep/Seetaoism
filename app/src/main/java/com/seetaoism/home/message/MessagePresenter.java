package com.seetaoism.home.message;

import androidx.annotation.NonNull;

import com.mr.k.mvp.base.BasePresenter;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.AppConstant;
import com.seetaoism.data.entity.MessageData;
import com.seetaoism.data.entity.NoticedetailsBean;
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

    //我的消息详情
    @Override
    public void getMessagedetails(int id) {
        HashMap<String, String> params = new HashMap<>();

        params.put("id", String.valueOf(id));

        mMode.getMessagedetails(getLifecycleProvider(), params, new IBaseCallBack<NoticedetailsBean>() {
            @Override
            public void onSuccess(@NonNull NoticedetailsBean data) {
                if (mView != null) {
                    mView.MessagedetailsSucceed(data);
                }
            }

            @Override
            public void onFail(@NonNull ResultException e) {
                if (mView != null) {
                    mView.MessagedetailsFail(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getMeaasgeDelete(String id) {
        HashMap<String, String> params = new HashMap<>();

        params.put("id", id);

        mMode.getMeaasgeDelete(getLifecycleProvider(), params, new IBaseCallBack<String>() {
            @Override
            public void onSuccess(@NonNull String data) {
                if (mView != null) {
                    mView.MeaasgeDeleteSucceed(data);
                }
            }

            @Override
            public void onFail(@NonNull ResultException e) {
                if (mView != null) {
                    mView.MeaasgeDeleteFail(e.getMessage());
                }
            }
        });
    }
    //评论删除
    @Override
    public void getMessagedetailsDelete(int id) {
        HashMap<String, String> params = new HashMap<>();

        params.put("comment_id", String.valueOf(id));

        mMode.getgetMessagedetailsDelete(getLifecycleProvider(), params, new IBaseCallBack<String>() {
            @Override
            public void onSuccess(@NonNull String data) {
                if (mView!=null){
                    mView.MessagedetailsDeleteSucceed(data);
                }
            }

            @Override
            public void onFail(@NonNull ResultException e) {
                if (mView!=null){
                    mView.MessagedetailsDeleteFail(e.getMessage());
                }
            }
        });
    }
    //评论回复删除
    @Override
    public void getMessagedetailsreplyDelete(int id) {
        HashMap<String, String> params = new HashMap<>();

        params.put("reply_id", String.valueOf(id));

        mMode.getMessagedetailsreplyDelete(getLifecycleProvider(), params, new IBaseCallBack<String>() {
            @Override
            public void onSuccess(@NonNull String data) {
                if (mView!=null){
                    mView.MessagedetailsreplyDeleteSucceed(data);
                }
            }

            @Override
            public void onFail(@NonNull ResultException e) {
                if (mView!=null){
                    mView.MessagedetailsreplyDeleteFail(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getArticledelete(int id) {
        HashMap<String, String> params = new HashMap<>();

        params.put("id", String.valueOf(id));

        mMode.getArticledelete(getLifecycleProvider(), params, new IBaseCallBack<String>() {
            @Override
            public void onSuccess(@NonNull String data) {
                if (mView!=null){
                    mView.ArticledeleteSucceed(data);
                }
            }

            @Override
            public void onFail(@NonNull ResultException e) {
                if (mView!=null){
                    mView.ArticledeleteFail(e.getMessage());
                }
            }
        });
    }
}
