package com.seetaoism.home.push;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.seetaoism.AppConstant;

import cn.jpush.android.api.JPushInterface;

public class TestActivity extends Activity {

    private String extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("用户自定义打开的Activity");
        Intent intent = getIntent();
        if (null != intent) {
	        Bundle bundle = getIntent().getExtras();
            String title = null;
            String content = null;
            if(bundle!=null){
                title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                content = bundle.getString(JPushInterface.EXTRA_ALERT);
                extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
                Log.d("content", "onCreate: "+content);



                Gson gson = new Gson();
                PushBean pushBean = gson.fromJson(extras, PushBean.class);


//                PushBean pushBean = gson.fromJson(content, PushBean.class);
                //文章id
               /* String id = pushBean.getId();
                String link = pushBean.getLink();
                Intent intent1 = new Intent(TestActivity.this, DetailActivity.class);
                intent1.putExtra(AppConstant.BundleParamsKeys.ARTICLE_ID, id);
                intent1.putExtra(AppConstant.BundleParamsKeys.ARTICLE_LINK_URL,link );
                startActivity(intent1);
                finish();*/
            }
        }
        addContentView(tv, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
    }

}
