package com.pichs.common.base.stack;

import android.app.Activity;

import java.util.List;

/**
 * 栈的管理
 */
public final class StackManager implements IStackContainer {

    private StackManager() {
    }

    private final static class Holder {
        final static StackManager INSTANCE = new StackManager();
    }

    public static StackManager get() {
        return Holder.INSTANCE;
    }

    private void add(IStackChild stackChild) {
        mStackList.add(0, stackChild);
    }

    private void remove(IStackChild stackChild) {
        mStackList.remove(stackChild);
    }

    /**
     * 获取栈顶的 对象
     *
     * @return IStackChild
     */
    public IStackChild getTop() {
        if (!mStackList.isEmpty()) {
            return mStackList.get(0);
        }
        return null;
    }

    /**
     * 获取栈顶的 Activity 对象
     * 循环寻找 最上面的Activity，如果不是activity则继续寻找，直到结束
     *
     * @return IStackChild
     */
    public Activity getTopActivity() {
        if (!mStackList.isEmpty()) {
            for (int i = 0; i < mStackList.size(); ++i) {
                if (mStackList.get(i) != null && mStackList.get(i) instanceof Activity) {
                    return (Activity) mStackList.get(i);
                }
            }
        }
        return null;
    }

    @Override
    public void addActivity(IStackChild activity) {
        add(activity);
    }

    @Override
    public void removeActivity(IStackChild activity) {
        remove(activity);
    }

    @Override
    public List<IStackChild> getStackList() {
        return mStackList;
    }


    /**
     * 退出应用
     * 获取所有栈中的Activity 并调用finish方法。
     */
    public void exitApp() {
        for (int i = 0; i < mStackList.size(); ++i) {
            if (mStackList.get(i) != null && mStackList.get(i) instanceof Activity) {
                ((Activity) mStackList.get(i)).finish();
            }
        }
    }
}
