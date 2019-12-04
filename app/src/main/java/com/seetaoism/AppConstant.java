package com.seetaoism;

import static com.seetaoism.AppConstant.RequestUrl.*;

/*
 * created by Cherry on 2019-08-25
 **/
public interface AppConstant {
   String BASE_URL = BuildConfig.BASE_URL;



    String CACHE_USER_DATA_FILE_NAME = "user_info.json";


    interface BundleParamsKeys {

        String ARTICLE_ID = "articleId";
        String ARTICLE_POSITION = "articlePosition";
        String DETAIL_NEWS_COLUMN_ID = "columnId";
        String ARTICLE_LINK_URL = "article_link_url";
        String ARTICLE_SHARE_LINK_URL = "article_Share_link_url";
        String ARTICLE_DESCRIPTION = "article_description";
        String ARTICLE_TITLE = "article_title";
        String ARTICLE_IMAGE_URL = "article_img_url";

        String ACCOUNT = "account";
    }

    interface SPKeys{
        String COLUMNS = "columns";
        String SEARCH = "search";

    }
    public interface RequestParamsKey{

        String LANG = "lang"; // 语言类型
        String FROM = "from"; // 设备类型 android ,ios ,pc
        String SIGNATURE = "signature"; // 把时间长戳和 随机数通过sha1 机密生成的一个 签名串
        String TIMES_STAMP = "timestamp"; // 时间戳
        String NONCE = "nonce"; // 随机数

        String MOBILE = "mobile"; // 手机号
        String SMS_CODE_TYPE = "type"; // 1, 注册，2， 修改密码，3， 修改手机号，4。登录
        String SMS_CODE= "sms_code"; // 验证码
        String PASSWORD= "password"; // 注册时第一个密码
        String AFFIRM_PASSWORD= "affirm_password"; // 注册时确认密码
        String USER_NAME= "username"; // 用户名
        String TOKEN= "token"; //

        String NEWS_START = "start";// 新闻下次加载开始位置
        String NEWS_POINT_TIME = "point_time";// 新闻下次加载开始时间点
        String COLUMN_ID = "id";// 请求新闻的栏目id

        String DETAIL_ARTICLE_ID = "id"; // 详情页请求相关新闻的新闻id
        String DETAIL_ARTICLE_ID2 = "article_id"; // 详情页请求相关新闻的新闻id

        String DETAIL_COMMENT_START = "start"; // 详情页请求评论开始位置
        String DETAIL_COMMENT_POINT_TIME = "point_time"; // 详情页请求评论节点时间
        String DETAIL_COMMENT_ID = "comment_id"; // 详情页请求回复列表的评论id
        String DETAIL_REPLY_START = "start"; // 详情页请求评论的回复列表的开始位置
        String DETAIL_REPLY_POINT_TIME = "point_time"; // 详情页请求评论的回复列表的时间节点

        String DETAIL_COMMENT_ARTICLE_ID = "article_id";// 评论文章的时 文章的id
        String DETAIL_COMMENT_ARTICLE_CONTENT = "content";// 评论文章的时的内容
        String DETAIL_COMMENT_TO_ID = "to_id";// 评论回复时，回复给谁的id
        String DETAIL_COMMENT_TYPE = "type";// 回复主评论传1，回复评论的回复传2
        String DETAIL_COMMENT_REPLY_ID= "reply_id";// 回复主评论传0，回复评论的回复时，传这条回复信息的id
        String DETAIL_DO_ARTICLE_LICK_TYPE= "type";
        String DETAIL_DO_ARTICLE_COLLECT_TYPE= "type";


        String VIDEOSTART = "start";//下次请求文章开始位置（搜索）
        String VIDEOTIME = "point_time";//下次请求时间节点（搜索）
        String SEARCHKEY = "keywords";//搜索关键字（搜索）

        String EMAIL="email";//邮箱

        String COLUMN_IDS = "column_id";
        String COLUMN_SORT = "sort";
    }


    public interface RequestUrl {
        String LOGIN_BY_PSD = "api/user/login";
        String GET_SMS_CODE = "api/sms/sendsms";
        String GET_USER_BY_TOKEN = "api/user/getuserinfo"; // 通过 token 获取用户信息
        String SOCIAL_LOGIN = "/api/user/sociallogin"; // 社交登录



        String GET_ARTICLE_COMMENT_LIST = "/api/comment_reply/commentlist"; // 文章评论列表

        String GET_ARTICLE_COMMENT_REPLAY_LIST = "/api/comment_reply/replylist"; // 文章评论回复列表
        String DETAIL_DO_COMMENT_LIKE = "/api/comment_reply/commentpraise"; // 文章评论回复列表
        String DETAIL_REPLY_COMMENT = "/api/comment_reply/userreply";
        String DETAIL_COMMENT_ARTICLE = "/api/comment_reply/usercomment";

        String DETAIL_DO_ARTICLE_LIKE = "/api/article/userevaluate"; // 文章评论回复列表
        String DETAIL_DO_ARTICLE_COLLECT = "/api/article/usercollect"; //
        String DETAIL_GET_ARTICLE_ATTRIBUTE = "/api/article/articleattribute"; //
        String SHARE_ADD_INTEGRAL = "/api/user/sharearticleaddintegral"; //




    }

    // 把需要登录才能进行操作的 URL 放到这个数组里面
    String [] SHOULD_LOGIN_URL = {DETAIL_DO_ARTICLE_LIKE,DETAIL_DO_ARTICLE_COLLECT,DETAIL_COMMENT_ARTICLE,DETAIL_REPLY_COMMENT};

}
