@file:JvmName("MvpFragmentManager")

package com.mr.k.mvp.kotlin.base

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import com.mr.k.mvp.base.BaseFragment
import com.mr.k.mvp.utils.Logger


/*
 * created by Cherry on 2019-10-31
**/

private const val TAG = "MvpFragmentManager"

@JvmOverloads
internal fun <C : BaseFragment> addFragment(fragmentManager: FragmentManager, clazz: Class<C>, containerId: Int, args: Bundle? = null, tag : String = getFragmentTag(clazz), doOnCommit: Function1<C,Unit>? = null): C? {

    Logger.d("%s add %s", TAG, clazz.simpleName)

    val fragmentTransition = fragmentManager.beginTransaction();


    val fragment = fragmentManager.findFragmentByTag(tag)

    var baseFragment: C? = null

    if (fragment == null) {
        baseFragment = clazz.newInstance();
        fragmentTransition.toAdd(baseFragment, containerId, tag);

    } else {
        baseFragment = fragment as C


        fragmentTransition.run {
            fragment.let {
                if (it.isAdded) {
                    if (it.isHidden)
                        show(it)
                } else {
                    if (baseFragment.getLifecycle().getCurrentState() != Lifecycle.State.INITIALIZED) {
                        return null;
                    }
                    toAdd(baseFragment, containerId, tag);
                }
            }
        }
    }
    baseFragment.arguments = args

    doOnCommit?.invoke(baseFragment)

    if (baseFragment.isHidePreFragment) {
        baseFragment.run {

            val fragments = fragmentManager.fragments
            for (f in fragments) {
                if (f !== baseFragment && !f.isHidden) {
                    fragmentTransition.hide(f)
                }
            }
        }
    }
    fragmentTransition.commitAllowingStateLoss()

    return baseFragment
}

private fun <C : BaseFragment> FragmentTransaction.toAdd(fragment: C?, id: Int, tag: String) {

    fragment?.let {
        if (it.isNeedAddToBackStack) addToBackStack(tag)
        setCustomAnimations(it.enter(), it.exit(), it.popEnter(), it.popExit())
        add(id, it, tag)
    }

}

fun <C> getFragmentTag(clazz: Class<C>) = clazz.name

