package com.seetaoism.libdownlaod;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import java.io.File;

/*
 * created by Cherry on 2019-10-25
 **/
 class NotificationListener implements DownloadListener {

    private static final String DOWNLOAD_CHANNEL_ID = "download";

    private Context mContext;

    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;


    public NotificationListener(Context context) {
        this.mContext = context;
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createNotificationChannel();
    }


    /**
     * 创建通知分类
     */
    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(DOWNLOAD_CHANNEL_ID, "下载", importance);
            channel.setDescription("文件下载类通知");
            //channel.setSound(null, null);// 禁用通知的声音

            NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);



        }
    }

    // 创建并显示通知
    private void createAndShowNotification(int notificaitonId, String title, String text){

        mBuilder = new NotificationCompat.Builder(mContext, DOWNLOAD_CHANNEL_ID);

        mBuilder.setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setPriority(NotificationCompat.PRIORITY_LOW);

        int PROGRESS_MAX = 100;
        int PROGRESS_CURRENT = 0;
        mBuilder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false).setAutoCancel(false);
        Notification notification = mBuilder.build();

        // notification.flags  |= Notification.FLAG_NO_CLEAR;// 不让用户清除通知
        mNotificationManager.notify(notificaitonId,notification);
    }



    @Override
    public void prepare(Task task) {
        Log.d("Test", "prepare");
        String title = "";
        String description = "";
        if(!TextUtils.isEmpty(task.getFileName())){
                title = task.getFileName();
                description = "准备下载";
        }else{
            title =  "准备下载";
        }
        createAndShowNotification(task.getNotificationId(), title,description);
    }



    @Override
    public void onStart(Task task) {
        Log.d("Test", "onStart");
        mBuilder.setContentTitle(task.getFileName()).setContentText("正在下载 " + task.getProgress() + " %s").setProgress(100, task.getProgress(), false);
        mNotificationManager.notify(task.getNotificationId(), mBuilder.build());
    }

    @Override
    public void onError(Task task, String msg) {
        Log.d("Test", "onError");

        if(TextUtils.isEmpty(task.getFileName())){ // 之前通知没有设置正确的文件名，
            mBuilder.setContentTitle("下载失败");
            mBuilder.setContentText(msg);

        }else{
            mBuilder.setContentTitle(task.getFileName());
            mBuilder.setContentText("下载失败 : " + msg);
        }

        mNotificationManager.notify(task.getNotificationId(), mBuilder.build());

    }

    @Override
    public void onProgress(Task task) {
        Log.d("Test", "onProgress " + task.getProgress());
        mBuilder.setContentTitle(task.getFileName()).setContentText("正在下载 " + task.getProgress() + " %s").setProgress(100, task.getProgress(), false);
        mNotificationManager.notify(task.getNotificationId(), mBuilder.build());
    }

    @Override
    public void onEnd(Task task) {
        Log.d("Test", "onEnd");
        // setProgress(0,0,false) 都设置为 0 表示关闭进度条
        File file = new File(task.getUrl());
        Uri uri = getUriByFile(file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");

        PendingIntent pendingIntent = PendingIntent.getActivity(mContext.getApplicationContext(), task.getNotificationId(), intent, 0);

        mContext.startActivity(intent);


        mNotificationManager.notify(task.getNotificationId(), mBuilder.setContentText("下载完成").setProgress(0,0,false).setContentIntent(pendingIntent).setAutoCancel(true).build());
    }

    private Uri getUriByFile(File file) {
        Uri uri = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                // content:/Android/data/jy.com.wanandroid/cache/{name}/xxxx.apk,这儿这个name 和 xml 里面定义的那个name.用name 代替 apk_load

                // 第二个参数一定要和 AndroidManifest.xml 文件中声明的FileProvider 中的   android:authorities 一样
                return FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".download.fileProvider", file);
            } else {
                // file://Android/data/jy.com.wanandroid/cache/apk_load/xxxx.apk
                return Uri.fromFile(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
