package com.mr.k.mvp.ok;

import com.mr.k.mvp.BuildConfig;


import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * created by taofu on 2019-08-25
 **/
public class ApiDataService {


    private static final long DEFAULT_TIMEOUT = 20000; // 默认超时20s

    private static List<Interceptor> mInterceptors;
    private  static Converter.Factory mFactory;
    private static volatile Object mApiService;

    private static String mBaseUrl;
    private static Class mAClass;



    public static void init(List<Interceptor> interceptors, Converter.Factory factory,String baseUrl,Class aClass){
        mInterceptors = interceptors;
        mFactory = factory;
        mBaseUrl = baseUrl;
        mAClass = aClass;

    }

    public static Object getApiService(){

        if(mBaseUrl == null || mAClass == null){
            throw new NullPointerException(" Must init ( ApiDataService.init(url,class) ) before invoke getApiService");
        }

        if(mApiService == null){

            synchronized (ApiDataService.class){

                if(mApiService == null){

                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

                    /**
                     * 注意，如果有大文件下载，或者 response 里面的body 很大，要么不加HttpLoggingInterceptor 拦截器
                     * 如果非要加，日志级别不能是 BODY,否则容易内存溢出。
                     */
                    if (BuildConfig.DEBUG) {
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                    } else {
                        logging.setLevel(HttpLoggingInterceptor.Level.NONE);
                    }

                  OkHttpClient.Builder builder  = new OkHttpClient.Builder();

                    if(mInterceptors != null){
                        for(Interceptor interceptor : mInterceptors){
                            builder.addInterceptor(interceptor);
                        }
                    }

                    OkHttpClient httpClient  = builder.addInterceptor(logging)
                            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .build();

                    Retrofit retrofit = new Retrofit.Builder()
                            .client(httpClient)
                            .addConverterFactory(mFactory == null ? GsonConverterFactory.create() : mFactory) // 帮我们把json 窜转为 entity 对象
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 结合 rxjava 使用
                            .baseUrl(mBaseUrl)
                            .build();


                    mApiService = retrofit.create(mAClass);

                }

            }
        }
        return mApiService;
    }
}
