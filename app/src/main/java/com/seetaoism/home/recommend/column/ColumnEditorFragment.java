package com.seetaoism.home.recommend.column;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.mr.k.mvp.base.BaseFragment;
import com.mr.k.mvp.utils.DataCacheUtils;
import com.mr.k.mvp.utils.SPUtils;
import com.seetaoism.AppConstant;
import com.seetaoism.R;
import com.seetaoism.data.entity.NewsColumn;
import com.seetaoism.data.entity.NewsColumnData;
import com.seetaoism.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;


public class ColumnEditorFragment extends BaseFragment implements View.OnClickListener {

    private final static String TAG = "ColumnEditorFragment";

    private ColumnEditorAdapter<NewsColumn> mColumnEditorAdapter;
    private NewsColumnData mNewsColumnData;
    private RecyclerView mRecyclerView;
    private HomeActivity mHomeActivity;
    private ImageView imageView2;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof HomeActivity) {
            mHomeActivity = (HomeActivity) activity;

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_column_editor;
    }

    @Override
    protected void initView(View root) {
        imageView2 = root.findViewById(R.id.imageView2);
        mRecyclerView = root.findViewById(R.id.column_edit_list);


        imageView2.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        String jsonStr = SPUtils.getValue(AppConstant.SPKeys.COLUMNS);
        if (!TextUtils.isEmpty(jsonStr)) {
            mNewsColumnData = DataCacheUtils.convertDataFromJson(NewsColumnData.class, jsonStr);
            if (mNewsColumnData != null) {
                //createMockData();
                if (mNewsColumnData.getList() != null) {

                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ColumnItemTouchHelperCallback());
                    itemTouchHelper.attachToRecyclerView(mRecyclerView);
                    final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
                    mRecyclerView.setLayoutManager(gridLayoutManager);

                    List<NewsColumn> myList = mNewsColumnData.getList().getMyColumnList();

                    if (myList != null && myList.size() > 0) {
                        myList.get(0).setFix(true);
                        if (myList.size() > 1) {
                            myList.get(1).setFix(true);
                        }
                    }
                    mColumnEditorAdapter = new ColumnEditorAdapter<NewsColumn>(mRecyclerView, gridLayoutManager, itemTouchHelper, mNewsColumnData.getList().getMyColumnList(), mNewsColumnData.getList().getMoreColumnList());
                    mColumnEditorAdapter.setOnColumnChangedListener(new ColumnEditorAdapter.OnColumnChangedListener<NewsColumn>() {
                        @Override
                        public void onChanged(List<NewsColumn> my, List<NewsColumn> more) {
                            showToast("改变了");
                            SPUtils.saveValueToDefaultSpByCommit(AppConstant.SPKeys.COLUMNS, DataCacheUtils.convertToJsonFromObject(mNewsColumnData));
                            if (mHomeActivity != null) {
                                mHomeActivity.onColumnChanged(new ArrayList<NewsColumn>(my));
                            }
                        }
                    });

                    gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            int type = mColumnEditorAdapter.getItemViewType(position);

                            if (type == ColumnEditorAdapter.ITEM_TYPE_MY_COLUMN_TITLE || type == ColumnEditorAdapter.ITEM_TYPE_MORE_COLUMN_TITLE) {
                                return gridLayoutManager.getSpanCount();
                            }
                            return 1;
                        }
                    });
                    mRecyclerView.setAdapter(mColumnEditorAdapter);
                }


            }
        }
    }


    @Override
    public int enter() {
        return R.anim.common_page_bottom_in;
    }

    @Override
    public int exit() {
        return 0;
    }

    @Override
    public int popExit() {
        return R.anim.common_page_bottom_out;
    }

    @Override
    public int popEnter() {
        return 0;
    }

    @Override
    protected void cancelRequest() {

    }


    @Override
    public boolean isNeedAnimation() {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mColumnEditorAdapter.notifyChanged();

    }

    // 生成假数据
    private void createMockData() {
        //----------------——
        mNewsColumnData.getList().getMyColumnList().clear();

        for (int i = 0; i < 160; i++) {
            NewsColumn column = new NewsColumn();
            column.setName("选中" + i);
            mNewsColumnData.getList().getMyColumnList().add(column);
        }

        mNewsColumnData.getList().getMoreColumnList().clear();

        for (int i = 0; i < 120; i++) {
            NewsColumn column = new NewsColumn();
            column.setName("未选中" + i);
            mNewsColumnData.getList().getMoreColumnList().add(column);
        }
        //----------------——
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView2:
                back();
                break;
        }
    }
}
