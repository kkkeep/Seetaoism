package com.seetaoism.data.entity;

import java.util.ArrayList;

public class SearchData {

    private int start;
    private int point_time;
    private int more;
    private String keywords;
    private ArrayList<Searchlist> list;

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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public ArrayList<Searchlist> getList() {
        return list;
    }

    public void setList(ArrayList<Searchlist> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "SearchData{" +
                "start=" + start +
                ", point_time=" + point_time +
                ", more=" + more +
                ", keywords='" + keywords + '\'' +
                ", list=" + list +
                '}';
    }

    public static  class  Searchlist extends NewsData.NewsBean {




        private int view_type;
        private int type;
        private String column_name;
        private String video_url;
        private String content;
        private String edit_time;

        @Override
        public String toString() {
            return "Searchlist{" +
                    "id='" + id + '\'' +
                    ", theme='" + theme + '\'' +
                    ", description='" + description + '\'' +
                    ", view_type=" + view_type +
                    ", type=" + type +
                    ", column_name='" + column_name + '\'' +
                    ", image_url='" + image_url + '\'' +
                    ", is_good=" + is_good +
                    ", is_collect=" + is_collect +
                    ", link='" + link + '\'' +
                    ", share_link='" + share_link + '\'' +
                    ", video_url='" + video_url + '\'' +
                    ", content='" + content + '\'' +
                    ", edit_time='" + edit_time + '\'' +
                    '}';
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

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getEdit_time() {
            return edit_time;
        }

        public void setEdit_time(String edit_time) {
            this.edit_time = edit_time;
        }
    }


}
