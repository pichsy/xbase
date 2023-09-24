package com.pichs.xbase.stack

import androidx.activity.ComponentActivity

/**
 * 栈的被管理者接口
 */
interface IStackChild {

    fun isResume(): Boolean

    fun isStop(): Boolean

    /**
     * 当触发，添加接口
     */
    fun onAddActivity()

    /**
     * 当触发，移除接口
     */
    fun onRemoveActivity()
}

/**
 * 转换成 ComponentActivity
 */
fun IStackChild.asActivity(): ComponentActivity? {
    if (this is ComponentActivity) {
        return this
    }
    return null
}

/**
 * 转换成 IStackChild接口
 */
fun ComponentActivity.asStackChild(): IStackChild? {
    if (this is IStackChild) {
        return this
    }
    return null
}