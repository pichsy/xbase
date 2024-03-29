package com.pichs.xbase.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Gson工具类
 *
 *  请将所有属性都进行添加注解。否则你会后悔的。
 *  支持忽略注解 Expose，所有未添加Expose注解的字段都不会被序列化和反序列化，
    该对象只是个demo，请根据自己的需求进行修改修改。
    public val gsonExcludeExpose by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }
 */
object GsonUtils {

    // 支持注解过滤功能。
    private val mGson = Gson()


    /**
     * 普通json, 传入不带泛型的 class 对象
     */
    fun <T> fromJson(json: String, tClass: Class<T>): T {
        return mGson.fromJson(json, tClass)
    }

    /**
     * 特殊的json
     * 比如你的类型中带泛型，或者多重泛型嵌套的，就需要用到TypeToken
     */
    fun <T> fromJson(json: String, typeToken: TypeToken<T>): T {
        return fromJson(json, typeToken.type)
    }

    /**
     * 特殊的json  TypeToken.getType()获取
     */
    fun <T> fromJson(json: String, typeOfT: Type): T {
        return mGson.fromJson(json, typeOfT)
    }

    /**
     *  对象转换为json字符串结构
     */
    fun toJson(obj: Any): String {
        return mGson.toJson(obj)
    }
}