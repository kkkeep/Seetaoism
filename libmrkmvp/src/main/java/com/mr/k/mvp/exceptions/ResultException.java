package com.mr.k.mvp.exceptions;

import android.content.Context;

import com.mr.k.mvp.MvpManager;

import java.io.IOException;


public class ResultException extends IOException {

    public static final int SERVER_ERROR = 0x1000000;



    public int code;

    public Throwable source;
    public  String msg;



    public ResultException(Throwable source) {
        super(source);
        this.source = source;
    }

    public ResultException(int code) {
        super("");
        this.code = code;
    }


    public ResultException(int code, String message) {
        this(message);
        this.msg = message;
        this.code = code;
    }

    public ResultException(String message) {
        super(message);
        this.msg = message;
        this.code = -1;
    }


    private String getMessage(Context context) {
        if(source != null && source instanceof IOException){
            return MvpManager.getMessageGetter().getNetError();
        }

        if(msg != null){
            return msg;
        }

        if(code == SERVER_ERROR ){
            return MvpManager.getMessageGetter().getServerError();
        }

         return MvpManager.getMessageGetter().getUnKnowError();
    }

    @Override
    public String getMessage() {
        return getMessage(MvpManager.getContext());

    }
}
