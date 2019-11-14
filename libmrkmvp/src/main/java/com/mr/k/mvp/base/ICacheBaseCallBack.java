package com.mr.k.mvp.base;

/*
 *
 * ICacheBaseCallBack 集成至 IBaseCallBack 的 onSuccess 默认就表示 从服务器回来的数据。
 **/
public interface ICacheBaseCallBack<T> extends IBaseCallBack<T>{


    // 表示数据从内存缓存里面读取出来的
    void onMemoryCacheBack(T data);

    // 表示数据从 sdcard 中读取出来的
    void onDiskCacheBack(T data);

}
