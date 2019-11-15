package com.seetaoism.data.entity;

import java.io.Serializable;
import java.util.Objects;

public class NoticedetailsBean implements Serializable {
    /*
    * type=1
    */
    private String content;
    private String time;
    private String from_type;
    /*
     * type=2
     */
    private String reply_user_head_url;
    private String reply_user_name;
    private String reply_time;
    private String by_reply_user_name;
    private String reply_content;
    private String by_reply_content;
    private String reply_type;
    private String link;
    private String article_image_url;
    private String article_theme;
    private String article_time;
    private String delete_comment_reply_id;
    private String article_id;
    private int article_is_good;
    private int article_is_collect;
    /*
     * type=5
     */
    private String comment_content;
    private String comment_user_head_url;
    private String comment_user_name;
    private String comment_time;
    private int delete_article_id;
    /*
     * type=4
     */
    private String praise_content;
    private String praise_user_head_url;
    private String praise_user_name;
    private String praise_time;
    private String article_praise_time;
    private String article_praise_count;
    /*
     * type=3
     */
    private String comment_praise_time;
    private String comment_praise_count;
    private int delete_comment_id;
    private String description;
    private String share_link;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShare_link() {
        return share_link;
    }

    public void setShare_link(String share_link) {
        this.share_link = share_link;
    }

    public String getArticle_praise_count() {
        return article_praise_count;
    }

    public void setArticle_praise_count(String article_praise_count) {
        this.article_praise_count = article_praise_count;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFrom_type() {
        return from_type;
    }

    public void setFrom_type(String from_type) {
        this.from_type = from_type;
    }

    public String getReply_user_head_url() {
        return reply_user_head_url;
    }

    public void setReply_user_head_url(String reply_user_head_url) {
        this.reply_user_head_url = reply_user_head_url;
    }

    public String getReply_user_name() {
        return reply_user_name;
    }

    public void setReply_user_name(String reply_user_name) {
        this.reply_user_name = reply_user_name;
    }

    public String getReply_time() {
        return reply_time;
    }

    public void setReply_time(String reply_time) {
        this.reply_time = reply_time;
    }

    public String getBy_reply_user_name() {
        return by_reply_user_name;
    }

    public void setBy_reply_user_name(String by_reply_user_name) {
        this.by_reply_user_name = by_reply_user_name;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public String getBy_reply_content() {
        return by_reply_content;
    }

    public void setBy_reply_content(String by_reply_content) {
        this.by_reply_content = by_reply_content;
    }

    public String getReply_type() {
        return reply_type;
    }

    public void setReply_type(String reply_type) {
        this.reply_type = reply_type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getArticle_image_url() {
        return article_image_url;
    }

    public void setArticle_image_url(String article_image_url) {
        this.article_image_url = article_image_url;
    }

    public String getArticle_theme() {
        return article_theme;
    }

    public void setArticle_theme(String article_theme) {
        this.article_theme = article_theme;
    }

    public String getArticle_time() {
        return article_time;
    }

    public void setArticle_time(String article_time) {
        this.article_time = article_time;
    }

    public String getDelete_comment_reply_id() {
        return delete_comment_reply_id;
    }

    public void setDelete_comment_reply_id(String delete_comment_reply_id) {
        this.delete_comment_reply_id = delete_comment_reply_id;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public int getArticle_is_good() {
        return article_is_good;
    }

    public void setArticle_is_good(int article_is_good) {
        this.article_is_good = article_is_good;
    }

    public int getArticle_is_collect() {
        return article_is_collect;
    }

    public void setArticle_is_collect(int article_is_collect) {
        this.article_is_collect = article_is_collect;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getComment_user_head_url() {
        return comment_user_head_url;
    }

    public void setComment_user_head_url(String comment_user_head_url) {
        this.comment_user_head_url = comment_user_head_url;
    }

    public String getComment_user_name() {
        return comment_user_name;
    }

    public void setComment_user_name(String comment_user_name) {
        this.comment_user_name = comment_user_name;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public int getDelete_article_id() {
        return delete_article_id;
    }

    public void setDelete_article_id(int delete_article_id) {
        this.delete_article_id = delete_article_id;
    }

    public String getPraise_content() {
        return praise_content;
    }

    public void setPraise_content(String praise_content) {
        this.praise_content = praise_content;
    }

    public String getPraise_user_head_url() {
        return praise_user_head_url;
    }

    public void setPraise_user_head_url(String praise_user_head_url) {
        this.praise_user_head_url = praise_user_head_url;
    }

    public String getPraise_user_name() {
        return praise_user_name;
    }

    public void setPraise_user_name(String praise_user_name) {
        this.praise_user_name = praise_user_name;
    }

    public String getPraise_time() {
        return praise_time;
    }

    public void setPraise_time(String praise_time) {
        this.praise_time = praise_time;
    }

    public String getArticle_praise_time() {
        return article_praise_time;
    }

    public void setArticle_praise_time(String article_praise_time) {
        this.article_praise_time = article_praise_time;
    }

    public String getComment_praise_time() {
        return comment_praise_time;
    }

    public void setComment_praise_time(String comment_praise_time) {
        this.comment_praise_time = comment_praise_time;
    }

    public String getComment_praise_count() {
        return comment_praise_count;
    }

    public void setComment_praise_count(String comment_praise_count) {
        this.comment_praise_count = comment_praise_count;
    }

    public int getDelete_comment_id() {
        return delete_comment_id;
    }

    public void setDelete_comment_id(int delete_comment_id) {
        this.delete_comment_id = delete_comment_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoticedetailsBean that = (NoticedetailsBean) o;
        return article_is_good == that.article_is_good &&
                article_is_collect == that.article_is_collect &&
                delete_article_id == that.delete_article_id &&
                delete_comment_id == that.delete_comment_id &&
                Objects.equals(content, that.content) &&
                Objects.equals(time, that.time) &&
                Objects.equals(description, that.description) &&
                Objects.equals(share_link, that.share_link) &&
                Objects.equals(from_type, that.from_type) &&
                Objects.equals(reply_user_head_url, that.reply_user_head_url) &&
                Objects.equals(reply_user_name, that.reply_user_name) &&
                Objects.equals(reply_time, that.reply_time) &&
                Objects.equals(by_reply_user_name, that.by_reply_user_name) &&
                Objects.equals(reply_content, that.reply_content) &&
                Objects.equals(by_reply_content, that.by_reply_content) &&
                Objects.equals(reply_type, that.reply_type) &&
                Objects.equals(link, that.link) &&
                Objects.equals(article_image_url, that.article_image_url) &&
                Objects.equals(article_theme, that.article_theme) &&
                Objects.equals(article_time, that.article_time) &&
                Objects.equals(delete_comment_reply_id, that.delete_comment_reply_id) &&
                Objects.equals(article_id, that.article_id) &&
                Objects.equals(comment_content, that.comment_content) &&
                Objects.equals(comment_user_head_url, that.comment_user_head_url) &&
                Objects.equals(comment_user_name, that.comment_user_name) &&
                Objects.equals(comment_time, that.comment_time) &&
                Objects.equals(praise_content, that.praise_content) &&
                Objects.equals(praise_user_head_url, that.praise_user_head_url) &&
                Objects.equals(praise_user_name, that.praise_user_name) &&
                Objects.equals(praise_time, that.praise_time) &&
                Objects.equals(article_praise_count, that.article_praise_count) &&
                Objects.equals(article_praise_time, that.article_praise_time) &&
                Objects.equals(comment_praise_time, that.comment_praise_time) &&
                Objects.equals(comment_praise_count, that.comment_praise_count);
    }

    @Override
    public int hashCode() {

        return Objects.hash(description,share_link,article_praise_count,content, time, from_type, reply_user_head_url, reply_user_name, reply_time, by_reply_user_name, reply_content, by_reply_content, reply_type, link, article_image_url, article_theme, article_time, delete_comment_reply_id, article_id, article_is_good, article_is_collect, comment_content, comment_user_head_url, comment_user_name, comment_time, delete_article_id, praise_content, praise_user_head_url, praise_user_name, praise_time, article_praise_time, comment_praise_time, comment_praise_count, delete_comment_id);
    }

    @Override
    public String toString() {
        return "NoticedetailsBean{" +
                "content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", description='" + description + '\'' +
                ",share_link ='" + share_link + '\'' +
                ", from_type='" + from_type + '\'' +
                ", reply_user_head_url='" + reply_user_head_url + '\'' +
                ", reply_user_name='" + reply_user_name + '\'' +
                ", reply_time='" + reply_time + '\'' +
                ", by_reply_user_name='" + by_reply_user_name + '\'' +
                ", reply_content='" + reply_content + '\'' +
                ", by_reply_content='" + by_reply_content + '\'' +
                ", reply_type='" + reply_type + '\'' +
                ", link='" + link + '\'' +
                ", article_praise_count='" + article_praise_count + '\'' +
                ", article_image_url='" + article_image_url + '\'' +
                ", article_theme='" + article_theme + '\'' +
                ", article_time='" + article_time + '\'' +
                ", delete_comment_reply_id='" + delete_comment_reply_id + '\'' +
                ", article_id='" + article_id + '\'' +
                ", article_is_good=" + article_is_good +
                ", article_is_collect=" + article_is_collect +
                ", comment_content='" + comment_content + '\'' +
                ", comment_user_head_url='" + comment_user_head_url + '\'' +
                ", comment_user_name='" + comment_user_name + '\'' +
                ", comment_time='" + comment_time + '\'' +
                ", delete_article_id=" + delete_article_id +
                ", praise_content='" + praise_content + '\'' +
                ", praise_user_head_url='" + praise_user_head_url + '\'' +
                ", praise_user_name='" + praise_user_name + '\'' +
                ", praise_time='" + praise_time + '\'' +
                ", article_praise_time='" + article_praise_time + '\'' +
                ", comment_praise_time='" + comment_praise_time + '\'' +
                ", comment_praise_count='" + comment_praise_count + '\'' +
                ", delete_comment_id=" + delete_comment_id +
                '}';
    }
}
