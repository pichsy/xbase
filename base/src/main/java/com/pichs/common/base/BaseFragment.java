package com.pichs.common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pichs.common.uikit.dialog.ProgressCommonDialog;
import com.pichs.common.utils.utils.ToastUtils;

public abstract class BaseFragment extends Fragment {

    protected View mRootView;
    protected Context mContext;
    protected Activity mActivity;
    protected BaseActivity mBaseActivity;
    protected LayoutInflater inflater;
    protected ViewGroup container;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        if (mRootView == null) {
            mActivity = getActivity();
            if (mActivity != null && mActivity instanceof BaseActivity) {
                mBaseActivity = (BaseActivity) mActivity;
            }
            beforeOnCreateView(savedInstanceState);
            mRootView = inflater.inflate(getLayoutId(), container, false);
        } else {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        afterOnCreateView(view);
    }

    protected abstract int getLayoutId();

    protected abstract void beforeOnCreateView(Bundle savedInstanceState);

    protected abstract void afterOnCreateView(View rootView);

    public <T extends View> T findViewById(@IdRes int id) {
        if (mRootView != null)
            return mRootView.findViewById(id);
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRootView = null;
        container = null;
        inflater = null;
    }


    protected boolean NULL(String msg) {
        if (TextUtils.isEmpty(msg))
            return true;
        return false;
    }

    protected boolean NOTNULL(String msg) {
        if (TextUtils.isEmpty(msg))
            return false;
        return true;
    }

    protected void toast(String msg) {
        if (NOTNULL(msg)) {
            ToastUtils.toast(mActivity, msg);
        }
    }

    protected ProgressCommonDialog mProgressCommonDialog;

    /**
     * 显示加载dialog
     */
    protected void showProgressDialog() {
        if (mProgressCommonDialog == null) {
            mProgressCommonDialog = new ProgressCommonDialog(mActivity);
            mProgressCommonDialog.setText("请稍等");
            mProgressCommonDialog.circularStyle();
        }
        mProgressCommonDialog.show();
    }

    /**
     * 更换提示语
     *
     * @param text
     */
    protected void showProgressDialog(String text) {
        mProgressCommonDialog = new ProgressCommonDialog(mActivity);
        mProgressCommonDialog.setText(text);
        mProgressCommonDialog.circularStyle();
        mProgressCommonDialog.show();
    }

    /**
     * 隐藏加载dialog
     */
    protected void dismissProgressDialog() {
        if (mProgressCommonDialog != null) {
            mProgressCommonDialog.dismiss();
        }
    }
}
