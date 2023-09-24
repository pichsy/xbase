package com.pichs.xbase.kotlinext


import android.app.Activity
import android.content.Context
import android.content.Intent
import com.pichs.xbase.utils.UiKit

fun Context.startActivitySafely(intent: Intent) {
    if (this is Activity) {
        if (!this.isFinishing) {
            try {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                this.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    } else {
        try {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            this.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun Context.sendBroadcastSafely(intent: Intent) {
    try {
        sendBroadcast(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun UiKit.sendBroadcastSafely(intent: Intent){
    try {
        getApplication().sendBroadcast(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
