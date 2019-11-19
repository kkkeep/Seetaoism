package com.seetaoism.data.entity;


public class UserInfo {

    private String head_url;
    private String nickname;
    private String mobile;
    private String email;
    private int check_in_status;

    private int sina_bind;

    public UserInfo(int sina_bind) {
        this.sina_bind = sina_bind;
    }

    public int getSina_bind() {
        return sina_bind;
    }

    public void setSina_bind(int sina_bind) {
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
}
