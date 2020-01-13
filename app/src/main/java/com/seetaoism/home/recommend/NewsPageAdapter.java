package com.seetaoism.home.recommend;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.mr.k.mvp.utils.Logger;
import com.mr.k.mvp.utils.SystemFacade;
import com.seetaoism.GlideApp;
import com.seetaoism.GlideRequests;
import com.seetaoism.R;
import com.seetaoism.data.entity.NewsData;
import com.seetaoism.data.entity.VideoData;
import com.seetaoism.home.video.SampleCoverVideo;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import jy.com.libbanner.IJBannerAdapter;
import jy.com.libbanner.JBanner;
import jy.com.libbanner.MarqueeView;


public class NewsPageAdapter extends RecyclerView.Adapter<NewsPageAdapter.BaseHolder> {

    public String VIDEO_TAG = "NewsPageAdapter " + hashCode();

    private static final String TAG = "NewsPageAdapter";

    private static final int TYPE_HEADER = 0x100; // banner
    private static final int TYPE_NEWS_FLASH = 5; // 快讯
    private static final int TYPE_NEWS_VIDEO = 4; // 视频类
    private static final int TYPE_NEWS_SPECIAL = 2; // 特写
    private static final int TYPE_NEWS_LEFT = 1; // 普通新闻左图
    private static final int TYPE_NEWS_RIGHT = 3; // 普通新闻右图

    //'视图类型：1左图，2中间大图，3右图，4视频，5即时',

    private List<NewsData.Banner> mBanners;
    private List<NewsData.Flash> mFlashes;
    private List<NewsData.News> mNews;


    private OnItemClickListener mOnItemClickListener;


    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Class<? extends BaseHolder> aClass = null;
        int rId = 0;

        switch (viewType) {
            case TYPE_HEADER: {
                aClass = HeaderHolder.class;
                //banner
                rId = R.layout.layout_header_banner;
                break;
            }
            case TYPE_NEWS_VIDEO: {
                //视频
                aClass = VideoNewsHolder.class;
                rId = R.layout.layout_news_item_video;
                break;
            }
            case TYPE_NEWS_SPECIAL: {
                aClass = SpecialNewsHolder.class;
                rId = R.layout.layout_news_item_special;
                break;
            }
            case TYPE_NEWS_FLASH: {
                aClass = FlashNewsHolder.class;
                rId = R.layout.layout_news_item_flash;
                break;
            }
            case TYPE_NEWS_RIGHT: {
                aClass = NewsHolderRight.class;
                rId = R.layout.layout_news_item_news_right;
                break;
            }
            case  TYPE_NEWS_LEFT: {
                aClass = NewsHolderLeft.class;
                rId = R.layout.layout_news_item_news_left;
            }
        }


        try {
            Constructor<? extends BaseHolder> constructor = aClass.getConstructor(NewsPageAdapter.class, View.class);
            return constructor.newInstance(this, LayoutInflater.from(parent.getContext()).inflate(rId, parent, false));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == TYPE_HEADER) {
            ((HeaderHolder) holder).setData(mBanners, mFlashes);
        } else {
            holder.setData(mNews.get(getRealPosition(position)));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && !SystemFacade.isListEmpty(mBanners)) {
            return TYPE_HEADER;
        }


        if (!SystemFacade.isListEmpty(mBanners)) {
            position--;
        }

        return mNews.get(position).getView_type();

    }

    private int getRealPosition(int position) {
        if (!SystemFacade.isListEmpty(mBanners)) {
            position--;
        }
        return position;
    }

    @Override
    public int getItemCount() {
        int count = 0;

        if (!SystemFacade.isListEmpty(mBanners)) {
            count++;
        }

        if (!SystemFacade.isListEmpty(mNews)) {
            count = count + mNews.size();
        }

        return count;

    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setData(List<NewsData.Banner> mBanners, List<NewsData.Flash> mFlashes, List<NewsData.News> mNews) {
        refresh(mBanners, mFlashes, mNews);
    }

    public void refresh(List<NewsData.Banner> banners, List<NewsData.Flash> flashes, List<NewsData.News> news) {
        mNews = news;
        mBanners = banners;
        mFlashes = flashes;
        notifyDataSetChanged();
    }

    public List<NewsData.News> getNewsData() {
        return mNews;
    }
    public List<NewsData.Flash> getFlashData() {
        return mFlashes;
    }
    public List<NewsData.Banner> getBannerData() {
        return mBanners;
    }

    public void loadMore(List<NewsData.News> articleList) {
        mNews.addAll(articleList);
        notifyItemRangeChanged(getItemCount(), articleList.size());
    }

    public abstract class BaseHolder extends RecyclerView.ViewHolder {
        protected GlideRequests mGlide;

        public BaseHolder(@NonNull View itemView) {
            super(itemView);
            mGlide = GlideApp.with(itemView)/*.applyDefaultRequestOptions(RequestOptions.bitmapTransform(new RoundCorner(itemView.getContext(), 4)))*/;

            if (!(this instanceof HeaderHolder)) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener != null) {
                            int position = getAdapterPosition();
                            int realPosition = getRealPosition(position);
                            mOnItemClickListener.onClick(mNews.get(realPosition), realPosition);
                        }
                    }
                });
            }
        }


        protected abstract void setData(NewsData.News t);


    }

    private class HeaderHolder extends BaseHolder {

        private JBanner jBanner;

        private Group mFlashGroup;

        private MarqueeView mTvFlash;

        private ProgressBar mProgressBar;

        private ImageView mTvFlashimg;


        public HeaderHolder(@NonNull View itemView) {
            super(itemView);
            jBanner = itemView.findViewById(R.id.news_item_header_j_banner);
            mFlashGroup = itemView.findViewById(R.id.news_item_header_flash_layout);
            mTvFlash = itemView.findViewById(R.id.news_item_header_tv_flash);
            mTvFlashimg = itemView.findViewById(R.id.news_item_header_iv_flash_more);

            mProgressBar = itemView.findViewById(R.id.news_item_header_progressbar);


           mTvFlash.setOnMarqueeTextClickListener(new MarqueeView.OnMarqueeTextClickListener<MarqueeView.MarqueeData>() {
               @Override
               public void onClick(MarqueeView.MarqueeData data, int position) {
                   if(mOnItemClickListener != null){
                       mOnItemClickListener.onClick((NewsData.Flash)data, position);
                   }
               }
           });


            jBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    if (SystemFacade.hasN()) {
                        mProgressBar.setProgress((position % mBanners.size()) + 1, true);
                    } else {
                        mProgressBar.setProgress((position % mBanners.size()) + 1);
                    }

                    Logger.d("%s position = %s", (position % mBanners.size()), TAG);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });


            jBanner.setOnBannerItemClickListener((JBanner.OnBannerItemClickListener<NewsData.Banner>) (banner, position) -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(banner, position);
                }
            });


        }

        @Override
        protected void setData(NewsData.News t) {

        }


        public void setData(List<NewsData.Banner> banners, List<NewsData.Flash> flashes) {
            ArrayList<String> titles = new ArrayList<>();

            for (NewsData.Banner banner : banners) {
                titles.add(banner.getTheme());
            }

            mProgressBar.setMax(banners.size());


            jBanner.setData(banners, titles);
            jBanner.setLoop(banners.size() > 1);
            jBanner.setAdapter(new IJBannerAdapter<NewsData.Banner>() {
                @Override
                public void fillBannerItemData(JBanner banner, ImageView imageView, NewsData.Banner mode, int position) {
                    GlideApp.with(imageView.getContext()).load(mode.getImageUrl()).into(imageView);
                }
            });

            if (SystemFacade.isListEmpty(flashes)) {
                mFlashGroup.setVisibility(View.GONE);
            } else {
                mFlashGroup.setVisibility(View.VISIBLE);
                mTvFlash.setClickableText(flashes);

            }


        }

    }

    private class NewsHolderLeft extends BaseHolder {
        private ImageView pic;
        private TextView tvTitle;
        private TextView tvHot;
        private TextView tvLabel;

        public NewsHolderLeft(@NonNull View itemView) {
            super(itemView);

            pic = itemView.findViewById(R.id.detail_item_news_pic);
            tvTitle = itemView.findViewById(R.id.news_item_tv_title);
            tvHot = itemView.findViewById(R.id.news_item_tv_hot);
            tvLabel = itemView.findViewById(R.id.news_item_tv_label);


        }


        public void setData(NewsData.News data) {
            mGlide.load(data.getImageUrl()).into(pic);
            tvTitle.setText(data.getTheme());
            tvLabel.setText(data.getColumn_name());
        }
    }

    private class NewsHolderRight extends NewsHolderLeft {


        public NewsHolderRight(@NonNull View itemView) {
            super(itemView);
        }
    }

    private class FlashNewsHolder extends BaseHolder {

        private TextView tvTitle;
        private TextView tvContent;
        private TextView tvTime;
        private ImageView ivShare;


        public FlashNewsHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.news_item_flash_tv_title);
            tvContent = itemView.findViewById(R.id.news_item_flash_tv_content);
            tvTime = itemView.findViewById(R.id.news_item_flash_tv_time);
            ivShare = itemView.findViewById(R.id.news_item_flash_iv_share);

            ivShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(mOnItemClickListener != null){
                       mOnItemClickListener.onShareAction(mNews.get(getAdapterPosition()), getAdapterPosition());
                   }
                }
            });


        }

        public void setData(NewsData.News data) {

            tvTitle.setText("[ "+data.getTheme()+" ]");
            tvContent.setText(data.getContent());
            tvTime.setText(data.getEditTime());
        }
    }

    private class VideoNewsHolder extends BaseHolder {

        private ImageView ivCover;
        private TextView tvTitle;
        private TextView tvLabel;
        private  SampleCoverVideo sampleCoverVideo;
        GSYVideoOptionBuilder gsyVideoOptionBuilder;

        public VideoNewsHolder(@NonNull View itemView) {
            super(itemView);
            gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
            ivCover = itemView.findViewById(R.id.news_item_video_im_cover);
            tvTitle = itemView.findViewById(R.id.news_item_video_tv_title);
            tvLabel = itemView.findViewById(R.id.news_item_video_tv_label);
            sampleCoverVideo = itemView.findViewById(R.id.news_item_video_gsypalyer);

            sampleCoverVideo.getBackButton().setVisibility(View.GONE);

            //设置全屏按键功能
            sampleCoverVideo.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resolveFullBtn(sampleCoverVideo);
                }
            });
        }

        public void setData(NewsData.News data) {
            tvTitle.setText(data.getTheme());
            mGlide.load(data.getImageUrl()).into(ivCover);
            tvLabel.setText(data.getColumn_name());

            if(!TextUtils.isEmpty(data.getVideoUrl())){
                bindVideo(data);
            }

        }

        private void bindVideo(NewsData.News data){


            //  GlideApp.with(itemView).load(data.getImage_url()).into(ivCover);
            sampleCoverVideo.loadCoverImage(data.getImageUrl(),0);
            gsyVideoOptionBuilder
                    .setIsTouchWiget(false)
                    // .setThumbImageView(ivCover)
                    .setUrl(data.getVideoUrl())
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

    private class SpecialNewsHolder extends BaseHolder {
        private ImageView ivCover;
        private TextView tvTitle;
        private TextView tvLabel;

        public SpecialNewsHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.news_item_special_pic);
            tvTitle = itemView.findViewById(R.id.news_item_special_tv_title);
            tvLabel = itemView.findViewById(R.id.news_item_special_tv_label);
        }

        public void setData(NewsData.News data) {
            tvTitle.setText(data.getTheme());
            mGlide.load(data.getImageUrl()).into(ivCover);
            tvLabel.setText(data.getColumn_name());

        }
    }


    public interface OnItemClickListener {
        void onClick(NewsData.NewsBean news, int position);

        void onShareAction(NewsData.NewsBean news, int position);
    }
}
