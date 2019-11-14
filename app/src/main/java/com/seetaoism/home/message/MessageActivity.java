package com.seetaoism.home.message;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.seetaoism.R;
import com.seetaoism.base.JDMvpBaseActivity;
import com.seetaoism.data.entity.MessageData;
import com.shehuan.niv.NiceImageView;

public class MessageActivity extends JDMvpBaseActivity<MessageContract.MessagePresenter> implements MessageContract.MessageView, View.OnClickListener {


    private NiceImageView mClose;
    private TextView mBianji;
    private Toolbar mToolbar;
    private RecyclerView mMessageRec;
    private SmartRefreshLayout mMessageSm;
    private MessageAdapter adapter;

    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {
        mClose = findViewById(R.id.close);
        mBianji = findViewById(R.id.bianji);
        mToolbar = findViewById(R.id.toolbar);
        mMessageRec = findViewById(R.id.message_rec);
        mMessageSm = findViewById(R.id.message_sm);


        mClose.setOnClickListener(this);

        mPresenter.getMessageList(0, 0);

        mMessageRec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new MessageAdapter(this);
        mMessageRec.setAdapter(adapter);
        adapter.setonItemClickListener(new MessageAdapter.onItemClickListener() {
            @Override
            public void setonItemClickListener(View view, int position) {
                if (adapter.mList.get(position).getNotice_status() == 1) {
                    Intent intent = new Intent(MessageActivity.this, MessagedetailsActivity.class);
                    intent.putExtra("id", adapter.mList.get(position).getId());
                    startActivity(intent);
                } else {
                    showToast("页面异常");
                }
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    public void MessageSucceed(MessageData data) {
//        for (MessageData.MessageList messageList : data.getList()) {
//
//        }
        if (data.getList().size() > 0) {
            adapter.setData(data.getList());
        }

    }

    @Override
    public void MessageFail(String s) {
        showToast(s);
    }

    @Override
    public MessageContract.MessagePresenter createPresenter() {
        return new MessagePresenter();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                finish();
                break;
            case R.id.bianji:
                //编辑
                break;
        }
    }
}
