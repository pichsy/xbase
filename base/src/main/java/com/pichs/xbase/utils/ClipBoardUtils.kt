package com.pichs.xbase.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.text.TextUtils
import android.content.Context
import java.lang.Exception

/**
 * 注册监听板
 */
object ClipBoardUtils {

    private var mClipboardManager: ClipboardManager? = null

    fun getClipBoardContent(activity: Activity): String? {
        if (mClipboardManager == null) {
            mClipboardManager =
                activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        }
        try {
            if (mClipboardManager!!.hasPrimaryClip()) {
                val primaryClip = mClipboardManager!!.primaryClip
                if (primaryClip != null && primaryClip.itemCount > 0) {
                    val content = primaryClip.getItemAt(0).text.toString()
                    if (!TextUtils.isEmpty(content)) {
                        return content
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun copyToClipBoard(activity: Activity, content: String?) {
        if (mClipboardManager == null) {
            mClipboardManager =
                activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        }
        try {
            val clipData = ClipData.newPlainText("", content)
            mClipboardManager!!.setPrimaryClip(clipData)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addClipBoardChaneListener(
        activity: Activity,
        onClipBoardChangedListener: OnClipBoardChangedListener?
    ) {
        if (mClipboardManager == null) {
            mClipboardManager =
                activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        }
        mClipboardManager!!.addPrimaryClipChangedListener {
            try {
                if (mClipboardManager!!.hasPrimaryClip()) {
                    val primaryClip = mClipboardManager!!.primaryClip
                    if (primaryClip != null && primaryClip.itemCount > 0) {
                        val content = primaryClip.getItemAt(0).text.toString()
                        if (!TextUtils.isEmpty(content)) {
                            onClipBoardChangedListener?.onChanged(content)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

interface OnClipBoardChangedListener {
    fun onChanged(text: String?)
}