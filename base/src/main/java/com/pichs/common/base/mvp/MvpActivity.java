package com.pichs.common.base.mvp;


import com.pichs.common.base.BaseActivity;

public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity implements BaseView {

    protected P mPresenter;

    @Override
    protected void onCreate() {
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.attach(this);
        }
    }



    @Override
    protected void onDestroy() {
        if (mPresenter != null)
            mPresenter.detach();
        super.onDestroy();
    }

    protected abstract P initPresenter();
}
