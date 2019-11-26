package com.seetaoism.home.message;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.seetaoism.R;
import com.seetaoism.base.JDMvpBaseActivity;
import com.seetaoism.data.entity.MessageData;
import com.seetaoism.data.entity.NoticedetailsBean;
import com.shehuan.niv.NiceImageView;

import java.util.ArrayList;
import java.util.List;

public class MessagedetailsActivity extends JDMvpBaseActivity<MessageContract.MessagePresenter> implements MessageContract.MessageView, NoticedatilsRecAdapter.setOnItemCLick {


    private NiceImageView mClose;
    private Toolbar mToolbar;
    private RecyclerView mCollectAllRc;
    private SmartRefreshLayout mCollectAllSm;
    private LinearLayout mCollectList;
    private RelativeLayout mKong;
    private CheckBox mBtnAllSelect;
    private TextView mBtnDelete;
    private RelativeLayout mPop;
    private List<NoticedetailsBean> mList = new ArrayList<>();
    private NoticedatilsRecAdapter noticedatilsRecAdapter;


    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {


        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);


        mClose = findViewById(R.id.close);
        mToolbar = findViewById(R.id.toolbar);
        mCollectAllRc = findViewById(R.id.collect_all_rc);
        mCollectAllSm = findViewById(R.id.collect_all_sm);
        mCollectList = findViewById(R.id.collect_list);
        mKong = findViewById(R.id.kong);
        mBtnAllSelect = findViewById(R.id.btn_allSelect);
        mBtnDelete = findViewById(R.id.btn_delete);
        mPop = findViewById(R.id.pop);
        //我的消息详情列表
        mPresenter.getMessagedetails(id);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_messagedetails;
    }

    @Override
    public void MessageSucceed(MessageData data) {

    }

    @Override
    public void MessageFail(String s) {

    }

    public void MessagedetailsSucceed(NoticedetailsBean data) {
        closeLoading();
        if (data != null) {
            mList.add(data);
        }

        noticedatilsRecAdapter = new NoticedatilsRecAdapter(mList);
        mCollectAllRc.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mCollectAllRc.setAdapter(noticedatilsRecAdapter);
        noticedatilsRecAdapter.setOnItemCLick(this);

    }

    @Override
    public void MessagedetailsFail(String s) {
        showToast(s);
    }

    @Override
    public void MeaasgeDeleteSucceed(String msg) {

    }

    @Override
    public void MeaasgeDeleteFail(String msg) {

    }

    //删除一级评论回复
    @Override
    public void MessagedetailsDeleteSucceed(String s) {
        finish();
    }

    //删除评论
    @Override
    public void MessagedetailsDeleteFail(String s) {
        showToast(s);
    }

    //删除二级评论回复
    @Override
    public void MessagedetailsreplyDeleteSucceed(String s) {
        finish();
    }

    //回复删除
    @Override
    public void MessagedetailsreplyDeleteFail(String s) {
        showToast(s);

    }

    @Override
    public void ArticledeleteSucceed(String s) {
        finish();
    }

    @Override
    public void ArticledeleteFail(String s) {

    }

    @Override
    public MessageContract.MessagePresenter createPresenter() {
        return new MessagePresenter();
    }

    @Override
    public void setOnLinkListener(int position, NoticedetailsBean mData) {
        String fromType = mData.getFrom_type();
        String reply_type = mData.getReply_type();
        String delete_comment_reply_id = mData.getDelete_comment_reply_id();
        int id = mData.getDelete_comment_id();
        String articleId = mData.getArticle_id();


        //1系统通知，2评论被回复通知，3评论被点赞通知，4文章被点赞，5文章被评论


        if ("2".equals(fromType)) {
            if ("1".equals(reply_type)) {
                mPresenter.getMessagedetailsDelete(Integer.valueOf(delete_comment_reply_id));
            } else if ("2".equals(reply_type)) {
                mPresenter.getMessagedetailsreplyDelete(Integer.valueOf(delete_comment_reply_id));
            }
        } else if ("3".equals(fromType)) {
            mPresenter.getMessagedetailsDelete(Integer.valueOf(id));
            showToast("评论被点赞通知");
        } else if ("4".equals(fromType)) {
            mPresenter.getArticledelete(Integer.valueOf(articleId));
        } else if ("5".equals(fromType)) {
            mPresenter.getArticledelete(Integer.valueOf(articleId));
        }


    }
}
