package com.pichs.xbase.kotlinext

import android.content.res.Resources
import android.util.TypedValue
import androidx.core.content.ContextCompat
import com.pichs.xbase.utils.UiKit
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


fun Int.dimen2px(): Float {
    return try {
        UiKit.getApplication().resources?.getDimension(this) ?: 0f
    } catch (e: Exception) {
        0f
    }
}

fun Int.dimen2pxInt(): Int {
    return try {
        UiKit.getApplication().resources?.getDimensionPixelSize(this) ?: 0
    } catch (e: Exception) {
        0
    }
}

fun Int.res2Color(): Int {
    return try {
        UiKit.getApplication()?.let {
            ContextCompat.getColor(it, this)
        } ?: 0
    } catch (e: Exception) {
        0
    }
}
