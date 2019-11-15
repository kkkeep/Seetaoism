package com.seetaoism.home;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mr.k.mvp.base.BaseActivity;
import com.mr.k.mvp.base.BaseFragment;
import com.mr.k.mvp.statusbar.StatusBarUtils;
import com.seetaoism.R;
import com.seetaoism.TestConstraintActivity;
import com.seetaoism.base.JDBaseActivity;
import com.seetaoism.data.entity.NewsColumn;
import com.seetaoism.data.entity.NewsColumnData;
import com.seetaoism.data.repositories.NewsRepository;
import com.seetaoism.home.mine.MineFragment;
import com.seetaoism.home.recommend.RecommendFragment;
import com.seetaoism.home.recommend.column.ColumnEditorFragment;
import com.seetaoism.home.topic.TopicFragment;
import com.seetaoism.home.video.VideoFragment;
import com.seetaoism.widgets.FeedbackPopwindow;
import com.seetaoism.widgets.bottomtablaout.BottomTabLayout;
import com.umeng.socialize.UMShareAPI;

import java.util.List;


public class HomeActivity extends JDBaseActivity implements View.OnClickListener {

    private BottomTabLayout mBottomTabLayout;
    private DrawerLayout mDrawerLayout;
    private TextView editText;
    private TextView home_drawer_goto_colum_editor;
    private TextView home_drawer_feedback;
    private TextView home_drawer_share_app;
    private RecyclerView mDrawerColumnListView;
    private FeedbackPopwindow mFeedPopwindow;

    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {
        mDrawerLayout = findViewById(R.id.home_drawer_layout);
        editText = findViewById(R.id.editText);
        home_drawer_goto_colum_editor = findViewById(R.id.home_drawer_goto_colum_editor);
        home_drawer_feedback = findViewById(R.id.home_drawer_feedback);
        home_drawer_share_app = findViewById(R.id.home_drawer_share_app);
        mDrawerColumnListView = mDrawerLayout.findViewById(R.id.home_drawer_colum_list);
        mBottomTabLayout = findViewById(R.id.home_bottom_layout);


        editText.setOnClickListener(this);
        home_drawer_goto_colum_editor.setOnClickListener(this);
        home_drawer_feedback.setOnClickListener(this);
        home_drawer_share_app.setOnClickListener(this);


        mBottomTabLayout.setTabSelectedChangeListener(new BottomTabLayout.OnTabSelectedChangeListener() {
            @Override
            public void onSelect(int position) {
                switchFragment(position);
            }

            @Override
            public void onSelectedAgain(int position) {

            }
        });

        mBottomTabLayout.selectTab(1);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    private void switchFragment(int position) {

        Class<? extends BaseFragment> aClass = null;
        switch (position) {
            case 1: {
                aClass = RecommendFragment.class;
                break;
            }
            case 2: {
                aClass = VideoFragment.class;
                break;
            }
            case 3: {
                aClass = TopicFragment.class;
                break;
            }
            case 4: {
                aClass = MineFragment.class;
                break;
            }

        }
        //我感觉是得这样写、、、
        addFragment(getSupportFragmentManager(), aClass, R.id.home_fragment_container, null);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public void updateTabTitle(int tabIndex, String tabValue) {
        mBottomTabLayout.setTabTitle(tabIndex, tabValue);
    }

    public void onColumnChanged(List<NewsColumn> data) {

        RecommendFragment recommendFragment = (RecommendFragment) getSupportFragmentManager().findFragmentByTag(getFragmentTag(RecommendFragment.class));
        if (recommendFragment != null) {
            recommendFragment.refreshColumn(data);
        }

        DrawerAdapter drawerAdapter = (DrawerAdapter) mDrawerColumnListView.getAdapter();
        if (drawerAdapter != null) {
            drawerAdapter.update(data);
        }

    }

    public void setDrawerColumnList(NewsColumnData data) {
        DrawerAdapter drawerAdapter = new DrawerAdapter(data.getList().getMyColumnList());
        mDrawerColumnListView.setLayoutManager(new GridLayoutManager(this, 2));
        mDrawerColumnListView.setAdapter(drawerAdapter);
    }


    private void jumpToSpecifiedColumnNewsPage(NewsColumn column) {
        RecommendFragment recommendFragment = (RecommendFragment) getSupportFragmentManager().findFragmentByTag(getFragmentTag(RecommendFragment.class));
        if (recommendFragment != null) {
            recommendFragment.openSpecifiedColumnPage(column);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NewsRepository.destroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editText:
                //搜索
                startActivity(new Intent(HomeActivity.this, SsearchActivity.class));
                break;
            case R.id.home_drawer_goto_colum_editor:
                //订阅
                addFragment(getSupportFragmentManager(), ColumnEditorFragment.class, android.R.id.content, null);
                break;
            case R.id.home_drawer_share_app:
                break;
            case R.id.home_drawer_feedback:
                //意见反馈
                mFeedPopwindow = new FeedbackPopwindow(HomeActivity.this, getItemsOnClick);
                mFeedPopwindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                break;
        }

    }

    //意见反馈
    private View.OnClickListener getItemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //mFeedPopwindow.dismiss();
            mFeedPopwindow.backgroundAlpha(HomeActivity.this, 1f);
            switch (v.getId()) {
                case R.id.call_opinion:
                    Intent myCallIntent = new Intent(Intent.ACTION_DIAL,
                            Uri.parse("tel:010-58760804"));
                    startActivity(myCallIntent);
                    break;
                case R.id.close_opinion:
                    mFeedPopwindow.dismiss();
                    break;
            }
        }
    };


    public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ColumnHolder> {

        private List<NewsColumn> mNewsColumns;


        public DrawerAdapter(List<NewsColumn> newsColumns) {
            this.mNewsColumns = newsColumns;
        }

        public void update(List<NewsColumn> newsColumns) {
            this.mNewsColumns = newsColumns;
            notifyDataSetChanged();


        }

        @NonNull
        @Override
        public ColumnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ColumnHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer_column, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ColumnHolder holder, int position) {
            holder.setData(mNewsColumns.get(position));
        }

        @Override
        public int getItemCount() {
            return mNewsColumns == null ? 0 : mNewsColumns.size();
        }

        public class ColumnHolder extends RecyclerView.ViewHolder {
            private ImageView tag;
            private TextView value;

            public ColumnHolder(@NonNull View itemView) {
                super(itemView);

                tag = itemView.findViewById(R.id.home_drawer_item_iv_tag);
                value = itemView.findViewById(R.id.home_drawer_item_tv_value);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpToSpecifiedColumnNewsPage(mNewsColumns.get(getAdapterPosition()));
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                    }
                });
            }

            public void setData(NewsColumn column) {
                tag.setColorFilter(column.getColor());
                value.setText(column.getName());
            }
        }
    }


}
