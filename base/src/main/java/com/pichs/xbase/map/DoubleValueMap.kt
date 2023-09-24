package com.pichs.xbase.map

import java.io.Serializable

/**
 * 两个值的map，一个key
 */
class DoubleValueMap<String, Value1, Value2> {

    private val map = mutableMapOf<String, DoubleValue<Value1, Value2>>()

    fun map(action: (String, Value1?, Value2?) -> Unit) {
        map.map {
            action(it.key, it.value.first, it.value.second)
        }
    }

    fun forEach(action: (String, Value1?, Value2?) -> Unit) {
        map.forEach {
            action(it.key, it.value.first, it.value.second)
        }
    }


    /**
     * 添加 数据
     */
    fun put(key: String, value1: Value1?, value2: Value2?) {
        map[key] = DoubleValue(value1, value2)
    }

    operator fun get(key: String): DoubleValue<Value1, Value2>? {
        return map[key]
    }

    fun getValue1(key: String): Value1? {
        return map[key]?.first
    }

    fun getValue2(key: String): Value2? {
        return map[key]?.second
    }

    fun remove(key: String) {
        map.remove(key)
    }

    fun clear() {
        map.clear()
    }
}


data class DoubleValue<Value1, Value2>(
    var first: Value1? = null,
    var second: Value2? = null
) : Serializable {
    override fun toString(): String {
        return "($first,$second)"
    }
}

fun <K, V1, V2> twoValueMapOf(): DoubleValueMap<K, V1, V2> = DoubleValueMap()