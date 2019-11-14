package com.seetaoism.data.entity;


public class Token {

    private String value;
    private Long expire_time;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getExpireTime() {
        return expire_time;
    }

    public void setExpireTime(Long expire_time) {
        this.expire_time = expire_time;
    }


    @Override
    public String toString() {
        return "Token{" +
                "value='" + value + '\'' +
                ", expire_time=" + expire_time +
                '}';
    }
}
