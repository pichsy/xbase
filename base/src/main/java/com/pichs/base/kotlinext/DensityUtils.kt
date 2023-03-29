package com.pichs.base.kotlinext

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.util.DisplayMetrics
import com.pichs.common.widget.utils.XDisplayHelper
import kotlin.math.roundToInt


val Int.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).roundToInt()

/**
 * dp 转 px
 */
val Float.dp2px
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    ).roundToInt()

/**
 * px 转 dp
 */
val Float.px2dp get() = this / Resources.getSystem().displayMetrics.density + 0.5f

/**
 * sp 转 px
 */
val Float.sp2px get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics)

fun getScreenWidth(context: Context): Int {
    val metric: DisplayMetrics = context.resources.displayMetrics
    return metric.widthPixels
}

fun getScreenHeight(context: Context): Int {
    val metric: DisplayMetrics = context.resources.displayMetrics
    return metric.heightPixels
}

fun getScreenRealWidth(context: Context): Int {
    return XDisplayHelper.getRealScreenSize(context)[0]
}

fun getScreenRealHeight(context: Context): Int {
    return XDisplayHelper.getRealScreenSize(context)[1]
}
