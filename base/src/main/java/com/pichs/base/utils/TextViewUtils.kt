package com.pichs.base.utils

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import com.pichs.common.widget.textview.XTextSizeSpan
import com.pichs.common.widget.textview.XTouchableSpan
import com.pichs.common.widget.utils.XDisplayHelper

object TextViewUtils {

    fun setSizeSpan(
        tv: TextView,
        text: String,
        startIndex: Int,
        endIndex: Int,
        spanTextSizeDp: Int
    ) {
        val sizeSpan =
            XTextSizeSpan(XDisplayHelper.dp2px(tv.context, spanTextSizeDp), 0, tv.typeface)
        val spanString = SpannableStringBuilder(text)
        spanString.setSpan(
            sizeSpan,
            startIndex,
            endIndex,
            SpannableStringBuilder.SPAN_INCLUSIVE_EXCLUSIVE
        )
        tv.text = spanString
    }

    fun setColorSpan(
        tv: TextView,
        text: String,
        startIndex: Int,
        endIndex: Int,
        @ColorInt spanColor: Int
    ) {
        val touchSpan = object :
            XTouchableSpan(false, spanColor, spanColor, Color.TRANSPARENT, Color.TRANSPARENT) {
            override fun onSpanClick(p0: View?) {
            }
        }
        val spanString = SpannableStringBuilder(text)
        spanString.setSpan(
            touchSpan,
            startIndex,
            endIndex,
            SpannableStringBuilder.SPAN_INCLUSIVE_EXCLUSIVE
        )
        tv.text = spanString
    }

}