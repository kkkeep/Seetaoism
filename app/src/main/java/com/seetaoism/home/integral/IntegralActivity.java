package com.seetaoism.home.integral;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.seetaoism.R;
import com.seetaoism.base.JDMvpBaseActivity;
import com.seetaoism.data.entity.IntergrelData;

public class IntegralActivity extends JDMvpBaseActivity<IntegrelContract.IntegrelPresnter> implements IntegrelContract.IntegrelView, View.OnClickListener {


    private ImageView mBack;
    private TextView mJifen;
    private TextView mAboutJifen;
    private TextView mJifenTitle;
    private TextView mRenwu;
    private TextView mRegister;
    private ImageView mMoneyRegister;
    private TextView mQiandao;
    private ImageView mMoneyQiandao;
    private TextView mRead;
    private ImageView mMoneyRead;
    private TextView mShare;
    private ImageView mMoneyShare;
    private TextView qiandao_tv;
    private TextView ten1_tv;
    private TextView ten_tv;


    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {

        mBack = findViewById(R.id.back);
        mJifen = findViewById(R.id.jifen);
        mAboutJifen = findViewById(R.id.about_jifen);
        mJifenTitle = findViewById(R.id.jifen_title);
        mRenwu = findViewById(R.id.renwu);
        mRegister = findViewById(R.id.register);
        mMoneyRegister = findViewById(R.id.money_register);
        mQiandao = findViewById(R.id.qiandao);
        mMoneyQiandao = findViewById(R.id.money_qiandao);
        mRead = findViewById(R.id.read);
        mMoneyRead = findViewById(R.id.money_read);
        mShare = findViewById(R.id.share);
        mMoneyShare = findViewById(R.id.money_share);
        qiandao_tv = findViewById(R.id.qiandao_tv);
        ten1_tv = findViewById(R.id.ten1_tv);
        ten_tv = findViewById(R.id.ten_tv);

        mPresenter.getIntegrel();

        mAboutJifen.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_integral;
    }

    @Override
    public void onIntegrelSuccess(IntergrelData data) {
        //文本框只可以设置string类型的
        mJifenTitle.setText(data.getMy_integral() + "");
        if (data.getCheck_in_status() == 1) {
            qiandao_tv.setText("已签到");
        } else {
            qiandao_tv.setText("未签到");

        }
        ten_tv.setText(data.getRead_article_count()+"");
        ten1_tv.setText(data.getShare_article_count()+"");


    }

    @Override
    public void onIntegrelFail(String msg) {

    }

    @Override
    public IntegrelContract.IntegrelPresnter createPresenter() {
        return new IntegrelPresenter();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.about_jifen:
                startActivity(new Intent(IntegralActivity.this, Main2Activity.class));
                break;
        }
    }
}
