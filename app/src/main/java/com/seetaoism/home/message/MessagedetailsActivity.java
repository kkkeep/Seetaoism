package com.seetaoism.home.message;

import android.content.Intent;
import android.os.Bundle;
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

public class MessagedetailsActivity extends JDMvpBaseActivity<MessageContract.MessagePresenter> implements MessageContract.MessageView {


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

        mPresenter.getMessagedetails(id);

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

    @Override
    public void MessagedetailsSucceed(NoticedetailsBean data) {
        closeLoading();
        if (data != null) {
            mList.add(data);
        }
        noticedatilsRecAdapter = new NoticedatilsRecAdapter(mList);
        mCollectAllRc.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mCollectAllRc.setAdapter(noticedatilsRecAdapter);
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

    @Override
    public MessageContract.MessagePresenter createPresenter() {
        return new MessagePresenter();
    }
}
