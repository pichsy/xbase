package com.pichs.base.stack

/**
 * 栈的被管理者接口
 */
interface IStackChild {
    /**
     * 当触发，添加接口
     */
    fun onAddActivity()

    /**
     * 当触发，移除接口
     */
    fun onRemoveActivity()
}