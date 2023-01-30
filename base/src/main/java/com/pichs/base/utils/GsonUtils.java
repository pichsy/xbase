package com.pichs.base.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

/**
 * @author pichs
 */
public class GsonUtils {

    private static final Gson mGson = new Gson();

    // 普通json
    public static <T> T fromJson(String json, Class<T> tClass) {
        return mGson.fromJson(json, tClass);
    }

    // 特殊的json
    public static <T> T fromJson(String json, TypeToken<T> typeToken) {
        return fromJson(json, typeToken.getType());
    }

    // 特殊的json
    public static <T> T fromJson(String json, Type typeOfT) {
        return mGson.fromJson(json, typeOfT);
    }

    // 转换为json字符串
    public static String toJson(Object obj) {
        return mGson.toJson(obj);
    }

}