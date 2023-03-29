package com.pichs.base.kotlinext

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.annotation.DimenRes
import androidx.core.view.iterator
import androidx.core.view.updateLayoutParams
import java.util.*

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
inline fun <reified T : ViewGroup.LayoutParams> View.setViewSize(
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
 * View 开始晃动动画
 */
fun View?.startShake() {
    this ?: return
    val animDuration = 100
    val rotateArray = floatArrayOf(1.5f, -1.5f, 1.8f, -1.8f, 2.0f, -2.0f)
    val rotate = rotateArray[Random().nextInt(rotateArray.size)]

    val rotate1 = RotateAnimation(rotate, -rotate, this.width / 2F, this.height / 2F)
    val rotate2 = RotateAnimation(-rotate, rotate, this.width / 2F, this.height / 2F)
    rotate1.duration = animDuration.toLong()
    rotate2.duration = animDuration.toLong()

    // 设置旋转动画的监听
    rotate1.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationEnd(animation: Animation) {
            rotate1.reset() // 重置动画
            startAnimation(rotate2) // 第一个动画结束开始第二个旋转动画
        }

        override fun onAnimationRepeat(animation: Animation) {}
        override fun onAnimationStart(animation: Animation) {}
    })
    rotate2.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationEnd(animation: Animation) {
            rotate2.reset()
            startAnimation(rotate1) // 第二个动画结束开始第一个旋转动画
        }

        override fun onAnimationRepeat(animation: Animation) {}
        override fun onAnimationStart(animation: Animation) {}
    })
    startAnimation(rotate1)
}

/**
 * View 停止晃动动画
 */
fun View?.stopShake() {
    this ?: return
    clearAnimation()
}

fun View.onThrottledClick(
    throttleDelay: Long = 500L,
    onClick: (View) -> Unit
) {
    setOnClickListener {
        onClick(this)
        isClickable = false
        postDelayed({ isClickable = true }, throttleDelay)
    }
}