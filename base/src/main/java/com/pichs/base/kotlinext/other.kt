package com.pichs.base.kotlinext

import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
fun <T> getClazz(obj: Any): T {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as T
}