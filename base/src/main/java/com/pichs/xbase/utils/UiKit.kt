package com.pichs.xbase.utils

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel


/**
 * 全局上下文存储类。
 */
object UiKit : CoroutineScope by MainScope() {

    /**
     * 存储 app 上下文
     * 存application的上下文,用途更广
     */
    private lateinit var application: Application

    fun init(app: Application) {
        application = app
    }

    /**
     * app 上下文
     */
    fun getApplication(): Application {
        return application
    }

    /**
     * 获取当前应用的包名
     */
    fun getPackageName(): String {
        return application.packageName
    }

    /**
     * 释放资源
     */
    fun release() {
        this.cancel()
    }
}