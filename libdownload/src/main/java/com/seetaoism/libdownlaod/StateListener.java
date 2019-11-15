package com.seetaoism.libdownlaod;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import java.lang.ref.WeakReference;

/*
 * created by Cherry on 2019-10-28
 **/
public abstract class StateListener implements DownloadListener{

    WeakReference<Activity> mActivityWeakReference;
    WeakReference<Fragment> mFragmentWeakReference;


    public StateListener(Activity activity){
        mActivityWeakReference = new WeakReference<>(activity);
    }

    public StateListener(Fragment fragment){
        mFragmentWeakReference =new WeakReference<>(fragment);
    }


    boolean isAlive(){
        boolean isAlive = false;
        if(mActivityWeakReference != null){
            Activity activity = mActivityWeakReference.get();
            if(activity != null && !activity.isFinishing() && !activity.isDestroyed()){
                isAlive =  true;
            }
        }


        if(mFragmentWeakReference != null){
            Fragment fragment = mFragmentWeakReference.get();
            if(fragment != null &&  !fragment.isHidden() && !fragment.isDetached()){
                isAlive = true;
            }
        }
        return isAlive;
    }




}
