package com.mr.k.mvp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

/*
 * created by taofu on 2019-08-26
 **/
@SuppressWarnings("ALL")
public abstract class MvpBaseFragment< P extends IBasePresenter> extends BaseFragment implements IBaseView<P> {

    protected P mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mPresenter = createPresenter();

        if(mPresenter != null){
            mPresenter.attachView(this);
        }
    }


    @Override
    public Activity getActivityObj() {
        return getActivity();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected void cancelRequest() {
         mPresenter.cancelRequest();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mPresenter != null){
            mPresenter.detachView();
        }
    }

    @Override
    public void closeLoading() {
        super.closeLoading();
    }

}
