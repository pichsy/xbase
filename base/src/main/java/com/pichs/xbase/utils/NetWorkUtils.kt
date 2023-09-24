package com.pichs.xbase.utils

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
    fun isOnline(): Boolean {
        val cm = UiKit.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        @SuppressLint("MissingPermission") val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected && netInfo.isAvailable
    }

    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
     */
    fun getNetworkType(): Int {
        var netType = 0
        val connectivityManager = UiKit.getApplication()
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

    fun isWifiConnect(): Boolean {
        return getNetworkType() == 1
    }

    fun isNetConnect(): Boolean {
        return getNetworkType() != 0
    }
}