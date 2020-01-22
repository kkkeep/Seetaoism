package com.seetaoism.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Objects;

/*
 * created by Cherry on 2020-01-09
 **/
@Entity
public class Ad {

    @Id
    private String id;

    private String title;
    private String target_href;
    private int layout;
    private int width;
    private int height;
    private String ad_url;
    private String filePath;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTarget_href() {
        return target_href;
    }

    public void setTarget_href(String target_href) {
        this.target_href = target_href;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getAd_url() {
        return ad_url;
    }

    public void setAd_url(String ad_url) {
        this.ad_url = ad_url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ad ad = (Ad) o;
        return layout == ad.layout &&
                id.equals(ad.id) &&
                target_href.equals(ad.target_href) &&
                ad_url.equals(ad.ad_url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, target_href, layout, ad_url);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
