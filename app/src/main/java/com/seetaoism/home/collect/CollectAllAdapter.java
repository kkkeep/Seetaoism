package com.seetaoism.home.collect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.seetaoism.R;
import com.seetaoism.data.entity.NewsColumnList;
import com.seetaoism.data.entity.NewsData;
import com.seetaoism.data.entity.VideoData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CollectAllAdapter extends RecyclerView.Adapter {


    public ArrayList<VideoData.NewList> recyclerview_item_collect;
    private Context context;
    int mEditMode = CollectActivity.MODE_VIEW;
    private OnItemClickListener mListener;

    public CollectAllAdapter(ArrayList<VideoData.NewList> recyclerview_item_collect, Context context) {
        this.recyclerview_item_collect = recyclerview_item_collect;
        this.context = context;
    }

    public void setEditMode(int editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.collect_all_item, null, false);
        return new CollectHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CollectHolder holder1 = (CollectHolder) holder;
        holder1.collect_all_head.setText(recyclerview_item_collect.get(position).getRead_count());
        holder1.collect_all_time.setText(recyclerview_item_collect.get(position).getTime());
        holder1.collect_all_titel.setText(recyclerview_item_collect.get(position).getTheme());
        RequestOptions options = new RequestOptions()
                .centerCrop();//高版本的Glide得这样


        Glide.with(context).asBitmap().load(recyclerview_item_collect.get(position).getImage_url()).apply(options).into(holder1.collect_all_pic);
        holder1.setIsRecyclable(false); // 为了条目不复用
        VideoData.NewList bean = recyclerview_item_collect.get(position);

        if (mEditMode == CollectActivity.MODE_VIEW) {
            holder1.cb_select.setVisibility(View.GONE);
        } else {
            holder1.cb_select.setVisibility(View.VISIBLE);
            if (bean.isSelect()) {
                holder1.cb_select.setChecked(true);
            } else {
                holder1.cb_select.setChecked(false);
            }
        }

        holder1.cb_select.setOnClickListener(v -> {
            if (mListener != null) {
                CheckBox cb = (CheckBox) v;
                if (bean.isSelect()) {
                    cb.setChecked(false);
                    bean.setSelect(false);
                } else {
                    cb.setChecked(true);
                    bean.setSelect(true);
                }
                mListener.onItemClick(recyclerview_item_collect, position);
            }
        });
        holder1.itemView.setOnClickListener(view -> {
            if (mListener != null) {
                if (holder1.cb_select.isChecked()) {
                    holder1.cb_select.setChecked(false);
                    bean.setSelect(false);
                } else {
                    holder1.cb_select.setChecked(true);
                    bean.setSelect(true);
                }
                mListener.onItemClick(recyclerview_item_collect, position);
            }
        });
    }

    public ArrayList<VideoData.NewList> getSelectedNews() {
        ArrayList<VideoData.NewList> list = new ArrayList<>();
        if (recyclerview_item_collect != null) {
            for (VideoData.NewList data : recyclerview_item_collect) {
                if (data.isSelect()) {
                    list.add(data);
                }
            }
        }
        return list;
    }

    public void deleteSuccess() {
        Iterator<VideoData.NewList> iterator = recyclerview_item_collect.iterator();
        VideoData.NewList data;
        while (iterator.hasNext()) {
            data = iterator.next();
            if (data.isSelect()) {
                iterator.remove();
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return recyclerview_item_collect.size();
    }

    public void setData(ArrayList<VideoData.NewList> newLists) {
        recyclerview_item_collect = newLists;
        notifyDataSetChanged();
    }

    private class CollectHolder extends RecyclerView.ViewHolder {

        private final TextView collect_all_titel;
        private final TextView collect_all_time;
        private final TextView collect_all_head;
        private final ImageView collect_all_pic;
        private final CheckBox cb_select;
        private final LinearLayout ll;

        public CollectHolder(View inflate) {
            super(inflate);
            collect_all_titel = inflate.findViewById(R.id.collect_all_titel);
            collect_all_time = inflate.findViewById(R.id.collect_all_time);
            collect_all_head = inflate.findViewById(R.id.collect_all_head);
            collect_all_pic = inflate.findViewById(R.id.collect_all_pic);
            ll = inflate.findViewById(R.id.ll);
            cb_select = inflate.findViewById(R.id.cb_select);


            inflate.setOnClickListener(v ->{
                if(mListener != null){
                    mListener.onItemClick(recyclerview_item_collect,getAdapterPosition());
                }
            });


        }
    }

    public interface OnItemClickListener {
        void onItemClick(List<? extends NewsData.NewsBean> list, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
}
