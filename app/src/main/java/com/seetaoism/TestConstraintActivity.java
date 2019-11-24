package com.seetaoism;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mr.k.mvp.base.BaseActivity;
import com.mr.k.mvp.utils.Logger;
import com.mr.k.mvp.utils.PermissionUtils;
import com.seetaoism.libdownlaod.DownLoadManager;
import com.seetaoism.libdownlaod.StateListener;
import com.seetaoism.libdownlaod.Task;
import com.seetaoism.widgets.ColumnItemView;

import jy.com.libbanner.MarqueeTextView;


public class TestConstraintActivity extends BaseActivity {

    public static final String DOWNLOAD_URL1 = "https://imtt.dd.qq.com/16891/89E1C87A75EB3E1221F2CDE47A60824A.apk?fsname=com.snda.wifilocating_4.2.62_3192.apk&csr=1bbd";
    public static final String DOWNLOAD_URL2 = "https://www.wanandroid.com/blogimgs/64542bbb-b0f6-4c0e-828f-e38eb3abdc00.apk";
    public static final String DOWNLOAD_URL3 = "https://ftp-new-apk.pconline.com.cn/4ffdda3af99a7f1826fa997bea3e1033/pub/download/201907/pconline1562082007841.apk";
    public static final String DOWNLOAD_URL4 = "https://downali.game.uc.cn/s/3/3/20190319171901_uc.apk?x-oss-process=udf/uc-apk,ZBHDhDR0L1tlSsK0wpLCng==40c29d34414a2b14&sh=10&sf=278909978&vh=56d180e146570661debd76a0fbce86f5&cc=1729610161&did=f307d17276b24e78b2d466e88aa0f2a2";
    public static final String DOWNLOAD_URL5 = "https://gdown.baidu.com/data/wisegame/0e58d76e3c525828/anzhishichang_6620.apk";
    ColumnItemView mColumnItemView;

    private static final String TAG = "TestConstraintActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test);


        TextView marqueeTextView  = findViewById(R.id.news_item_header_tv_flash);

         SpannableString string = new SpannableString("实际开发极乐世界市解放路聚少离多积分际开发极乐世界市解放路聚少离多积分际开发极乐世界市解放路聚少离多积分");

         ClickableSpan clickableSpan = new ClickableSpan() {
             @Override
             public void onClick(@NonNull View widget) {
                 Toast.makeText(TestConstraintActivity.this, "ddd",Toast.LENGTH_SHORT).show();
             }

             @Override
             public void updateDrawState(TextPaint ds) {
                 super.updateDrawState(ds);
                 // 设置显示的内容文本颜色
                 ds.setColor(0xff686868);
                 ds.setUnderlineText(false);
             }
         };
        marqueeTextView.setHighlightColor(getResources().getColor(android.R.color.transparent));
        marqueeTextView.setMovementMethod(LinkMovementMethod.getInstance());
        string.setSpan(clickableSpan, 0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
         marqueeTextView.setText(string);

       /* DownLoadManager.getInstance(this).startLoad(this, DOWNLOAD_URL2, new StateListener(this) {
            @Override
            public void prepare(Task task) {

                Logger.d("%s %s ", TAG,task.getFileName());
            }

            @Override
            public void onStart(Task task) {
                Logger.d("%s %s ", TAG,task.getFileName());
            }

            @Override
            public void onError(Task task, String msg) {
                Logger.d("%s %s ", TAG,task.getFileName());
            }

            @Override
            public void onProgress(Task task) {
                Logger.d("%s %s = %s",TAG, task.getFileName(),task.getProgress());
            }

            @Override
            public void onEnd(Task task) {
                Logger.d("%s,%s ", TAG,task.getFileName());
            }
        });*/


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Test", " invoke onStart method");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Test", " invoke onResume method");
    }

}
