package com.pichs.base.utils

import android.util.AttributeSet
import com.pichs.base.utils.AttrUtils

object AttrUtils {

    const val ANDROID_NAMESPACE = "http://schemas.android.com/apk/res/android"

    fun getAndroidAttr(attrs: AttributeSet, attrName: String?): String {
        return attrs.getAttributeValue(ANDROID_NAMESPACE, attrName)
    }

}