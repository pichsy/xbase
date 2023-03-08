package com.pichs.base.clickhelper

import android.graphics.Rect
import android.view.TouchDelegate
import android.view.View
import com.pichs.base.clickhelper.ClickPlayer.IS_PLAY_SOUND_DEFAULT

/**
 * 点击事件处理工具
 */
object ClickHelper {

    /**
     * 默认点击间隔 800 ms
     */
    private const val CLICK_INTERVAL_DEFAULT_VALUE = 800L

    /**
     * 点击事件防重，单击，非全局
     */
    fun clicks(
        vararg views: View,
        playAudio: Boolean = IS_PLAY_SOUND_DEFAULT,
        listener: View.OnClickListener?
    ) {
        if (views.isEmpty()) return
        val lis: OnDebouncingClickListener =
            object : OnDebouncingClickListener(CLICK_INTERVAL_DEFAULT_VALUE) {
                override fun onViewClicked(v: View?) {
                    listener?.onClick(v)
                    if (playAudio) {
                        ClickPlayer.play()
                    }
                }
            }
        for (view in views) {
            if (listener == null) {
                view.setOnClickListener(null)
            } else {
                view.setOnClickListener(lis)
            }
        }
    }

    /**
     * 扩大点击区域
     *
     * @param view       The view.
     * @param expandSize The size. 上下左右扩大区域。
     */
    fun expandClickArea(view: View, expandSize: Int) {
        expandClickArea(view, expandSize, expandSize, expandSize, expandSize)
    }

    /**
     * 扩大点击区域
     *
     * @param view The view.
     * 上下左右单独设置
     */
    fun expandClickArea(
        view: View,
        expandSizeTop: Int,
        expandSizeLeft: Int,
        expandSizeRight: Int,
        expandSizeBottom: Int
    ) {
        if (view.parent != null) {
            val parentView = view.parent as View
            parentView.post {
                val rect = Rect()
                view.getHitRect(rect)
                rect.top -= expandSizeTop
                rect.bottom += expandSizeBottom
                rect.left -= expandSizeLeft
                rect.right += expandSizeRight
                parentView.touchDelegate = TouchDelegate(rect, view)
            }
        }
    }

}

abstract class OnDebouncingClickListener(var mDuration: Long) : View.OnClickListener {

    @Volatile
    private var lastClickTime = 0L
    abstract fun onViewClicked(v: View?)

    override fun onClick(v: View) {
        if (isFastClick()) return
        onViewClicked(v)
    }

    @Synchronized
    private fun isFastClick(): Boolean {
        val curMills = System.currentTimeMillis()
        if (curMills - lastClickTime >= mDuration) {
            lastClickTime = curMills
            return false
        }
        return true
    }
}
