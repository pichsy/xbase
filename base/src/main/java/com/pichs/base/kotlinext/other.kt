package com.pichs.base.kotlinext

import java.lang.ClassCastException
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

/**
 * 获取当前对象的第一个泛型 Class<T> 对象
 */
@Suppress("UNCHECKED_CAST")
fun <T> getFirstGenericClazz(obj: Any): Class<T> {
    (obj.javaClass.genericSuperclass is ParameterizedType).isTRUE {
        (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.forEach {
            try {
                return@getFirstGenericClazz (it as Class<T>)
            } catch (e: NoSuchMethodException) {
                throw e
            } catch (e: ClassCastException) {
                throw e
            } catch (e: InvocationTargetException) {
                throw e.targetException
            }
        }
    }
    throw NullPointerException("Cannot find genericSuperclass, please check your code is not wrong")
}
