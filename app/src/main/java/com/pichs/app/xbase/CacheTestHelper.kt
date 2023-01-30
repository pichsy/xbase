package com.pichs.app.xbase

import com.pichs.base.cache.BaseMMKVHelper

/**
 * MMKV 封装
 */
class CacheTestHelper private constructor() : BaseMMKVHelper("xbase_test_not_sssjjaqajsd") {

    companion object {
        // 单利模式
        @JvmStatic
        private val instance: CacheTestHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            CacheTestHelper()
        }

        /**
         * 单利模式
         */
        @JvmStatic
        fun get(): CacheTestHelper {
            return instance
        }
    }

}