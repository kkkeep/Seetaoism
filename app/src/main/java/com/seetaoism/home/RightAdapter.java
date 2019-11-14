package com.seetaoism.home;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mr.k.mvp.utils.Logger;
import com.mr.k.mvp.utils.SystemFacade;
import com.seetaoism.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;


public class RightAdapter extends RecyclerView.Adapter<RightAdapter.RightHolder> {
    private static final String TAG = "RightAdapter";


    private ArrayList<String> mDatas;
    private ArrayList<TextView> mCacheTagViewList = new ArrayList<>();

    public RightAdapter(ArrayList<String> his, SsearchActivity ssearchActivity) {
        mDatas = his;
    }


    @NonNull
    @Override
    public RightHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RightHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_flowlayout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RightHolder holder, int position) {
        holder.mFlowLayout.setTag(position);
        holder.setData(mDatas);
    }

    @Override
    public int getItemCount() {

        return mDatas == null ? 0 : mDatas.size();
    }

    public class RightHolder extends RecyclerView.ViewHolder {

        private TagFlowLayout mFlowLayout;

        public RightHolder(@NonNull View itemView) {
            super(itemView);
            mFlowLayout = itemView.findViewById(R.id.flow_layout);
        }


        public void setData(final ArrayList<String> data) {


            if (data == null) {
                mFlowLayout.setVisibility(View.GONE);
            } else {
                mFlowLayout.setVisibility(View.VISIBLE);
            }

            mFlowLayout.setAdapter(new TagAdapter<String>(mDatas) {

                @Override
                public View getView(FlowLayout parent, int position, String s) {


                    // 第一步得到当前 tag在recyclerView 中对应的 position 命名为 positionOfRecyclerView
                    int positionOfRecyclerView = (int) mFlowLayout.getTag();


                    // 第二步 算出 recyclerView 中 positionOfRecyclerView 前面的所有item 中 所有 tag 之和。
                    int count = 0;
                    for (int i = 0; i < positionOfRecyclerView; i++) {
                        count = count + mDatas.size();
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
                        //textView.setBackgroundResource(R.drawable.navigation_right_child_item_tag_bg);
                        textView.setTextColor(SystemFacade.randomColor());
                        mCacheTagViewList.add(textView);

                    }
                    int h = SystemFacade.dp2px(parent.getContext(), 10);
                    int w = SystemFacade.dp2px(parent.getContext(), 6);
                    ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(w, w, w, w);
                    textView.setPadding(w, h, w, h);
                    textView.setLayoutParams(layoutParams);
                    textView.setText(s);

                    return textView;
                }
            });

        }

    }
}
