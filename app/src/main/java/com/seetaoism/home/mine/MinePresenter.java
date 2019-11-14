package com.seetaoism.home.mine;

import com.mr.k.mvp.base.BasePresenter;
import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.exceptions.ResultException;
import com.seetaoism.data.entity.User;
import com.seetaoism.data.repositories.MineRepository;

import java.util.HashMap;
import java.util.Map;


public class MinePresenter extends BasePresenter<MineContract.IMineView> implements MineContract.IMinePresnter {


    private MineContract.MineModel model;

    public MinePresenter() {
        this.model = new MineRepository();
    }

    @Override
    public void getMine() {
        Map<String, String> map = new HashMap<>();
        model.getMine(getLifecycleProvider(), map, new IBaseCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                mView.onMineSucceed(data);
            }

            @Override
            public void onFail(ResultException e) {
                mView.onMineFail(e.getMessage());
            }
        });
    }

    @Override
    public void getMineUser() {
        Map<String, String> map = new HashMap<>();
        model.getMineUser(getLifecycleProvider(), map, new IBaseCallBack<User>() {
            @Override
            public void onSuccess(User data) {
                mView.onMineUserSucceed(data);
            }

            @Override
            public void onFail(ResultException e) {
                mView.onMineUserFail(e.getMessage());
            }
        });
    }
}
