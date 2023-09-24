package com.pichs.xbase.cache

/**
 * MMKV 封装
 * 继承了 BaseMMKVHelper
 * 使用默认的 mmkv 对象
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