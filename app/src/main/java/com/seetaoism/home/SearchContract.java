package com.seetaoism.home;

import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.base.IBasePresenter;
import com.mr.k.mvp.base.IBaseView;
import com.seetaoism.data.entity.SearchData;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

public interface SearchContract {

    public interface ISearchView extends IBaseView<ISearchPresenter> {
        void onSearchSuccess(SearchData data,String msg);
    }

    public interface ISearchPresenter extends IBasePresenter<ISearchView> {
        void getSearchs(int start,int time,String keywords);
    }

    public interface ISearchModel{
        void getSearch(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<SearchData> callBack);
    }

}
