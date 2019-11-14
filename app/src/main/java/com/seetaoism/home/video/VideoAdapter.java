package com.seetaoism.home.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.seetaoism.R;
import com.seetaoism.data.entity.NewList;
import com.seetaoism.data.entity.VideoData;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter {

    public ArrayList<VideoData.NewList> mlist;
    private Context mContext;

    public VideoAdapter(ArrayList<VideoData.NewList> mlist, Context mContext) {
        this.mlist = mlist;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.video_item_layout, parent, false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VideoHolder openViewHolder = (VideoHolder) holder;
        VideoData.NewList data = mlist.get(position);
        openViewHolder.item_content.setText(data.getDescription());
        TextPaint paint = openViewHolder.item_title.getPaint();
        paint.setFakeBoldText(true);
        openViewHolder.item_title.setText(data.getTheme());
        RequestOptions options = new RequestOptions()
                .centerCrop();//高版本的Glide得这样
        Glide.with(mContext).asBitmap().load(mlist.get(position).getImage_url()).apply(options).into(openViewHolder.item_bac);


    }
    @Override
    public int getItemCount() {
        return mlist.size() > 0 ? mlist.size() : 0;
    }

    public void addData(ArrayList<VideoData.NewList> list) {
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    class VideoHolder extends RecyclerView.ViewHolder {


        private final ImageView item_pic;
        private final ImageView item_bac;
        private final TextView item_title;
        private final TextView item_content;

        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            item_bac = itemView.findViewById(R.id.videoplayer);
            item_pic = itemView.findViewById(R.id.video_play);
            item_title = itemView.findViewById(R.id.them);
            item_content = itemView.findViewById(R.id.descrip);

        }
    }


}
