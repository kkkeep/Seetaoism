package com.seetaoism.data.entity;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class VideoData {

    public int start;
    public int point_time;
    public int more;
    public ArrayList<NewList>  list;

    public VideoData() {
    }

    public VideoData(int start, int point_time, int more, ArrayList<NewList> list) {
        this.start = start;
        this.point_time = point_time;
        this.more = more;
        this.list = list;
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

    public ArrayList<NewList> getList() {
        return list;
    }

    public void setList(ArrayList<NewList> list) {
        this.list = list;
    }

    public static class NewList extends NewsData.NewsBean {


   /*     private String id;
        private String theme;
        private String description;
        private String image_url;

        private int is_good;
        private int is_collect;
        private String link;
        private String share_link;*/
         private String lead;
        private int view_type;
        private int type;
        private String column_name;
        private String video_is_sans_href;
        private String video_url;
        private String time;
        private String read_count;
        private boolean select;
        private String collect_id;
        private Ad ad;


        @Override
        public boolean equals(@Nullable Object obj) {

            if(obj == null){
                return false;
            }

            if(obj == this){
                return true;
            }
            if(obj instanceof NewList ){
                return ((NewList) obj).getId().equals(this.getId());

            }
            return false;

        }

        public NewList() {
        }

        public String getCollect_id() {
            return collect_id;
        }

        public void setCollect_id(String collect_id) {
            this.collect_id = collect_id;
        }

       /* public NewList(String id, String theme, String description, String lead, int view_type, int type, String column_name, String image_url, int is_good, int is_collect, String link, String share_link, String video_is_sans_href, String video_url, String time, String read_count, boolean select, String collect_id) {
            this.id = id;
            this.theme = theme;
            this.description = description;
            this.lead = lead;
            this.view_type = view_type;
            this.type = type;
            this.column_name = column_name;
            this.image_url = image_url;
            this.is_good = is_good;
            this.is_collect = is_collect;
            this.link = link;
            this.share_link = share_link;
            this.video_is_sans_href = video_is_sans_href;
            this.video_url = video_url;
            this.time = time;
            this.read_count = read_count;
            this.select = select;
            this.collect_id = collect_id;
        }*/

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public String getRead_count() {
            return read_count;
        }

        public void setRead_count(String read_count) {
            this.read_count = read_count;
        }

        public NewList(String time) {
            this.time = time;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public Ad getAd() {
            return ad;
        }

        public void setAd(Ad ad) {
            this.ad = ad;
        }

        @Override
        public String toString() {
            return "NewList{" +
                    "id='" + id + '\'' +
                    ", theme='" + theme + '\'' +
                    ", description='" + description + '\'' +
                    ", lead='" + lead + '\'' +
                    ", view_type=" + view_type +
                    ", type=" + type +
                    ", column_name='" + column_name + '\'' +
                    ", image_url='" + image_url + '\'' +
                    ", is_good=" + is_good +
                    ", is_collect=" + is_collect +
                    ", link='" + link + '\'' +
                    ", share_link='" + share_link + '\'' +
                    ", video_is_sans_href='" + video_is_sans_href + '\'' +
                    ", video_url='" + video_url + '\'' +
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

        public String getLead() {
            return lead;
        }

        public void setLead(String lead) {
            this.lead = lead;
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

        public String getVideo_is_sans_href() {
            return video_is_sans_href;
        }

        public void setVideo_is_sans_href(String video_is_sans_href) {
            this.video_is_sans_href = video_is_sans_href;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }
    }

}
