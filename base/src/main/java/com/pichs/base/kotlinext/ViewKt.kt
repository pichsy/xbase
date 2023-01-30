package com.pichs.base.kotlinext

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.core.view.iterator


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

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}