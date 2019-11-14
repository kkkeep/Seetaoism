package com.seetaoism.home;


import com.mr.k.mvp.base.BasePresenter;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.AppConstant;
import com.seetaoism.data.entity.SearchData;
import com.seetaoism.data.repositories.SearchRepository;

import java.util.HashMap;

public class SearchPresenter extends BasePresenter<SearchContract.ISearchView> implements SearchContract.ISearchPresenter {


    private SearchContract.ISearchModel mMode;


    public SearchPresenter() {
        this.mMode = new SearchRepository();
    }
    @Override
    public void getSearchs(int start,int time,String keywords) {
        HashMap<String, String> params = new HashMap<>();

        params.put(AppConstant.RequestParamsKey.VIDEOSTART, String.valueOf(start));
        params.put(AppConstant.RequestParamsKey.VIDEOTIME, String.valueOf(time));
        params.put(AppConstant.RequestParamsKey.SEARCHKEY, keywords);

        mMode.getSearch(getLifecycleProvider(), params, new IBaseCallBack<SearchData>() {


            @Override
            public void onSuccess(SearchData data) {
                if (mView!=null) {
                    mView.onSearchSuccess(data, null);
                }
            }

            @Override
            public void onFail(ResultException e) {
                if (mView!=null) {
                    mView.onSearchSuccess(null, e.getMessage());
                }
            }
        });
    }
}
