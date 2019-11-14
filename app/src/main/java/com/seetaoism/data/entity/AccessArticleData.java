package com.seetaoism.data.entity;

import java.util.List;


public class AccessArticleData {

    private List<NewsData.News>  access_article_list;

    public void setAccess_article_list(List<NewsData.News> access_article_list) {
        this.access_article_list = access_article_list;
    }

    public List<NewsData.News> getAccess_article_list() {
        return access_article_list;
    }
}
