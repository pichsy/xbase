package com.pichs.xbase.clickhelper

import android.view.View
import com.pichs.xbase.clickhelper.FastClickHelper.CLICK_INTERVAL_DEFAULT_VALUE

/**
 * 全局防快速,暴力，重复点击，
 * 只要使用此类 设置的点击事件，
 * 都属于同一组防止快速点击。
 * 一般同一界面都使用此类做点击事件防重即可
 * 多个按钮点击事件会使用同一个时间戳判断，
 *
 * 如果有特殊情况，单独设置点击事件防重
 * 可使用 [ClickHelper]
 */
object GlobalFastClickHelper {

    @Volatile
    private var lastClickTime = 0L

    init {
        lastClickTime = System.currentTimeMillis()
    }

    fun setLastClickTime(time: Long) {
        lastClickTime = time
    }

    fun clicks(view: View?, clickListener: View.OnClickListener?) {
        view?.let { v ->
            v.setOnClickListener {
                if (isFastClick()) return@setOnClickListener
                clickListener?.onClick(v)
                ClickPlayer.play()
            }
        }
    }

    fun clicks(view: View?, block: () -> Unit) {
        view?.let { v ->
            v.setOnClickListener {
                if (isFastClick()) return@setOnClickListener
                block()
                ClickPlayer.play()
            }
        }
    }

    /**
     * 多个View公用一个点击事件时
     */
    fun clicks(vararg views: View?, block: () -> Unit) {
        val clickListener = View.OnClickListener {
            if (isFastClick()) return@OnClickListener
            block()
            ClickPlayer.play()
        }
        views.forEach {
            it?.setOnClickListener(clickListener)
        }
    }

    /**
     * 多个View公用一个点击事件时
     */
    fun clicks(
        vararg views: View?, audioPlay: Boolean, interval: Long = CLICK_INTERVAL_DEFAULT_VALUE, block: () -> Unit
    ) {
        val clickListener = View.OnClickListener {
            if (isFastClick(interval)) return@OnClickListener
            block()
            if (audioPlay) {
                ClickPlayer.play()
            }
        }
        views.forEach {
            it?.setOnClickListener(clickListener)
        }
    }

    @Synchronized
    private fun isFastClick(interval: Long = CLICK_INTERVAL_DEFAULT_VALUE): Boolean {
        val curMills = System.currentTimeMillis()
        if ((curMills - lastClickTime >= interval) || (curMills - lastClickTime < 0)) {
            lastClickTime = curMills
            return false
        }
        return true
    }

}