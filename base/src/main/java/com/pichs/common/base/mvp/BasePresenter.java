package com.pichs.common.base.mvp;

public interface BasePresenter<T extends BaseView> {

    void attach(T baseView);

    void detach();

}