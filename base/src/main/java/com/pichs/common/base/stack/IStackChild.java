package com.pichs.common.base.stack;

/**
 * 栈的被管理者接口
 */
public interface IStackChild {

    /**
     * 当触发，添加接口
     */
    void onAddActivity();

    /**
     * 当触发，移除接口
     */
    void onRemoveActivity();

}
