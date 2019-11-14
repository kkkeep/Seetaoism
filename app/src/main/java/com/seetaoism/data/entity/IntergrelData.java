package com.seetaoism.data.entity;

public class IntergrelData {

    private int my_integral;
    private int check_in_status;
    private int read_article_count;
    private int share_article_count;

    public IntergrelData(int my_integral, int check_in_status, int read_article_count, int share_article_count) {
        this.my_integral = my_integral;
        this.check_in_status = check_in_status;
        this.read_article_count = read_article_count;
        this.share_article_count = share_article_count;
    }

    public IntergrelData() {
    }

    @Override
    public String toString() {
        return "IntergrelData{" +
                "my_integral=" + my_integral +
                ", check_in_status=" + check_in_status +
                ", read_article_count=" + read_article_count +
                ", share_article_count=" + share_article_count +
                '}';
    }

    public int getMy_integral() {
        return my_integral;
    }

    public void setMy_integral(int my_integral) {
        this.my_integral = my_integral;
    }

    public int getCheck_in_status() {
        return check_in_status;
    }

    public void setCheck_in_status(int check_in_status) {
        this.check_in_status = check_in_status;
    }

    public int getRead_article_count() {
        return read_article_count;
    }

    public void setRead_article_count(int read_article_count) {
        this.read_article_count = read_article_count;
    }

    public int getShare_article_count() {
        return share_article_count;
    }

    public void setShare_article_count(int share_article_count) {
        this.share_article_count = share_article_count;
    }
}
