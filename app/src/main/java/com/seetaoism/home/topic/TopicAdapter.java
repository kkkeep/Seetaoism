package com.seetaoism.home.topic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.mr.k.mvp.utils.SystemFacade;
import com.seetaoism.GlideApp;
import com.seetaoism.GlideRequests;
import com.seetaoism.R;
import com.seetaoism.data.entity.NewsData;
import com.seetaoism.data.entity.TopicData;

import java.util.ArrayList;
import java.util.List;

import jy.com.libbanner.IJBannerAdapter;
import jy.com.libbanner.JBanner;

public class TopicAdapter extends RecyclerView.Adapter {

    private static final int BANNER_VIEW = 0;       //banner
    private static final int NEWSLIST_VIEW = 1;     //下边数据
    private final Context context;
    private OnItemClickListener onItemClickListener;
    public final ArrayList<TopicData.Bannerlist> mBannerlist;
    public final ArrayList<TopicData.Topiclist> mlist;

    public TopicAdapter(Context context, ArrayList<TopicData.Bannerlist> mBannerlist, ArrayList<TopicData.Topiclist> mlist) {

        this.context = context;
        this.mBannerlist = mBannerlist;
        this.mlist = mlist;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == BANNER_VIEW) {
            View view1 = LayoutInflater.from(context).inflate(R.layout.banner_item, parent, false);
            return new BannerView(view1);
        } else {
            View view2 = LayoutInflater.from(context).inflate(R.layout.layout_news_item_news_left, parent, false);
            return new NewsHolder(view2);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == BANNER_VIEW) {
            BannerView holder1 = (BannerView) holder;
            holder1.setData(mBannerlist);
        } else if (itemViewType == NEWSLIST_VIEW) {
            NewsHolder holder1 = (NewsHolder) holder;

            int lastPos = position;
            //
            if (mBannerlist.size() > 0){
                lastPos = position - 1;
            }
            holder1.setData(mlist.get(lastPos));

        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {


        if (position ==BANNER_VIEW) {
            return BANNER_VIEW;
        } else {
            return NEWSLIST_VIEW;
        }
    }

    @Override
    public int getItemCount() {
        //这里其实相当于一个大的recycleview，返回的size，如果bannerlist大于0，他会把下标是0的位置空出来，
        // 如果bannerlist是空的，那么就不会把下标是0的空出来，他会自己顶上去  那他是怎么知道我的banner在第一个
        // 因为如果bannerlist有数据，首先我先在这里把整体的size+1了
        int count = SystemFacade.isListEmpty(mlist) ? 0 : mlist.size();
        if (!SystemFacade.isListEmpty(mBannerlist)) {

            return count + 1;
        } else {
            return count;
        }
    }

    private int getRealPosition(int itemPosition){
        if(SystemFacade.isListEmpty(mBannerlist)){
            return itemPosition;
        }else{
            return itemPosition -1;
        }
    }

    public void addAriticleData(ArrayList<TopicData.Topiclist> list) {
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    public void addBannerData(ArrayList<TopicData.Bannerlist> banner_list) {
        mBannerlist.addAll(banner_list);
        notifyDataSetChanged();
    }

    private class NewsHolder extends BaseHolder {
        private ImageView pic;
        private TextView tvTitle;
        private TextView tvHot;
        private TextView tvLabel;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);

            pic = itemView.findViewById(R.id.detail_item_news_pic);
            tvTitle = itemView.findViewById(R.id.news_item_tv_title);
            tvHot = itemView.findViewById(R.id.news_item_tv_hot);
            tvLabel = itemView.findViewById(R.id.news_item_tv_label);

            itemView.setOnClickListener(v ->{
                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(mlist, getRealPosition(getAdapterPosition()));
                }
            });
        }


        public void setData(TopicData.Topiclist data) {
            mGlide.load(data.getImage_url()).into(pic);
            tvTitle.setText(data.getTheme());
            tvLabel.setText(data.getColumn_name());
        }
    }

    public class BannerView extends BaseHolder {
        private JBanner jBanner;

        private ProgressBar mProgressBar;

        public BannerView(@NonNull View itemView) {
            super(itemView);
            jBanner = itemView.findViewById(R.id.news_item_header_j_banner);
            mProgressBar = itemView.findViewById(R.id.news_item_header_progressbar);



            jBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    if (SystemFacade.hasN()) {
                        mProgressBar.setProgress((position % mBannerlist.size()) + 1, true);
                    } else {
                        mProgressBar.setProgress((position % mBannerlist.size()) + 1);
                    }

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });

            jBanner.setOnBannerItemClickListener((d,p) ->{
                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(mBannerlist, p);
                }
            });

        }

        public void setData(ArrayList<TopicData.Bannerlist> banners) {
            ArrayList<String> titles = new ArrayList<>();

            for (TopicData.Bannerlist banner : banners) {
                titles.add(banner.getTheme());
            }

            mProgressBar.setMax(banners.size());
            jBanner.setData(banners, titles);
            jBanner.setLoop(banners.size() > 1);
            jBanner.setAdapter(new IJBannerAdapter<TopicData.Bannerlist>() {
                @Override
                public void fillBannerItemData(JBanner banner, ImageView imageView, TopicData.Bannerlist mode, int position) {
                    GlideApp.with(imageView.getContext()).load(mode.getImage_url()).into(imageView);
                }
            });
        }
    }


    public abstract class BaseHolder extends RecyclerView.ViewHolder {

        protected GlideRequests mGlide;

        public BaseHolder(@NonNull View itemView) {
            super(itemView);
            mGlide = GlideApp.with(itemView)/*.applyDefaultRequestOptions(RequestOptions.bitmapTransform(new RoundCorner(itemView.getContext(), 4)))*/;
        }
    }

    public static interface OnItemClickListener{
        void onItemClick(List<? extends NewsData.NewsBean> list, int position);
    }
}

