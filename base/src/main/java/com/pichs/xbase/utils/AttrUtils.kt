package com.pichs.xbase.utils

import android.util.AttributeSet

object AttrUtils {

    const val ANDROID_NAMESPACE = "http://schemas.android.com/apk/res/android"

    fun getAndroidAttr(attrs: AttributeSet, attrName: String?): String {
        return attrs.getAttributeValue(ANDROID_NAMESPACE, attrName)
    }

}