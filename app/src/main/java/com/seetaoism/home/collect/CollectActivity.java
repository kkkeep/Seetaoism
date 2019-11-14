package com.seetaoism.home.collect;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.seetaoism.R;
import com.seetaoism.base.JDMvpBaseActivity;
import com.seetaoism.data.entity.VideoData;
import com.shehuan.niv.NiceImageView;

import java.util.ArrayList;

public class CollectActivity extends JDMvpBaseActivity<CollectContract.ICollectPresenter> implements CollectContract.ICollectView, View.OnClickListener {
    //首先定义一个变量
    private static final int CHECK_GONE = 0;     //这个是表示隐藏
    private static final int CHECK_VISIBLE = 1;      //这个是表示显示
    private int mEditMode = CHECK_GONE;

    public static final String COLLECTALL = "0";
    public static final String COLLECT_VIDEO = "4";
    private NiceImageView mClose;
    private Toolbar mToolbar;
    private TabLayout mTablayoutCollect;
    private ViewPager mViewpagerCollect;
    private TextView bianji;

    private ArrayList<Fragment> fragmentList;
    private ArrayList<String> list_Title;


    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {
        mClose = findViewById(R.id.close);
        mToolbar = findViewById(R.id.toolbar);
        mTablayoutCollect = findViewById(R.id.tablayout_collect);
        //取消下划线
        mTablayoutCollect.setSelectedTabIndicatorHeight(0);
        mViewpagerCollect = findViewById(R.id.viewpager_collect);
        bianji = findViewById(R.id.bianji);
        mClose.setOnClickListener(this);
        bianji.setOnClickListener(this);

        fragmentList = new ArrayList<>();
        list_Title = new ArrayList<>();

        fragmentList.add(new CollectAllFragment());
        fragmentList.add(new CollectVideoFragment());

        list_Title.add("全部");
        list_Title.add("视频");

        mViewpagerCollect.setAdapter(new CollectAdapter(getSupportFragmentManager(), CollectActivity.this, fragmentList, list_Title));
        mTablayoutCollect.setupWithViewPager(mViewpagerCollect);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }


    @Override
    public void onICollectSucceed(VideoData data) {

    }

    @Override
    public void onICollectFail(String s) {

    }

    @Override
    public void onICollectVideoSucceed(VideoData data) {

    }

    @Override
    public void onICollectVideoFail(String s) {

    }

    @Override
    public void onICollectDelete(String data) {

    }

    @Override
    public void onICollectDeleteFail(String data) {

    }

    @Override
    public CollectContract.ICollectPresenter createPresenter() {
        return new CollectPresenter();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bianji:
                redact();
                break;
            case R.id.close:
                finish();
                break;
        }
    }

    private void redact() {
        boolean isSelect = false;
        //0是为了控制后边适配器子条目中的多选框隐藏，1是显示
        int i = mEditMode == CHECK_GONE ? CHECK_VISIBLE : CHECK_GONE;
        mEditMode = i;
        int tabPosition = mTablayoutCollect.getSelectedTabPosition();
        //这个方法为了获取当前tab选中的下标
        if (tabPosition == 0) {
            CollectAllFragment collectAllFragment = (CollectAllFragment) fragmentList.get(0);
            if (collectAllFragment != null) {
                if (mEditMode == CHECK_VISIBLE) {
                    //这个判断是true，所以把编辑二字改成了取消
                    bianji.setText("取消");
                    isSelect = true;
                    //这个isSelect是为了传给下一个页面判断是否要显示隐藏全选和删除的按钮
                    collectAllFragment.chooseNews(isSelect, mEditMode);
                } else {
                    bianji.setText("编辑");
                    isSelect = false;
                    collectAllFragment.chooseNews(isSelect, mEditMode);
                }
            }
        } else {
            //第二个页面

            CollectVideoFragment collectVideoFragment = (CollectVideoFragment) fragmentList.get(1);
            if (collectVideoFragment != null) {
                if (mEditMode == CHECK_VISIBLE) {
                    //这个判断是true，所以把编辑二字改成了取消
                    bianji.setText("取消");
                    isSelect = true;
                    //这个isSelect是为了传给下一个页面判断是否要显示隐藏全选和删除的按钮
                    collectVideoFragment.chooseNews(isSelect, mEditMode);
                } else {
                    bianji.setText("编辑");
                    isSelect = false;
                    collectVideoFragment.chooseNews(isSelect, mEditMode);
                }
            }
        }
    }
}