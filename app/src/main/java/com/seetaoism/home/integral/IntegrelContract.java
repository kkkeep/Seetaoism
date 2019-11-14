package com.seetaoism.home.integral;


import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.base.IBasePresenter;
import com.mr.k.mvp.base.IBaseView;
import com.seetaoism.data.entity.IntergrelData;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

public interface IntegrelContract {



    public interface IntegrelView extends IBaseView<IntegrelPresnter> {
        void onIntegrelSuccess(IntergrelData data);
        void onIntegrelFail(String msg);
    }

    public interface IntegrelPresnter extends IBasePresenter<IntegrelView> {
        void getIntegrel();
    }

    public interface IntegrelModel{
        void getIntegrel(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<IntergrelData> callBack);

    }

}
