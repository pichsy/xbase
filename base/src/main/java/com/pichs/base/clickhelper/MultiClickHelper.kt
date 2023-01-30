package com.pichs.base.clickhelper

import com.pichs.base.timermanager.TimerManager
import android.view.View
import com.pichs.base.utils.ThreadUtils

/**
 * 多次点击帮助类。
 * 可设置多秒（s > Int）内，点击次数监听。
 */
object MultiClickHelper {

    fun clicks(view: View): MultiClickHolder {
        return MultiClickHolder(view)
    }

}

/**
 * 多次店家事件的持有类
 */
class MultiClickHolder(private val mView: View) {
    private var clickTimes = 0
    private var times = 1

    // 锁定时间，触发点击事件后，该时间段不触发点击事件。
    private var lockedTime = 1000L
    private var isRunning = false
    private var mListener: OnMultiClickListener? = null
    private var mTimerManager: TimerManager? = null
    private var locked = false

    /**
     * 设置点击事件的时间总长，也就是多少秒结束
     *
     * @param time 1s 单位 s 默认1s
     * @return [MultiClickHolder]
     */
    fun setTimes(time: Int): MultiClickHolder {
        times = time
        return this
    }

    /**
     * 点击事件触发后锁定时间内不触发点击事件，锁定点击事件时间
     *
     * @param lockedTime lockedTime
     * @return [MultiClickHolder]
     */
    fun setLockedTime(lockedTime: Long): MultiClickHolder {
        this.lockedTime = lockedTime
        return this
    }

    /**
     * 设置监听事件回调
     *
     * @param onMultiClickListener [OnMultiClickListener]
     */
    fun call(onMultiClickListener: OnMultiClickListener?) {
        init()
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
                mTimerManager?.startTimer()
                isRunning = true
            }
        })
    }

    /**
     * 初始化计时器
     */
    private fun init() {
        if (mTimerManager == null) {
            mTimerManager = TimerManager(times = times, onFinish = {
                locked = true
                // 时间到 ,点击事件回调
                mListener?.onClicked(clickTimes)
                ThreadUtils.postDelay(lockedTime) {
                    clickTimes = 0
                    isRunning = false
                    locked = false
                }
            })
        }
    }
}

fun interface OnMultiClickListener {
    fun onClicked(clickedTimes: Int)
}
