package com.pichs.xbase.utils

import com.jeremyliao.liveeventbus.LiveEventBus
import com.jeremyliao.liveeventbus.core.Observable

object LiveBus {

    /**
     * 增加尾缀，保证是在自己进程中，避免跨进程乱了
     */
    private const val SUFFIX = "_xbase_app"

    // 适配kotlin
    fun <T> get(eventEntity: Class<T>): Observable<T> {
        return get(eventEntity.name + SUFFIX, eventEntity)
    }

    // 适配kotlin
    fun <T> get(tag: String, eventEntity: Class<T>): Observable<T> {
        return LiveEventBus.get(tag, eventEntity)
    }

    // 适配kotlin
    fun get(tag: String): Observable<Any> {
        return LiveEventBus.get(tag)
    }


}