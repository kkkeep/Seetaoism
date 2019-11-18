package com.mr.k.mvp.kotlin.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentManager
import com.mr.k.mvp.base.BaseFragment
import com.seetaoism.libloadingview.LoadingView
import com.mr.k.mvp.kotlin.base.getFragmentTag as deGetFragmentName
import com.mr.k.mvp.kotlin.base.addFragment as doAddFragment
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity


/*
 * created by Cherry on 2019-10-31
**/


open class BaseActivity : RxAppCompatActivity() {

    private var mTag: String = javaClass.simpleName

    private var mLoadingView: LoadingView? = null

    @JvmOverloads
    fun <C : BaseFragment> addFragment(fragmentManager: FragmentManager, clazz: Class<C>, containerId: Int, args: Bundle? = null, tag : String = getFragmentTag(clazz), doOnCommit: Function1<C,Unit>? = null): C? {
        return doAddFragment(fragmentManager, clazz, containerId, args,tag,doOnCommit );
    }


    fun getFragmentTag(clazz: Class<out Any>): String {
        return deGetFragmentName(clazz);
    }


    protected fun showToast(@StringRes id: Int) {
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show()
    }

    protected fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    }


    @JvmOverloads
    protected fun showLoading(@LoadingView.LoadingMode mode: Int = LoadingView.LOADING_MODE_WHITE_BG, @IdRes containerId: Int = android.R.id.content, cancelAble: Boolean = false) {
        showLoading(findViewById<View>(containerId) as ViewGroup, mode, cancelAble)
    }

    private fun showLoading(group: ViewGroup, @LoadingView.LoadingMode mode: Int = LoadingView.LOADING_MODE_WHITE_BG, cancelAble: Boolean = false) {

        mLoadingView = LoadingView.inject(this, group)?.also {
            with(it) {
                if (cancelAble)
                    setCancelListener { if (isShow) close() }
                else
                    setCancelListener(null)
                show(mode)
            }

        }
    }

    protected fun onError(msg: String, callBack: LoadingView.RetryCallBack) {
        mLoadingView?.let {
            if (it.isShow) it.onError(msg, callBack)
        }
    }


    protected open  fun closeLoading() {
        mLoadingView?.run {
            if (isShow) close()
        }
    }



}