package com.seetaoism.home.mine;

import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.base.IBasePresenter;
import com.mr.k.mvp.base.IBaseView;
import com.seetaoism.data.entity.SearchData;
import com.seetaoism.data.entity.User;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

public interface MineContract {


    public interface IMineView extends IBaseView<IMinePresnter> {
        void onMineSucceed(String msg);

        void onMineFail(String msg);

        void onMineUserSucceed(User user);

        void onMineUserFail(String msg);
    }

    public interface IMinePresnter extends IBasePresenter<IMineView> {
        void getMine();
        void getMineUser();
    }

    public interface MineModel {

        void getMine(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<String> callBack);

        void getMineUser(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<User> callBack);

    }
}
