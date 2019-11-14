# 集成分享的步骤

1. 去友盟官网注册账号

> 注意：除了友盟里面需要创建应用以外，你还必须到你需要分享的第三方平台去注册和创建
>   应用，然后把创建应用时得到的 id 或者 key 在 友盟初始化时使用。

2. 下载SDK(选中你需要的)
3. 把jar 和 res 下面的资源拷到我们工程相应的目录里面。
   - umeng-common-2.0.1.jar（commocn 目录下的）
   - umeng-share-core-6.9.4.jar（分享相关）
   - umeng-sharetool-6.9.4.jar（分享相关）
   - umeng-shareboard-widget-6.9.4.jar（如果不拷贝混淆就会出问题）
   - umeng-share-QQ-simplify-6.9.4 (分享qq 要用到的)
   - umeng-share-wechat-simplify-6.9.4.jar（分享微信要用的）
   - 把下载下来所有相关的 res 里面的东西复制到我们工程相应目录（drwable，layout，string）


   > 注意：
   > 1. 如果我们在libs 目录下再建了子目录，需要在我们得 build.gradle配置文件里面修改为如下：
   >  `api fileTree(dir: 'libs', include: ['*.jar',"**/*.jar"])`
   > 2. 如果需要被依赖该library的module混淆，需要修改为如下：
   > `consumerProguardFile 'proguard-rules.pro'`



 4. 配置我们得AndroidManifest.xml 文件

 ```xml
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
     <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     <uses-permission android:name="android.permission.INTERNET" />

     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>


 <!-- 友盟分享 -->

        <!--微信-->
        <activity
            android:name="com.jy.umeng.wxapi.WXEntryActivity"
            android:configChanges="keyboardHidden|orientation|screenSize"
            android:exported="true"
            android:theme="@android:style/Theme.Translucent.NoTitleBar" />
        <!--微信-->

        <!--QQ-->
        <activity
            android:name="com.tencent.tauth.AuthActivity"
            android:launchMode="singleTask"
            android:noHistory="true" >
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />

                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />

                <!--这儿需要替换成你自己在qq 平台申请的id,注意前面的 tencent 不要删掉，只替换后面的就行-->
                <data android:scheme="tencent100424468" />
            </intent-filter>
        </activity>
        <activity
            android:name="com.tencent.connect.common.AssistActivity"
            android:theme="@android:style/Theme.Translucent.NoTitleBar"
            android:configChanges="orientation|keyboardHidden|screenSize"/>
        <!--QQ-->

        <!-- 如果还有其他的分享渠道，需要按照官方文档集成 -->

        <!-- 友盟分享 -->
 ```

 5. 配置混淆文件：
 ```properties
 # 注意如果不加 ument-shareboard-widget-**.jar 混淆报错

 -dontshrink
 -dontoptimize
 -dontwarn com.google.android.maps.**
 -dontwarn android.webkit.WebView
 -dontwarn com.umeng.**
 -dontwarn com.tencent.weibo.sdk.**
 -dontwarn com.facebook.**
 -keep public class javax.**
 -keep public class android.webkit.**
 -dontwarn android.support.v4.**
 -keep enum com.facebook.**
 -keepattributes Exceptions,InnerClasses,Signature
 -keepattributes *Annotation*
 -keepattributes SourceFile,LineNumberTable

 -keep public interface com.facebook.**
 -keep public interface com.tencent.**
 -keep public interface com.umeng.socialize.**
 -keep public interface com.umeng.socialize.sensor.**
 -keep public interface com.umeng.scrshot.**

 -keep public class com.umeng.socialize.* {*;}


 -keep class com.facebook.**
 -keep class com.facebook.** { *; }
 -keep class com.umeng.scrshot.**
 -keep public class com.tencent.** {*;}
 -keep class com.umeng.socialize.sensor.**
 -keep class com.umeng.socialize.handler.**
 -keep class com.umeng.socialize.handler.*
 -keep class com.umeng.weixin.handler.**
 -keep class com.umeng.weixin.handler.*
 -keep class com.umeng.qq.handler.**
 -keep class com.umeng.qq.handler.*
 -keep class UMMoreHandler{*;}
 -keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
 -keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
 -keep class im.yixin.sdk.api.YXMessage {*;}
 -keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
 -keep class com.tencent.mm.sdk.** {
    *;
 }
 -keep class com.tencent.mm.opensdk.** {
    *;
 }
 -keep class com.tencent.wxop.** {
    *;
 }
 -keep class com.tencent.mm.sdk.** {
    *;
 }

 -keep class com.twitter.** { *; }

 -keep class com.tencent.** {*;}
 -dontwarn com.tencent.**
 -keep class com.kakao.** {*;}
 -dontwarn com.kakao.**
 -keep public class com.umeng.com.umeng.soexample.R$*{
     public static final int *;
 }
 -keep public class com.linkedin.android.mobilesdk.R$*{
     public static final int *;
 }
 -keepclassmembers enum * {
     public static **[] values();
     public static ** valueOf(java.lang.String);
 }

 -keep class com.tencent.open.TDialog$*
 -keep class com.tencent.open.TDialog$* {*;}
 -keep class com.tencent.open.PKDialog
 -keep class com.tencent.open.PKDialog {*;}
 -keep class com.tencent.open.PKDialog$*
 -keep class com.tencent.open.PKDialog$* {*;}
 -keep class com.umeng.socialize.impl.ImageImpl {*;}
 -keep class com.sina.** {*;}
 -dontwarn com.sina.**
 -keep class  com.alipay.share.sdk.** {
    *;
 }

 -keepnames class * implements android.os.Parcelable {
     public static final ** CREATOR;
 }

 -keep class com.linkedin.** { *; }
 -keep class com.android.dingtalk.share.ddsharemodule.** { *; }
 -keepattributes Signature

 ```

6. 如果有微信相关：
 需要在我们得包下面创建一个`wxapi` 的包，并创建一个名为 `WXEntryActivity.java` 的 `activity` 继承至 `WXCallbackActivity`

7. 如果有qq 或者微博相关的，需要在调用qq 或者 微博的 `Activity` 中 添加以下方法：

    如果不添加，就没办法收到回调。
```java
  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
```

8. 在使用之前需要需要在我们得 `Application` 中初始化友盟相关
```java
    private void initUmeng(){

        UMConfigure.setLogEnabled(BuildConfig.DEBUG);
         UMConfigure.init(context,"5da416070cafb26975000f3f","umeng",UMConfigure.DEVICE_TYPE_PHONE,"");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        // 下面所有ID 都得替换成你自己公司在相应创建应用时获得的id

        //第一个参数表示 AppID ，第二：AppSecret
        PlatformConfig.setWeixin("wxc18a6ee8aede4929", "1f958abec3dd62591b9e0bcc6d047730"); //
        //第一个参数表示 APP ID ，第二：APP KEY
        PlatformConfig.setQQZone("1109759123", "a3x6lGx32PYMrmK3");

       // 第一个参数表示 APP ID ，第二：App Secret，第三： 回调 地址
        PlatformConfig.setSinaWeibo("110298133","afb59986db07d0b9b0886e9980d28d1c","http://www.seetao.com");

    }
```

# 微信登录和分享注意事项
1. 注册微信时包名和签名必须正确。
2. PlatformConfig.setWeixin 参数必须真确
3. WXEntryActivity 所在的包的路径必须 applicationId.wxapi.WXEntryActivity
  不然就没有回调。applicationId 就是gradle里面的id同时也是你微信后台创建因应用的时填写的包名。
4. 如果把所有的都配置正确后，还是不行那么需要清除微信缓存。因为微信缓存里面可能保存了之前错误的信息。所以需要清除。




# 统计

1. 统计用户相关：
   1. 总用户
   2. 日活
   3. 流失
   4. 新增

2. 统计错误bug

3. 用户行为（页面）

4. 自定义事件

   ​




