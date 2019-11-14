package com.seetaoism.home.message;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seetaoism.R;
import com.seetaoism.data.entity.MessageData;

import java.util.ArrayList;
import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<MessageData.MessageList> mList;
    private Context mContext;
    private onItemClickListener listener;

    public MessageAdapter(Context mContext) {
        this.mContext = mContext;
        this.mList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_notice_list, parent, false);
        return new NoticeHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NoticeHolder) {
            ((NoticeHolder) holder).mNoticeContent.setText(mList.get(position).getContent());
            ((NoticeHolder) holder).mNoticeTime.setText(mList.get(position).getTime());
            int is_read = mList.get(position).getIs_read();
            if (is_read == 0) { // 未读
                ((NoticeHolder) holder).tag.setVisibility(View.VISIBLE);
                ((NoticeHolder) holder).tag.setColorFilter(Color.RED);
            } else if (is_read == 1) { // 已读
                ((NoticeHolder) holder).tag.setVisibility(View.GONE);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.setonItemClickListener(v,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
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

        NoticeHolder(View view) {
            super(view);
            mNoticeContent = view.findViewById(R.id.notice_content);
            mNoticeTime = view.findViewById(R.id.notice_time);
            tag = view.findViewById(R.id.tag);
        }
    }

    public interface onItemClickListener{
        void setonItemClickListener(View view,int position);
    }
    public void setonItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }
}
