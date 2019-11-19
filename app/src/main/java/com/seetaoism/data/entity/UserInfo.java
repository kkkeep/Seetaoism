package com.seetaoism.data.entity;


public class UserInfo {

    private String head_url;
    private String nickname;
    private String mobile;
    private String email;
    private String qq_openid;
    private String sina_openid;

    private String wechat_openid;
    private String wechat_unionid;
    private int check_in_status;
    private int qq_bind;
    private int sina_bind;
    private int wechat_bind;
    private int notice_count;
    private int my_integral;





    public UserInfo(int sina_bind) {
        this.sina_bind = sina_bind;
    }



    public UserInfo(String head_url, String nickname, String mobile, String email, int check_in_status) {
        this.head_url = head_url;
        this.nickname = nickname;
        this.mobile = mobile;
        this.email = email;
        this.check_in_status = check_in_status;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "head_url='" + head_url + '\'' +
                ", nickname='" + nickname + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", check_in_status=" + check_in_status +
                '}';
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCheck_in_status() {
        return check_in_status;
    }

    public void setCheck_in_status(int check_in_status) {
        this.check_in_status = check_in_status;
    }

    public String getQq_openid() {
        return qq_openid;
    }

    public void setQq_openid(String qq_openid) {
        this.qq_openid = qq_openid;
    }

    public String getSina_openid() {
        return sina_openid;
    }

    public void setSina_openid(String sina_openid) {
        this.sina_openid = sina_openid;
    }

    public String getWechat_openid() {
        return wechat_openid;
    }

    public void setWechat_openid(String wechat_openid) {
        this.wechat_openid = wechat_openid;
    }

    public String getWechat_unionid() {
        return wechat_unionid;
    }

    public void setWechat_unionid(String wechat_unionid) {
        this.wechat_unionid = wechat_unionid;
    }

    public int getQq_bind() {
        return qq_bind;
    }

    public void setQq_bind(int qq_bind) {
        this.qq_bind = qq_bind;
    }

    public int getSina_bind() {
        return sina_bind;
    }

    public void setSina_bind(int sina_bind) {
        this.sina_bind = sina_bind;
    }

    public int getWechat_bind() {
        return wechat_bind;
    }

    public void setWechat_bind(int wechat_bind) {
        this.wechat_bind = wechat_bind;
    }

    public int getNotice_count() {
        return notice_count;
    }

    public void setNotice_count(int notice_count) {
        this.notice_count = notice_count;
    }

    public int getMy_integral() {
        return my_integral;
    }

    public void setMy_integral(int my_integral) {
        this.my_integral = my_integral;
    }
}
