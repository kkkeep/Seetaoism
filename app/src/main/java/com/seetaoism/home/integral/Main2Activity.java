package com.seetaoism.home.integral;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.mr.k.mvp.base.IBasePresenter;
import com.seetaoism.R;
import com.seetaoism.base.JDMvpBaseActivity;
import com.shehuan.niv.NiceImageView;

public class Main2Activity extends JDMvpBaseActivity {


    private NiceImageView mClose;
    private Toolbar mToolbar;

    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {

        mClose = findViewById(R.id.close);
        mToolbar = findViewById(R.id.toolbar);

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main2;
    }

    @Override
    public IBasePresenter createPresenter() {
        return null;
    }
}
