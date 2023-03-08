package com.pichs.base.cache

import android.app.Application
import com.tencent.mmkv.MMKV
import java.io.File

/**
 * MMKV 封装
 */
class CacheHelper private constructor() : BaseMMKVHelper(null) {

    companion object {
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