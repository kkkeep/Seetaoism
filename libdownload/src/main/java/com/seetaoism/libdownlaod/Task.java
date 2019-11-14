package com.seetaoism.libdownlaod;

import java.io.File;

/*
 * created by taofu on 2019-10-23
 **/
public class Task {

    private DownloadListener listener;

    private File mDirectory; // 存储目录

    private String url; // 下载url

    private String fileName; // 文件名

    private String fileStorePath; // 下载完成后的存储路径

    private long contentLength; // 文件总大小

    private long loadedLength; // 已经下载了的大小

    private int progress; // 进度 百分比

    private int notificationId; // 通知id

    private String packageName; //包名

    String errorMsg;

    public Task(String url, String fileName, String packageName) {
        this.url = url;
        this.fileName = fileName;
        this.packageName = packageName;
        notificationId = url.hashCode();
    }

    public String getUrl() {
        return url;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }


    public DownloadListener getListener() {
        return listener;
    }

    public void setListener(DownloadListener listener) {
        this.listener = listener;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {

        this.fileName = fileName;
    }

    public String getFileStorePath() {
        return fileStorePath;
    }

    public void setFileStorePath(String fileStorePath) {
        this.fileStorePath = fileStorePath;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public long getLoadedLength() {
        return loadedLength;
    }

    public void setLoadedLength(long loadedLength) {
        this.loadedLength = loadedLength;

    }

    public int getProgress() {
        progress = (int) (loadedLength * 100 /  contentLength);
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getNotificationId() {

        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }
}
