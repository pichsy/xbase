package com.pichs.common.base.mvp;

import android.os.Bundle;

import com.pichs.common.base.BaseFragment;
import com.pichs.common.base.lazy.LazyFragment;


/**
 * MVP fragment
 */
public abstract class MvpLazyFragment<P extends BasePresenter> extends LazyFragment implements BaseView {

    protected P mPresenter;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.attach(this);
        }
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }


    protected abstract P initPresenter();

}