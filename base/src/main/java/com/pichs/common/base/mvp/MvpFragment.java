package com.pichs.common.base.mvp;

import android.os.Bundle;

import com.pichs.common.base.BaseFragment;


/**
 * MVP fragment
 */
public abstract class MvpFragment<P extends BasePresenter> extends BaseFragment implements BaseView {

    protected P mPresenter;

    @Override
    protected void beforeOnCreateView(Bundle savedInstanceState) {
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.attach(this);
        }
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detach();
        }
        super.onDestroyView();
    }

    protected abstract P initPresenter();

}