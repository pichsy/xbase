package com.pichs.base.utils

import android.net.ConnectivityManager
import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import java.lang.Exception
import java.util.*

/**
 * 判断手机是否联网，获取手机连接类型
 */
object NetWorkUtils {
    // 手机网络类型
    const val WIFI = 0x01
    const val CMWAP = 0x02
    const val CMNET = 0x03

    /**
     * 只关注是否联网
     */
    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        @SuppressLint("MissingPermission") val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected && netInfo.isAvailable
    }

    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
     */
    fun getNetworkType(context: Context): Int {
        var netType = 0
        val connectivityManager = context.applicationContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        try {
            @SuppressLint("MissingPermission") val networkInfo =
                connectivityManager.activeNetworkInfo
                    ?: return netType
            val nType = networkInfo.type
            if (nType == ConnectivityManager.TYPE_MOBILE) {
                val extraInfo = networkInfo.extraInfo
                if (!TextUtils.isEmpty(extraInfo)) {
                    netType = if (extraInfo.lowercase(Locale.getDefault()) == "cmnet") {
                        CMNET
                    } else {
                        CMWAP
                    }
                }
            } else if (nType == ConnectivityManager.TYPE_WIFI) {
                netType = WIFI
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return netType
    }

    fun isWifiConnect(context: Context): Boolean {
        return getNetworkType(context) == 1
    }

    fun isNetConnect(context: Context): Boolean {
        return getNetworkType(context) != 0
    }
}