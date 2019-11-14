package com.seetaoism.home.message;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.seetaoism.R;
import com.seetaoism.base.JDMvpBaseActivity;
import com.seetaoism.data.entity.MessageData;
import com.shehuan.niv.NiceImageView;

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
    public MessageContract.MessagePresenter createPresenter() {
        return null;
    }
}
