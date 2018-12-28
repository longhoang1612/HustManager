package com.hoanglong.hustmanager.screen;

import android.app.Application;

public class HustManager extends Application {

    private static HustManager sSelf;

    public static HustManager self() {
        return sSelf;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sSelf = this;
    }
}
