package com.pichs.base.cache

import android.app.Application
import com.tencent.mmkv.MMKV
import java.io.File

/**
 * MMKV 封装
 */
class CacheHelper private constructor() : BaseMMKVHelper(null) {

    companion object {
        private lateinit var application: Application

        /**
         * 初始化MMKV，在Application的 onCreate 中初始化
         */
        @JvmStatic
        fun init(application: Application) {
            Companion.application = application
            MMKV.initialize(
                application,
                "${application.filesDir.absolutePath}${File.separator}disk-cache"
            )
        }

        /**
         * 获取应用的上下文
         * @return Application
         */
        fun getApplication(): Application {
            return application
        }

        // 单利模式
        @JvmStatic
        private val instance: CacheHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            CacheHelper()
        }

        /**
         * 单利模式
         */
        @JvmStatic
        fun get(): CacheHelper {
            return instance
        }
    }

}