package com.seetaoism.home.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
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
import com.seetaoism.GlideApp;
import com.seetaoism.R;
import com.seetaoism.data.entity.NewsData;
import com.seetaoism.data.entity.VideoData;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;


public class VideoAdapter extends RecyclerView.Adapter {

    public static final String VIDEO_TAG = "VideoAdapter";
    public ArrayList<VideoData.NewList> mlist;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;


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
        ((VideoHolder) holder).bindVideo(data);
    }
    @Override
    public int getItemCount() {
        return mlist.size() > 0 ? mlist.size() : 0;
    }

    public void addData(ArrayList<VideoData.NewList> list) {
        mlist.addAll(list);
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    class VideoHolder extends RecyclerView.ViewHolder {


        private final ImageView item_pic;
        private final ImageView item_bac;
        private final TextView item_title;
        private final TextView item_content;
        private final SampleCoverVideo sampleCoverVideo;
        //private ImageView ivCover;
        GSYVideoOptionBuilder gsyVideoOptionBuilder;

        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
            item_bac = itemView.findViewById(R.id.videoplayer);
            item_pic = itemView.findViewById(R.id.video_play);
            item_title = itemView.findViewById(R.id.them);
            item_content = itemView.findViewById(R.id.descrip);
           /* ivCover = new ImageView(itemView.getContext());
            ivCover.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ivCover.setBackgroundColor(Color.WHITE);
*/

            sampleCoverVideo = itemView.findViewById(R.id.video_gsypalyer);
            //增加title
           //c sampleCoverVideo.getTitleTextView().setVisibility(View.GONE);

           // ((ViewGroup)sampleCoverVideo.getTitleTextView().getParent()).setVisibility(View.INVISIBLE);

            // gsyVideoPlayer.getStartButton().setBackgroundResource(R.drawable.exo_icon_play);
            //设置返回键
            sampleCoverVideo.getBackButton().setVisibility(View.GONE);

            //设置全屏按键功能
            sampleCoverVideo.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resolveFullBtn(sampleCoverVideo);
                }
            });
           // ((ViewGroup)sampleCoverVideo.getThumbImageViewLayout().getParent()).setBackgroundColor(itemView.getContext().getResources().getColor(R.color.white));
            itemView.setOnClickListener(v ->{
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(mlist, getAdapterPosition());
                }
            });

        }



        private void bindVideo(VideoData.NewList data){


          //  GlideApp.with(itemView).load(data.getImage_url()).into(ivCover);
            sampleCoverVideo.loadCoverImage(data.getImage_url(),0);
            gsyVideoOptionBuilder
                    .setIsTouchWiget(false)
                   // .setThumbImageView(ivCover)
                    .setUrl(data.getVideo_url())
                    .setCacheWithPlay(false)
                    .setRotateViewAuto(true)
                    .setLockLand(true)
                    .setPlayTag(VIDEO_TAG)
                    .setShowFullAnimation(false)
                    .setNeedLockFull(true)
                    .setPlayPosition(getAdapterPosition())
                    .setVideoAllCallBack(new GSYSampleCallBack() {
                        @Override
                        public void onPrepared(String url, Object... objects) {
                            super.onPrepared(url, objects);
                            /*if (!sampleCoverVideo.isIfCurrentIsFullscreen()) {
                                //静音
                                GSYVideoManager.instance().setNeedMute(true);
                            }*/

                        }

                        @Override
                        public void onQuitFullscreen(String url, Object... objects) {
                            super.onQuitFullscreen(url, objects);
                            //全屏不静音
                           // GSYVideoManager.instance().setNeedMute(true);
                        }

                        @Override
                        public void onEnterFullscreen(String url, Object... objects) {
                            super.onEnterFullscreen(url, objects);
                            GSYVideoManager.instance().setNeedMute(false);
                            sampleCoverVideo.getCurrentPlayer().getTitleTextView().setText((String)objects[0]);
                        }
                    }).build(sampleCoverVideo);
        }
        /**
         * 全屏幕按键处理
         */
        private void resolveFullBtn(final StandardGSYVideoPlayer standardGSYVideoPlayer) {
            standardGSYVideoPlayer.startWindowFullscreen(itemView.getContext(), true, true);
        }

    }
// 见道

    public static interface OnItemClickListener{
        void onItemClick(List<? extends NewsData.NewsBean> list, int position);
    }
}
