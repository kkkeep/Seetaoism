package com.seetaoism.home.perfect;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.flyco.roundview.RoundRelativeLayout;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.mr.k.mvp.UserManager;
import com.mr.k.mvp.utils.SPUtils;
import com.seetaoism.R;
import com.seetaoism.base.JDMvpBaseActivity;
import com.seetaoism.data.entity.SocialBindData;
import com.seetaoism.data.entity.User;
import com.seetaoism.data.entity.VideoData;
import com.seetaoism.home.changepassword.VerificationPswActivity;
import com.seetaoism.home.email.EmailActivity;
import com.seetaoism.home.phone.PhoneActivity;
import com.seetaoism.libloadingview.LoadingView;
import com.seetaoism.utils.MobileNumUtils;
import com.shehuan.niv.NiceImageView;
import com.umeng.commonsdk.debug.I;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.mr.k.mvp.MvpManager.getContext;

public class PerfectActivity extends JDMvpBaseActivity<PerfectContract.IPerfectPresenter> implements View.OnClickListener, PerfectContract.IPerfectView, UMAuthListener {

    private static final int BINT_TYPE_SINA = 0x100;
    private static final int BINT_TYPE_WECHAT = 0x101;
    private static final int BINT_TYPE_QQ = 0x102;
    private NiceImageView mClose;
    private Toolbar mToolbar;
    private NiceImageView mUsericon;
    private TextView mNikename;
    private TextView mOnbindmobile;
    private TextView mOnbindemail;
    private TextView per_phone;
    private NiceImageView mWeibo;
    private NiceImageView mWeixin;
    private NiceImageView mQq;
    private RoundRelativeLayout mModifypassword;
    private LinearLayout mActivityLogin;
    private PopupWindow popupWindow;
    public static final int TAKE_PHOTO = 1;
    public static final int SELECT_PHOTO = 2;
    private TextView updata_psw;
    private String photoPath;
    private String name;
    private int mClickBindType = -1;
    private User mUser;
    private AlertDialog builder;


    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {
        mClose = findViewById(R.id.close);
        mToolbar = findViewById(R.id.toolbar);
        mUsericon = findViewById(R.id.usericon);
        mNikename = findViewById(R.id.nikename);
        mOnbindmobile = findViewById(R.id.onbindmobile);
        mOnbindemail = findViewById(R.id.onbindemail);
        mWeibo = findViewById(R.id.weibo);
        mWeixin = findViewById(R.id.weixin);
        mQq = findViewById(R.id.qq);
        mModifypassword = findViewById(R.id.modifypassword);
        mActivityLogin = findViewById(R.id.activity_login);
        updata_psw = findViewById(R.id.updata_psw);
        per_phone = findViewById(R.id.per_phone);
        per_phone = findViewById(R.id.per_phone);
        mUsericon.setOnClickListener(this);
        mNikename.setOnClickListener(this);
        updata_psw.setOnClickListener(this);
        mOnbindmobile.setOnClickListener(this);
        mOnbindemail.setOnClickListener(this);
        mClose.setOnClickListener(this);
        mWeibo.setOnClickListener(this);
        mQq.setOnClickListener(this);
        mWeixin.setOnClickListener(this);

        photoPath = "";
        name = "";

        mUser = (User) UserManager.getUser();
        mPresenter.getUser();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_perfect;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.usericon:
                initPopupwindow(mUsericon);
                break;
            case R.id.nikename:
                updatename(mNikename);
                break;
            case R.id.updata_psw:
                startActivity(new Intent(PerfectActivity.this, VerificationPswActivity.class));
                break;
            case R.id.onbindmobile:
                Intent intent = new Intent(PerfectActivity.this, PhoneActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.onbindemail:
                Intent intent1 = new Intent(PerfectActivity.this, EmailActivity.class);
                startActivityForResult(intent1, 2);
                break;
            case R.id.close:
                Intent clos = new Intent();
                clos.putExtra("photow", photoPath);
                clos.putExtra("name", name);
                setResult(400, clos);
                finish();
                break;
            case R.id.weibo: {
                //showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG);
                if (mUser.getUserInfo().getSina_bind() == 1) {
                    builder = new AlertDialog.Builder(PerfectActivity.this)
                            .create();
                    builder.show();
                    if (builder.getWindow() == null) {
                        return;
                    }
                    builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
                    TextView msg = (TextView) builder.findViewById(R.id.tv_msg);
                    Button cancle = (Button) builder.findViewById(R.id.btn_cancle);
                    Button sure = (Button) builder.findViewById(R.id.btn_sure);

                    sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mPresenter.getSocialunbind("sina", mUser.getUserInfo().getSina_openid(), "");
                            showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG);
                            builder.dismiss();
                        }
                    });
                    cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            builder.dismiss();
                        }
                    });

                } else {
                    mClickBindType = BINT_TYPE_SINA;
                    UMShareAPI umShareAPI = UMShareAPI.get(this);
                    umShareAPI.getPlatformInfo(this, SHARE_MEDIA.SINA, this);
                }

                break;
            }

            case R.id.weixin: {
                //showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG);
                if (mUser.getUserInfo().getWechat_bind() == 1) {
                    builder = new AlertDialog.Builder(PerfectActivity.this)
                            .create();
                    builder.show();
                    if (builder.getWindow() == null) {
                        return;
                    }
                    builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
                    TextView msg = (TextView) builder.findViewById(R.id.tv_msg);
                    Button cancle = (Button) builder.findViewById(R.id.btn_cancle);
                    Button sure = (Button) builder.findViewById(R.id.btn_sure);

                    sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mPresenter.getSocialunbind("wechat", mUser.getUserInfo().getWechat_openid(), mUser.getUserInfo().getWechat_unionid());
                            showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG);
                            builder.dismiss();
                        }
                    });
                    cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            builder.dismiss();
                        }
                    });

                } else {
                    mClickBindType = BINT_TYPE_WECHAT;
                    UMShareAPI umShareAPI = UMShareAPI.get(this);
                    // wx 的 有效期是1个月。过了1个月就必须重新授权登录。没有过期则不用跳转到授权页面，直接返回用户相关信息。
                    umShareAPI.getPlatformInfo(this, SHARE_MEDIA.WEIXIN, this);
                }
                break;
            }
            case R.id.qq: {
                //  showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG);
                if (mUser.getUserInfo().getQq_bind() == 1) {
                    builder = new AlertDialog.Builder(PerfectActivity.this)
                            .create();
                    builder.show();
                    if (builder.getWindow() == null) {
                        return;
                    }
                    builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
                    TextView msg = (TextView) builder.findViewById(R.id.tv_msg);
                    Button cancle = (Button) builder.findViewById(R.id.btn_cancle);
                    Button sure = (Button) builder.findViewById(R.id.btn_sure);

                    sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mPresenter.getSocialunbind("qq", mUser.getUserInfo().getQq_openid(), "");
                            showLoading(LoadingView.LOADING_MODE_TRANSPARENT_BG);
                            builder.dismiss();
                        }
                    });
                    cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            builder.dismiss();
                        }
                    });

                } else {
                    mClickBindType = BINT_TYPE_QQ;
                    UMShareAPI umShareAPI = UMShareAPI.get(this);
                    umShareAPI.getPlatformInfo(this, SHARE_MEDIA.QQ, this);
                }
                break;
            }


        }
    }

    private void updatename(TextView mNikename) {
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }

        LinearLayout view = (LinearLayout) getLayoutInflater().inflate(R.layout.update_name_layout, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(mNikename, Gravity.LEFT | Gravity.RIGHT, 0, 0);
        setupdatename(view);

    }

    private void setupdatename(LinearLayout view) {
        final EditText up_name = view.findViewById(R.id.up_name);
        final TextView up_flase = view.findViewById(R.id.up_flase);
        TextView up_true = view.findViewById(R.id.up_true);


        up_flase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        up_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = up_name.getText().toString();
                mPresenter.getUpdateNameP(string);
                popupWindow.dismiss();
            }
        });
    }

    //弹出popwindow
    private void initPopupwindow(View mUsericon) {
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.window_popup, null);
        popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        int[] location = new int[2];
        mUsericon.getLocationOnScreen(location);
        popupWindow.showAtLocation(mUsericon, Gravity.LEFT | Gravity.BOTTOM, 0, -location[1]);
        //添加按键事件监听
        setButtonListeners(layout);
    }

    //popwindpw监听事件
    private void setButtonListeners(LinearLayout layout) {
        TextView mCamera = (TextView) layout.findViewById(R.id.camera);
        TextView mGallery = (TextView) layout.findViewById(R.id.photo);
        Button mCancel = (Button) layout.findViewById(R.id.canle);
        //相机点击事件
        mCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyPhotos.createCamera(PerfectActivity.this)
                        .setFileProviderAuthority("com.huantansheng.easyphotos.demo.fileprovider")
                        .start(101);
                popupWindow.dismiss();
            }
        });
        //相册点击事件
        mGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyPhotos.createAlbum(PerfectActivity.this, true, GlideEngine.getInstance())
                        .setCount(1)
                        .start(101);
                popupWindow.dismiss();
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
    }

    /**
     * 打开相册的方法
     */
    private void openAlbum() {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PHOTO);
    }

    //打开相册的权限
    public void select_photo() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }
    }


    private void setHeadImg(String path) {
        File file = new File(path);
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part formData = MultipartBody.Part.createFormData("file", file.getName(), body);
        mPresenter.getPerfect(formData);
    }


    @Override
    public PerfectContract.IPerfectPresenter createPresenter() {
        return new PerfectPresenter();
    }

    @Override
    public void onPerfectSuccess(User s, String msg) {
        //图片5兆限制
        if (s != null) {
            if (!TextUtils.isEmpty(s.getUserInfo().getHead_url())) {
                Glide.with(this).load(s.getUserInfo().getHead_url()).into(mUsericon);
                photoPath = s.getUserInfo().getHead_url();
            }
        } else {
            showToast(msg);
        }
    }

    @Override
    public void getUpdateNameSuccess(User user, String msg) {
        mNikename.setText(user.getUserInfo().getNickname());
        name = user.getUserInfo().getNickname();
    }


    @Override
    public void onUserSuccess(User user) {

        if (user.getUserInfo().getHead_url() != null) {
            Glide.with(this).load(user.getUserInfo().getHead_url()).apply(RequestOptions.circleCropTransform().placeholder(R.drawable.ic_launcher_background)).into(mUsericon);
        }
        if (user.getUserInfo().getNickname() != null) {
            mNikename.setText(user.getUserInfo().getNickname());
        }
        if (!TextUtils.isEmpty(user.getUserInfo().getMobile())) {
            mOnbindmobile.setText(MobileNumUtils.changeMobileNum(user.getUserInfo().getMobile()));
        } else {
            mOnbindmobile.setText("未绑定");
        }
        if (!TextUtils.isEmpty(user.getUserInfo().getEmail())) {
            mOnbindemail.setText(MobileNumUtils.changeMobileNum(user.getUserInfo().getEmail()));
        } else {
            mOnbindemail.setText("未绑定");
        }
        if (user.getUserInfo().getSina_bind() == 1) {
            mWeibo.setImageResource(R.drawable.weibo_per);
        } else {
            mWeibo.setImageResource(R.drawable.set_wb);
        }

        if (user.getUserInfo().getWechat_bind() == 1) {
            mWeixin.setImageResource(R.drawable.weatch_per);
        } else {
            mWeixin.setImageResource(R.drawable.set_wx);
        }
        if (user.getUserInfo().getQq_bind() == 1) {
            mQq.setImageResource(R.drawable.qq);
        } else {
            mQq.setImageResource(R.drawable.set_qq);
        }

    }

    @Override
    public void onSocialbindSuccess(SocialBindData data) {
        if (data.getType().equals("sina")) {
            mWeibo.setImageResource(R.drawable.weibo_per);
            mUser.getUserInfo().setSina_bind(1);
        } else if (data.getType().equals("qq")) {
            mQq.setImageResource(R.drawable.qq);
            mUser.getUserInfo().setQq_bind(1);
        } else if (data.getType().equals("wechat")) {
            mWeixin.setImageResource(R.drawable.weatch_per);
            mUser.getUserInfo().setWechat_bind(1);
        }
        showToast(data.getType());
    }

    @Override
    public void onSocialbindFail(String msg) {
        mClickBindType = -1;
        showToast(msg);
    }

    @Override
    public void onSocialunbindSuccess(SocialBindData data) {
        if (data.getType().equals("sina")) {
            mWeibo.setImageResource(R.drawable.set_wb);
            mUser.getUserInfo().setSina_bind(0);
        } else if (data.getType().equals("qq")) {
            mQq.setImageResource(R.drawable.set_qq);
            mUser.getUserInfo().setQq_bind(0);
        } else if (data.getType().equals("wechat")) {
            mWeixin.setImageResource(R.drawable.set_wx);
            mUser.getUserInfo().setWechat_bind(0);
        }
        closeLoading();
        showToast("解除绑定成功");
    }

    @Override
    public void onSocialunbindFail(String msg) {
        closeLoading();
        showToast(msg);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("photow", photoPath);
        intent.putExtra("name", name);
        setResult(400, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (data != null) {
                ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
                Photo photo = resultPhotos.get(0);
                String path = photo.path;
                long size = photo.size;
                Log.d("size", "onActivityResult: " + size);
                setHeadImg(path);
            }
        }
        if (requestCode == 1 && resultCode == 200) {
            String phone = data.getStringExtra("phone");
            //Log.d("phone", "onActivityResult: "+phone);
            mOnbindmobile.setText(phone);
        }
        if (requestCode == 2 && resultCode == 300) {
            String email = data.getStringExtra("email");
            //Log.d("phone", "onActivityResult: "+phone);
            mOnbindemail.setText(email);
        }
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

        String type = "";
        String openId = "";
        String nickname = "";
        String head_url = " ";
        String unionid = "";


        String openIdKey = "openid";


        if (share_media == SHARE_MEDIA.SINA) {
            type = "sina";
            openIdKey = "uid";

        } else if (share_media == SHARE_MEDIA.WEIXIN) {
            type = "wechat";
        } else if (share_media == SHARE_MEDIA.QQ) {
            type = "qq";
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            //uid
            if (entry.getKey().equals(openIdKey)) {
                openId = entry.getValue();

            }
            //昵称
            if (entry.getKey().equals("name")) {
                nickname = entry.getValue();
            }
            //头像
            if (entry.getKey().equals("profile_image_url")) {
                head_url = entry.getValue();
                ;
            }

            if (entry.getKey().equals("unionid")) {
                unionid = entry.getValue();
            }

        }
        if (share_media == SHARE_MEDIA.SINA) {
            mUser.getUserInfo().setSina_openid(openId);
        } else if (share_media == SHARE_MEDIA.WEIXIN) {
            mUser.getUserInfo().setWechat_unionid(openId);
            mUser.getUserInfo().setWechat_unionid(unionid);
        } else if (share_media == SHARE_MEDIA.QQ) {
            mUser.getUserInfo().setQq_openid(openId);
        }

        mPresenter.getSocialbind(type, openId, nickname, head_url, unionid);
    }

    @Override
    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media, int i) {

    }
}
