package com.seetaoism.home.detail.page;

import androidx.annotation.NonNull;

import com.mr.k.mvp.base.BasePresenter;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.base.ICancelBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.mr.k.mvp.utils.MapBuilder;
import com.seetaoism.AppConstant;
import com.seetaoism.data.entity.CommentData;
import com.seetaoism.data.entity.CommentReplyData;
import com.seetaoism.data.entity.NewsData;
import com.seetaoism.data.repositories.DetailRepository;
import com.seetaoism.home.detail.DetailsContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/*
 * created by Cherry on 2019-10-21
 **/
public class DetailPagePresenter extends BasePresenter<DetailsContract.IDetailPageView> implements DetailsContract.IDetailPagePresenter {


    private DetailsContract.IDetailRepository mRepository;


    public DetailPagePresenter() {
        this.mRepository = new DetailRepository();
    }

    @Override
    public void getRelatedArticleList(String articleId) {

        Map<String, String> params = new HashMap<>();
        params.put(AppConstant.RequestParamsKey.DETAIL_ARTICLE_ID,articleId);

        mRepository.getNewsData(getLifecycleProvider(), params, new IBaseCallBack<List<NewsData.News>>() {
            @Override
            public void onSuccess(List<NewsData.News> data) {
                if(mView != null){
                    mView.onArticleListResult(data, null);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if(mView != null){
                    mView.onArticleListResult(null, e.getMessage());
                }
            }
        });
    }

    @Override
    public void getArticleUserCommentList(String articleId, int start, long pointTime) {
        Map<String, String> params = new HashMap<>();
        params.put(AppConstant.RequestParamsKey.DETAIL_COMMENT_ARTICLE_ID,articleId);
        params.put(AppConstant.RequestParamsKey.DETAIL_COMMENT_START,String.valueOf(start));
        params.put(AppConstant.RequestParamsKey.DETAIL_COMMENT_POINT_TIME,String.valueOf(pointTime));

        mRepository.getArticleUserCommentList(getLifecycleProvider(), params, new IBaseCallBack<CommentData>() {
            @Override
            public void onSuccess(CommentData data) {
                if(mView != null){
                    mView.onArticleUserCommentResult(data,null);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if(mView != null){
                    mView.onArticleUserCommentResult(null,e.getMessage());
                }
            }
        });

    }

    @Override
    public void getArticleUserCommentReplyList(String articleId, String commentId, int start, long pointTime) {
        Map<String, String> params = new HashMap<>();
            params.put(AppConstant.RequestParamsKey.DETAIL_COMMENT_ARTICLE_ID,articleId);
        params.put(AppConstant.RequestParamsKey.DETAIL_COMMENT_ID,articleId);
        params.put(AppConstant.RequestParamsKey.DETAIL_REPLY_START,String.valueOf(start));
        params.put(AppConstant.RequestParamsKey.DETAIL_REPLY_POINT_TIME,String.valueOf(pointTime));

        mRepository.getArticleUserCommentReplyList(getLifecycleProvider(), params, new IBaseCallBack<CommentReplyData>() {
            @Override
            public void onSuccess(CommentReplyData data) {
                if(mView != null){
                    mView.onArticleUserCommentReplyResult(data, null);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if(mView != null){
                    mView.onArticleUserCommentReplyResult(null, e.getMessage());
                }
            }
        });
    }

    @Override
    public void commentArticle(String articleId, String content) {

        Map<String, String> params = new MapBuilder<String,String>()
                .put(AppConstant.RequestParamsKey.DETAIL_COMMENT_ARTICLE_ID, articleId)
                .put(AppConstant.RequestParamsKey.DETAIL_COMMENT_ARTICLE_CONTENT, content)
                .builder();


        mRepository.commentArticle(getLifecycleProvider(), params, new ICancelBaseCallBack<CommentData.Comment>() {
            @Override
            public void onStart(Disposable disposable) {
                cacheRequest(disposable);
            }

            @Override
            public void onSuccess(CommentData.Comment data) {
                if(mView != null){
                    mView.onCommentResult(data, null);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if(mView != null){
                    mView.onCommentResult(null, e.getMessage());
                }
            }
        });
    }

    @Override
    public void replyComment(String articleId, String commentId, String content, String toUserId, int type, String replyId) {
        Map<String, String> params = new MapBuilder<String,String>()
                .put(AppConstant.RequestParamsKey.DETAIL_COMMENT_ARTICLE_ID, articleId)
                .put(AppConstant.RequestParamsKey.DETAIL_COMMENT_ARTICLE_CONTENT, content)
                .put(AppConstant.RequestParamsKey.DETAIL_COMMENT_ID, commentId)
                .put(AppConstant.RequestParamsKey.DETAIL_COMMENT_TO_ID, toUserId)
                .put(AppConstant.RequestParamsKey.DETAIL_COMMENT_TYPE, String.valueOf(type))
                .put(AppConstant.RequestParamsKey.DETAIL_COMMENT_REPLY_ID, replyId)
                .builder();

        mRepository.replyComment(getLifecycleProvider(), params, new ICancelBaseCallBack<CommentData.Reply>() {
            @Override
            public void onStart(Disposable disposable) {
                cacheRequest(disposable);
            }

            @Override
            public void onSuccess(CommentData.Reply data) {
                if(mView != null){
                    mView.onReplyCommentResult(data, null);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if(mView != null){
                    mView.onReplyCommentResult(null, e.getMessage());
                }
            }
        });
    }


    @Override
    public void doCommentLike(String commentId) {
        Map<String, String> params = new MapBuilder<String,String>()
                .put(AppConstant.RequestParamsKey.DETAIL_COMMENT_ID, commentId)
                .builder();

        mRepository.doCommentLike(getLifecycleProvider(), params, new ICancelBaseCallBack<String>() {
            @Override
            public void onStart(Disposable disposable) {
                cacheRequest(disposable);
            }

            @Override
            public void onSuccess(String data) {
                if(mView != null){
                    mView.onDoLikeResult(data, null);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if(mView != null){
                    mView.onDoLikeResult(null, e.getMessage());
                }
            }
        });
    }

    @Override
    public void readArticleFroIntegral(String articleId) {

        mRepository.readArticleFroIntegral(getLifecycleProvider(), new MapBuilder<String, String>().put(AppConstant.RequestParamsKey.DETAIL_ARTICLE_ID, articleId).builder(), new IBaseCallBack<String>() {
            @Override
            public void onSuccess(@NonNull String data) {
                if(mView != null){
                    mView.onReadArticleFroIntegralResult(data, null);
                }

            }

            @Override
            public void onFail(@NonNull ResultException e) {
                if(mView != null){
                    mView.onReadArticleFroIntegralResult(null, e.getMessage());
                }
            }
        });
    }
}
