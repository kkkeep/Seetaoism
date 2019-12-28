package com.seetaoism.home.set;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;

import com.flyco.roundview.RoundTextView;
import com.mr.k.mvp.UserManager;
import com.mr.k.mvp.utils.DataCacheUtils;
import com.mr.k.mvp.utils.Logger;
import com.mr.k.mvp.utils.SPUtils;
import com.mr.k.mvp.utils.SystemFacade;
import com.seetaoism.AppConstant;
import com.seetaoism.BuildConfig;
import com.seetaoism.JDApplication;
import com.seetaoism.MainActivity;
import com.seetaoism.R;
import com.seetaoism.base.JDMvpBaseActivity;
import com.seetaoism.data.entity.CheckUpdateData;
import com.seetaoism.data.entity.User;
import com.seetaoism.data.okhttp.Interceptor.CommonParamsInterceptor;
import com.seetaoism.home.HomeActivity;
import com.seetaoism.home.push.ExampleUtil;
import com.seetaoism.home.push.LocalBroadcastManager;
import com.seetaoism.libdownlaod.DownLoadManager;
import com.seetaoism.libloadingview.LoadingView;
import com.seetaoism.user.login.LoginActivity;
import com.seetaoism.user.register.PrivacyPolicyFragment;
import com.seetaoism.user.register.UserProtocolFragment;
import com.seetaoism.utils.ShareUtils;
import com.seetaoism.widgets.CheckUpdatePopWindow;
import com.shehuan.niv.NiceImageView;
import com.suke.widget.SwitchButton;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

import static com.mr.k.mvp.UserManager.login;

public class SettingActivity extends JDMvpBaseActivity<SetContract.ISetPresenter> implements View.OnClickListener, SetContract.ISetView {

    private NiceImageView mClose;
    private Toolbar mToolbar;
    private RoundTextView mCleanCach;
    private RoundTextView mCheckUpdata;
    private TextView mCall;
    private RoundTextView mRecommend;
    private RoundTextView mOutlogin;
    private RoundTextView mVersion;
    private RoundTextView xieyi;
    private LinearLayout mActivityLogin;
    private File mCacheUserFile;
    private Switch switchButton;
    private Switch push1_pic;
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    public static boolean isForeground = false;
    private User user;

    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {
        initView();
        registerMessageReceiver();
        String b = SPUtils.getValue("b");
        if (b.equals("open")){
            switchButton.setChecked(true);
        }else {
            switchButton.setChecked(false);

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    private void initView() {
        mClose = findViewById(R.id.close);
        mToolbar = findViewById(R.id.toolbar);
        mCleanCach = findViewById(R.id.clean_cach);
        xieyi = findViewById(R.id.xieyi);
        mCheckUpdata = findViewById(R.id.check_updata);
        mCall = findViewById(R.id.call);
        switchButton = findViewById(R.id.push_Button);
        mRecommend = findViewById(R.id.recommend);
        mOutlogin = findViewById(R.id.outlogin);
        mVersion = findViewById(R.id.version);
        mActivityLogin = findViewById(R.id.activity_login);
        push1_pic = findViewById(R.id.push1_pic);
        xieyi.setOnClickListener(this);
        mOutlogin.setOnClickListener(this);
        mRecommend.setOnClickListener(this);
        mClose.setOnClickListener(this);
        mCall.setOnClickListener(this);
        mCleanCach.setOnClickListener(this);
        mCheckUpdata.setOnClickListener(this);
        push1_pic.setChecked(true);

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                NotificationManagerCompat manager = NotificationManagerCompat.from(JDApplication.mApplicationContext);
                boolean isOpened = manager.areNotificationsEnabled();
                if (isOpened) {
                    // ToastUtils.show("已开启通知");
                } else {
                    //   ToastUtils.show("未开启通知");
                    Intent localIntent = new Intent();
                    localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (Build.VERSION.SDK_INT >= 9) {
                        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        localIntent.setData(Uri.fromParts("package", JDApplication.mApplicationContext.getPackageName(), null));
                    } else if (Build.VERSION.SDK_INT <= 8) {
                        localIntent.setAction(Intent.ACTION_VIEW);
                        localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                        localIntent.putExtra("com.android.settings.ApplicationPkgName", JDApplication.mApplicationContext.getPackageName());
                    }
                    startActivity(localIntent);
                }
                if (b) {
                    SPUtils.saveValueToDefaultSpByCommit("b","open");
                    //选中说明打开了
                    JPushInterface.resumePush(getApplicationContext());
                    BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(SettingActivity.this);
                    builder.statusBarDrawable = R.mipmap.ic_launcher_foreground;
                    JPushInterface.setPushNotificationBuilder(1, builder);
                    user = (User) UserManager.getUser();
                    if (user != null) {
                        //在这里掉方法   一个是登录
                        String registrationID = JPushInterface.getRegistrationID(SettingActivity.this);
                        //紧接着网络请求
                        mPresenter.getPushid(registrationID);
                    } else {
                        String registrationID = JPushInterface.getRegistrationID(SettingActivity.this);
                        //紧接着网络请求
                        mPresenter.getPushid(registrationID);
                    }
                } else {
                    //否则就关闭了
                    SPUtils.saveValueToDefaultSpByCommit("b",null);

                    JPushInterface.stopPush(getApplicationContext());

                }
            }
        });

        mVersion.setText("见道" + BuildConfig.VERSION_NAME);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.outlogin:
                mPresenter.getOutLogin();
                break;
            case R.id.close:
                finish();
                break;
            case R.id.recommend:
                //分享
                ShareUtils.shareApp(this, new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                    }
                });
                break;
            case R.id.call:
                //打电话
                Intent myCallIntent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:010-58760804"));
                startActivity(myCallIntent);
                break;

            case R.id.clean_cach:
                showToast("清理完毕");
                break;

            case R.id.check_updata:{
                showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG);
                mPresenter.checkUpdate(BuildConfig.VERSION_NAME);
                break;
            }
            case R.id.xieyi:
                addFragment(getSupportFragmentManager(), PrivacyPolicyFragment.class, android.R.id.content, null);
                break;

        }

    }

    @Override
    public void onOutLoginSuccess(String user) {
        showToast("操作成功");
        Intent intent = new Intent(this,HomeActivity.class);
        intent.putExtra("from", 2);
        startActivity(intent);
        finish();

    }

    @Override
    public void onOutLoginFail(String msg) {
        showToast(msg);
    }

    @Override
    public void onPushidSuccess(String success) {
    }

    @Override
    public void onPushidFail(String msg) {
        showToast(msg);
    }

    @Override
    public void onCheckUpdateResult(CheckUpdateData data, String msg) {

        closeLoading();

        if(data != null && msg == null){
            if(data.is_upgrade() == 1){
                CheckUpdatePopWindow checkUpdatePopWindow = new CheckUpdatePopWindow(this);
                checkUpdatePopWindow.show(mCheckUpdata, data, new CheckUpdatePopWindow.OnUpdateButtonClickListener() {
                    @Override
                    public void onUpdate(@NotNull CheckUpdateData data) {
                        //showToast("升级");
                        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
                        String nonce = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
                        String url = AppConstant.BASE_URL + "/api/file/appupgrade?from=android&lang=zh" +  "&version_id=" + data.getVersion_id() + "&nonce=" + nonce + "&timestamp=" + timestamp + "&signature=" + CommonParamsInterceptor.getSHA1(timestamp, nonce);
                        DownLoadManager.getInstance(getApplicationContext()).startLoad(getApplicationContext(),url,"jiandao_"+ data.getVersion() + ".apk");
                    }
                });

            }else{
                showToast("当前为最新版本");
                closeLoading();
            }

        }else{
            showToast(msg);
        }
    }

    @Override
    public void onAppUpdateResult(String data, String msg) {

        /*if(data != null && msg == null){
            CheckUpdatePopWindow checkUpdatePopWindow = new CheckUpdatePopWindow(this);
            checkUpdatePopWindow.show(mCheckUpdata);
        }else{
            showToast(msg);
        }*/
    }

    @Override
    public SetContract.ISetPresenter createPresenter() {
        return new SetPresenter();
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    showToast(showMsg.toString());
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }
}
