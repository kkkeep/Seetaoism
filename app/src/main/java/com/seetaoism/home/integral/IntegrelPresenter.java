package com.seetaoism.home.integral;

import com.mr.k.mvp.base.BasePresenter;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.AppConstant;
import com.seetaoism.data.entity.IntergrelData;
import com.seetaoism.data.entity.SearchData;
import com.seetaoism.data.repositories.IntegrelRepository;

import java.util.HashMap;

public class IntegrelPresenter extends BasePresenter<IntegrelContract.IntegrelView> implements IntegrelContract.IntegrelPresnter {

    private IntegrelContract.IntegrelModel integrelModel;

    public IntegrelPresenter() {
        this.integrelModel = new IntegrelRepository();
    }

    @Override
    public void getIntegrel() {
        HashMap<String, String> params = new HashMap<>();
        integrelModel.getIntegrel(getLifecycleProvider(), params, new IBaseCallBack<IntergrelData>() {
            @Override
            public void onSuccess(IntergrelData data) {
                mView.onIntegrelSuccess(data);
            }

            @Override
            public void onFail(ResultException e) {
                mView.onIntegrelFail(e.getMessage());
            }
        });
    }
}
