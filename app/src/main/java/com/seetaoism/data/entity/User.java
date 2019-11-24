package com.seetaoism.data.entity;

import com.mr.k.mvp.IUser;

import org.jetbrains.annotations.NotNull;

/*
 * created by Cherry on 2019-08-26
 **/
public class User implements IUser {

    private Token token;
    private UserInfo user_info;

    private boolean isRefresh;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public UserInfo getUserInfo() {
        return user_info;
    }

    public void setUserInfo(UserInfo user_info) {
        this.user_info = user_info;
    }

    public UserInfo getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfo user_info) {
        this.user_info = user_info;
    }

    @Override
    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;

    }

    @Override
    public String toString() {
        return "User{" +
                "token=" + token +
                ", user_info=" + user_info +
                '}';
    }

    @Override
    public String getTokenString() {
        return token == null ? null : token.getValue();
    }
}
