package com.pichs.base.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * 清单文件中的 meta 数据获取
 */
public class MetaUtils {

    public static String getMetaDataString(Context context, String metaDataName, String... defaultValue) {
        String value = "";
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            value = (defaultValue != null && defaultValue.length > 0) ? defaultValue[0] : value;
            value = appInfo.metaData.getString(metaDataName, value);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static int getMetaDataInt(Context context, String metaDataName, int... defaultValue) {
        int value = 0;
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            value = (defaultValue != null && defaultValue.length > 0) ? defaultValue[0] : value;
            value = appInfo.metaData.getInt(metaDataName, value);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static boolean getMetaDataBoolean(Context context, String metaDataName, boolean... defaultValue) {
        boolean value = false;
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            value = (defaultValue != null && defaultValue.length > 0) ? defaultValue[0] : value;
            value = appInfo.metaData.getBoolean(metaDataName, value);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static Object getMetaData(Context context, String metaDataName) throws Exception {
        ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                context.getPackageName(), PackageManager.GET_META_DATA);
        return appInfo.metaData.get(metaDataName);
    }

    public static float getMetaDataFloat(Context context, String metaDataName, float... defaultValue) {
        float value = 0.0f;
        try {
            value = (float) getMetaData(context, metaDataName);
        } catch (Exception e) {
            value = (defaultValue != null && defaultValue.length > 0) ? defaultValue[0] : value;
        }
        return value;
    }

    @SafeVarargs
    public static <T> T getMetaData(Context context, String metaDataName, T... defaultValue) {
        T value = null;
        try {
            value = (T) getMetaData(context, metaDataName);
        } catch (Exception e) {
            value = (defaultValue != null && defaultValue.length > 0) ? defaultValue[0] : value;
        }
        return value;
    }

}
