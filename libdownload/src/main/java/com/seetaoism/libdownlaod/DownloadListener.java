package com.seetaoism.libdownlaod;

/*
 * created by taofu on 2019-10-24
 **/
 interface DownloadListener {

    void prepare(Task task);
    void onStart(Task task);
    void onError(Task task,String msg);
    void onProgress(Task task);
    void onEnd(Task task);

}
