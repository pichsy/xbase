package com.pichs.xbase.clickhelper

import android.view.View
import android.view.View.OnClickListener
import com.pichs.xbase.audio.SoundPoolPlayer
import com.pichs.xbase.clickhelper.ClickPlayer.IS_PLAY_SOUND_DEFAULT
import com.pichs.xbase.clickhelper.FastClickHelper.CLICK_INTERVAL_DEFAULT_VALUE

/**
 * 点击事件处理工具，
 * 单个按钮的防重点击事件
 * 可设置 点击音效
 * 使用点击音效 需要先初始化 [ClickPlayer] 点击音频播放器
 * 详见 [ClickPlayer]
 */
object FastClickHelper {

    /**
     * 默认点击间隔 500 ms
     */
    const val CLICK_INTERVAL_DEFAULT_VALUE = 500L

    /**
     * 点击事件防重，单击，非全局
     */
    fun clicks(
        vararg views: View, playAudio: Boolean = IS_PLAY_SOUND_DEFAULT, listener: View.OnClickListener?
    ) {
        if (views.isEmpty()) return
        val lis: OnDebouncingClickListener = object : OnDebouncingClickListener(CLICK_INTERVAL_DEFAULT_VALUE) {
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
     * 点击事件防重，单击，非全局
     */
    fun clicks(
        vararg views: View, playAudio: Boolean = IS_PLAY_SOUND_DEFAULT, interval: Long = CLICK_INTERVAL_DEFAULT_VALUE, listener: View.OnClickListener?
    ) {
        if (views.isEmpty()) return
        val lis: OnDebouncingClickListener = object : OnDebouncingClickListener(interval) {
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
}

open abstract class OnDebouncingClickListener(private val mDuration: Long) : View.OnClickListener {

    @Volatile
    private var lastClickTime = 0L

    open abstract fun onViewClicked(v: View?)

    override fun onClick(v: View) {
        if (isFastClick()) return
        onViewClicked(v)
    }

    @Synchronized
    private fun isFastClick(): Boolean {
        val curMills = System.currentTimeMillis()
        if ((curMills - lastClickTime >= mDuration) || (curMills - lastClickTime < 0)) {
            lastClickTime = curMills
            return false
        }
        return true
    }
}

// 扩展函数
fun View.fastClick(isPlayAudio: Boolean = IS_PLAY_SOUND_DEFAULT, interval: Long = CLICK_INTERVAL_DEFAULT_VALUE, block: () -> Unit) {
    FastClickHelper.clicks(this, playAudio = isPlayAudio, interval = interval) {
        block.invoke()
    }
}

fun View.fastClickWithAudio(audioAssetsPath: String? = null, block: () -> Unit) {
    if (audioAssetsPath.isNullOrEmpty().not()) {
        SoundPoolPlayer.playOnce(audioAssetsPath!!)
        FastClickHelper.clicks(this, playAudio = false, listener = {
            block.invoke()
        })
    } else {
        FastClickHelper.clicks(this, playAudio = true, listener = {
            block.invoke()
        })
    }
}

fun View.fastClickWithAudio(
    isPlayAudio: Boolean = IS_PLAY_SOUND_DEFAULT,
    interval: Long = CLICK_INTERVAL_DEFAULT_VALUE,
    audioAssetsPath: String? = null,
    block: () -> Unit
) {
    if (audioAssetsPath.isNullOrEmpty().not()) {
        if (isPlayAudio) {
            SoundPoolPlayer.playOnce(audioAssetsPath!!)
        }
        FastClickHelper.clicks(this, playAudio = false, interval = interval, listener = {
            block.invoke()
        })
    } else {
        FastClickHelper.clicks(this, playAudio = isPlayAudio, interval = interval, listener = {
            block.invoke()
        })
    }
}
