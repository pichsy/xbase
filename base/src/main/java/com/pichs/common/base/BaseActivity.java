package com.pichs.common.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pichs.common.base.stack.IStackChild;
import com.pichs.common.base.stack.StackManager;
import com.pichs.common.utils.utils.ResourceLoader;
import com.pichs.skin.xskinloader.SkinInflaterFactory;
import com.pichs.xdialog.loading.ProgressCommonDialog;


public abstract class BaseActivity extends AppCompatActivity implements IStackChild {

    protected AppCompatActivity mActivity;
    protected BaseActivity mBaseActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SkinInflaterFactory.setFactory(this);
        super.onCreate(savedInstanceState);
        if (getTheme() == null) {
            setTheme(ResourceLoader.getInstance(this).getStyle("AppTheme"));
        }
        onAddActivity();
        mActivity = this;
        mBaseActivity = this;
        beforeOnCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        onCreate();
    }

    protected abstract int getLayoutId();

    protected abstract void beforeOnCreate(Bundle savedInstanceState);

    protected abstract void onCreate();

    @Override
    public void onAddActivity() {
        StackManager.get().addActivity(this);
    }

    @Override
    public void onRemoveActivity() {
        StackManager.get().removeActivity(this);
    }

    @Override
    protected void onDestroy() {
        onRemoveActivity();
        super.onDestroy();
    }

    private ProgressCommonDialog mProgressCommonDialog;

    /**
     * 显示加载dialog
     */
    public void showProgressDialog() {
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
    public void showProgressDialog(String text) {
        mProgressCommonDialog = new ProgressCommonDialog(mActivity);
        mProgressCommonDialog.setText(text);
        mProgressCommonDialog.circularStyle();
        mProgressCommonDialog.show();
    }

    /**
     * 隐藏加载dialog
     */
    public void dismissProgressDialog() {
        if (mProgressCommonDialog != null) {
            mProgressCommonDialog.dismiss();
        }
    }

}
