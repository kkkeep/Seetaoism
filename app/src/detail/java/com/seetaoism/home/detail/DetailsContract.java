package com.seetaoism.home.detail;

import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.base.IBasePresenter;
import com.mr.k.mvp.base.IBaseView;
import com.mr.k.mvp.base.ICancelBaseCallBack;
import com.seetaoism.data.entity.AccessArticleData;
import com.seetaoism.data.entity.CommentData;
import com.seetaoism.data.entity.CommentReplyData;
import com.seetaoism.data.entity.NewsAttribute;
import com.seetaoism.data.entity.NewsData;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.List;
import java.util.Map;

/*
 * created by Cherry on 2019-10-21
 **/
public interface DetailsContract {

    public interface IDetailPageView extends IBaseView<IDetailPagePresenter> {

        void onArticleListResult(List<NewsData.News> data,String msg);

        void onArticleUserCommentResult(CommentData commentData,String msg);
        void onArticleUserCommentReplyResult(CommentReplyData commentReplyData,String msg);

        void onCommentResult(CommentData.Comment comment,String msg);

        void onReplyCommentResult(CommentData.Reply reply,String msg);

        void onDoLikeResult(String content,String msg);

        void onReadArticleFroIntegralResult(String data, String msg);

    }


    public interface IDetailVpView extends IBaseView<IDetailVpPresenter>{
        void onArticleAttributeResult(NewsAttribute newsAttribute,String errorMsg);
        void onDoArticleLikeResult(String data,String msg);
        void onDoArticleCollectResult(String data,String msg);


    }

    public interface IDetailVpPresenter extends IBasePresenter<IDetailVpView>{
        void getArticleAttribute(String id);
        void doArticleLike(String id,int type);
        void doArticleCollect(String id,int type);



    }


    public interface IDetailPagePresenter extends IBasePresenter<IDetailPageView> {

        void getRelatedArticleList(String articleId);
        void getArticleUserCommentList(String articleId,int start,long pointTime);
        void getArticleUserCommentReplyList(String articleId,String commentId,int start,long pointTime);
        void commentArticle(String  articleId,String content);
        void replyComment(String  articleId,String commentId,String content,String toUserId,int type,String replyId );
        void doCommentLike(String commentId);
        void readArticleFroIntegral(String articleId);

    }


    public interface  IDetailRepository{
        void getNewsData(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<List<NewsData.News>> callBack);
        void getArticleUserCommentList(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<CommentData> callBack);
        void getArticleUserCommentReplyList(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<CommentReplyData> callBack);

        void commentArticle(LifecycleProvider provider, Map<String, String> params, ICancelBaseCallBack<CommentData.Comment> callBack);
        void replyComment(LifecycleProvider provider, Map<String, String> params, ICancelBaseCallBack<CommentData.Reply> callBack);
        void doCommentLike(LifecycleProvider provider, Map<String, String> params, ICancelBaseCallBack<String> callBack);

        void getArticleAttribute(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<NewsAttribute> callBack);
        void doArticleLike(LifecycleProvider provider, Map<String, String> params, ICancelBaseCallBack<String> callBack);
        void doArticleCollect(LifecycleProvider provider, Map<String, String> params, ICancelBaseCallBack<String> callBack);

        void readArticleFroIntegral(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<String> callBack);


    }



}
