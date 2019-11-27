package com.seetaoism.home.message;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seetaoism.R;
import com.seetaoism.data.entity.MessageData;
import com.seetaoism.data.entity.VideoData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<MessageData.MessageList> mList;
    private Context mContext;
    private onItemClickListener listener;
    private static final int MYLIVE_MODE_CHECK = 0;
    int mEditMode = MYLIVE_MODE_CHECK;

    public MessageAdapter(Context mContext) {
        this.mContext = mContext;
        this.mList = new ArrayList<>();
    }
    public void setEditMode(int editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_notice_list, parent, false);
        return new NoticeHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NoticeHolder holder1 = (NoticeHolder) holder;
        holder1.mNoticeContent.setText(mList.get(position).getContent());
        holder1.mNoticeTime.setText(mList.get(position).getTime());
            int is_read = mList.get(position).getIs_read();
            if (is_read == 0) { // 未读

                if(mEditMode == MYLIVE_MODE_CHECK){
                    holder1.tag.setVisibility(View.VISIBLE);
                }else{
                    holder1.tag.setVisibility(View.GONE);
                }

                holder1.tag.setColorFilter(Color.RED);

            } else if (is_read == 1) { // 已读
                holder1.tag.setVisibility(View.GONE);
            }

        holder1.setIsRecyclable(false); // 为了条目不复用
            MessageData.MessageList bean = mList.get(position);
            if (mEditMode == MYLIVE_MODE_CHECK) {
                holder1.cb_select.setVisibility(View.GONE);
            } else {
                holder1.cb_select.setVisibility(View.VISIBLE);
                if (bean.isSelect()) {
                    holder1.cb_select.setChecked(true);
                } else {
                    holder1.cb_select.setChecked(false);
                }
            }
        holder1.cb_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    CheckBox cb = (CheckBox) view;
                    if (bean.isSelect()) {
                        cb.setChecked(false);
                        bean.setSelect(false);
                    } else {
                        cb.setChecked(true);
                        bean.setSelect(true);
                    }
                    listener.setonItemClickListener(view, position);
                }
            }
        });
        holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        if (holder1.cb_select.isChecked()) {
                            holder1.cb_select.setChecked(false);
                            bean.setSelect(false);
                        } else {
                            holder1.cb_select.setChecked(true);
                            bean.setSelect(true);
                        }
                        listener.setonItemClickListener(v,position);
                    }
                }
            });
    }
    public void deleteSuccess() {
        Iterator<MessageData.MessageList> iterator = mList.iterator();
        MessageData.MessageList data;
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
        return mList != null ? mList.size() : 0;
    }
    public ArrayList<MessageData.MessageList> getSelectedNews() {
        ArrayList<MessageData.MessageList> list = new ArrayList<>();
        if (mList != null) {
            for (MessageData.MessageList data : mList) {
                if (data.isSelect()) {
                    list.add(data);
                }
            }
        }
        return list;
    }
    public void setData(List<MessageData.MessageList> noticeBeans) {
        for (MessageData.MessageList n : noticeBeans) {
            if (!mList.contains(n)) {
                mList.add(n);
            }
        }
        notifyDataSetChanged();
    }

    public void cleanData() {
        this.mList.clear();
        notifyDataSetChanged();
    }

    static class NoticeHolder extends RecyclerView.ViewHolder {

        TextView mNoticeContent;
        TextView mNoticeTime;
        ImageView tag;
        CheckBox cb_select;



        NoticeHolder(View view) {
            super(view);
            mNoticeContent = view.findViewById(R.id.notice_content);
            mNoticeTime = view.findViewById(R.id.notice_time);
            tag = view.findViewById(R.id.tag);
            cb_select = view.findViewById(R.id.cb_select);
        }
    }

    public interface onItemClickListener{
        void setonItemClickListener(View view,int position);
    }
    public void setonItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }
}
