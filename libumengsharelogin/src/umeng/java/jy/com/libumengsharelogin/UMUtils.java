package jy.com.libumengsharelogin;

import android.content.Context;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.bean.PlatformName;

/*
 * created by Cherry on 2019/5/7
 **/
public class UMUtils {


    public  static void initUmeng(Context context){

        UMConfigure.setLogEnabled(true);
        //平台表单名称
        PlatformName.SINA="新浪微博";


        UMConfigure.init(context,"5da416070cafb26975000f3f","umeng",UMConfigure.DEVICE_TYPE_PHONE,"");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        // 下面所有ID 都得替换成你自己公司在相应创建应用时获得的id

        //第一个参数表示 AppID ，第二：AppSecret
        PlatformConfig.setWeixin("wxc18a6ee8aede4929", "cc1459aa71cc4c3ea24232d62df6231d"); //
        //第一个参数表示 APP ID ，第二：APP KEY
        PlatformConfig.setQQZone("1109759123", "a3x6lGx32PYMrmK3");

       // 第一个参数表示 APP ID ，第二：App Secret，第三： 回调 地址
        PlatformConfig.setSinaWeibo("110298133","afb59986db07d0b9b0886e9980d28d1c","https://www.seetao.com");

    }
}
