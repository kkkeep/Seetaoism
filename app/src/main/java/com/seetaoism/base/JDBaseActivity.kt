package com.seetaoism.base

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.FragmentActivity

import com.mr.k.mvp.kotlin.base.BaseActivity
import com.mr.k.mvp.statusbar.StatusBarUtils
import com.seetaoism.data.entity.NewsData
import com.seetaoism.utils.*
import com.umeng.socialize.UMShareListener

/*
 * created by Cherry on 2019-10-28
 *
 **/



abstract class JDBaseActivity : BaseActivity() {

    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutId)

        StatusBarUtils.setStatusBarLightMode(this, Color.WHITE)


        doOnCreate(savedInstanceState)
    }

    protected abstract fun doOnCreate(savedInstanceState: Bundle?)


}
