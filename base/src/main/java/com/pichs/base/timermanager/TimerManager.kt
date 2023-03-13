package com.pichs.base.timermanager

import android.os.Handler
import android.os.Looper
import android.os.Message
import java.util.*

/**
 * 默认 times = 0 就是 正计时器
 * 当times > 0 有值时就 倒计时器
 * 开始后第一个数会立即触发，根据情况进行 delay=1000L
 */
class TimerManager constructor(
    var delay: Long = 0L,
    var period: Long = 1000L,
    val times: Int = 0,
    var onPause: () -> Unit = {},
    var onResume: () -> Unit = {},
    var onCancel: () -> Unit = {},
    var onFinish: () -> Unit = {},
    var onProgress: (Int) -> Unit = {},
) {

    private var mTotalTimes = 0
    private var timerTask: TimerTask? = null
    private var timer: Timer? = null
    private var timesCount = 0
    private var handler: Handler? = null

    // 基础定义
    private val _PROGRESS = 0
    private val _FINISHED = 1
    private val _CANCELED = 2
    private val _PAUSE = 3
    private val _RESUME = 4

    constructor(
        delay: Long = 0L,
        period: Long = 1000L,
        times: Int = 0,
        callback: OnTimerCallback? = null
    ) : this(delay, period, times, {
        callback?.onPause()
    }, {
        callback?.onResume()
    }, {
        callback?.onCancel()
    }, {
        callback?.onFinish()
    }, {
        callback?.onProgress(it)
    })

    init {
        handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    _PROGRESS -> {
                        // progress
                        onProgress(msg.arg1)
                    }
                    _FINISHED -> {
                        // finish
                        onFinish()
                    }
                    _CANCELED -> {
                        // cancel
                        onCancel()
                    }
                    _PAUSE -> {
                        // onPause
                        onPause()
                    }
                    _RESUME -> {
                        // onResume
                        onResume()
                    }
                }
            }
        }
    }

    @Volatile
    private var isFinishing = false

    init {
        mTotalTimes = if (times <= 0) 0 else times
    }

    fun startTimer() {
        startTimer(true)
    }

    private fun startTimer(reset: Boolean) {
        if (reset) {
            resetTimer()
        }
        if (timerTask == null) {
            timerTask = object : TimerTask() {
                override fun run() {
                    if (isFinishing) {
                        return
                    }
                    if (mTotalTimes > 0) {
                        handler?.let { h ->
                            val obtain = Message.obtain()
                            obtain.what = _PROGRESS
                            obtain.arg1 = mTotalTimes - timesCount
                            h.sendMessage(obtain)
                        }
                    } else {
                        handler?.let { h ->
                            val obtain = Message.obtain()
                            obtain.what = _PROGRESS
                            obtain.arg1 = timesCount
                            h.sendMessage(obtain)
                        }
                    }
                    if (mTotalTimes > 0 && timesCount == mTotalTimes) {
                        finishTimer()
                    }
                    timesCount++
                }
            }
        }
        timer = Timer()
        timer?.schedule(timerTask, delay, period)
    }

    fun pauseTimer() {
        timerTask?.cancel()
        timer?.cancel()
        timer = null
        timerTask = null
        handler?.sendEmptyMessage(_PAUSE)
    }

    fun resumeTimer() {
        // 结束了，不再继续了。倒计时的情况下，进行判断处理。
        if (mTotalTimes in 1..timesCount) {
            finishTimer()
            return
        }
        handler?.sendEmptyMessage(_RESUME)
        startTimer(false)
    }

    fun cancelTimer() {
        timerTask?.cancel()
        timer?.cancel()
        timer = null
        timerTask = null
        handler?.sendEmptyMessage(_CANCELED)
        isFinishing = false
        timesCount = 0
        mTotalTimes = if (times <= 0) 0 else times
    }

    private fun resetTimer() {
        timerTask?.cancel()
        timer?.cancel()
        timer = null
        timerTask = null
        isFinishing = false
        timesCount = 0
        mTotalTimes = if (times <= 0) 0 else times
    }

    private fun finishTimer() {
        isFinishing = true
        timerTask?.cancel()
        timer?.cancel()
        timer = null
        timerTask = null
        handler?.sendEmptyMessage(_FINISHED)
        isFinishing = false
        timesCount = 0
        mTotalTimes = if (times <= 0) 0 else times
    }

}

fun interface OnTimerCallback {
    fun onProgress(time: Int)
    fun onFinish() {}
    fun onCancel() {}
    fun onPause() {}
    fun onResume() {}
}