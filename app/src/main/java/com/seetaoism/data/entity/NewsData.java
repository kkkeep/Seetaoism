package com.seetaoism.data.entity;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import jy.com.libbanner.MarqueeView;


public class NewsData {

    private int start; // 普通新闻开始的index ,
    private long point_time;  // 视频类新闻 开始index
    private int more; // 是否还有更多的的新闻，
    private int flash_id;


    private List<Banner> banner_list;
    private List<Flash> flash_list;
    private List<News> article_list;

    private List<? extends NewsBean> detailPageList;


    public List<Banner> getBannerList() {
        return banner_list;
    }

    public void setBannerList(List<Banner> banner_list) {
        this.banner_list = banner_list;
    }

    public List<Flash> getFlashList() {
        return flash_list;
    }

    public void setFlashList(List<Flash> flash_list) {
        this.flash_list = flash_list;
    }

    public List<News> getArticleList() {
        return article_list;
    }

    public void setArticleList(List<News> article_list) {
        this.article_list = article_list;
    }

    public List<? extends NewsBean> getDetailPageList() {
        return detailPageList;
    }

    public void setDetailPageList(List<? extends NewsBean> detailPageList) {
        this.detailPageList = detailPageList;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public long getPoint_time() {
        return point_time;
    }

    public void setPoint_time(long point_time) {
        this.point_time = point_time;
    }

    public int getMore() {
        return more;
    }

    public void setMore(int more) {
        this.more = more;
    }

    public int getFlashId() {
        return flash_id;
    }

    public void setFlashId(int flash_id) {
        this.flash_id = flash_id;
    }


    @Override
    public String toString() {
        return "NewsData{" +
                "start=" + start +
                ", point_time=" + point_time +
                ", more=" + more +
                ", flash_id=" + flash_id +
                ", banner_list=" + Arrays.toString(banner_list.toArray()) +
                ", flash_list=" + Arrays.toString(flash_list.toArray()) +
                ", article_list=" + Arrays.toString(article_list.toArray()) +
                '}';
    }


    public static class NewsBean{
        /**
         * id : 4292
         * theme : 金句频出！康辉亲民风播报国际锐评，满嘴跑火车、怨妇心态都出来了
         * description : 美国一百多名所谓对华强硬派人士近日发表联名公开信，妄称中国以经济利益“诱导”美国盟友和其他国家，扩张全球影响力，反映出美国某些人对中国经济实力增强的“羡慕嫉妒恨” 。
         * image_url : http://static.cmsino.com/Public/Uploads/publicity/2019-07-27/y_jiyktp231bxif.png
         * is_good : 0
         * is_collect : 0
         * link : http://www.cmsino.com/api/article/details/id/4292/from/android/lang/zh.html
         * share_link : http://www.cmsino.com/m_details/4292/from/android/lang/zh.html
         */

        protected String id;
        protected String theme;
        protected String description;
        protected String image_url;
        protected int is_good;
        protected int is_collect;
        protected String link;
        protected String share_link;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImageUrl() {
            return image_url;
        }

        public void setImageUrl(String imageUrl) {
            this.image_url = imageUrl;
        }

        public int getIs_good() {
            return is_good;
        }

        public void setIs_good(int is_good) {
            this.is_good = is_good;
        }

        public int getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(int is_collect) {
            this.is_collect = is_collect;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getShare_link() {
            return share_link;
        }

        public void setShare_link(String share_link) {
            this.share_link = share_link;
        }
        @Override
        public String toString() {
            return "Banner{" +
                    "id='" + id + '\'' +
                    ", theme='" + theme + '\'' +
                    ", description='" + description + '\'' +
                    ", image_url='" + image_url + '\'' +
                    ", is_good=" + is_good +
                    ", is_collect=" + is_collect +
                    ", link='" + link + '\'' +
                    ", share_link='" + share_link + '\'' +
                    '}';
        }
    }


    public static class Banner extends NewsBean {
    }

    public static class Flash  extends NewsBean implements MarqueeView.MarqueeData {
        @NotNull
        @Override
        public String getString() {
            return getTheme();
        }
    }

    public static class News  extends NewsBean{
        /**
         * id : 4295
         * theme : 英欧离婚剧迎来最紧张一集：协议和平分手还是干脆一纸休书？
         * description : 英国表示，无论以何种方式英国都将于2019年10月31日前“脱欧”

         * type : 1
         * column_name : 地缘
         * content : <p>现在，关于英国要不要和欧盟离婚已经不是问题的核心，双方通过哪种方式离婚才是问题的关键。</p><p>目前的状况是这样的：英国一心想要尽快结束这段“感情”以迈向新生活，哪怕在没有任何协议的情况下。看来，英国已经厌倦了在咖啡馆里繁文缛节的商讨分手细节，其迫不及待的想要“恢复单身”，越快越好。而欧盟的态度也很明确，在没有一份令其满意的“离婚协议”的情况下，欧盟宁愿耗着。欧盟对英国急于分手的焦急毫不在意，对于欧盟来说夏天过后还有秋天，今年之后还有明年。</p><p>这种在电视剧中经常出现的离婚桥段，现在正发生在英国和欧盟之间：</p><p>2019年7月25日，英国新首相鲍里斯·约翰逊向议会下院发表就任后首次讲话，阐释关联退出欧洲联盟事宜的政策主张。</p><p style="text-align:center"><img src="http://static.cmsino.com/Public/Uploads/editor/image/2019-07-28/5d3d26996ab6b.jpg" alt=""/></p><p>欧盟方面当天晚些时候回应，说约翰逊呼吁删除协议中“备份安排”内容“当然不可接受”，但欧方准备与英方“建设性合作”，就“脱欧”事宜启动对话。</p><p><strong>英国：这婚离定了，但孩子抚养权归谁？</strong></p><p>英国广播公司报道，约翰逊当天告诉下院议员，他致力于“摆脱”现有“脱欧”协议中的“备份安排”，说没有哪个“独立、自尊”的国家会同意放弃“经济独立和政府自治”、接受诸如“备份安排”之类的条款。</p><p style="text-align:center"><img src="http://static.cmsino.com/Public/Uploads/editor/image/2019-07-28/5d3d25d29715a.gif" alt=""/></p><p>为避免北爱尔兰地区与爱尔兰之间恢复“硬边界”，即重新设置实体海关和边防检查设施，“备份安排”要求英国与欧盟在找到北爱尔兰贸易解决方案前尽可能维持现有紧密经贸关系。</p><p>作为保险兜底政策，“备份安排”不设期限、不允许单边退出，可能让北爱尔兰受制于欧盟贸易规则，从而实际上将北爱尔兰“留在”欧盟。这是导致“脱欧”协议三次遭英国议会下院否决的主要原因之一。</p><p>法新社报道，约翰逊呼吁欧盟“重新考虑”拒绝重谈“脱欧”协议的立场，威胁否则英国不得不“无协议脱欧”。他声称要在2019年10月31日“脱欧”期限前就可能发生的“无协议脱欧”加紧筹备，说英国先前承诺给欧盟的390亿英镑“分手费”可能转而用于筹备工作。</p><p>路透社推断，约翰逊打算以“无协议脱欧”形成的经济乱局为威胁，让以德国和法国为首的欧盟同意修改现有“脱欧”协议。</p><p><strong>欧盟：没有分手协议，这婚我不离</strong><br/></p><p>欧盟“脱欧”谈判首席代表米歇尔·巴尼耶数小时后拒绝约翰逊的要求。他向欧盟成员国代表发送电子邮件，重申一贯立场，说删除“备份安排”的要求“当然不可接受”。</p><p style="text-align:center"><img src="http://static.cmsino.com/Public/Uploads/editor/image/2019-07-28/5d3d260750d15.jpg" alt=""/></p><p>巴尼耶认为约翰逊的论调“相当好斗”，建议欧盟其他27国作好准备，应对英方优先筹划“无协议脱欧”。“无论如何”，欧盟必须“保持冷静、坚持原则方针、显现团结统一”。</p><p>他同时不排除与英国对话磋商的可能，说准备“在职权范围内”与英方“建设性合作”，愿意分析英方“任何符合现有协议条款的”方案，他“整个夏天都可以与英方会谈”。</p><p>英国首相府一名发言人拒绝回应巴尼耶的立场，以约翰逊刚刚接受相位为由，告诉法新社：“这只第一天。”</p><p style="text-align:center"><img src="http://static.cmsino.com/Public/Uploads/editor/image/2019-07-28/5d3d282b60517.jpg" alt=""/></p><p>欧盟委员会主席让－克洛德·容克7月25日与约翰逊通话，重申现有“脱欧”协议是可能达成的最好协议；如果英国有意，欧盟委员会今后数周愿与英方会商。英国首相府一名发言人告诉路透社，约翰逊在通话中坚持要求取消“备份安排”。双方同意继续沟通。</p><p>爱尔兰是“无协议脱欧”潜在受损失最大的一方。总理利奥·瓦拉德卡同一天说，希望约翰逊没有决意“无协议脱欧”。</p><p><strong>离婚后，日子怎么过？</strong></p><p>法新社报道，约翰逊在议会下院的演说多次遭反对党议员打断。</p><p>欧盟“脱欧”谈判首席代表巴尼耶注意到英国国内对新首相“脱欧”主张的反对声音，认为欧盟“必须仔细跟踪”英国国内后续在“政治和经济方面的反馈与走势”。</p><p style="text-align:center"><img src="http://static.cmsino.com/Public/Uploads/editor/image/2019-07-28/5d3d26b45a668.jpg" alt=""/></p><p>欧盟和英国国内反对“备份安排”和反对“无协议脱欧”人士的既定立场目前都没有显现转圜迹象。</p><p>路透社报道，欧盟坚决拒绝重谈“脱欧”协议法律条款，但愿意接受改动阐述双方未来关系的“政治宣言”。约翰逊领导的保守党政府依靠北爱尔兰民主统一党维持议会下院多数席位优势。北爱民主统一党坚决反对“备份安排”。而不少约翰逊所属保守党的下院议员决意阻止“无协议脱欧”。</p><p>如果欧盟拒绝合作、而约翰逊转而寻求“无协议脱欧”，他可能需要解散议会、提前选举。</p><p>故事可能发展到任何方向。现在世界之间的关系日益紧密，这场离婚如果处理不当，不可预知的混乱有可能波及到世界上许多国家与地区。</p><p>（作者：宋晓波）</p><p><br/></p>
         * edit_time : 2019-07-28 12:45
         * image_url : http://static.cmsino.com/Public/Uploads/thumbnail/2019-07-28/y_s_m08cwpxn65uwh.png
         * is_good : 0
         * is_collect : 0
         * link : http://www.cmsino.com/api/article/details/id/4295/from/android/lang/zh.html
         * share_link : http://www.cmsino.com/m_details/4295/from/android/lang/zh.html
         * lead_one : 绿水青山不只属于地球，还是人类的金山银山，是我们人类所依赖的宝藏。保护它，不但保护生物圈，还是保护我们人类自己
         * video_is_sans_href : 0
         * video_url : http://static.cmsino.com/Public/Uploads/video/2019-07-26/5d3a458689769.mp4
         */

        private int type; //'文章类型：1新闻，2快讯，3图片，4视频，5期刊，6专题',
        private String column_name;
        private String content;        private String edit_time;
        private String lead_one;
        private String video_is_sans_href;
        private String video_url;
        private int view_type; //'视图类型：1左图，2中间大图，3右图，4视频，5即时',


        public int getView_type() {
            return view_type;
        }

        public void setView_type(int view_type) {
            this.view_type = view_type;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getColumn_name() {
            return column_name;
        }

        public void setColumnName(String column_name) {
            this.column_name = column_name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getEditTime() {
            return edit_time;
        }

        public void setEditTime(String edit_time) {
            this.edit_time = edit_time;
        }



        public String getLeadOne() {
            return lead_one;
        }

        public void setLeadOne(String lead_one) {
            this.lead_one = lead_one;
        }

        public String getVideoIsSansHref() {
            return video_is_sans_href;
        }

        public void setVideoIsSansHref(String video_is_sans_href) {
            this.video_is_sans_href = video_is_sans_href;
        }

        public String getVideoUrl() {
            return video_url;
        }

        public void setVideoUrl(String video_url) {
            this.video_url = video_url;
        }

        @Override
        public String toString() {
            return "News{" +
                    "id='" + getId() + '\'' +
                    ", theme='" + getTheme() + '\'' +
                    ", description='" + getDescription() + '\'' +
                    ", type=" + type +
                    ", column_name='" + column_name + '\'' +
                    ", content='" + content + '\'' +
                    ", edit_time='" + edit_time + '\'' +
                    ", image_url='" + getImageUrl() + '\'' +
                    ", is_good=" + getIs_good() +
                    ", is_collect=" + getIs_collect() +
                    ", link='" + getLink() + '\'' +
                    ", share_link='" + getShare_link() + '\'' +
                    ", lead_one='" + lead_one + '\'' +
                    ", video_is_sans_href='" + video_is_sans_href + '\'' +
                    ", video_url='" + video_url + '\'' +
                    '}';
        }
    }
}
