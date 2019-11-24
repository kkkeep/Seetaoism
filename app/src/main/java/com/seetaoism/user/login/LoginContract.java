package com.seetaoism.user.login;

import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.base.IBasePresenter;
import com.mr.k.mvp.base.IBaseView;
import com.seetaoism.data.entity.User;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;


public interface LoginContract {

    // 用户登录时全局广播
    String USER_LOGIN_ACTION = LoginContract.class.getName() + ".user.login";


    public interface ISocialLoginView {

        void onSocialLoginResult(User user,String msg);

    }

    public interface ISocialLoginPresenter{

        void socialLogin(String type,String openId,String head_url,String nickname,String unionid);
    }



    // -------------- 密码登录 -------------------



    public interface ILoginPsView extends IBaseView<ILoginPsPresenter>,ISocialLoginView{

        void onLoginSuccess(User user);

        void onLoginFail(String msg);
    }


    public interface ILoginPsPresenter extends IBasePresenter<ILoginPsView>,ISocialLoginPresenter {

        void login(String phoneNum, String ps);

    }

    // -------------- 密码登录 -------------------




    // -------------- 验证码登录 -------------------
    public interface ILoginCodeView extends IBaseView<ILoginCodePresenter>,ISocialLoginView{
        void onSmsCodeResult(String user,boolean success);

        void onLoginSuccess(User user);

        void onLoginFail(String msg,boolean success );
    }


    public interface ILoginCodePresenter extends IBasePresenter<ILoginCodeView>,ISocialLoginPresenter {

        void getSmsCode(String phoneNumber);
        void loginByCode(String phoneNumber,String verify);

    }


    // -------------- 验证码登录 -------------------


    // -------------- 注册 -------------------
    public interface IRegisterView extends IBaseView<IRegisterPresenter> ,ISocialLoginView{

        void onSmsCodeResult(String msg,boolean success);

        void onVerifySmsCodeResult(String msg,boolean success);

    }


    public interface IRegisterPresenter extends IBasePresenter<IRegisterView> ,ISocialLoginPresenter{

        void getSmsCode(String phoneNumber);

        void verifySmsCode(String phoneNumber,String code);



    }

    // -------------- 注册 -------------------




    // -------------- 注册确认密码 -------------------
    public interface IRegisterSetPsdView extends IBaseView<IRegisterSetPsdPresenter>,ISocialLoginView {

        void onRegisterResultSuccess(User user);
        void onRegisterResultFail(String msg);
    }


    public interface IRegisterSetPsdPresenter extends IBasePresenter<IRegisterSetPsdView>,ISocialLoginPresenter {
            void register(String phoneNumber,String psd,String confirmPsd);
    }

    // -------------- 注册确认密码 -------------------




    // -------------------  获取用户信息-----------------

    public  interface ILoginGetUserInfoView extends IBaseView<ILoginGetUserInfoPresenter> {

        void onUserInfoSuccess(User user);
        void onUserInfoFail(String msg);
    }

    public interface ILoginGetUserInfoPresenter extends IBasePresenter<ILoginGetUserInfoView> {


        void getUserInfoByToken(String token);
    }

    // -------------------  获取用户信息-----------------



    // ------------------- 修改密码-----------------
    public interface IUpdateView extends IBaseView<IUpdatePresenter> {
        void IUpdateSuccess(String user);

        void IUpdatetFail(String msg);


    }

    public interface IUpdatePresenter extends IBasePresenter<IUpdateView> {

        void IUpdatePsw(String phone, String verif, String psw);

        void SetPsw(String phone, String verif, String psw);

    }


    // ------------------- 修改密码-----------------



    // ------------------- 忘记密码-----------------
    public interface IForgetPsView extends IBaseView<IForgetPresenter> {
        //验证码
        void onLoginSuccess(String msg, boolean success);

        //是不是本手机的验证码
        void onLoginFail(String msg, boolean success);
    }


    public interface IForgetPresenter extends IBasePresenter<IForgetPsView> {

        void ForgetCode(String phoneNumber);

        void ForgetSmsCode(String phoneNumber, String code);
    }
    // ------------------- 忘记密码-----------------


    public interface ILoginModel {

        void loginByPs(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<User> callBack);

        void loginByCode(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<User> callBack);

        void getSmsCode(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<String> callBack);

        void verifySmsCode(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<String> callBack);

        void register(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<User> callBack);
        void getUserByToken(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<User> callBack);
        //修改密码
        void updatePsw(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<String> callBack);

        //我的模块修改密码
        void editpassword(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<String> callBack);


        void socialLogin(LifecycleProvider provider,Map<String,String> params,IBaseCallBack<User> callBack);




    }

}
