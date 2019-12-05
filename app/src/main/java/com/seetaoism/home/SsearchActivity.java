package com.seetaoism.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mr.k.mvp.kotlin.base.BaseActivity;
import com.mr.k.mvp.utils.Logger;
import com.mr.k.mvp.utils.SystemFacade;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.seetaoism.AppConstant;
import com.seetaoism.R;
import com.seetaoism.base.JDMvpBaseActivity;
import com.seetaoism.data.entity.DetailExclusiveData;
import com.seetaoism.data.entity.FROM;
import com.seetaoism.data.entity.NewsData;
import com.seetaoism.data.entity.SearchData;
import com.seetaoism.home.detail.vp.DetailVPFragment;
import com.seetaoism.utils.SharedPrefrenceUtils;
import com.shehuan.niv.NiceImageView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SsearchActivity extends JDMvpBaseActivity<SearchContract.ISearchPresenter> implements View.OnClickListener, SearchContract.ISearchView, TextView.OnEditorActionListener {

    private static final Object TAG = "SsearchActivity";
    private NiceImageView mClose;
    private EditText mEditSearch;
    private TextView mSearchText;
    private Toolbar mToolbar;
    private FrameLayout mBox;
    private RecyclerView mRvNews;
    private SmartRefreshLayout mRefreshLayout;
    private TextView mRecord;
    private TextView mClean;
    private FrameLayout mHistory;
    private int start = 0;
    private int time = 0;
    private int more=0;
    private SearchAdapter adapter;
    public ArrayList<SearchData.Searchlist> mlist = new ArrayList<>();
    public ArrayList<String> his = new ArrayList<>();
    private List<String> stringList;
    private TagFlowLayout flow;
    private ArrayList<TextView> mCacheTagViewList = new ArrayList<>();



    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {

        mClose = findViewById(R.id.close);
        mEditSearch = findViewById(R.id.edit_search);
        mSearchText = findViewById(R.id.search_text);
        mToolbar = findViewById(R.id.toolbar);
        mBox = findViewById(R.id.box);
        mRvNews = findViewById(R.id.rv_news);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecord = findViewById(R.id.record);
        mClean = findViewById(R.id.clean_list);
        mHistory = findViewById(R.id.history);
        flow = findViewById(R.id.flow_layout);
        mRvNews.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchAdapter(mlist, this);

        adapter.setmOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onClick(NewsData.NewsBean news, int position) {

                ArrayList<NewsData.NewsBean> beans = new ArrayList<>();
                beans.add(news);


                DetailExclusiveData data = new DetailExclusiveData(FROM.SEARCH,beans,0);
                data.setStart(start);
                data.setStartPointTime(time);


                DetailVPFragment.Launcher.open(SsearchActivity.this, data, null);
            }
        });
        mRvNews.setAdapter(adapter);

        mClose.setOnClickListener(this);
        mSearchText.setOnClickListener(this);
        mClean.setOnClickListener(this);
        mEditSearch.setOnEditorActionListener(this);

        histroy();

        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mRefreshLayout.finishLoadMore(2000);       //2s加载结束
                if (more==1) {
                    mPresenter.getSearchs(start, time, mEditSearch.getText().toString());
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mRefreshLayout.finishRefresh(2000);    //2s刷新结束
                start = 0;
                time = 0;

            }
        });


        //历史点击事件
        flow.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                mEditSearch.setText(his.get(position));
                mPresenter.getSearchs(start, time, mEditSearch.getText().toString());
                return true;
            }
        });

        if (his.size() > 0){
            mHistory.setVisibility(View.VISIBLE);
        } else {
            mHistory.setVisibility(View.GONE);
            mClean.setVisibility(View.GONE);
            mRecord.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ssearch;
    }

    private void histroy() {
        //历史记录
        final List<String> stringList = SharedPrefrenceUtils.getStringList(this, AppConstant.SPKeys.SEARCH);

        if (stringList!=null&&stringList.size() > 0) {
            his.addAll(stringList);
            mHistory.setVisibility(View.VISIBLE);
            mRecord.setVisibility(View.VISIBLE);
            mClean.setVisibility(View.VISIBLE);
            flow.setVisibility(View.VISIBLE);
            mRefreshLayout.setVisibility(View.GONE);
            mRvNews.setVisibility(View.GONE);
            mBox.setVisibility(View.GONE);
        }
        flowSetData(his);
    }

    private void flowSetData(final ArrayList<String> his) {
        flow.setAdapter(new TagAdapter<String>(his) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {


                // 第一步得到当前 tag在recyclerView 中对应的 position 命名为 positionOfRecyclerView
                int positionOfRecyclerView = 1;


                // 第二步 算出 recyclerView 中 positionOfRecyclerView 前面的所有item 中 所有 tag 之和。
                int count = 0;
                for (int i = 0; i < positionOfRecyclerView; i++) {
                    count = count + his.size();
                }

                // 第三步,用第二步算出的数量 + 当前tag 在 FlowLayout中的position,即可得到当前tag 在整个recyclerView 中是第几个
                count = count + position;


                TextView textView = null;
                if (count < mCacheTagViewList.size()) { // 如果count 小于 cache size ,说明 这个textview 还没有放到缓存里面
                    textView = mCacheTagViewList.get(count);
                    ViewGroup parentView = (ViewGroup) textView.getParent();
                    parentView.removeView(textView);

                } else { //
                    Logger.d("%s new flow layout item view %s and put in cache", TAG, count);
                    textView = new TextView(parent.getContext());
                    textView.setBackgroundResource(R.drawable.history_item);
                    mCacheTagViewList.add(textView);
                }
                int h = SystemFacade.dp2px(parent.getContext(), 10);
                int w = SystemFacade.dp2px(parent.getContext(), 6);
                int wj = SystemFacade.dp2px(parent.getContext(), 8);
                ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(w, w, w, w);
                textView.setPadding(wj, h, wj, h);
                textView.setLayoutParams(layoutParams);
                textView.setText(s);

                return textView;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //搜索
            case R.id.close:
                Search();
                hideKeyboard(mClose);

                break;
            //关闭界面
            case R.id.search_text:
                finish();
                break;
            case R.id.clean_list:
                //清除历史
                showToast("清除");
                his.clear();
                flowSetData(his);
                mBox.setVisibility(View.VISIBLE);
                SharedPrefrenceUtils.putStringList(this, AppConstant.SPKeys.SEARCH, his);
                break;

        }

    }
    /**
     * 隐藏软键盘
     *
     * @param :上下文
     * @param view :一般为EditText
     */
    public void hideKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void Search() {
        String s = mEditSearch.getText().toString();
        if (!TextUtils.isEmpty(s)) {
            if (adapter.mlist.size() > 0) {
                //再次点击搜索的时候清空之前搜索的数据，防止叠加
                adapter.mlist.clear();
            }
            SharedPrefrenceUtils.saveString(this, "isWords", s);
             //判断集合中是否包含要搜索的内容，如果包含不添加
            if (!his.contains(s)) {
                //保存搜索数据
                his.add(s);
            }
            SharedPrefrenceUtils.putStringList(this, AppConstant.SPKeys.SEARCH, his);
            mPresenter.getSearchs(start, time, s);
        } else {
            mBox.setVisibility(View.VISIBLE);
            mHistory.setVisibility(View.GONE);
        }
    }

    @Override
    public SearchContract.ISearchPresenter createPresenter() {
        return new SearchPresenter();
    }

    @Override
    public void closeLoading() {

    }

    @Override
    public Activity getActivityObj() {

        return null;
    }


    @Override
    public void onSearchSuccess(SearchData data, String msg) {
        if (data != null && data.getList().size() > 0) {
            more=data.getMore();
            start=data.getStart();
            time=data.getPoint_time();
            mlist.addAll(data.getList());
            adapter.notifyDataSetChanged();
            mRefreshLayout.setVisibility(View.VISIBLE);
            mRvNews.setVisibility(View.VISIBLE);
            mBox.setVisibility(View.GONE);
            mHistory.setVisibility(View.GONE);
        } else {
            mBox.setVisibility(View.VISIBLE);
            mHistory.setVisibility(View.GONE);

        }
    }
   //输入法搜索
    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        /*判断是否是“GO”键*/
        if (i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_UNSPECIFIED) {
            Search();
            hideKeyboard(mEditSearch);
            return true;
        }
        return false;

    }
}


