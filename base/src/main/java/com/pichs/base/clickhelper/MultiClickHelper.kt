package com.pichs.base.clickhelper

import android.view.View
import com.pichs.base.utils.ThreadUtils

/**
 * 设置时间内: 多次点击 帮助类。
 * 可设置多少毫秒内（ms），点击次数监听。
 */
object MultiClickHelper {
    fun clicks(view: View): MCLHolder {
        return MCLHolder(view)
    }
}

/**
 * 多次点击事件响应的持有类
 */
class MCLHolder(private val mView: View) {
    private var clickTimes = 0
    private var mTimeMills = 1000

    // 锁定时间，触发点击事件后，该时间段不触发点击事件。
    private var lockedTime = 1000L
    private var isRunning = false
    private var mListener: OnMultiClickListener? = null
    private var locked = false

    /**
     * 设置点击事件的时间总长，也就是多少秒结束
     *
     * @param time 1s 单位 s 默认1s
     * @return [MCLHolder]
     */
    fun setTimeMills(timeMills: Int): MCLHolder {
        mTimeMills = timeMills
        return this
    }

    /**
     * 点击事件触发后锁定时间内不触发点击事件，锁定点击事件时间
     *
     * @param lockedTime lockedTime
     * @return [MCLHolder]
     */
    fun setLockedTime(lockedTime: Long): MCLHolder {
        this.lockedTime = lockedTime
        return this
    }

    /**
     * 设置监听事件回调
     *
     * @param onMultiClickListener [OnMultiClickListener]
     */
    fun call(onMultiClickListener: OnMultiClickListener?) {
        mListener = onMultiClickListener
        mView.setOnClickListener(View.OnClickListener aa@{
            if (locked) {
                return@aa
            }
            // 如果单击事件，直接回调，无需计算
            if (isRunning) {
                clickTimes++
            }
            if (!isRunning) {
                clickTimes = 1
                isRunning = true
                callbackDelay()
            }
        })
    }

    private fun callbackDelay() {
        if (clickTimes == 1) {
            ThreadUtils.postDelay(mTimeMills.toLong()) {
                locked = true
                // 时间到 ,点击事件回调
                mListener?.onClicked(clickTimes)
                ThreadUtils.postDelay(lockedTime) {
                    clickTimes = 0
                    isRunning = false
                    locked = false
                }
            }
        }
    }
}

fun interface OnMultiClickListener {
    fun onClicked(clickedTimes: Int)
}
