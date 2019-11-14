package com.seetaoism.home.push;

import android.content.Context;

import cn.jpush.android.service.WakedResultReceiver;

/**
 * Created by chenzheng on 2019/11/1.
 * The only genius that is worth anything is the genius for hard work
 *
 * @author chenzheng
 * @date 2019/11/1
 * @description
 */
public class MyWakedResultReceiver extends WakedResultReceiver {
    @Override
    public void onWake(int i) {
        super.onWake(i);
    }

    @Override
    public void onWake(Context context, int i) {
        super.onWake(context, i);
    }
}
