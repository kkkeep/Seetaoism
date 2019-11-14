package com.seetaoism.data.entity;


public class HttpResult<T> {

    public String message;
    public int code;
    public T data;

    @Override
    public String toString() {
        return "HttpResult{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
