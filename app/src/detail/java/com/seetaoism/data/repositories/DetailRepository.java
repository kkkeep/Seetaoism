package com.seetaoism.data.repositories;

import com.mr.k.mvp.base.BaseRepository;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.base.ICancelBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.data.entity.AccessArticleData;
import com.seetaoism.data.entity.CommentData;
import com.seetaoism.data.entity.CommentReplyData;
import com.seetaoism.data.entity.HttpResult;
import com.seetaoism.data.entity.NewsAttribute;
import com.seetaoism.data.entity.NewsData;
import com.seetaoism.data.okhttp.JDDataService;
import com.seetaoism.home.detail.DetailsContract;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import static com.seetaoism.data.okhttp.JDDataService.service;

/*
 * created by Cherry on 2019-10-21
 **/
public class DetailRepository extends BaseRepository implements DetailsContract.IDetailRepository {
    @Override
    public void getNewsData(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<List<NewsData.News>> callBack) {
        observer(provider, service().getArticleAccess(params), listHttpResult -> {
            if(listHttpResult.data != null && listHttpResult.data.getAccess_article_list() != null){
                return Observable.just(listHttpResult.data.getAccess_article_list());
            }
            return Observable.error(new ResultException(ResultException.SERVER_ERROR));

        },callBack);
    }

    @Override
    public void getArticleUserCommentList(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<CommentData> callBack) {
        observer(provider, service().getCommentList(params), commentDataHttpResult -> {
            if(commentDataHttpResult.data != null && isListNotEmpty(commentDataHttpResult.data.getComment_list())){

             return Observable.just(commentDataHttpResult.data);
            }

            return Observable.error(new ResultException(ResultException.SERVER_ERROR));
        },callBack);
    }

    @Override
    public void getArticleUserCommentReplyList(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<CommentReplyData> callBack) {

        observer(provider, service().getCommentReplayList(params), commentReplyDataHttpResult -> {
            if(commentReplyDataHttpResult.data != null){
                return Observable.just(commentReplyDataHttpResult.data);
            }
            return Observable.error(new ResultException(ResultException.SERVER_ERROR));
        },callBack);
    }



    @Override
    public void commentArticle(LifecycleProvider provider, Map<String, String> params, ICancelBaseCallBack<CommentData.Comment> callBack) {

        observer(provider, service().commentArticle(params), commentHttpResult -> {
                if(commentHttpResult.data != null){
                    return Observable.just(commentHttpResult.data);
                }
                return Observable.error(new ResultException(ResultException.SERVER_ERROR));
        }, callBack);
    }

    @Override
    public void replyComment(LifecycleProvider provider, Map<String, String> params, ICancelBaseCallBack<CommentData.Reply> callBack) {

        observer(provider, service().replyComment(params), replyCommentHttpResult -> {
            if(replyCommentHttpResult.data != null){
                return Observable.just(replyCommentHttpResult.data);
            }
            return Observable.error(new ResultException(ResultException.SERVER_ERROR));
        }, callBack);
    }


    @Override
    public void doCommentLike(LifecycleProvider provider, Map<String, String> params, ICancelBaseCallBack<String> callBack) {
        observer(provider,service().doCommentLick(params),stringHttpResult -> Observable.just(stringHttpResult.data),callBack);
    }

    @Override
    public void getArticleAttribute(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<NewsAttribute> callBack) {
        observer(provider, service().getNewsAttribute(params), newsAttributeHttpResult ->{
            if(newsAttributeHttpResult.data != null){
                return Observable.just(newsAttributeHttpResult.data);
            }else{
                return Observable.error(new ResultException(newsAttributeHttpResult.message));
            }


        } , callBack);

    }

    @Override
    public void doArticleLike(LifecycleProvider provider, Map<String, String> params, ICancelBaseCallBack<String> callBack) {

        observer(provider, service().doArticleLike(params), stringHttpResult -> Observable.just(stringHttpResult.data),callBack);
    }

    @Override
    public void doArticleCollect(LifecycleProvider provider, Map<String, String> params, ICancelBaseCallBack<String> callBack) {
        observer(provider, service().doArticleCollect(params), stringHttpResult -> Observable.just(stringHttpResult.data),callBack);
    }
}
