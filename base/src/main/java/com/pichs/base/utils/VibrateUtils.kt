package com.pichs.base.utils

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
    fun vibrate(context: Context, milliseconds: Long) {
        if (!hasVibrator(context)) return
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            getVibrator(context)?.vibrate(milliseconds)
        } else {
            getVibrator(context)?.vibrate(
                VibrationEffect.createOneShot(
                    milliseconds,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        }
    }

    @RequiresPermission("android.permission.VIBRATE")
    fun vibrateWave(context: Context, timings: LongArray?, repeat: Int) {
        if (!hasVibrator(context)) return
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            getVibrator(context)?.vibrate(timings, repeat)
        } else {
            getVibrator(context)?.vibrate(
                VibrationEffect.createWaveform(
                    timings,
                    repeat
                )
            )
        }
    }

    fun hasVibrator(context: Context): Boolean {
        return getVibrator(context)?.hasVibrator() ?: false
    }

    @RequiresPermission("android.permission.VIBRATE")
    fun cancel(context: Context) {
        getVibrator(context)?.cancel()
    }

    private fun getVibrator(context: Context): Vibrator? {
        if (vibrator == null) {
            vibrator = context.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
        }
        return vibrator
    }

}