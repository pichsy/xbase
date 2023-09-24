package com.pichs.xbase.kotlinext


fun <T> T?.orDefault(default: T): T {
    return this ?: default
}

fun Any?.isNull() = this == null

fun Int?.orZero(): Int = this ?: 0

fun Long?.orZero(): Long = this ?: 0

fun Float?.orZero(): Float = this ?: 0F

val <T> Collection<T>?.sizeOrZero: Int
    get() = this?.size ?: 0


fun IntArray.getOrDefault(index: Int, defValue: Int): Int {
    if (isEmpty()) {
        return defValue
    }
    return if (index in 0..lastIndex) get(index) else defValue
}

fun IntArray.setOnSafe(index: Int, result: Int) {
    if (isEmpty()) {
        return
    }
    if (index in 0..lastIndex) {
        this[index] = result
    }
}

fun <T> List<T>.getOrDefault(index: Int, defValue: T): T {
    if (isEmpty()) {
        return defValue
    }
    return if (index in 0..lastIndex) get(index) else defValue
}