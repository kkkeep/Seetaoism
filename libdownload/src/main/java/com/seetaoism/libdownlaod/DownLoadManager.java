package com.seetaoism.libdownlaod;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.webkit.URLUtil;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Request;
import okhttp3.Response;

/*
 * created by Cherry on 2019-10-23
 **/
public class DownLoadManager {


    final static String URL = "download_url";
    final static String PACKAGE_NAME = "download_package_name";
    final static String FILE_NAME = "download_file_name";

    private static volatile DownLoadManager mInstance;

    private Context mApplicationContext;

    private Map<String, StateListener> mDownloadListeners;

    private ArrayList<String> mLoadingUrls;
    private File mDownloadFileDirectory;
    private Handler mHandler;
    private Random mRandom;

    private DownLoadManager(Context context) {
        mApplicationContext = context.getApplicationContext();
        mDownloadListeners = new ConcurrentHashMap<>();
        mLoadingUrls = new ArrayList<>();
        mRandom = new Random();

        mHandler = new UIHandler();


        File cacheDir = context.getExternalCacheDir();
        if (cacheDir == null) {
            Toast.makeText(context.getApplicationContext(), "存储异常,不能下载", Toast.LENGTH_LONG).show();
            return;
        }

        mDownloadFileDirectory = new File(cacheDir, "apk_load");

        if (!mDownloadFileDirectory.exists()) {
            boolean success = mDownloadFileDirectory.mkdirs();
            if (!success) {
                Toast.makeText(context.getApplicationContext(), "存储异常,不能下载", Toast.LENGTH_LONG).show();
                mDownloadFileDirectory = null;
            }
        }


    }


    public static DownLoadManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DownLoadManager.class) {
                if (mInstance == null) {
                    mInstance = new DownLoadManager(context);
                }
            }
        }

        return mInstance;
    }


    public void startLoad(Context context, String url,String fileName) {
        startLoad(context, url, null,fileName,null);

    }

    public void startLoad(Context context, String url, StateListener listener) {
        startLoad(context, url, null, listener);

    }

    public void startLoad(Context context, String url, String packageName, StateListener listener) {
        startLoad(context, url, packageName, null, listener);
    }

    public void startLoad(Context context, String url, String packageName, String fileMame, StateListener listener) {

        if (mDownloadFileDirectory == null) {
            Toast.makeText(context, "下载路径不合法", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(url) || !URLUtil.isValidUrl(url)) {
            Toast.makeText(context, "下载路径不合法", Toast.LENGTH_LONG).show();
            return;
        }

        if(listener != null){
            mDownloadListeners.put(url, listener);

        }

        if (isInLoading(url)) {
            Toast.makeText(context, "正在下载", Toast.LENGTH_SHORT).show();
            return;
        }


        addUrlToList(url);


        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra(URL, url);
        if (!TextUtils.isEmpty(packageName)) {

            intent.putExtra(PACKAGE_NAME, packageName);
        }
        if (!TextUtils.isEmpty(fileMame)) {
            intent.putExtra(FILE_NAME, fileMame);
        }


        context.startService(intent);
    }


    void startLoad(Task task) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            handLoadFail(task, "不能再主线程里面做下载任务");
            return;
        }

        handPrepareLoadStart(task);

        Request.Builder builder = new Request.Builder();
        try {
            Response response = OkHttpUtils.getOkClient().newCall(builder.url(task.getUrl()).build()).execute();

            task.setFileName(guessFileName(response, task));
            File apkFile = new File(mDownloadFileDirectory, task.getFileName());
            task.setFileStorePath(apkFile.getAbsolutePath());

            if (response.body() != null) {
                task.setContentLength(response.body().contentLength());

                if (!isNeedLoad(task.getFileName(), task.getContentLength())) {

                    handLoadEnd(task);
                    return;
                }


                handLoadStart(task);

                writeToFile(task, response.body().byteStream());
            }


        } catch (IOException e) {
            handLoadFail(task, "请检查网络!");
            e.printStackTrace();
        }
    }

    // 检查本地是否已经下载过
    private boolean isNeedLoad(String fileName, long length) {

        File file = new File(mDownloadFileDirectory, fileName);

        if (file.exists()) {
            if (file.length() == length) {
                return false;
            } else {
                file.delete();
            }
        }

        // 删除所有之前下载过的apk
        File[] files = file.getParentFile().listFiles();
        for (File child : files) {
            if (child.isFile() && child.exists()) {
                child.delete();
            }
        }

        return true;
    }


    private void writeToFile(Task task, InputStream stream) {

        task.setFileName(task.getFileName());


        FileOutputStream outputStream = null;
        try {

            outputStream = new FileOutputStream(new File(task.getFileStorePath()));


            byte[] buffer = new byte[1024 * 4];
            int len = 0;

            int lastProgress = 0;

            int step = 1;
            while ((len = stream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);

                task.setLoadedLength(task.getLoadedLength() + len);

                if ((task.getProgress() - lastProgress) >= step) {
                    step = (mRandom.nextInt(3) + 1);
                    handLoadProgress(task);
                    lastProgress = task.getProgress();
                }
            }


            Thread.sleep(1000);

            handLoadEnd(task);

        } catch (Exception e) {
            if (e.getClass().getName().contains("net")) {
                handLoadFail(task, "网络异常!");
            } else {
                handLoadFail(task, "文件一次，请检查手机存储空间是否可用!");
            }
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    // 准备开始下载，
    private void handPrepareLoadStart(Task task) {
        task.getListener().prepare(task);
        postHandLoad(task, UIHandler.ACTION_PREPARE);
    }

    private void handLoadStart(Task task) {
        task.getListener().onStart(task);
        postHandLoad(task, UIHandler.ACTION_START);
    }

    private void handLoadFail(Task task, String msg) {
        if (task.getListener() != null) {
            task.getListener().onError(task, msg);
        }

        task.errorMsg = msg;

        postHandLoad(task, UIHandler.ACTION_ERROR);
        removeUrlFromList(task.getUrl());
    }

    private void handLoadProgress(Task task) {
        task.getListener().onProgress(task);
        postHandLoad(task, UIHandler.ACTION_PROGRESS);
    }

    private void handLoadEnd(Task task) {
        task.getListener().onEnd(task);
        removeUrlFromList(task.getUrl());
        postHandLoad(task, UIHandler.ACTION_END);



    }


    private void postHandLoad(Task task, int action) {
        Message message = Message.obtain();
        message.what = action;
        message.obj = task;
        mHandler.sendMessage(message);
    }



    // 是否正在下载中
    private boolean isInLoading(String newUrl) {
        for (String url : mLoadingUrls) {
            if (url.equals(newUrl)) {
                return true;
            }
        }
        return false;
    }

    private void removeUrlFromList(String url) {
        mLoadingUrls.remove(url);
    }

    private void addUrlToList(String url) {
        mLoadingUrls.add(url);
    }

    private String guessFileName(Response response, Task task) {
        if (!TextUtils.isEmpty(task.getFileName())) {
            return task.getFileName();
        }
        String cd = response.headers().get("Content-Disposition");
        String ct = response.headers().get("content-type");

        // 如果能拿到文件名就返回，拿不到就返回一个 "downloadfile.apk"
        String name = URLUtil.guessFileName(task.getUrl(), cd, ct);

        if (!TextUtils.isEmpty(name) && name.contains("downloadfile")) {
            name =  System.currentTimeMillis() + name;
        }
        return name;
    }

    public static String getSHA1(Task task) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(task.getUrl().getBytes());
            byte[] digest = md.digest();

            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            return hexstr.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return System.currentTimeMillis() +"" ;
    }




     static void destroy(){
         mInstance = null;
    }

    public class UIHandler extends Handler {

        private static final int ACTION_PREPARE = 0x100;
        private static final int ACTION_START = 0x101;
        private static final int ACTION_PROGRESS = 0x102;
        private static final int ACTION_ERROR = 0x103;
        private static final int ACTION_END = 0x104;


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int action = msg.what;

            Task task = (Task) msg.obj;

            StateListener listener = mDownloadListeners.get(task.getUrl());

            if (listener == null || !listener.isAlive()) {
                return;
            }

            switch (action) {
                case ACTION_PREPARE: {
                    listener.prepare(task);
                    break;
                }

                case ACTION_START: {
                    listener.onStart(task);
                    break;
                }

                case ACTION_PROGRESS: {
                    listener.onProgress(task);
                    break;
                }

                case ACTION_ERROR: {
                    listener.onError(task, task.errorMsg);
                    mDownloadListeners.remove(task.getUrl());
                    break;
                }

                case ACTION_END: {
                    listener.onEnd(task);
                    mDownloadListeners.remove(task.getUrl());
                    break;
                }
            }
        }
    }
}
