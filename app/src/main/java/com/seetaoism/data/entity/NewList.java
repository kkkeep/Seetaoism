package com.seetaoism.data.entity;

public class NewList {


    private String id;
    private String theme;
    private String description;
    private String lead;
    private int view_type;
    private int type;
    private String column_name;
    private String image_url;
    private int is_good;
    private int is_collect;
    private String link;
    private String share_link;
    private String video_is_sans_href;
    private String video_url;

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
