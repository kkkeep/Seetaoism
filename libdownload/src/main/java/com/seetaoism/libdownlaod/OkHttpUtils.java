package com.seetaoism.libdownlaod;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/*
 * created by taofu on 2019-10-24
 **/
public class OkHttpUtils {

    private static final long DEFAULT_TIMEOUT = 20000; // 默认超时20s

    private static OkHttpClient mOkHttpClient;

    public synchronized static OkHttpClient getOkClient(){

        if(mOkHttpClient != null){
            return  mOkHttpClient;
        }

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        /**
         * 注意，如果有大文件下载，或者 response 里面的body 很大，要么不加HttpLoggingInterceptor 拦截器
         * 如果非要加，日志级别不能是 BODY,否则容易内存溢出。
         */
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        mOkHttpClient = new OkHttpClient.Builder()

                .addInterceptor(logging)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        return mOkHttpClient;
    }

}
