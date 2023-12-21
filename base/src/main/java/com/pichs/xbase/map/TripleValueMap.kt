package com.pichs.xbase.map

import com.pichs.xbase.kotlinext.isNULL
import com.pichs.xbase.kotlinext.isNotNULL
import java.io.Serializable

/**
 * 三个值的map，一个key
 */
class TripleValueMap<Key, Value1, Value2, Value3> {

    private val map = mutableMapOf<Key, TripleValue<Value1, Value2, Value3>>()

    fun map(action: (Key, Value1?, Value2?, Value3?) -> Unit) {
        map.map {
            action(it.key, it.value.first, it.value.second, it.value.third)
        }
    }

    fun forEach(action: (Key, Value1?, Value2?, Value3?) -> Unit) {
        map.forEach {
            action(it.key, it.value.first, it.value.second, it.value.third)
        }
    }

    /**
     * 添加 数据
     */
    fun put(key: Key, value1: Value1?, value2: Value2?, value3: Value3?) {
        map[key].isNotNULL { triple ->
            triple.first = value1
            triple.second = value2
            triple.third = value3
        }.isNULL {
            map[key] = TripleValue(value1, value2, value3)
        }
    }

    operator fun get(key: Key): TripleValue<Value1, Value2, Value3>? {
        return map[key]
    }

    fun getValue1(key: Key): Value1? {
        return map[key]?.first
    }

    fun getValue2(key: Key): Value2? {
        return map[key]?.second
    }

    fun getValue3(key: Key): Value3? {
        return map[key]?.third
    }

    fun remove(key: Key) {
        map.remove(key)
    }

    fun clear() {
        map.clear()
    }
}

data class TripleValue<Value1, Value2, Value3>(
    var first: Value1? = null,
    var second: Value2? = null,
    var third: Value3? = null
) : Serializable {
    override fun toString(): String {
        return "($first,$second,$third)"
    }
}

fun <K, V1, V2, V3> tripleValueMapOf() = TripleValueMap<K, V1, V2, V3>()