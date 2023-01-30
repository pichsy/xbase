package com.pichs.base.clickhelper

import android.graphics.Rect
import android.view.View.OnTouchListener
import android.view.MotionEvent
import android.view.TouchDelegate
import android.view.View

/**
 * 点击事件处理工具
 */
object ClickHelper {

    /**
     * 是否 默认播放 点击音效，默认关闭，
     * 可在application中初始化 修改默认，改为true后，则默认有点击音效
     * 再次之前还需要进行 音效的初始化。[ClickPlayer.initSound] ("assetsPathName.mp3")
     * 当然，代码也可以设置 不播放音效。
     */
    var IS_PLAY_SOUND_DEFAULT = false

    /**
     * 点击放大
     */
    const val PRESSED_VIEW_SCALE_BIGGER_VALUE = -0.06f

    /**
     * 点击缩小， 默认
     */
    const val PRESSED_VIEW_SCALE_SMALLER_VALUE = 0.06f

    // 其他参数，后期会追加到 xwidget中，使用属性设置，更加优雅
    const val PRESSED_VIEW_SCALE_TAG = -1

    /**
     * 默认点击间隔 800 ms
     */
    private const val CLICK_INTERVAL_DEFAULT_VALUE = 800L

    /**
     * 按压放大，或者缩小
     */
    fun pressedViewScale(views: Array<View>?, scaleFactors: FloatArray?) {
        if (views == null || views.isEmpty()) {
            return
        }
        for (i in views.indices) {
            if (scaleFactors == null || i >= scaleFactors.size) {
                pressedViewScale(PRESSED_VIEW_SCALE_SMALLER_VALUE, views[i])
            } else {
                pressedViewScale(scaleFactors[i], views[i])
            }
        }
    }

    /**
     * 按压放大，或者缩小，批量的统一比例
     */
    fun pressedViewScale(scaleFactor: Float, vararg views: View) {
        if (views.isEmpty()) {
            return
        }
        for (view in views) {
            view.setTag(PRESSED_VIEW_SCALE_TAG, scaleFactor)
            view.setOnTouchListener(OnScaleTouchListener.getInstance())
        }
    }

    /**
     * 点击事件防重，单击，非全局
     */
    private fun clicks(
        vararg views: View,
        playAudio: Boolean = IS_PLAY_SOUND_DEFAULT,
        listener: View.OnClickListener? = null
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

abstract class OnDebouncingClickListener(private val mDuration: Long) : View.OnClickListener {
    private var lastClickTime = 0L
    abstract fun onViewClicked(v: View?)
    override fun onClick(v: View) {
        if (isFastClick()) return
        onClick(v)
    }

    private fun isFastClick(): Boolean {
        val curMills = System.currentTimeMillis()
        if (curMills - lastClickTime >= mDuration) {
            lastClickTime = curMills
            return false
        }
        return true
    }
}

class OnScaleTouchListener private constructor() : OnTouchListener {

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val action = event.action
        if (action == MotionEvent.ACTION_DOWN) {
            processScale(v, true)
        } else if (action == MotionEvent.ACTION_UP
            || action == MotionEvent.ACTION_CANCEL
        ) {
            processScale(v, false)
        }
        return false
    }

    private fun processScale(view: View, isDown: Boolean) {
        val tag = view.getTag(ClickHelper.PRESSED_VIEW_SCALE_TAG) as? Float ?: return
        val value: Float = if (isDown) 1f + tag else 1f
        view.animate()
            .scaleX(value)
            .scaleY(value)
            .setDuration(200)
            .start()
    }

    private object LazyHolder {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { OnScaleTouchListener() }
    }

    companion object {
        @JvmStatic
        fun getInstance(): OnScaleTouchListener {
            return LazyHolder.instance
        }
    }
}
