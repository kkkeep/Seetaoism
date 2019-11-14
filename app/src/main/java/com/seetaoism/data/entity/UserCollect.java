package com.seetaoism.data.entity;

public class UserCollect {



    String is_good;
    String is_collect;
    String is_diff;

    public UserCollect(String is_good, String is_collect, String is_diff) {
        this.is_good = is_good;
        this.is_collect = is_collect;
        this.is_diff = is_diff;
    }

    public UserCollect() {
    }

    @Override
    public String toString() {
        return "UserCollect{" +
                "is_good='" + is_good + '\'' +
                ", is_collect='" + is_collect + '\'' +
                ", is_diff='" + is_diff + '\'' +
                '}';
    }

    public String getIs_good() {
        return is_good;
    }

    public void setIs_good(String is_good) {
        this.is_good = is_good;
    }

    public String getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(String is_collect) {
        this.is_collect = is_collect;
    }

    public String getIs_diff() {
        return is_diff;
    }

    public void setIs_diff(String is_diff) {
        this.is_diff = is_diff;
    }
}
