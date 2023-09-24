package com.pichs.xbase.map

/**
 * 两个值的map，一个value
 */
class DoubleKeyMap<Key1, Key2, Value> {

    private val map = mutableMapOf<Key1, MutableMap<Key2, Value>>()

    fun iterator(): Iterator<Map.Entry<Key1, MutableMap<Key2, Value>>> {
        return map.iterator()
    }

    fun map(action: (Key1, MutableMap<Key2, Value>) -> Unit) {
        map.map {
            action(it.key, it.value)
        }
    }

    fun forEach(action: (Key1, MutableMap<Key2, Value>) -> Unit) {
        map.forEach {
            action(it.key, it.value)
        }
    }


    /**
     * 添加 数据
     */
    fun put(key1: Key1, key2: Key2, value: Value) {
        val resMap = map[key1]
        if (resMap == null) {
            val newMap = mutableMapOf<Key2, Value>()
            newMap[key2] = value
            map[key1] = newMap
        } else {
            resMap[key2] = value
        }
    }

    fun get(key1: Key1): MutableMap<Key2, Value>? {
        return map[key1]
    }

    fun get(key1: Key1, key2: Key2): Value? {
        return map[key1]?.get(key2)
    }

    fun clear() {
        map.clear()
    }
}