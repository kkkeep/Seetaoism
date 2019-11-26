package com.seetaoism.home.mine;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.seetaoism.libloadingview.LoadingView;
import com.seetaoism.user.login.LoginActivity;
import com.seetaoism.widgets.IntegralWidget;
import com.shehuan.niv.NiceImageView;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class MineFragment extends MvpBaseFragment<MineContract.IMinePresnter> implements MineContract.IMineView, View.OnClickListener {
    private RoundTextView mine_login;
    private RoundLinearLayout mine_collect;
    private RoundLinearLayout mine_jifen;
    private RoundLinearLayout mine_message;
    private RoundLinearLayout mine_seeting;
    private NiceImageView mine_pic;
    private TextView jifen;
    private TextView qiandao_bt;
    private TextView text_hint;
    private TextView text_title;
    private TextView yuan;

    private BroadcastReceiver mReceiver;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    protected void initView(View root) {
        mine_collect = root.findViewById(R.id.mine_collect);
        mine_login = root.findViewById(R.id.mine_login);
        mine_message = root.findViewById(R.id.mine_message);
        mine_seeting = root.findViewById(R.id.mine_seeting);
        mine_jifen = root.findViewById(R.id.mine_jifen);
        text_hint = root.findViewById(R.id.text_hint);
        text_title = root.findViewById(R.id.text_title);
        mine_pic = root.findViewById(R.id.mine_pic);
        qiandao_bt = root.findViewById(R.id.qiandao_bt);
        yuan = root.findViewById(R.id.yuan);
        mine_collect.setOnClickListener(this);
        mine_login.setOnClickListener(this);
        mine_message.setOnClickListener(this);
        mine_seeting.setOnClickListener(this);
        mine_pic.setOnClickListener(this);
        mine_jifen.setOnClickListener(this);
        qiandao_bt.setOnClickListener(this);

        mReceiver = UserManager.registerUserBroadcastReceiver((iUser,userState) -> {
            setData();
            return Unit.INSTANCE;
        });
    }

    @Override
    protected void initData() {
        User user = (User) UserManager.getUser();

        if (user != null && !user.isRefresh()) {
            showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG);
            mPresenter.getMineUser();
        }else{
            setData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //登录
            case R.id.mine_login:
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
            //设置
            case R.id.mine_seeting:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            //个人资料
            case R.id.mine_pic:
                //如果登录直接跳转，如果没有登录跳转登录
                User user = (User) UserManager.getUser();
                if (user != null) {
                    startActivityForResult(new Intent(getContext(), PerfectActivity.class), 100);
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            //我的收藏
            case R.id.mine_collect:
                User mine_collect = (User) UserManager.getUser();
                if (mine_collect != null) {
                    startActivity(new Intent(getContext(), CollectActivity.class));
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            //我的积分
            case R.id.mine_jifen:
                User jifen = (User) UserManager.getUser();
                if (jifen != null) {
                    startActivity(new Intent(getContext(), IntegralActivity.class));
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));

                }
                break;

            case R.id.qiandao_bt:
                //签到
                User qiandao_bt1 = (User) UserManager.getUser();
                if(qiandao_bt1 == null){
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }else{
                    if(qiandao_bt1.getUserInfo().getCheck_in_status() != 1){
                        mPresenter.getMine();
                    }else{
                        showToast(R.string.text_qiandao);
                        qiandao_bt.setBackgroundResource(R.drawable.minewhite);
                    }
                }

                break;
            case R.id.mine_message:
                User mine_message = (User) UserManager.getUser();
                if (mine_message != null) {
                    startActivity(new Intent(getContext(), MessageActivity.class));
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
        }
    }

    @Override
    public void onMineSucceed(String msg) {
        User user = (User) UserManager.getUser();
        user.getUserInfo().setCheck_in_status(1);
        IntegralWidget.show(getActivity(), 20);
        qiandao_bt.setText(getString(R.string.text_qiandao));
        qiandao_bt.setBackgroundResource(R.drawable.minewhite);

    }

    @Override
    public void onMineFail(String msg) {
        qiandao_bt.setText(getString(R.string.text_un_qiandao));
        qiandao_bt.setBackgroundResource(R.drawable.mine_black);
        showToast(msg);
    }


    @Override
    public void onMineUserSucceed(User user) {
        closeLoading();
        setData();
    }


    @Override
    public void onMineUserFail(String msg) {
        closeLoading();
        setData();
    }


    private void setData() {
        User user = (User) UserManager.getUser();
        if (user == null || !user.isRefresh()) {
            text_hint.setText(getString(R.string.text_unlogin));
            qiandao_bt.setText(getString(R.string.text_un_qiandao));
            text_title.setVisibility(View.VISIBLE);
            yuan.setVisibility(View.GONE);
            mine_pic.setImageResource(R.drawable.mine_pic);
            mine_login.setVisibility(View.VISIBLE);
        }else{
            text_title.setVisibility(View.GONE);
            text_hint.setText(user.getUserInfo().getNickname());
            if (user.getUserInfo().getHead_url() != null) {
                Glide.with(this).load(user.getUserInfo().getHead_url()).apply(RequestOptions.circleCropTransform().placeholder(R.drawable.mine_pic)).into(mine_pic);
            }

            if (user.getUserInfo().getCheck_in_status() == 1) {
                //签到按钮调接口
                qiandao_bt.setText(getString(R.string.text_qiandao));
                qiandao_bt.setBackgroundResource(R.drawable.minewhite);
            }else{
                qiandao_bt.setText(getResources().getString(R.string.text_un_qiandao));
                qiandao_bt.setBackgroundResource(R.drawable.mine_black);
            }

            if (user.getToken() != null) {
                mine_login.setVisibility(View.GONE);
            } else {
                mine_login.setVisibility(View.VISIBLE);
            }

            if (user.getUserInfo() != null&&user.getUserInfo().getNotice_count()>0) {
                yuan.setVisibility(View.VISIBLE);
                yuan.setText(user.getUserInfo().getNotice_count() + "");
            } else {
                yuan.setVisibility(View.GONE);
            }

        }


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
        if (requestCode == 100 && resultCode == 400) {
            String photo = data.getStringExtra("photow");
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(photo)) {
                Glide.with(getContext()).load(photo).apply(RequestOptions.circleCropTransform().placeholder(R.drawable.mine_pic)).into(mine_pic);
            }
            if (!TextUtils.isEmpty(name)) {
                text_hint.setText(name);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mReceiver != null){
            UserManager.unRegisterUserBroadcastReceiver(mReceiver);
            mReceiver = null;
        }

    }
}
