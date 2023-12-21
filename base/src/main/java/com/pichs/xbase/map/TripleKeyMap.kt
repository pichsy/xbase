package com.pichs.xbase.map

class TripleKeyMap<Key1, Key2, Key3, Value> {

    private val map = mutableMapOf<TripleKey<Key1, Key2, Key3>, Value?>()

    /**
     * 迭代器
     */
    fun iterator(): Iterator<Map.Entry<TripleKey<Key1, Key2, Key3>, Value?>> {
        return map.iterator()
    }

    /**
     * 遍历
     */
    fun map(action: (Key1, Key2, Key3, Value?) -> Unit) {
        map.map {
            action(it.key.first, it.key.second, it.key.third, it.value)
        }
    }

    /**
     * 遍历
     */
    fun forEach(action: (Key1, Key2, Key3, Value?) -> Unit) {
        map.map {
            action(it.key.first, it.key.second, it.key.third, it.value)
        }
    }

    /**
     * 添加 数据
     */
    fun put(key1: Key1, key2: Key2, key3: Key3, value: Value?) {
        val key = TripleKey(key1, key2, key3)
        val resValue = map[key]
        if (resValue == null) {
            map[key] = value
        } else {
            map[key] = value
        }
    }

    /**
     * 获取数据
     */
    fun get(key1: Key1, key2: Key2, key3: Key3): Value? {
        return map[TripleKey(key1, key2, key3)]
    }

    /**
     * 查找key1的map
     */
    fun findKey1Map(key1: Key1): MutableMap<TripleKey<Key1, Key2, Key3>, Value?> {
        val resMap = mutableMapOf<TripleKey<Key1, Key2, Key3>, Value?>()
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
    fun findKey2Map(key2: Key2): MutableMap<TripleKey<Key1, Key2, Key3>, Value?> {
        val resMap = mutableMapOf<TripleKey<Key1, Key2, Key3>, Value?>()
        map.map {
            if (it.key.second == key2) {
                resMap[it.key] = it.value
            }
        }
        return resMap
    }

    /**
     * 查找key3的map
     */
    fun findKey3Map(key3: Key3): MutableMap<TripleKey<Key1, Key2, Key3>, Value?> {
        val resMap = mutableMapOf<TripleKey<Key1, Key2, Key3>, Value?>()
        map.map {
            if (it.key.third == key3) {
                resMap[it.key] = it.value
            }
        }
        return resMap
    }

    /**
     * 清除数据
     */
    fun clear() {
        map.clear()
    }


}

data class TripleKey<Key1, Key2, Key3>(
    var first: Key1,
    var second: Key2,
    var third: Key3
) {
    override fun toString(): String {
        return "$first,$second,$third"
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is TripleKey<*, *, *>) return false
        return other.first == first && other.second == second && other.third == third
    }

    override fun hashCode(): Int {
        return toString().hashCode()
    }
}

fun <Key1, Key2, Key3, Value> tripleKeyMapOf() = TripleKeyMap<Key1, Key2, Key3, Value>()