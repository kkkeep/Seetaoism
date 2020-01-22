package com.seetaoism.data.okhttp;


import android.service.autofill.UserData;

import com.seetaoism.AppConstant;
import com.seetaoism.data.entity.AccessArticleData;
import com.seetaoism.data.entity.Ad;
import com.seetaoism.data.entity.CheckUpdateData;
import com.seetaoism.data.entity.CommentData;
import com.seetaoism.data.entity.CommentReplyData;
import com.seetaoism.data.entity.HttpResult;
import com.seetaoism.data.entity.IntergrelData;
import com.seetaoism.data.entity.MessageData;
import com.seetaoism.data.entity.NewsAttribute;
import com.seetaoism.data.entity.NewsColumnData;
import com.seetaoism.data.entity.NewsData;
import com.seetaoism.data.entity.NoticedetailsBean;
import com.seetaoism.data.entity.SearchData;
import com.seetaoism.data.entity.SocialBindData;
import com.seetaoism.data.entity.TopicData;
import com.seetaoism.data.entity.User;
import com.seetaoism.data.entity.UserCollect;
import com.seetaoism.data.entity.VideoData;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

import static com.seetaoism.AppConstant.RequestUrl.*;

public interface JDApiService {


    /**
     * 密码登录
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(AppConstant.RequestUrl.LOGIN_BY_PSD)
    Observable<HttpResult<User>> loginByPs(@FieldMap Map<String, String> params);

    /**
     * 验证码登录
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/smslogin")
    Observable<HttpResult<User>> loginByCode(@FieldMap Map<String, String> map);


    /**
     * 注册
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/register")
    Observable<HttpResult<User>> register(@FieldMap Map<String, String> map);

    /**
     * 发送验证码
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(AppConstant.RequestUrl.GET_SMS_CODE)
    Observable<HttpResult<String>> getSmsCode(@FieldMap Map<String, String> map);

    /**
     * 验证码是否正确
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("api/sms/checksmscode")
    Observable<HttpResult<String>> verifySmsCode(@FieldMap Map<String, String> map);


    // 根据token 获取用户信息
    @GET("api/user/getuserinfo")
    Observable<HttpResult<User>> getUserinfoByToken(@QueryMap Map<String, String> map);

    // 根据token 获取用户信息
    @GET("api/user/getuserinfo")
    Observable<String> getUserinfoByToken1(@QueryMap Map<String, String> map);


    // 请求 首页新闻 栏目， 如果登录需要传入token,
    @GET("api/column/columnmanagelist")
    Observable<HttpResult<NewsColumnData>> getNewsColumnList(@QueryMap Map<String, String> map);

    // 请求 首页新闻 栏目， 如果登录需要传入token,
    @POST("api/column/columnmanage")
    @FormUrlEncoded
    Observable<HttpResult<String>> uploadColumn(@FieldMap Map<String, String> map);

    // 请求首页新闻
    @GET("/app/v_1_3/article/recommendlist")
    Observable<HttpResult<NewsData>> getNewsData(@QueryMap Map<String, String> map);

    //请求评论点赞
    @FormUrlEncoded
    @POST("/api/comment_reply/commentpraise")
    Observable<HttpResult<String>> commentpraise(@FieldMap Map<String, String> map);

    //                        //api/comment_reply/userreply
    //请求用户评论
    @FormUrlEncoded
    @POST("/api/comment_reply/usercomment")
    Observable<HttpResult<CommentData.Comment>> usercomment(@FieldMap Map<String, String> map);

    //请求用户回复评论
    @FormUrlEncoded
    @POST("api/comment_reply/userreply")
    Observable<HttpResult<CommentData.Reply>> userreply(@FieldMap Map<String, String> map);

    //请求修改昵称
    @FormUrlEncoded
    @POST("/api/user/editnickname")
    Observable<HttpResult<User>> updatename(@FieldMap Map<String, String> map);

    //请求退出登录
    @FormUrlEncoded
    @POST("/api/user/logout")
    Observable<HttpResult<String>> getlogout(@FieldMap Map<String, String> map);

    //请求修改密码
    @FormUrlEncoded
    @POST("/api/user/editpassword")
    Observable<HttpResult<String>> editpassword(@FieldMap Map<String, String> map);

    //请求搜索历史
    @GET("/app/v_1_1/article/search")
    Observable<HttpResult<SearchData>> getSeachData(@QueryMap Map<String, String> map);

    //请求专题列表
    @GET("/app/v_1_3/article/speciallist")
    Observable<HttpResult<TopicData>> getTopicData(@QueryMap Map<String, String> map);

    //请求上传头像
    @Multipart
    @POST("/api/user/uploadhead")
    Observable<HttpResult<User>> uploadHead(@Part MultipartBody.Part parts);

    //忘记修改密码
    @FormUrlEncoded
    @POST("/app/v_1_1/user/savepassword")
    Observable<HttpResult<String>> updatepaw(@FieldMap Map<String, String> map);

    //请求视频列表
    @GET("/app/v_1_3/article/videolist")
    Observable<HttpResult<VideoData>> getVideoData(@QueryMap Map<String, String> map);

    //请求修改手机号
    @FormUrlEncoded
    @POST("/api/user/editmobile")
    Observable<HttpResult<User>> editmobile(@FieldMap Map<String, String> map);

    //修改邮箱
    @FormUrlEncoded
    @POST("/api/user/editemail")
    Observable<HttpResult<User>> editemail(@FieldMap Map<String, String> map);

    //请求用户收藏和取消收藏文章
    @FormUrlEncoded
    @POST("/api/article/usercollect")
    Observable<HttpResult<String>> usercollect(@FieldMap Map<String, String> map);

    //请求点赞
    @FormUrlEncoded
    @POST("/api/article/userevaluate")
    Observable<HttpResult<String>> userevaluate(@FieldMap Map<String, String> map);

    //请求文章是否被用户 点赞，点踩，收藏
    @FormUrlEncoded
    @POST("/api/article/articleattribute")
    Observable<HttpResult<UserCollect>> articleattribute(@FieldMap Map<String, String> map);

    //请求我的收藏
    @GET("/api/user/collect")
    Observable<HttpResult<VideoData>> collect(@QueryMap Map<String, String> map);


    //请求积分
    @GET("/app/v_1_1/user/myintegral")
    Observable<HttpResult<IntergrelData>> myintegral(@QueryMap Map<String, String> map);

    //请求删除收藏记录
    @FormUrlEncoded
    @POST("/app/v_1_1/user/deletecollect")
    Observable<HttpResult<String>> deletecollect(@FieldMap Map<String, String> map);

    //请求每日签到获取积分
    @FormUrlEncoded
    @POST("/app/v_1_1/user/checkinaddintegral")
    Observable<HttpResult<String>> checkinaddintegral(@FieldMap Map<String, String> map);

    //请求极光推送
    @FormUrlEncoded
    @POST("/api/user/setpushid")
    Observable<HttpResult<String>> setpushid(@FieldMap Map<String, String> map);


    //阅读文章增加积分
    @FormUrlEncoded
    @POST("/api/article/readarticleaddintegral")
    Observable<HttpResult<String>> readarticleaddintegral(@FieldMap Map<String, String> map);


    //请求我的消息
    @GET("/api/user/notice")
    Observable<HttpResult<MessageData>> notice(@QueryMap Map<String, String> map);

    // 获取详情相关新闻
    @GET("/api/article/articleaccess")
    Observable<HttpResult<AccessArticleData>> getArticleAccess(@QueryMap Map<String, String> map);


    // 获取评论列表
    @GET(AppConstant.RequestUrl.GET_ARTICLE_COMMENT_LIST)
    Observable<HttpResult<CommentData>> getCommentList(@QueryMap Map<String, String> map);


    // 获取评论回复列表
    @GET(AppConstant.RequestUrl.GET_ARTICLE_COMMENT_REPLAY_LIST)
    Observable<HttpResult<CommentReplyData>> getCommentReplayList(@QueryMap Map<String, String> map);

    /**
     * 用户评论文章
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(DETAIL_COMMENT_ARTICLE)
    Observable<HttpResult<CommentData.Comment>> commentArticle(@FieldMap Map<String, String> map);


    /**
     * 回复别人的评论
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(DETAIL_REPLY_COMMENT)
    Observable<HttpResult<CommentData.Reply>> replyComment(@FieldMap Map<String, String> map);

    /**
     * 评论点赞
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(DETAIL_DO_COMMENT_LIKE)
    Observable<HttpResult<String>> doCommentLick(@FieldMap Map<String, String> map);


    /**
     * 点赞文章
     */
    @FormUrlEncoded
    @POST(AppConstant.RequestUrl.DETAIL_DO_ARTICLE_LIKE)
    Observable<HttpResult<String>> doArticleLike(@FieldMap Map<String, String> map);


    /**
     * 收藏文章
     */
    @FormUrlEncoded
    @POST(AppConstant.RequestUrl.DETAIL_DO_ARTICLE_COLLECT)
    Observable<HttpResult<String>> doArticleCollect(@FieldMap Map<String, String> map);


    /**
     * 删除我的消息
     */
    @FormUrlEncoded
    @POST("/app/v_1_1/user/deletenotice")
    Observable<HttpResult<String>> deletenotice(@FieldMap Map<String, String> map);


    // 获取消息详情
    @GET("/api/user/noticedetails")
    Observable<HttpResult<NoticedetailsBean>> noticedetails(@QueryMap Map<String, String> map);


    /**
     * 获取该文章是否已经点赞和收藏
     */
    @FormUrlEncoded
    @POST(AppConstant.RequestUrl.DETAIL_GET_ARTICLE_ATTRIBUTE)
    Observable<HttpResult<NewsAttribute>> getNewsAttribute(@FieldMap Map<String, String> map);


    /**
     * 获取删除评论接口
     */
    @FormUrlEncoded
    @POST("/api/comment_reply/commentdelete")
    Observable<HttpResult<String>> commentdelete(@FieldMap Map<String, String> map);


    /**
     * 获取评论回复删除
     */
    @FormUrlEncoded
    @POST("/api/comment_reply/replydelete")
    Observable<HttpResult<String>> replydelete(@FieldMap Map<String, String> map);


    /**
     * 获取文章删除
     */
    @FormUrlEncoded
    @POST("/api/article/articledelete")
    Observable<HttpResult<String>> articledelete(@FieldMap Map<String, String> map);


    /**
     * 获取社交绑定
     */
    @FormUrlEncoded
    @POST("/api/user/socialbind")
    Observable<HttpResult<SocialBindData>> socialbind(@FieldMap Map<String, String> map);

    /**
     * 获取社交解除绑定
     */
    @FormUrlEncoded
    @POST("/api/user/socialunbind")
    Observable<HttpResult<SocialBindData>> socialunbind(@FieldMap Map<String, String> map);


    /**
     * 获取社交登录
     */
    @FormUrlEncoded
    @POST("/api/user/sociallogin")
    Observable<HttpResult<User>> sociallogin(@FieldMap Map<String, String> map);



    /**
     * 分享增加积分
     */
    @FormUrlEncoded
    @POST(SHARE_ADD_INTEGRAL)
    Observable<HttpResult<String>> shareAddIntegral(@FieldMap Map<String, String> map);

    /**
     * 检查更新
     */
    @GET("/api/user/checkupdate")
    Observable<HttpResult<CheckUpdateData>> checkUpdate(@QueryMap Map<String, String> map);


    /**
     * 检查更新
     */
    @GET("/api/file/appupgrade")
    Observable<HttpResult<String>> appUpgrade(@QueryMap Map<String, String> map);


    @GET("/app/v_1_3/ad/coopen")
    Observable<HttpResult<List<Ad>>> getSplashAd();
}
