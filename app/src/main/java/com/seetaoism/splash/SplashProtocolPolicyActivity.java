package com.seetaoism.splash;

import android.os.Bundle;

import com.seetaoism.R;
import com.seetaoism.base.JDBaseActivity;
import com.seetaoism.user.register.PrivacyPolicyFragment;
import com.seetaoism.user.register.UserProtocolFragment;

import org.jetbrains.annotations.Nullable;

/*
 * created by Cherry on 2019-12-20
 **/
public class SplashProtocolPolicyActivity extends JDBaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash_protocol_policy;
    }

    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {

        int from = getIntent().getIntExtra("from", 1);

        if(from == 1){
            addFragment(getSupportFragmentManager(), SplashUserProtocolFragment.class, R.id.splash_protocol_policy_container);
        }else{
            addFragment(getSupportFragmentManager(), SplashPrivacyPolicyFragment.class, R.id.splash_protocol_policy_container);
        }

    }
}
