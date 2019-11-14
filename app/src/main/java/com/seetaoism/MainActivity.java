package com.seetaoism;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.mr.k.mvp.base.BaseActivity;
import com.seetaoism.widgets.bottomtablaout.BottomTabLayout;

public class MainActivity extends BaseActivity {


    private static final String TAG = "MainActivity";

    private BottomTabLayout mBottomTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        mBottomTabLayout = findViewById(R.id.home_bottom_layout);



        mBottomTabLayout.setTabSelectedChangeListener(new BottomTabLayout.OnTabSelectedChangeListener() {
            @Override
            public void onSelect(int posotion) {
                showToast("position " + posotion);
            }

            @Override
            public void onSelectedAgain(int position) {
                showToast("再次点击 " + position);
            }
        });

    }

}
