package com.seetaoism;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.mr.k.mvp.statusbar.StatusBarUtils;

public class AdDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mFan;
    private ProgressBar mProgressBar1;
    private WebView mWebview1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_detail);
        StatusBarUtils.setStatusBarLightMode(this, Color.WHITE);

        mFan = findViewById(R.id.fan);
        mFan.setOnClickListener(this);
        init();

        String url  = getIntent().getStringExtra("detail");

        mWebview1.loadUrl(url);


    }

    private void init() {
        // TODO 自动生成的方法存根
        mWebview1 = (WebView) findViewById(R.id.webview1);
        mProgressBar1 = (ProgressBar) findViewById(R.id.progressBar1);

        mWebview1.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO 自动生成的方法存根
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings seting = mWebview1.getSettings();
        seting.setJavaScriptEnabled(true);//设置webview支持javascript脚本
        mWebview1.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根

                if (newProgress == 100) {
                    mProgressBar1.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    mProgressBar1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    mProgressBar1.setProgress(newProgress);//设置进度值
                }

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fan:
                finish();
                break;
        }
    }
}
