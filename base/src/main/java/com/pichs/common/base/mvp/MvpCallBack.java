package com.pichs.common.base.mvp;

public interface MvpCallBack<T> {
    void onSuccess(T t);
    void onFailure(String e);
}
