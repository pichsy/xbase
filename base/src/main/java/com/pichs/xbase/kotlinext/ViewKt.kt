package com.pichs.xbase.kotlinext

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.core.view.iterator
import androidx.core.view.updateLayoutParams
import com.pichs.xbase.clickhelper.FastClickHelper
import com.pichs.xbase.clickhelper.GlobalFastClickHelper

/**
 * 扩展一下，调用方便
 */
inline fun View.delay(timeMills: Long, crossinline block: () -> Unit) {
    postDelayed({ block() }, timeMills)
}

fun ViewGroup.removeAllExclude(view: View?) {
    if (view == null) {
        removeAllViews()
        return
    }
    if (childCount == 0) {
        return
    }
    val iterator = this.iterator()
    while (iterator.hasNext()) {
        val v = iterator.next()
        if (v != view) {
            iterator.remove()
        }
    }
}

fun <T : View> ViewGroup.removeAllExclude(clz: Class<T>?) {
    if (clz == null) {
        removeAllViews()
        return
    }
    if (childCount == 0) {
        return
    }
    val iterator = this.iterator()
    while (iterator.hasNext()) {
        val v = iterator.next()
        if (v.javaClass != clz.javaClass) {
            iterator.remove()
        }
    }
}

fun ViewGroup.hideAllExclude(view: View?) {
    if (view == null) {
        removeAllViews()
        return
    }
    if (childCount == 0) {
        return
    }
    val iterator = this.iterator()
    while (iterator.hasNext()) {
        val v = iterator.next()
        if (v != view) {
            v.visibility = View.INVISIBLE
        }
    }
}

/**
 * 获取View在屏幕中的位置
 */
fun View.getViewRectOnScreen(): Rect {
    val rect = Rect()
    val loc = IntArray(2)
    this.getLocationOnScreen(loc)
    rect.left = loc[0]
    rect.top = loc[1]
    rect.right = rect.left + this.measuredWidth
    rect.bottom = rect.top + this.measuredHeight
    return rect
}

fun View?.removeSelf() {
    this ?: return
    val parent = parent as? ViewGroup ?: return
    parent.removeView(this)
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.toggleVisibility(): View {
    visibility = if (visibility == View.VISIBLE) {
        View.GONE
    } else {
        View.VISIBLE
    }
    return this
}


/**
 *
 * @receiver View
 * @param resIdHeight Int?
 * @param resIdWith Int?
 */
inline fun <reified T : ViewGroup.LayoutParams> View.setViewWidthHeightDimenRes(
    @DimenRes resIdWith: Int? = null,
    @DimenRes resIdHeight: Int? = null
) {
    this.updateLayoutParams<T> {
        resIdHeight?.let {
            height = resources.getDimension(resIdHeight).toInt()
        }
        resIdWith?.let {
            width = resources.getDimension(resIdWith).toInt()
        }
    }
}

/**
 *
 * @receiver View
 * @param newWith Int? 新的宽度
 * @param newHeight Int? 新的高度
 */
inline fun <reified T : ViewGroup.LayoutParams> View.setViewWidthHeight(
    newWith: Int? = null,
    newHeight: Int? = null
) {
    this.updateLayoutParams<T> {
        newWith?.let {
            height = it
        }
        newHeight?.let {
            width = it
        }
    }
}

/**
 * 单按钮快速点击防重
 */
fun View.fastClick(l: View.OnClickListener) {
    FastClickHelper.clicks(this, listener = l)
}

/**
 * 全局快速点击限制防重
 */
fun View.globalFastClick(l: View.OnClickListener) {
    GlobalFastClickHelper.clicks(this, l)
}
