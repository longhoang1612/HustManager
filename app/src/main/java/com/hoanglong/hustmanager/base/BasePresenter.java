package com.hoanglong.hustmanager.base;

public interface BasePresenter<T> {
    void setView(T view);

    void onStart();

    void onStop();
}
