package com.seetaoism.home.collect;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mr.k.mvp.base.MvpBaseFragment;
import com.mr.k.mvp.kotlin.base.BaseActivity;
import com.mr.k.mvp.utils.SystemFacade;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.seetaoism.R;
import com.seetaoism.data.entity.DetailExclusiveData;
import com.seetaoism.data.entity.FROM;
import com.seetaoism.data.entity.NewsData;
import com.seetaoism.data.entity.VideoData;
import com.seetaoism.home.detail.vp.DetailVPFragment;
import com.seetaoism.libloadingview.LoadingView;

import java.util.ArrayList;
import java.util.List;

public class CollectVideoFragment extends MvpBaseFragment<CollectContract.ICollectPresenter> implements CollectContract.ICollectView, View.OnClickListener {

    private static final String TAG = "CollectVideoFragment";
    private RecyclerView collect_all_rc;
    private SmartRefreshLayout collect_all_sm;
    ArrayList<VideoData.NewList> newLists = new ArrayList<>();
    private CollectAllAdapter allAdapter;
    private RelativeLayout kong;
    private LinearLayout collect_list;
    private CollectActivity mCollectActivity;
    private CheckBox btnAllSelect;
    private TextView btnDelete;
    private boolean isSelectAll = false;
    private int mEditMode;

    private CollectActivity CollectVideoFragment;
    private RelativeLayout pop;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof CollectActivity) {
            mCollectActivity = (CollectActivity) activity;
        }
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getCollectVideo(0,0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collect_video;
    }

    @Override
    protected void initView(View root) {
        collect_all_rc = root.findViewById(R.id.collect_all_rc);
        collect_all_sm = root.findViewById(R.id.collect_all_sm);
        btnAllSelect = root.findViewById(R.id.btn_allSelect);
        btnDelete = root.findViewById(R.id.btn_delete);
        kong = root.findViewById(R.id.kong);
        collect_list = root.findViewById(R.id.collect_list);
        pop = root.findViewById(R.id.pop);

        btnAllSelect.setOnClickListener(this);
        btnDelete.setOnClickListener(this);


        allAdapter = new CollectAllAdapter(newLists, getContext());
        collect_all_rc.setLayoutManager(new LinearLayoutManager(getContext()));
        collect_all_rc.setAdapter(allAdapter);

        //全选多选框监听事件
        btnAllSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //如果选中则全选
                    if (allAdapter == null) {
                        return;
                    }

                    int index = 0;
                    if (!isSelectAll) {

                        for (int i = 0, j = allAdapter.recyclerview_item_collect.size(); i < j; i++) {

                            allAdapter.recyclerview_item_collect.get(i).setSelect(true);
                        }
                        index = allAdapter.recyclerview_item_collect.size();

                        btnAllSelect.setText("全选");
                        isSelectAll = true;
                    }

                    allAdapter.notifyDataSetChanged();
                    setBtnBackground(index);
                } else {
                    //如果没选中则反选
                    if (allAdapter == null) {
                        return;
                    }

                    int index = 0;
                    if (isSelectAll) {
                        for (int i = 0, j = allAdapter.recyclerview_item_collect.size(); i < j; i++) {
                            allAdapter.recyclerview_item_collect.get(i).setSelect(false);
                        }
                        index = 0;
                        btnAllSelect.setText("全选");
                        isSelectAll = false;
                    }
                    allAdapter.notifyDataSetChanged();
                    setBtnBackground(index);
                }
            }
        });



        allAdapter.setOnItemClickListener((list, position) -> {
            if(mEditMode == CollectActivity.MODE_EDIT){
                List<VideoData.NewList> selectedList = allAdapter.getSelectedNews();
                if(SystemFacade.isListEmpty(selectedList)){
                    setBtnBackground(0);
                }else{
                    setBtnBackground(selectedList.size());
                }
            }else{
                DetailExclusiveData data  = new DetailExclusiveData(FROM.COLLECT,list,position);
                DetailVPFragment.Launcher.open((BaseActivity) getActivity(), data, null);
            }
        });;


        CollectViewModel collectViewModel = ViewModelProviders.of(getActivity()).get(CollectViewModel.class);

        collectViewModel.getNewsLiveData().observe(this, newsBean ->{
            allAdapter.delete(newsBean);
            if(allAdapter.getItemCount() == 0){
                onICollectVideoFail("");
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_delete:
                deleteVideo();
                break;
        }

    }

    @Override
    public void onICollectSucceed(VideoData data) {

    }

    @Override
    public void onICollectFail(String s) {

    }

    @Override
    public void onICollectVideoSucceed(VideoData data) {
        if (data.getList().size()>0){
            newLists.addAll(data.getList());
            allAdapter.setData(newLists);
            kong.setVisibility(View.GONE);
        }else {
            kong.setVisibility(View.VISIBLE);
            collect_list.setVisibility(View.GONE);
        }

    }

    @Override
    public void onICollectVideoFail(String s) {
        kong.setVisibility(View.VISIBLE);
        collect_list.setVisibility(View.GONE);
    }

    @Override
    public void onICollectDelete(String data) {
        showToast("删除成功");
        closeLoading();

        allAdapter.deleteSuccess();

        if(allAdapter.getItemCount() == 0){
            onICollectVideoFail("");
        }
    }

    @Override
    public void onICollectDeleteFail(String data) {
        showToast("删除失败");
        closeLoading();
    }

    @Override
    public CollectContract.ICollectPresenter createPresenter() {
        return new CollectPresenter();
    }

    public void chooseNews(boolean isSelect, int editMode) {
        mEditMode = editMode;
        //控制全选删除按钮显示隐藏
        if (isSelect) {
            //显示
            pop.setVisibility(View.VISIBLE);
        } else {
            pop.setVisibility(View.GONE);

            isSelectAll = true;
            selectAllMain();
            clearAll();
        }
        //控制多选框的显示隐藏
        allAdapter.setEditMode(mEditMode);
    }

    private void clearAll() {
        isSelectAll = false;
        btnAllSelect.setText("全选");
        setBtnBackground(0);
    }

    /**
     * 删除逻辑
     */
    private void deleteVideo() {

        final ArrayList<VideoData.NewList> selectedList = allAdapter.getSelectedNews();

        int index = selectedList.size();

        if(index == 0){
            return;
        }
        final AlertDialog builder = new AlertDialog.Builder(getContext())
                .create();
        builder.show();
        if (builder.getWindow() == null) {
            return;
        }
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
        builder.getWindow().setBackgroundDrawableResource(R.drawable.dailog);
        TextView msg = (TextView) builder.findViewById(R.id.tv_msg);
        Button cancle = (Button) builder.findViewById(R.id.btn_cancle);
        Button sure = (Button) builder.findViewById(R.id.btn_sure);
        if (msg == null || cancle == null || sure == null) {
            return;
        }




        if (index == 1) {
            //msg.setText("删除后不可恢复，是否删除该条目？");
        } else {
            //msg.setText("删除后不可恢复，是否删除这" + index + "个条目？");
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

                for(VideoData.NewList data : selectedList){
                    sb.append(data.getCollect_id()).append(",");
                }
                sb.delete(sb.length()-1,sb.length());

                mPresenter.getCollectDelet(sb.toString());

                showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG);
                builder.dismiss();

            }


        });
    }

    /**
     * 全选和反选
     */
    private void selectAllMain() {
        if (allAdapter == null) {
            return;
        }

        int index = 0;
        if (!isSelectAll) {

            for (int i = 0, j = allAdapter.recyclerview_item_collect.size(); i < j; i++) {

                allAdapter.recyclerview_item_collect.get(i).setSelect(true);
            }
            index = allAdapter.recyclerview_item_collect.size();

            btnAllSelect.setText("全选");
            isSelectAll = true;
        } else {

            for (int i = 0, j = allAdapter.recyclerview_item_collect.size(); i < j; i++) {
                allAdapter.recyclerview_item_collect.get(i).setSelect(false);
            }
            index = 0;
            btnAllSelect.setText("全选");
            isSelectAll = false;
        }
        allAdapter.notifyDataSetChanged();
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
            btnDelete.setTextColor(Color.BLACK);
        }
    }
}
