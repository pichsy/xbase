package com.pichs.xbase.api

import com.pichs.xbase.cache.CacheHelper

object ApiEnvUtils {

    const val ENV_PREVIEW = "preview"
    const val ENV_RELEASE = "release"
    const val ENV_TEST = "test"

    fun changeEnv(env: String) {
        CacheHelper.get().setString("xbase_api_env", env)
    }

    /**
     *  切换 api 环境  如果未获取到，默认 release 环境
     *  @return api 环境: 正式环境=release ,预发布环境=preview
     */
    fun getEnv(): String {
        return CacheHelper.get().getString("xbase_api_env", "release") ?: "release"
    }
}