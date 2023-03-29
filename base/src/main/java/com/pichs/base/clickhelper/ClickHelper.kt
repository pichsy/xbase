package com.pichs.base.clickhelper

import android.graphics.Rect
import android.view.TouchDelegate
import android.view.View
import com.pichs.base.clickhelper.ClickPlayer.IS_PLAY_SOUND_DEFAULT

/**
 * 点击事件处理工具，
 * 单个按钮的防重点击事件
 * 可设置 点击音效
 * 使用点击音效 需要先初始化 [ClickPlayer] 点击音频播放器
 * 详见 [ClickPlayer]
 * eg: ClickPlayer.initSound("assetsPathName.mp3", isPlaySoundDefault)
 * 通过方法设置：点击间隔，默认500ms [setClickInterval]   设置防误触 点击间隔
 */
object ClickHelper {

    /**
     * 默认点击间隔 500 ms
     */
    private var CLICK_INTERVAL_DEFAULT_VALUE = 500L

    /**
     * 设置防误触 点击间隔
     * @param interval 间隔时间 单位 ms
     */
    fun setClickInterval(@androidx.annotation.IntRange(from = 0) interval: Long) {
        CLICK_INTERVAL_DEFAULT_VALUE = interval
    }

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
