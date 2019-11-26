package com.seetaoism.home.message;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.seetaoism.R;
import com.seetaoism.data.entity.NoticedetailsBean;

import java.util.List;


public class NoticedatilsRecAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NoticedetailsBean> mData;
    private Context mContext;
    private setOnItemCLick mListener;

    public NoticedatilsRecAdapter(List<NoticedetailsBean> mList) {
        this.mData = mList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        if (i == 1) {//系统通知systematic notification
            return new SystemacticHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_itme_systematic, viewGroup, false));
        } else if (i == 2) {//评论被回复通知
            return new CommentBackHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_itme_commentback, viewGroup, false));
        } else if (i == 3) {//评论被点赞通知
            return new CommentLikeHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_itme_commentlike, viewGroup, false));
        } else if (i == 4) {//文章被点赞
            return new ArticleLikeHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_itme_articlelike, viewGroup, false));
        } else if (i == 5) {//文章被评论
            return new ArticleCommentHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_itme_articlecomment, viewGroup, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        SystemacticHolder systemacticHolder = null;
        CommentBackHolder commentBackHolder = null;
        CommentLikeHolder commentLikeHolder = null;
        ArticleLikeHolder articleLikeHolder = null;
        ArticleCommentHolder articleCommentHolder = null;
        int viewType = getItemViewType(i);
        if (viewType == 1) {
            systemacticHolder = (SystemacticHolder) viewHolder;
            systemacticHolder.setData(systemacticHolder, i);
        } else if (viewType == 2) {
            commentBackHolder = (CommentBackHolder) viewHolder;
            commentBackHolder.setData(commentBackHolder, i);
        } else if (viewType == 3) {
            commentLikeHolder = (CommentLikeHolder) viewHolder;
            commentLikeHolder.setData(commentLikeHolder, i);
        } else if (viewType == 4) {
            articleLikeHolder = (ArticleLikeHolder) viewHolder;
            articleLikeHolder.setData(articleLikeHolder, i);
        } else if (viewType == 5) {
            articleCommentHolder = (ArticleCommentHolder) viewHolder;
            articleCommentHolder.setData(articleCommentHolder, i);
        }
    }

    public void cleanData() {
        this.mData.clear();
        notifyDataSetChanged();
    }


    class SystemacticHolder extends RecyclerView.ViewHolder {
        TextView notice_content;
        TextView notice_time;

        public SystemacticHolder(View itemView) {
            super(itemView);
            notice_content=itemView.findViewById(R.id.notice_content);
            notice_time=itemView.findViewById(R.id.notice_time);
        }

        public void setData(SystemacticHolder systemacticHolder, int i) {
            systemacticHolder.notice_content.setText(mData.get(i).getContent());
            systemacticHolder.notice_time.setText(mData.get(i).getTime());
        }
    }

    class CommentBackHolder extends RecyclerView.ViewHolder {
        LinearLayout comment_link;
        ImageView comment_reply_user_head_url;
        TextView comment_reply_user_name;
        TextView comment_reply_time;
        TextView by_reply_user_name;
        TextView comment_reply_content;
        TextView comment_by_reply_user_name;
        TextView comment_by_reply_content;
        TextView comment_detele;
        ImageView comment_article_image_url;
        TextView comment_article_theme;
        TextView comment_article_time;

        public CommentBackHolder(View itemView) {
            super(itemView);

            comment_link = itemView.findViewById(R.id.comment_link);
            comment_reply_user_head_url=itemView.findViewById(R.id.comment_reply_user_head_url);
            comment_reply_user_name=itemView.findViewById(R.id.comment_reply_user_name);
            comment_reply_time=itemView.findViewById(R.id.comment_reply_time);
            by_reply_user_name=itemView.findViewById(R.id.by_reply_user_name);
            comment_reply_content=itemView.findViewById(R.id.comment_reply_content);
            comment_by_reply_user_name=itemView.findViewById(R.id.comment_by_reply_user_name);
            comment_by_reply_content=itemView.findViewById(R.id.comment_by_reply_content);
            comment_detele=itemView.findViewById(R.id.comment_detele);
            comment_article_image_url=itemView.findViewById(R.id.comment_article_image_url);
            comment_article_theme=itemView.findViewById(R.id.comment_article_theme);
            comment_article_time=itemView.findViewById(R.id.comment_article_time);

            comment_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(v.getContext(), "aa", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setData(CommentBackHolder commentBackHolder, int i) {
            Glide.with(mContext).load(mData.get(i).getReply_user_head_url()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(commentBackHolder.comment_reply_user_head_url);
            commentBackHolder.comment_reply_user_name.setText(mData.get(i).getReply_user_name());
            commentBackHolder.by_reply_user_name.setText(mData.get(i).getBy_reply_user_name() + " :");
            commentBackHolder.comment_by_reply_user_name.setText(mData.get(i).getBy_reply_user_name() + " :");
            Glide.with(mContext).load(mData.get(i).getArticle_image_url()).into(commentBackHolder.comment_article_image_url);
            commentBackHolder.comment_article_theme.setText(mData.get(i).getArticle_theme());
            commentBackHolder.comment_article_time.setText(mData.get(i).getArticle_time());
            commentBackHolder.comment_reply_content.setText(mData.get(i).getReply_content());
            commentBackHolder.comment_by_reply_content.setText(mData.get(i).getBy_reply_content());
            commentBackHolder.comment_reply_time.setText(mData.get(i).getReply_time());
            commentBackHolder.comment_detele.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.setOnLinkListener(i, mData.get(i));
                    }
                }
            });
        }
    }

    class CommentLikeHolder extends RecyclerView.ViewHolder {
        LinearLayout comment_link;
        ImageView comment_reply_user_head_url;
        TextView comment_reply_user_name;
        TextView comment_reply_time;
        TextView comment_by_reply_user_name;
        TextView comment_by_reply_content;
        TextView comment_detele;
        ImageView comment_article_image_url;
        TextView comment_article_theme;
        TextView comment_article_time;
        TextView comment_count;
        TextView content;

        public CommentLikeHolder(View itemView) {
            super(itemView);
            comment_link=itemView.findViewById(R.id.comment_link);
            comment_reply_user_head_url=itemView.findViewById(R.id.comment_reply_user_head_url);
            comment_reply_user_name=itemView.findViewById(R.id.comment_reply_user_name);
            comment_reply_time=itemView.findViewById(R.id.comment_reply_time);
            comment_by_reply_user_name=itemView.findViewById(R.id.comment_by_reply_user_name);
            comment_by_reply_content=itemView.findViewById(R.id.comment_by_reply_content);
            comment_detele=itemView.findViewById(R.id.comment_detele);
            comment_article_image_url=itemView.findViewById(R.id.comment_article_image_url);
            comment_article_theme=itemView.findViewById(R.id.comment_article_theme);
            comment_article_time=itemView.findViewById(R.id.comment_article_time);
            comment_count=itemView.findViewById(R.id.comment_count);
            content=itemView.findViewById(R.id.content);
        }

        public void setData(CommentLikeHolder commentLikeHolder, int i) {
            Glide.with(mContext).load(mData.get(i).getPraise_user_head_url()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(commentLikeHolder.comment_reply_user_head_url);
            commentLikeHolder.comment_reply_user_name.setText(mData.get(i).getPraise_user_name() + " :");
            commentLikeHolder.comment_by_reply_user_name.setText(mData.get(i).getComment_user_name() + " :");
            Glide.with(mContext).load(mData.get(i).getArticle_image_url()).into(commentLikeHolder.comment_article_image_url);
            commentLikeHolder.content.setText(mData.get(i).getPraise_content());
            commentLikeHolder.comment_article_theme.setText(mData.get(i).getArticle_theme());
            commentLikeHolder.comment_article_time.setText(mData.get(i).getArticle_time());
            commentLikeHolder.comment_by_reply_content.setText(mData.get(i).getComment_content());
            commentLikeHolder.comment_reply_time.setText(mData.get(i).getPraise_time());
            commentLikeHolder.comment_count.setText(mData.get(i).getComment_praise_count() + "人赞过");
            commentLikeHolder.comment_detele.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mListener != null) {
                        mListener.setOnLinkListener(i, mData.get(i));
                    }
                }
            });
        }
    }

    class ArticleLikeHolder extends RecyclerView.ViewHolder {
        LinearLayout comment_link;
        ImageView comment_reply_user_head_url;
        TextView comment_reply_user_name;
        TextView comment_reply_time;
        TextView comment_detele;
        ImageView comment_article_image_url;
        TextView comment_article_theme;
        TextView comment_article_time;
        TextView comment_count;
        TextView content;

        public ArticleLikeHolder(View itemView) {
            super(itemView);
            comment_link=itemView.findViewById(R.id.comment_link);
            comment_reply_user_head_url= itemView.findViewById(R.id.comment_reply_user_head_url);
            comment_reply_user_name=itemView.findViewById(R.id.comment_reply_user_name);
            comment_reply_time=itemView.findViewById(R.id.comment_reply_time);
            comment_detele=itemView.findViewById(R.id.comment_detele);
            comment_article_image_url=itemView.findViewById(R.id.comment_article_image_url);
            comment_article_theme=itemView.findViewById(R.id.comment_article_theme);
            comment_article_time=itemView.findViewById(R.id.comment_article_time);
            comment_count=itemView.findViewById(R.id.comment_count);
            content=itemView.findViewById(R.id.content);

        }

        public void setData(ArticleLikeHolder articleLikeHolder, int i) {
            Glide.with(mContext).load(mData.get(i).getPraise_user_head_url()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(articleLikeHolder.comment_reply_user_head_url);
            articleLikeHolder.comment_reply_user_name.setText(mData.get(i).getPraise_user_name() + " :");
            Glide.with(mContext).load(mData.get(i).getArticle_image_url()).into(articleLikeHolder.comment_article_image_url);
            articleLikeHolder.comment_article_theme.setText(mData.get(i).getArticle_theme());
            articleLikeHolder.content.setText(mData.get(i).getPraise_content());
            articleLikeHolder.comment_article_time.setText(mData.get(i).getArticle_time());
            articleLikeHolder.comment_reply_time.setText(mData.get(i).getPraise_time());
            articleLikeHolder.comment_count.setText(mData.get(i).getArticle_praise_count() + "人赞过");
            articleLikeHolder.comment_detele.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mListener != null) {
                        mListener.setOnLinkListener(i, mData.get(i));
                    }
                }
            });
        }
    }

    class ArticleCommentHolder extends RecyclerView.ViewHolder {
        LinearLayout comment_link;
        TextView comment_detele;
        ImageView comment_article_image_url;
        TextView comment_article_theme;
        TextView comment_article_time;
        ImageView comment_reply_user_head_url;
        TextView comment_reply_user_name;
        TextView comment_reply_time;
        TextView content;

        public ArticleCommentHolder(View itemView) {
            super(itemView);
            comment_link=itemView.findViewById(R.id.comment_link);
            comment_detele=itemView.findViewById(R.id.comment_detele);
            comment_article_image_url=itemView.findViewById(R.id.comment_article_image_url);
            comment_article_theme=itemView.findViewById(R.id.comment_article_theme);
            comment_article_time=itemView.findViewById(R.id.comment_article_time);
            comment_reply_user_head_url=itemView.findViewById(R.id.comment_reply_user_head_url);
            comment_reply_user_name=itemView.findViewById(R.id.comment_reply_user_name);
            comment_reply_time=itemView.findViewById(R.id.comment_reply_time);
            content=itemView.findViewById(R.id.content);

        }

        public void setData(ArticleCommentHolder articleCommentHolder, int i) {
            Glide.with(mContext).load(mData.get(i).getComment_user_head_url()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(articleCommentHolder.comment_reply_user_head_url);
            articleCommentHolder.comment_reply_user_name.setText(mData.get(i).getComment_user_name() + " :");
            Glide.with(mContext).load(mData.get(i).getArticle_image_url()).into(articleCommentHolder.comment_article_image_url);
            articleCommentHolder.content.setText(mData.get(i).getComment_content());
            articleCommentHolder.comment_article_theme.setText(mData.get(i).getArticle_theme());
            articleCommentHolder.comment_article_time.setText(mData.get(i).getArticle_time());
            articleCommentHolder.comment_reply_time.setText(mData.get(i).getComment_time());
            articleCommentHolder.comment_detele.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mListener != null) {
                        mListener.setOnLinkListener(i, mData.get(i));
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.size() != 0) {
            return Integer.parseInt(mData.get(position).getFrom_type());
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (mData.size() != 0) {
            return mData.size();
        }
        return 0;
    }

    public interface setOnItemCLick {
        void setOnLinkListener(int position,NoticedetailsBean mData);
    }

    public void setOnItemCLick(setOnItemCLick setOnItemCLick) {
        this.mListener = setOnItemCLick;
    }
}
