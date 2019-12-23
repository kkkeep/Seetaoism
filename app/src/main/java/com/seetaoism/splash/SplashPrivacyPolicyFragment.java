package com.seetaoism.splash;

import com.seetaoism.user.register.PrivacyPolicyFragment;

/*
 * created by Cherry on 2019-12-20
 **/
public class SplashPrivacyPolicyFragment extends PrivacyPolicyFragment {
    @Override
    protected void back() {
        super.back();

        getActivity().finish();
    }
    @Override
    public boolean isNeedAddToBackStack() {
        return false;
    }

    @Override
    public boolean isNeedAnimation() {
        return false;
    }
}
