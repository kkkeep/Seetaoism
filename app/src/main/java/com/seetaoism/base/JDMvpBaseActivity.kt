@file:Suppress("FINITE_BOUNDS_VIOLATION_IN_JAVA")

package com.seetaoism.base

import android.graphics.Color
import android.os.Bundle


import com.mr.k.mvp.base.IBasePresenter
import com.mr.k.mvp.base.IBaseView
import com.mr.k.mvp.base.MvpBaseActivity
import com.mr.k.mvp.statusbar.StatusBarUtils


abstract class JDMvpBaseActivity<P : IBasePresenter<*>> : MvpBaseActivity<P>() {

    protected abstract val layoutId: Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        // 4.4 ~ 5.0 的手机必须写在setContentView 之后
        StatusBarUtils.setStatusBarLightMode(this, Color.WHITE)

        doOnCreate(savedInstanceState)
    }

    protected abstract fun doOnCreate(savedInstanceState: Bundle?)
}
