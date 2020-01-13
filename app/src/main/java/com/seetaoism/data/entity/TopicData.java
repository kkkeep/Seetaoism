package com.seetaoism.data.entity;

import java.util.ArrayList;

public class TopicData {


    private int start;
    private int point_time;
    private int more;
    private int number;
    private ArrayList<Bannerlist> banner_list;
    private ArrayList<Topiclist> list;

    public TopicData(int start, int point_time, int more, ArrayList<Bannerlist> banner_list, ArrayList<Topiclist> list) {
        this.start = start;
        this.point_time = point_time;
        this.more = more;
        this.banner_list = banner_list;
        this.list = list;
    }

    public TopicData() {
    }

    @Override
    public String toString() {
        return "TopicData{" +
                "start=" + start +
                ", point_time=" + point_time +
                ", more=" + more +
                ", banner_list=" + banner_list +
                ", list=" + list +
                '}';
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getPoint_time() {
        return point_time;
    }

    public void setPoint_time(int point_time) {
        this.point_time = point_time;
    }

    public int getMore() {
        return more;
    }

    public void setMore(int more) {
        this.more = more;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<Bannerlist> getBanner_list() {
        return banner_list;
    }

    public void setBanner_list(ArrayList<Bannerlist> banner_list) {
        this.banner_list = banner_list;
    }

    public ArrayList<Topiclist> getList() {
        return list;
    }

    public void setList(ArrayList<Topiclist> list) {
        this.list = list;
    }

    public static class Bannerlist extends NewsData.NewsBean {
            /*'id': '文章id',
            'theme': '文章标题',
            'description': '文章描述',
            'image_url': '文章预览图',
            'is_good': '是否点赞，1已点赞，0未点赞',
            'is_collect': '是否收藏，1已收藏，0未收藏',
            'link': '文章链接',
            'share_link': '文章分享链接',*/

        @Override
        public String toString() {
            return "Bannerlist{" +
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

        public Bannerlist() {
        }

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

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
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
    }

    public static class Topiclist extends NewsData.NewsBean {
        /*'id': '文章id',
        'view_type': '视图类型：1左图，2中间大图，3右图，4视频，5即时',
        'type': '文章类型：1新闻，2快讯，3图片，4视频，5期刊，6专题',
        'column_name': '栏目分类',
        'theme': '文章标题',
        'description': '文章描述',
        'lead': '导语',
        'image_url': '文章预览图',
        'is_good': '是否点赞，1已点赞，0未点赞',
        'is_collect': '是否收藏，1已收藏，0未收藏',
        'link': '文章链接',
        'share_link': '文章分享链接',*/




        private int view_type;
        private int type;
        private int video_is_sans_href;
        private String column_name;
        private String lead;
        private String video_url;

        @Override
        public String toString() {
            return "Topiclist{" +
                    "id='" + id + '\'' +
                    ", view_type=" + view_type +
                    ", type=" + type +
                    ", column_name='" + column_name + '\'' +
                    ", theme='" + theme + '\'' +
                    ", description='" + description + '\'' +
                    ", lead='" + lead + '\'' +
                    ", image_url='" + image_url + '\'' +
                    ", is_good=" + is_good +
                    ", is_collect=" + is_collect +
                    ", link='" + link + '\'' +
                    ", share_link='" + share_link + '\'' +
                    '}';
        }

        public int getVideo_is_sans_href() {
            return video_is_sans_href;
        }

        public void setVideo_is_sans_href(int video_is_sans_href) {
            this.video_is_sans_href = video_is_sans_href;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public void setColumn_name(String column_name) {
            this.column_name = column_name;
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

        public String getLead() {
            return lead;
        }

        public void setLead(String lead) {
            this.lead = lead;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
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

        public Topiclist() {
        }

        public Topiclist(String id, int view_type, int type, String column_name, String theme, String description, String lead, String image_url, int is_good, int is_collect, String link, String share_link) {
            this.id = id;
            this.view_type = view_type;
            this.type = type;
            this.column_name = column_name;
            this.theme = theme;
            this.description = description;
            this.lead = lead;
            this.image_url = image_url;
            this.is_good = is_good;
            this.is_collect = is_collect;
            this.link = link;
            this.share_link = share_link;
        }
    }


}
