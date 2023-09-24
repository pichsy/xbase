package com.pichs.xbase.stack
/**
 * 栈的管理者接口
 */
interface IStackContainer {
    /**
     * 添加Activity， 建议添加到list的头部
     * 即：list.add(0,activity)
     *
     * @param activity IStackChild
     */
    fun addActivity(activity: com.pichs.xbase.stack.IStackChild)

    /**
     * 删除Activity
     *
     * @param activity IStackChild
     */
    fun removeActivity(activity: com.pichs.xbase.stack.IStackChild)
}