package com.seetaoism;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.mr.k.mvp.ExceptionMessageGetter;
import com.mr.k.mvp.MvpManager;
import com.mr.k.mvp.UserManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.seetaoism.data.okhttp.Interceptor.CommonParamsInterceptor;
import com.seetaoism.data.okhttp.Interceptor.UserInterceptor;
import com.seetaoism.data.okhttp.JDApiService;
import com.seetaoism.data.okhttp.convertor.JDGsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import jy.com.libumengsharelogin.UMUtils;
import okhttp3.Interceptor;


public class JDApplication extends Application {

    public static Application mApplicationContext;
    @Override
    public void onCreate() {
        super.onCreate();

        // 主要是添加下面这句代码
        MultiDex.install(this);


        mApplicationContext = this;


		initRefreshLayout();
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);// 初始化 JPush


        initMvp();
        UMUtils.initUmeng(this);
    }


    private void initMvp(){

        MvpManager.init(this);
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new UserInterceptor());
        interceptors.add(new CommonParamsInterceptor());
        MvpManager.initOkHttp(AppConstant.BASE_URL, JDApiService.class, JDGsonConverterFactory.create(),interceptors);
    }


    private void initRefreshLayout() {

        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {//设置全局的Header构建器
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new ClassicsHeader(context)
                        .setDrawableSize(14)
                        .setTextSizeTitle(12)
                        .setAccentColor(0xffb5b5b5)//文字提示颜色
                        .setPrimaryColor(0xfffafafa)//全局设置主题颜色
                        .setEnableLastTime(false);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });

        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() { //设置全局的Footer构建器
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new ClassicsFooter(context) //指定为经典Footer，默认是 BallPulseFooter
                        .setDrawableSize(14)
                        .setTextSizeTitle(12)
                        .setAccentColor(0xffb5b5b5)//文字提示颜色
                        .setPrimaryColor(0xfffafafa);//全局设置主题颜色
            }
        });
    }


}
