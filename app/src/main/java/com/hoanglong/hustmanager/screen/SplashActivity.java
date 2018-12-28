package com.hoanglong.hustmanager.screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
    private int rule;

    @Override
    protected int getLayoutResources() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initComponent() {
        ButterKnife.bind(this);
        mIsLogin = SharedPrefs.getInstance().get(Constants.SPR_LOGIN, Boolean.class);
        rule = SharedPrefs.getInstance().get(Constants.PHANQUYEN, Integer.class);
        login();
    }

    private void login() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgressSplash.setVisibility(View.GONE);
                if (mIsLogin) {
                    if (rule == 0) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(SplashActivity.this, TeacherActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, Constants.DELAY_1000);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
