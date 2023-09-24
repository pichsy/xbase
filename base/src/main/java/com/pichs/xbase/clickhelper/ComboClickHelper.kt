package com.pichs.xbase.clickhelper

import android.view.View

/**
 * 连击记录事件
 * 只要在 设定 [CBCHolder.intervalTime] 时间间隔内点击下一次，就会累计点击事件，否则重置点击事件
 * 每次点击都会回调给监听器。
 * 适用于唤起隐藏功能 自行判断连击次数，达标后进行自己的逻辑处理。
 */
object ComboClickHelper {
    /**
     * 点击事件的入口
     */
    fun clicks(view: View): CBCHolder {
        return CBCHolder(view)
    }
}

class CBCHolder(var mView: View) {

    private var intervalTime = 800
    private var mListener: OnComboClickListener? = null
    private var clickTime = 0L
    private var times = 0

    fun setIntervalTime(time: Int): CBCHolder {
        this.intervalTime = time
        return this
    }

    fun call(listener: OnComboClickListener) {
        this.mListener = listener
        mView.setOnClickListener {
            val curMill = System.currentTimeMillis()
            if (curMill - clickTime > intervalTime) {
                times = 1
                mListener?.onClicked(times)
            } else {
                times++
                mListener?.onClicked(times)
            }
            clickTime = curMill
        }
    }
}


fun interface OnComboClickListener {
    fun onClicked(clickedTimes: Int)
}
