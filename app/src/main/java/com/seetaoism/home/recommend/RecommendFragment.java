package com.seetaoism.home.recommend;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.mr.k.mvp.base.MvpBaseFragment;
import com.mr.k.mvp.utils.Logger;
import com.mr.k.mvp.utils.SystemFacade;
import com.seetaoism.R;
import com.seetaoism.data.entity.NewsColumn;
import com.seetaoism.data.entity.NewsColumnData;
import com.seetaoism.home.HomeActivity;
import com.seetaoism.home.SsearchActivity;
import com.seetaoism.home.recommend.column.ColumnEditorFragment;
import com.seetaoism.libloadingview.LoadingView;

import java.util.List;


public class RecommendFragment extends MvpBaseFragment<RecommendContract.IRecommendPresenter> implements RecommendContract.IRecommendView, View.OnClickListener {


    private static final String TAG = "RecommendFragment";

    private SlidingTabLayout mTabLayout;

    private ViewPager mViewPager;

    // 抽屉开关
    private ImageView mIvDrawerSwitch;

    // 点击进入频道编辑页面
    private ImageView mIvTabEdit;

    private RecommendPageAdapter mPageAdapter;

    private HomeActivity mHomeActivity;

    private NewsColumnData mNewsColumnData;

    private int mCurStartPosition = -1;




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof HomeActivity){
            mHomeActivity = (HomeActivity) activity;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommed;
    }

    private boolean isDragging;

    @Override
    protected void initView(View root) {
        mTabLayout = root.findViewById(R.id.home_recommend_top_tab_layout);
        mViewPager = root.findViewById(R.id.home_recommend_viewpager);

        mIvDrawerSwitch = bindViewAndSetListener(R.id.home_recommend_top_logo, this);
        mIvTabEdit = bindViewAndSetListener(R.id.home_recommend_top_column_edit, this);

        mViewPager.setOffscreenPageLimit(1);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                Logger.d("%s position = %s positionOffset = %s positionOffsetPixels = %s", TAG, position, positionOffset, positionOffsetPixels);

                if (isDragging) {
                    changeTabColor(position, positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {

                // TabLayout 的一个bug ,在横竖屏切换的时候如果不用这种方式就会crash
                mViewPager.post(() -> changeTabColor(position, 0));


                if(mHomeActivity != null){
                    mHomeActivity.updateTabTitle(1, mPageAdapter.getColumn(position).getName());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    isDragging = true;
                } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                    isDragging = false;
                    mCurStartPosition = -1;
                }

            }
        });

    }

    public void reloadColumn(){
        mPresenter.getNewsColumn();
    }

    public void refreshColumn(List<NewsColumn> data) {
        mPageAdapter.refreshColumn(data);
        mTabLayout.notifyDataSetChanged();// 必须加这一行，不然tab 不刷新

        mPresenter.uploadColumn(data);
    }


    public void openSpecifiedColumnPage(NewsColumn column) {
        mTabLayout.setCurrentTab(column.getPosition());
        // mViewPager.setCurrentItem(column.getPosition());
    }

    @Override
    protected void initData() {
        showLoading(LoadingView.LOADING_MODE_WHITE_BG);
        mPresenter.getNewsColumn();
    }

    @Override
    public void onNewsColumnSuccess(NewsColumnData data) {
        Logger.d("%s News tab = %s", TAG, data);

        if (data.getList() != null && !SystemFacade.isListEmpty(data.getList().getMyColumnList())) {
            if(mPageAdapter == null) {

                mNewsColumnData = data;
                mPageAdapter = new RecommendPageAdapter(getChildFragmentManager(), data.getList().getMyColumnList());
                mViewPager.setAdapter(mPageAdapter);
                mTabLayout.setViewPager(mViewPager);
                changeTabColor(0, 0);
                closeLoading();
                mHomeActivity.setDrawerColumnList(data);
            }else{

                List<NewsColumn> serverData = data.getList().getMyColumnList();
                List<NewsColumn> localData = mPageAdapter.getColumns();
                boolean isRefresh = false;

                if(localData == null || localData.size() == 0){
                  isRefresh = true;
                }

                if( !isRefresh && (serverData.size() != localData.size())){
                    isRefresh = true;
                }
                if(!isRefresh){
                    for(NewsColumn column : serverData){
                        if(!localData.contains(column)){
                            isRefresh = true;
                            break;
                        }
                    }
                }
                if(isRefresh){
                    ((HomeActivity)getActivity()).onColumnChanged(serverData);
                }

            }
        } else {
            handError(getString(R.string.text_error_server_exception));
        }

    }

    @Override
    public void onNewsColumnFail(String msg) {
        Logger.d("%s News tab error ", TAG, msg);
        handError(msg);
    }

    private void handError(String msg) {
        onError(msg, new LoadingView.RetryCallBack() {
            @Override
            public void onRetry() {
                mPresenter.getNewsColumn();
            }
        });
    }

    /*
        改变tab 滑块 和字体颜色
     */
    private void changeTabColor(int position, float offset) {
        if (mPageAdapter != null) {

            //
            if (mCurStartPosition == -1) {
                mCurStartPosition = mViewPager.getCurrentItem(); //  getCurrentItem返回值会随着pager 的滑动举例而改变
            }

            int nextPosition = -1;

            if (position < mCurStartPosition) { // 往回滑动
                nextPosition = mCurStartPosition - 1;
            } else {

                nextPosition = mCurStartPosition + 1;
            }


            Logger.d("%s ----- curPosition = %s,nextPosition = %s offset = %s", TAG, mCurStartPosition, nextPosition, offset);

            NewsColumn curColumn = mPageAdapter.getColumn(mCurStartPosition);
            NewsColumn nextColumn = mPageAdapter.getColumn(nextPosition);

            TextView curTitleView = mTabLayout.getTitleView(mCurStartPosition);
            int curTitleColor = curTitleView.getPaint().getColor();

            if (nextPosition > mCurStartPosition) {

                if (offset > 0.2) {
                    if (nextColumn != null) {

                        if (mTabLayout.getIndicatorColor() != nextColumn.getColor() && nextColumn.getColor() != Color.BLACK) {
                            mTabLayout.setIndicatorColor(nextColumn.getColor());
                        }

                    }

                    if (curTitleColor != Color.BLACK) {
                        curTitleView.setTextColor(Color.BLACK);
                    }

                } else if (offset > 0) {
                    if (mTabLayout.getIndicatorColor() != curColumn.getColor()) {
                        mTabLayout.setIndicatorColor(curColumn.getColor());
                    }
                    if (curTitleColor != Color.WHITE) {
                        curTitleView.setTextColor(Color.WHITE);
                    }
                } else if (offset == 0) {
                    if (mViewPager.getCurrentItem() > mCurStartPosition) {

                        if (mTabLayout.getIndicatorColor() != nextColumn.getColor()) {
                            mTabLayout.setIndicatorColor(nextColumn.getColor());
                        }
                    } else {
                        if (mTabLayout.getIndicatorColor() != curColumn.getColor()) {
                            mTabLayout.setIndicatorColor(curColumn.getColor());
                        }
                    }

                }

            } else {
                if (offset < 0.8) {
                    if (nextColumn != null) {
                        if (mTabLayout.getIndicatorColor() != nextColumn.getColor() && nextColumn.getColor() != Color.BLACK) {
                            mTabLayout.setIndicatorColor(nextColumn.getColor());
                        }

                    }

                    if (curTitleColor != Color.BLACK) {
                        curTitleView.setTextColor(Color.BLACK);
                    }
                } else if (offset > 0) {
                    if (mTabLayout.getIndicatorColor() != curColumn.getColor()) {
                        mTabLayout.setIndicatorColor(curColumn.getColor());
                    }
                    if (curTitleColor != Color.WHITE) {
                        curTitleView.setTextColor(Color.WHITE);
                    } else if (offset == 0) {
                        if (mTabLayout.getIndicatorColor() != nextColumn.getColor()) {
                            mTabLayout.setIndicatorColor(nextColumn.getColor());
                        }

                    }
                }

            }

        }

    }

    @Override
    public RecommendContract.IRecommendPresenter createPresenter() {
        return new RecommendPresenter();
    }

    @Override
    public boolean isNeedAnimation() {
        return false;
    }

    @Override
    public boolean isNeedAddToBackStack() {
        return false;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.home_recommend_top_column_edit: {

//                if(mNewsColumnData != null){
//
//                    ColumnEditorFragment fragment = (ColumnEditorFragment) addFragment(getFragmentManager(), ColumnEditorFragment.class,android.R.id.content,null);
//
//                }
                startActivity(new Intent(getContext(), SsearchActivity.class));

                break;

            }
            case R.id.home_recommend_top_logo:{
                //打开侧滑
                HomeActivity activity = (HomeActivity) getActivity();
                activity.mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            }
        }
    }
}
