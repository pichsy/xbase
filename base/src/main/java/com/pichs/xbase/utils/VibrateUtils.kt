package com.pichs.xbase.utils

import android.app.Service
import android.content.Context
import android.os.Build
import android.os.Vibrator
import androidx.annotation.RequiresPermission
import android.os.VibrationEffect

/**
 * 震动工具类
 */
object VibrateUtils {

    private var vibrator: Vibrator? = null

    @RequiresPermission("android.permission.VIBRATE")
    fun vibrate(milliseconds: Long) {
        if (!hasVibrator()) return
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            getVibrator()?.vibrate(milliseconds)
        } else {
            getVibrator()?.vibrate(
                VibrationEffect.createOneShot(
                    milliseconds,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        }
    }

    @RequiresPermission("android.permission.VIBRATE")
    fun vibrateWave(timings: LongArray?, repeat: Int) {
        if (!hasVibrator()) return
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            getVibrator()?.vibrate(timings, repeat)
        } else {
            getVibrator()?.vibrate(
                VibrationEffect.createWaveform(
                    timings,
                    repeat
                )
            )
        }
    }

    fun hasVibrator(): Boolean {
        return getVibrator()?.hasVibrator() ?: false
    }

    @RequiresPermission("android.permission.VIBRATE")
    fun cancel() {
        getVibrator()?.cancel()
    }

    private fun getVibrator(): Vibrator? {
        if (vibrator == null) {
            vibrator = UiKit.getApplication().getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
        }
        return vibrator
    }

}