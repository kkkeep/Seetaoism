package com.seetaoism.libdownlaod;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

/*
 * created by Cherry on 2019-10-23
 **/
public class DownloadService extends IntentService {


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadService() {
        super("DownloadService");
    }




    @Override
    public void onCreate() {
        super.onCreate();

    }





    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null){
            String url = intent.getStringExtra(DownLoadManager.URL);
            String pageName = intent.getStringExtra(DownLoadManager.PACKAGE_NAME);
            String fileName = intent.getStringExtra(DownLoadManager.FILE_NAME);
            Task task = new Task(url,fileName,pageName);


            task.setListener(new NotificationListener(this));
            DownLoadManager.getInstance(this).startLoad(task);
        }


    }




    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        DownLoadManager.destroy();
    }
}
