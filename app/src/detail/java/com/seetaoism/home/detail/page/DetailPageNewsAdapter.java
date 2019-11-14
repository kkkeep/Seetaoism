package com.seetaoism.home.detail.page;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mr.k.mvp.kotlin.widget.ToggleStateView;
import com.mr.k.mvp.utils.SystemFacade;
import com.seetaoism.GlideApp;
import com.seetaoism.R;
import com.seetaoism.data.entity.CommentData;
import com.seetaoism.data.entity.NewsData;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.POST;

/*
 * created by taofu on 2019-10-21
 **/
public class DetailPageNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_NEWS = 1;
    private static final int TYPE_COMMENT = 2;


    private DetailItemOnClickListener mItemOnClickListener;

    private List<NewsData.News> mNews;

    private List<CommentData.Comment> mComments;

    public void setNews(List<NewsData.News> news) {
        this.mNews = news;
    }

    public void addComments(List<CommentData.Comment> comments) {
        if (mComments == null) {
            mComments = new ArrayList<>();
        }
        int start = getItemCount();
        mComments.addAll(comments);
        notifyItemRangeChanged(start, comments.size());


    }

    public void addComment(CommentData.Comment comment) {
        if (mComments == null) {
            mComments = new ArrayList<>();
        }
        mComments.add(0, comment);
        notifyItemInserted(mNews.size());

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_NEWS) {
            return new NewsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_news, parent, false));
        } else {
            return new CommentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detial_comment, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == TYPE_NEWS) {
            ((NewsHolder) holder).setData(mNews.get(getRealPosition(position)));
        } else {
            ((CommentHolder) holder).setData(mComments.get(getRealPosition(position)));
        }

    }

    @Override
    public int getItemCount() {
        int count = mNews == null ? 0 : mNews.size();

        if (mComments != null) {
            count += mComments.size();
        }
        return count;
    }

    private int getRealPosition(int itemPosition) {
        if (mNews != null) {
            if (itemPosition < mNews.size()) {
                return itemPosition;
            } else {
                return itemPosition - mNews.size();
            }
        }
        return itemPosition;
    }

    @Override
    public int getItemViewType(int position) {
        if (mNews != null && position < mNews.size()) {
            return TYPE_NEWS;
        }

        return TYPE_COMMENT;

    }


    public CommentData.Comment getCommentByPosition(int position) {
        return mComments.get(getRealPosition(position));
    }

    public boolean hasComments(){
        return !SystemFacade.isListEmpty(mComments);
    }
    public void setItemOnClickListener(DetailItemOnClickListener itemOnClickListener) {
        this.mItemOnClickListener = itemOnClickListener;
    }

    public void addReply(List<CommentData.Reply> reply, int itemPosition) {
        CommentData.Comment comment = mComments.get(getRealPosition(itemPosition));
        if (comment.getReply_list() == null) {
            comment.setReply_list(new ArrayList<>());
        }
        comment.getReply_list().addAll(reply);

        notifyItemChanged(itemPosition);
    }

    public void doLike(int itemPosition) {
        CommentData.Comment comment = mComments.get(getRealPosition(itemPosition));
        comment.setPraise_count_describe(comment.getPraise_count_describe()+1);
        comment.setIs_praise(1);
        notifyItemChanged(itemPosition);
    }

    public class NewsHolder extends RecyclerView.ViewHolder {


        private TextView title;
        private ImageView pic;
        private TextView label;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.detail_item_news_title);
            pic = itemView.findViewById(R.id.detail_comcomt_item_user_head_pic);
            label = itemView.findViewById(R.id.detail_item_news_label);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemOnClickListener != null) {
                        mItemOnClickListener.onNewsClick(mNews, getRealPosition(getAdapterPosition()));
                    }
                }
            });

        }


        public void setData(NewsData.News data) {
            GlideApp.with(itemView).load(data.getImageUrl()).into(pic);
            title.setText(data.getTheme());
            label.setText(data.getColumn_name());
        }

    }


    public class CommentHolder extends RecyclerView.ViewHolder {

        private ImageView pic;
        private TextView tvUserName;
        private TextView tvTime;
        private ToggleStateView cbLike;
        private TextView tvContent;
        private CommentsView commentsView;
        private TextView tvShowMore;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);

            pic = itemView.findViewById(R.id.detail_comcomt_item_user_head_pic);
            tvUserName = itemView.findViewById(R.id.detail_comment_item_tv_username);
            tvTime = itemView.findViewById(R.id.detail_comment_item_tv_time);
            cbLike = itemView.findViewById(R.id.detail_comment_item_cb_lick);
            tvContent = itemView.findViewById(R.id.detail_comment_item_tv_content);
            commentsView = itemView.findViewById(R.id.detail_comment_item_reply);
            tvShowMore = itemView.findViewById(R.id.detail_comment_item_show_more);
            tvShowMore.setVisibility(View.GONE);
            cbLike.setOnClickListener((v)-> {
                CommentData.Comment comment = getCommentByPosition(getAdapterPosition());
                if(comment.getIs_praise() == 1){
                    Toast.makeText(v.getContext(),v.getContext().getString(R.string.text_detail_do_lick_toast), Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( mItemOnClickListener != null) {
                    mItemOnClickListener.onCommentLikeClick(mComments.get(getRealPosition(getAdapterPosition())), getAdapterPosition());
                }
            });
            commentsView.setOnItemClickListener((position, bean) -> {
                if (mItemOnClickListener != null) {
                    mItemOnClickListener.onCommentReplayClick(bean, getAdapterPosition(), position);
                }
            });

            itemView.setOnClickListener(v -> {
                if (mItemOnClickListener != null) {
                    mItemOnClickListener.onCommentClick(mComments.get(getRealPosition(getAdapterPosition())), getAdapterPosition());
                }
            });

            tvShowMore.setOnClickListener(v -> {
                if (mItemOnClickListener != null) {
                    mItemOnClickListener.onLoadMoreReplyClick(mComments.get(getRealPosition(getAdapterPosition())), getAdapterPosition());
                }
            });
        }


        public void setData(CommentData.Comment data) {
            GlideApp.with(itemView).load(data.getHead_url()).into(pic);
            tvUserName.setText(data.getUsername());
            tvTime.setText(data.getTime_describe());
            cbLike.setText(data.getPraise_count_describe() + "");
            cbLike.setChecked(data.getIs_praise() == 1);
            tvContent.setText(data.getContent());

           cbLike.setChecked( data.getIs_praise() == 1);
            commentsView.setList(data.getReply_list());

            commentsView.notifyDataSetChanged();
            if (data.getReply_more() == 1) {
                tvShowMore.setVisibility(View.VISIBLE);
            } else {
                tvShowMore.setVisibility(View.GONE);
            }


        }
    }


    public static interface DetailItemOnClickListener {

        void onNewsClick(List<NewsData.News> newsList, int realPosition);

        void onCommentClick(CommentData.Comment comment, int itemPosition);

        void onCommentReplayClick(CommentData.Reply reply, int itemPosition, int replayPosition);

        void onLoadMoreReplyClick(CommentData.Comment comment, int itemPosition);

        void onCommentLikeClick(CommentData.Comment comment, int itemPosition);
    }

}


