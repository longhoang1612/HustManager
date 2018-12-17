package com.hoanglong.hustmanager.screen;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.hoanglong.hustmanager.R;
import com.hoanglong.hustmanager.base.BaseActivity;
import com.hoanglong.hustmanager.screen.login.LoginActivity;
import com.hoanglong.hustmanager.utils.Constants;
import com.hoanglong.hustmanager.utils.SharedPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.progress_splash)
    ProgressBar mProgressSplash;
    private boolean mIsLogin;

    @Override
    protected int getLayoutResources() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initComponent() {
        ButterKnife.bind(this);
        mIsLogin = SharedPrefs.getInstance().get(Constants.SPR_LOGIN,Boolean.class);
        login();
    }

    private void login() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgressSplash.setVisibility(View.GONE);
                if(mIsLogin){
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        },Constants.DELAY_1000);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
