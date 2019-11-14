package com.mr.k.mvp.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;

import com.mr.k.mvp.R;
import com.mr.k.mvp.kotlin.base.BaseActivity;
import com.mr.k.mvp.utils.Logger;
import com.seetaoism.libloadingview.LoadingView;
import com.trello.rxlifecycle2.components.support.RxFragment;


/*
 * created by taofu on 2019-08-26
 **/
public abstract class BaseFragment extends RxFragment {


    private LoadingView mLoadingView;

    private BaseActivity mBaseActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Logger.DEBUG_D){
            Logger.d("%s column = %s", getClass().getSimpleName(),getColumnId());
        }
    }

    public String getFragmentTag(){
        return getClass().getName();
    }

    public String getColumnId(){
        return "";
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(Logger.DEBUG_D){
            Logger.d("%s ", getClass().getSimpleName());
        }
        if (activity instanceof BaseActivity) {
            mBaseActivity = (BaseActivity) activity;
        }
    }


    @Nullable
    @Override
    public  View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(Logger.DEBUG_D){
            Logger.d("%s column = %s", getClass().getSimpleName(),getColumnId());
        }
        View v =  inflater.inflate(getLayoutId(),container,false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(Logger.DEBUG_D){
            Logger.d("%s column = %s", getClass().getSimpleName(),getColumnId());
        }
        initView(view);

        initData();
    }

    protected void initData(){

    };

    protected abstract int getLayoutId();

    protected abstract void initView(View root);

    protected <T extends View>  T bindViewAndSetListener(int id, View.OnClickListener listener){
        T t = getView().findViewById(id);
        if(listener != null){
            t.setOnClickListener(listener);
        }

        return t;
    }

    protected <T extends BaseFragment> T addFragment(FragmentManager manager, Class<T> aClass, int containerId, Bundle args) {
        if (mBaseActivity != null) {
            return mBaseActivity.addFragment(manager, aClass, containerId, args);
        }

        return null;
    }

    private ViewGroup getParentViewGroup(){
        ViewGroup viewGroup = (ViewGroup) getView().getParent();
        return viewGroup;
    }

    protected abstract void cancelRequest();


    protected void showLoading(@LoadingView.LoadingMode int mode){
        showLoading(mode,getParentViewGroup(),false);
    }

    protected void showLoading(@LoadingView.LoadingMode int mode, boolean cancelAble){
        showLoading(mode,getParentViewGroup(),cancelAble);
    }

    protected void showLoading(@LoadingView.LoadingMode int mode, @IdRes int containerId, boolean cancelAble){
        showLoading(mode,(ViewGroup) getView().findViewById(containerId),cancelAble);
    }


    protected  void showLoadingForViewPager(){
        showLoading(LoadingView.LOADING_MODE_WHITE_BG,(ViewGroup) getView(),false);
    }

    protected void showLoading(int mode, ViewGroup group,boolean cancelAble){

        mLoadingView = LoadingView.inject(getContext(),group);

        if(cancelAble){
            mLoadingView.setCancelListener(new LoadingView.OnCancelListener() {
                @Override
                public void onCancel() {
                    cancelRequest();
                }
            });
        }else{
            mLoadingView.setCancelListener(null);
        }

        mLoadingView.show(mode,cancelAble);
    }



    protected void onError(String msg, LoadingView.RetryCallBack callBack){

        if(mLoadingView != null && mLoadingView.isShow()){// 如果这个loading 页面已经被添加显示
            mLoadingView.onError(msg, callBack);
        }
    }

    protected  void closeLoading(){
        if(mLoadingView != null && mLoadingView.isShow()){// 如果这个loading 页面已经被添加显示
            mLoadingView.close();
        }
    }


    public LoadingView getLoadingView() {
        return mLoadingView;
    }

    protected  void back(){
        getFragmentManager().popBackStack();
    }

    public int enter() {
        if (!isNeedAnimation()) {
            return 0;
        }
        return R.anim.common_page_right_in;
    }

    public int exit() {
        if (!isNeedAnimation()) {
            return 0;
        }
        return R.anim.common_page_left_out;
    }

    public int popEnter() {
        if (!isNeedAnimation()) {
            return 0;
        }
        return R.anim.common_page_left_in;
    }

    public int popExit() {
        if (!isNeedAnimation()) {
            return 0;
        }
        return R.anim.common_page_right_out;
    }

    public boolean isNeedAddToBackStack() {
        return true;
    }

    public boolean isNeedAnimation() {
        return true;
    }

    public boolean isHidePreFragment(){
        return true;
    }
    protected void showToast(@StringRes int id){
        Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@NonNull String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(Logger.DEBUG_D){
            Logger.d("%s column = %s", getClass().getSimpleName(),getColumnId());
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(Logger.DEBUG_D){
            Logger.d("%s column = %s", getClass().getSimpleName(),getColumnId());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(Logger.DEBUG_D){
            Logger.d("%s column = %s", getClass().getSimpleName(),getColumnId());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Logger.DEBUG_D){
            Logger.d("%s column = %s", getClass().getSimpleName(),getColumnId());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(Logger.DEBUG_D){
            Logger.d("%s column = %s", getClass().getSimpleName(),getColumnId());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(Logger.DEBUG_D){
            Logger.d("%s column = %s", getClass().getSimpleName(),getColumnId());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(Logger.DEBUG_D){
            Logger.d("%s column = %s", getClass().getSimpleName(),getColumnId());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(Logger.DEBUG_D){
            Logger.d("%s column = %s", getClass().getSimpleName(),getColumnId());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(Logger.DEBUG_D){
            Logger.d("%s column = %s", getClass().getSimpleName(),getColumnId());
        }
    }
}
