package com.pichs.xbase.kotlinext

import android.graphics.Paint
import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat

/**
 * TextView 显示下划线
 */
fun TextView.showUnderLine() {
    this.paintFlags = this.paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

/**
 * TextView  隐藏下划线
 */
fun TextView.hideUnderLine() {
    this.paintFlags = this.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
}

/**
 * TextView 显示删除线
 */
fun TextView.showDeleteLine() {
    this.paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}

/**
 * TextView 隐藏删除线
 */
fun TextView.hideDeleteLine() {
    this.paintFlags = this.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
}


/**
 * TextView 设置颜色
 */
fun TextView.setTextColorResource(@ColorRes int: Int) {
    this.setTextColor(ContextCompat.getColor(context, int))
}

fun TextView.setTextSizeDimens(@DimenRes resId: Int) {
    this.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(resId))
}

