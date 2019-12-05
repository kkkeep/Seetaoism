package com.seetaoism.home.message;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mr.k.mvp.UserManager;
import com.mr.k.mvp.utils.SystemFacade;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.seetaoism.R;
import com.seetaoism.base.JDMvpBaseActivity;
import com.seetaoism.data.entity.MessageData;
import com.seetaoism.data.entity.NoticedetailsBean;
import com.seetaoism.data.entity.User;
import com.seetaoism.data.entity.VideoData;
import com.seetaoism.libloadingview.LoadingView;
import com.shehuan.niv.NiceImageView;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MessageActivity extends JDMvpBaseActivity<MessageContract.MessagePresenter> implements MessageContract.MessageView, View.OnClickListener {

    private static final int START_DETAIL_REQUEST_CODE = 0X100;
    public static final int START_DETAIL_RESULT_CODE = 0X101;
    private NiceImageView mClose;
    private TextView mBianji;
    private Toolbar mToolbar;
    private RecyclerView mMessageRec;
    private SmartRefreshLayout mMessageSm;
    private MessageAdapter adapter;
    private CheckBox btnAllSelect;
    private TextView btnDelete;
    private boolean isSelectAll = false;
    //首先定义一个变量
    private static final int CHECK_GONE = 0;     //这个是表示隐藏
    private static final int CHECK_VISIBLE = 1;      //这个是表示显示
    private int mEditMode = CHECK_GONE;
    private RelativeLayout pop;
    private int more = 1;//默认可加载更多
    private int start = 0;
    private int time = 0;
    private RelativeLayout kong;

    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {
        mClose = findViewById(R.id.close);
        mBianji = findViewById(R.id.bianji);
        mToolbar = findViewById(R.id.toolbar);
        mMessageRec = findViewById(R.id.message_rec);
        mMessageSm = findViewById(R.id.message_sm);
        pop = findViewById(R.id.pop);
        btnAllSelect = findViewById(R.id.btn_allSelect);
        btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setEnabled(false);
        kong = findViewById(R.id.kong);

        mBianji.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        mClose.setOnClickListener(this);

        mPresenter.getMessageList(start, time);

        mMessageRec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new MessageAdapter(this);
        mMessageRec.setAdapter(adapter);

        mMessageSm.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mMessageSm.finishLoadMore(2000);       //2s加载结束
                if (more==1) {
                    mPresenter.getMessageList(start, time);
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mMessageSm.finishRefresh(2000);    //2s刷新结束
                start = 0;
                time = 0;
                adapter.mList.clear();
                mPresenter.getMessageList(start, time);
            }
        });


        adapter.setonItemClickListener(new MessageAdapter.onItemClickListener() {
            @Override
            public void setonItemClickListener(View view, int position) {
                CheckBox cb = view.findViewById(R.id.cb_select);
                int visibility = cb.getVisibility();
                if (View.VISIBLE == visibility) {
                    List<MessageData.MessageList> selectedList = adapter.getSelectedNews();
                    if (SystemFacade.isListEmpty(selectedList)) {
                        btnAllSelect.setText("全选");
                        setBtnBackground(0);
                    } else {
                        btnAllSelect.setText("取消全选");
                        setBtnBackground(selectedList.size());
                    }
                } else {
                    if (adapter.mList.get(position).getNotice_status() == 1) {

                        Intent intent = new Intent(MessageActivity.this, MessagedetailsActivity.class);
                        intent.putExtra("id", adapter.mList.get(position).getId());
                        MessageData.MessageList messageList =   adapter.mList.get(position);
                        if(messageList.getIs_read() == 0){
                            User user = (User) UserManager.getUser();
                            user.getUserInfo().setNotice_count(Math.max(0,user.getUserInfo().getNotice_count() -1));
                            UserManager.updateUserInfo();
                        }
                        adapter.mList.get(position).setIs_read(1);
                        ImageView tag = view.findViewById(R.id.tag);
                        tag.setVisibility(View.INVISIBLE);
                        startActivityForResult(intent,START_DETAIL_REQUEST_CODE);
                    } else {
                        showToast("相关数据已删除");
                    }
                }
            }
        });

        //全选多选框监听事件
        btnAllSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //如果选中则全选
                    if (adapter == null) {
                        return;
                    }

                    int index = 0;
                    if (!isSelectAll) {

                        for (int i = 0, j = adapter.mList.size(); i < j; i++) {

                            adapter.mList.get(i).setSelect(true);
                        }
                        index = adapter.mList.size();

                        btnAllSelect.setText("取消全选");
                        isSelectAll = true;
                    }

                    adapter.notifyDataSetChanged();
                    setBtnBackground(index);
                } else {
                    //如果没选中则反选
                    if (adapter == null) {
                        return;
                    }

                    int index = 0;
                    if (isSelectAll) {

                        for (int i = 0, j = adapter.mList.size(); i < j; i++) {
                            adapter.mList.get(i).setSelect(false);
                        }
                        index = 0;
                        btnAllSelect.setText("全选");
                        isSelectAll = false;
                    }
                    adapter.notifyDataSetChanged();
                    setBtnBackground(index);
                }
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == START_DETAIL_REQUEST_CODE && resultCode == START_DETAIL_RESULT_CODE){
            int id = data.getIntExtra("msgid", -1);
            if(id != -1 && adapter != null){
                adapter.setNoticeStatus(id, 0);
            }
        }

    }

    @Override
    public void MessageSucceed(MessageData data) {
        if (data.getList().size() > 0) {
            adapter.setData(data.getList());
            more = data.getMore();
            time = data.getPoint_time();
            start = data.getStart();
            kong.setVisibility(View.GONE);
            mMessageSm.setVisibility(View.VISIBLE);

        } else {
            mMessageSm.setVisibility(View.GONE);
            kong.setVisibility(View.VISIBLE);
            mBianji.setVisibility(View.GONE);
        }

    }

    @Override
    public void MessageFail(String s) {
        mMessageSm.setVisibility(View.GONE);
        kong.setVisibility(View.VISIBLE);
        mBianji.setVisibility(View.GONE);

    }

    @Override
    public void MessagedetailsSucceed(NoticedetailsBean data) {

    }

    @Override
    public void MessagedetailsFail(String s) {

    }

    @Override
    public void MeaasgeDeleteSucceed(String msg) {
        showToast("删除成功");
        closeLoading();
        adapter.deleteSuccess();
    }

    @Override
    public void MeaasgeDeleteFail(String msg) {

    }

    @Override
    public void MessagedetailsDeleteSucceed(String s) {

    }

    @Override
    public void MessagedetailsDeleteFail(String s) {

    }

    @Override
    public void MessagedetailsreplyDeleteSucceed(String s) {

    }

    @Override
    public void MessagedetailsreplyDeleteFail(String s) {

    }

    @Override
    public void ArticledeleteSucceed(String s) {

    }

    @Override
    public void ArticledeleteFail(String s) {

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
                compile();
                break;
            case R.id.btn_delete:
                deleteVideo();
                break;
        }
    }

    private void compile() {
        int i = mEditMode == CHECK_GONE ? CHECK_VISIBLE : CHECK_GONE;
        mEditMode = i;
        if (mEditMode == CHECK_VISIBLE) {
            //这个判断是true，所以把编辑二字改成了取消
            mBianji.setText("取消");
            pop.setVisibility(View.VISIBLE);
        } else {
            mBianji.setText("编辑");
            pop.setVisibility(View.GONE);
            isSelectAll = true;
            selectAllMain();
            clearAll();
        }
        //控制多选框的显示隐藏
        adapter.setEditMode(mEditMode);
    }


    private void clearAll() {
        btnAllSelect.setText("全选");
        setBtnBackground(0);
    }

    /**
     * 删除逻辑
     */
    private void deleteVideo() {

        List<MessageData.MessageList> mList = adapter.mList;

        int index = mList.size();

        if (index == 0) {
            return;
        }
        final AlertDialog builder = new AlertDialog.Builder(this)
                .create();
        builder.show();
        if (builder.getWindow() == null) {
            return;
        }
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
        TextView msg = (TextView) builder.findViewById(R.id.tv_msg);
        Button cancle = (Button) builder.findViewById(R.id.btn_cancle);
        Button sure = (Button) builder.findViewById(R.id.btn_sure);
        if (msg == null || cancle == null || sure == null) {
            return;
        }

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuffer sb = new StringBuffer();

                for (MessageData.MessageList messageList : mList) {
                    sb.append(messageList.getId()).append(",");

                }

                sb.delete(sb.length() - 1, sb.length());

                mPresenter.getMeaasgeDelete(sb.toString());

                showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG);
                builder.dismiss();

            }


        });
    }

    /**
     * 全选和反选
     */
    private void selectAllMain() {
        if (adapter == null) {
            return;
        }

        int index = 0;
        if (!isSelectAll) {
            for (int i = 0, j = adapter.mList.size(); i < j; i++) {
                adapter.mList.get(i).setSelect(true);
            }
            index = adapter.mList.size();

            btnAllSelect.setText("取消全选");
            isSelectAll = true;
        } else {

            for (int i = 0, j = adapter.mList.size(); i < j; i++) {
                adapter.mList.get(i).setSelect(false);
            }
            index = 0;
            btnAllSelect.setText("全选");
            isSelectAll = false;
        }
        adapter.notifyDataSetChanged();
        setBtnBackground(index);
    }


    /**
     * 控制删除按钮是否可点击
     *
     * @param size
     */
    private void setBtnBackground(int size) {
        if (size != 0) {
            btnDelete.setEnabled(true);
            btnDelete.setTextColor(Color.BLACK);
        } else {
            btnDelete.setEnabled(false);
            btnDelete.setTextColor(Color.GRAY);
        }
    }

}

