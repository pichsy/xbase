package com.pichs.xbase.map

/**
 * 两个值的map，一个value
 */
class DoubleKeyMap<Key1, Key2, Value> {

    private val map = mutableMapOf<DoubleKey<Key1, Key2>, Value?>()

    /**
     * 迭代器
     */
    fun iterator(): Iterator<Map.Entry<DoubleKey<Key1, Key2>, Value?>> {
        return map.iterator()
    }

    /**
     * 遍历
     */
    fun map(action: (Key1, Key2, Value?) -> Unit) {
        map.map {
            action(it.key.first, it.key.second, it.value)
        }
    }

    /**
     * 遍历
     */
    fun forEach(action: (Key1, Key2, Value?) -> Unit) {
        map.map {
            action(it.key.first, it.key.second, it.value)
        }
    }

    /**
     * 添加 数据
     */
    fun put(key1: Key1, key2: Key2, value: Value?) {
        val key = DoubleKey(key1, key2)
        val resValue = map[key]
        if (resValue == null) {
            map[key] = value
        } else {
            map[key] = value
        }
    }

    /**
     * 查找key1的map
     */
    fun findKey1Map(key1: Key1): MutableMap<DoubleKey<Key1, Key2>, Value?> {
        val resMap = mutableMapOf<DoubleKey<Key1, Key2>, Value?>()
        map.map {
            if (it.key.first == key1) {
                resMap[it.key] = it.value
            }
        }
        return resMap
    }

    /**
     * 查找key2的map
     */
    fun findKey2Map(key2: Key2): MutableMap<DoubleKey<Key1, Key2>, Value?> {
        val resMap = mutableMapOf<DoubleKey<Key1, Key2>, Value?>()
        map.map {
            if (it.key.second == key2) {
                resMap[it.key] = it.value
            }
        }
        return resMap
    }

    /**
     * 获取数据
     */
    fun get(key1: Key1, key2: Key2): Value? {
        return map[DoubleKey(key1, key2)]
    }

    /**
     * 清除数据
     */
    fun clear() {
        map.clear()
    }

}

data class DoubleKey<Key1, Key2>(
    var first: Key1,
    var second: Key2
) {
    override fun toString(): String {
        return "$first,$second"
    }

    override fun equals(other: Any?): Boolean {
        if (other is DoubleKey<*, *>) {
            return other.first == first && other.second == second
        }
        return false
    }

    override fun hashCode(): Int {
        return toString().hashCode()
    }
}

fun <Key1, Key2, Value> doubleKeyMapOf()= DoubleKeyMap<Key1, Key2, Value>()