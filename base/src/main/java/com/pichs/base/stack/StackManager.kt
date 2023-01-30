package com.pichs.base.stack

import android.app.Activity
import java.util.ArrayList

/**
 * 栈的管理
 */
class StackManager private constructor() : IStackContainer {

    private object Holder {
        val INSTANCE = StackManager()
    }

    /**
     * 集合 IStackChild  list
     */
    private val mStackList: MutableList<IStackChild> = ArrayList<IStackChild>()

    private fun add(stackChild: IStackChild) {
        mStackList.add(0, stackChild)
    }

    private fun remove(stackChild: IStackChild) {
        mStackList.remove(stackChild)
    }

    /**
     * 获取栈顶的 对象
     *
     * @return IStackChild
     */
    fun getTop(): IStackChild? {
        return if (mStackList.isNotEmpty()) {
            mStackList[0]
        } else null
    }

    /**
     * 获取栈顶的 Activity 对象
     * 循环寻找 最上面的Activity，如果不是activity则继续寻找，直到结束
     *
     * @return IStackChild
     */
    fun getTopActivity(): Activity? {
        if (mStackList.isNotEmpty()) {
            for (i in mStackList.indices) {
                if (mStackList[i] is Activity) {
                    return mStackList[i] as Activity?
                }
            }
        }
        return null
    }

    override fun addActivity(activity: IStackChild) {
        add(activity)
    }

    override fun removeActivity(activity: IStackChild) {
        remove(activity)
    }

    /**
     * 退出应用
     * 获取所有栈中的Activity 并调用finish方法。
     */
    fun exitApp() {
        for (i in mStackList.indices) {
            if (mStackList[i] is Activity) {
                (mStackList[i] as Activity?)!!.finish()
            }
        }
    }

    companion object {
        fun get(): StackManager {
            return Holder.INSTANCE
        }
    }
}