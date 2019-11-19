package com.seetaoism.home.mine;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.flyco.roundview.RoundLinearLayout;
import com.flyco.roundview.RoundTextView;
import com.mr.k.mvp.IUser;
import com.mr.k.mvp.UserManager;
import com.mr.k.mvp.base.IBasePresenter;
import com.mr.k.mvp.base.MvpBaseFragment;
import com.seetaoism.R;
import com.seetaoism.data.entity.User;
import com.seetaoism.home.collect.CollectActivity;
import com.seetaoism.home.integral.IntegralActivity;
import com.seetaoism.home.message.MessageActivity;
import com.seetaoism.home.perfect.PerfectActivity;
import com.seetaoism.home.set.SettingActivity;
import com.seetaoism.user.login.LoginActivity;
import com.shehuan.niv.NiceImageView;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class MineFragment extends MvpBaseFragment<MineContract.IMinePresnter> implements MineContract.IMineView, View.OnClickListener {
    private RoundTextView mine_login;
    private RoundLinearLayout mine_collect;
    private RoundLinearLayout mine_message;
    private RoundLinearLayout mine_seeting;
    private NiceImageView mine_pic;
    private TextView jifen;
    private TextView qiandao_bt;
    private TextView text_hint;
    private TextView text_title;

    private BroadcastReceiver mReceiver;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

       mReceiver =  UserManager.registerUserBroadcastReceiver(iUser -> Unit.INSTANCE);
    }

    @Override
    protected void initView(View root) {
        mine_collect = root.findViewById(R.id.mine_collect);
        mine_login = root.findViewById(R.id.mine_login);
        mine_message = root.findViewById(R.id.mine_message);
        mine_seeting = root.findViewById(R.id.mine_seeting);
        text_hint = root.findViewById(R.id.text_hint);
        text_title = root.findViewById(R.id.text_title);
        mine_pic = root.findViewById(R.id.mine_pic);
        jifen = root.findViewById(R.id.jifen);
        qiandao_bt = root.findViewById(R.id.qiandao_bt);
        mine_collect.setOnClickListener(this);
        mine_login.setOnClickListener(this);
        mine_message.setOnClickListener(this);
        mine_seeting.setOnClickListener(this);
        mine_pic.setOnClickListener(this);
        jifen.setOnClickListener(this);
        qiandao_bt.setOnClickListener(this);

        mPresenter.getMineUser();

    }

    @Override
    public void onResume() {
        super.onResume();
        User user = (User) UserManager.getUser();
        if (user == null){
            text_hint.setText("未登录");
            qiandao_bt.setText("未签到");
            text_title.setVisibility(View.VISIBLE);
            Glide.with(this).load("").apply(RequestOptions.circleCropTransform().placeholder(R.drawable.mine_pic)).into(mine_pic);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //登录
            case R.id.mine_login:
                startActivity(new Intent(getContext(), LoginActivity.class));
               // getActivity().finish();
                break;
            //设置
            case R.id.mine_seeting:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            //个人资料
            case R.id.mine_pic:
                //如果登录直接跳转，如果没有登录跳转登录
                User user = (User) UserManager.getUser();
                if (user != null && user.getToken() != null && !TextUtils.isEmpty(user.getToken().getValue())) {
                    startActivityForResult(new Intent(getContext(), PerfectActivity.class),100);
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    //getActivity().finish();
                }
                break;
            //我的收藏
            case R.id.mine_collect:
                User mine_collect = (User) UserManager.getUser();
                if (mine_collect != null && mine_collect.getToken() != null && !TextUtils.isEmpty(mine_collect.getToken().getValue())) {
                    startActivity(new Intent(getContext(), CollectActivity.class));
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                   // getActivity().finish();
                }
                break;
            //我的积分
            case R.id.jifen:
                User jifen = (User) UserManager.getUser();
                if (jifen != null && jifen.getToken() != null && !TextUtils.isEmpty(jifen.getToken().getValue())) {
                    startActivity(new Intent(getContext(), IntegralActivity.class));
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    //getActivity().finish();

                }
                break;

            case R.id.qiandao_bt:
                //签到
                User qiandao_bt = (User) UserManager.getUser();
                if (qiandao_bt != null) {
                    mPresenter.getMine();
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                   // getActivity().finish();
                }
                break;
            case R.id.mine_message:
                User mine_message = (User) UserManager.getUser();
                if (mine_message != null) {
                    startActivity(new Intent(getContext(), MessageActivity.class));
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                  //  getActivity().finish();
                }
                break;
        }
    }

    @Override
    public void onMineSucceed(String msg) {
        qiandao_bt.setText("已签到");
    }

    @Override
    public void onMineFail(String msg) {
        showToast(msg);
    }



    @Override
    public void onMineUserSucceed(User user) {
        if (user != null) {
            if (!TextUtils.isEmpty(user.getUserInfo().getNickname())) {
                text_title.setVisibility(View.GONE);
                text_hint.setText(user.getUserInfo().getNickname());
            }
            if (user.getUserInfo().getHead_url() != null) {
                Glide.with(this).load(user.getUserInfo().getHead_url()).apply(RequestOptions.circleCropTransform().placeholder(R.drawable.mine_pic)).into(mine_pic);
            }

            //签到按钮
            User user1 = (User) UserManager.getUser();
            if (user1.getToken() != null&&user1.getUserInfo().getCheck_in_status()==1) {
                //签到按钮调接口
                qiandao_bt.setText("已签到");
            }

            //登录按钮

            if (user1.getToken() != null) {
                mine_login.setVisibility(View.GONE);
            } else {
                mine_login.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onMineUserFail(String msg) {

    }

    @Override
    public MineContract.IMinePresnter createPresenter() {
        return new MinePresenter();
    }

    @Override
    public boolean isNeedAddToBackStack() {
        return false;
    }

    @Override
    public boolean isNeedAnimation() {
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 400){
            String photo = data.getStringExtra("photow");
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(photo)){
                Glide.with(getContext()).load(photo).apply(RequestOptions.circleCropTransform().placeholder(R.drawable.mine_pic)).into(mine_pic);
            }
            if (!TextUtils.isEmpty(name)){
                text_hint.setText(name);
            }
        }
    }
}
