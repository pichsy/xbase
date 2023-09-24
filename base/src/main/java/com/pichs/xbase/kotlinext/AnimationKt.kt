package com.pichs.xbase.kotlinext

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.ViewPropertyAnimator
import android.view.animation.Animation

/**
 * 爽就完了，inline函数 性能高效
 * Animation的监听器扩展函数，监听更加方便
 * ！！！但是注意，>>>>不支持--->挨个设置 doOnEnd，doOnStart，doOnRepeat
 * 因为[Animation]类中没有 addListener，只有 setListener,如果需要监听多个
 * 直接使用 [setListener] 即可。
 */
public inline fun Animation.doOnEnd(crossinline run: (animation: Animation?) -> Unit) {
    setListener(onEnd = run)
}

public inline fun Animation.doOnStart(crossinline run: (animation: Animation?) -> Unit) {
    setListener(onStart = run)
}

public inline fun Animation.doOnRepeat(crossinline run: (animation: Animation?) -> Unit) {
    setListener(onRepeat = run)
}

/**
 * 开启动画监听
 */
public inline fun Animation.setListener(
    crossinline onStart: (animation: Animation?) -> Unit = {},
    crossinline onRepeat: (animation: Animation?) -> Unit = {},
    crossinline onEnd: (animation: Animation?) -> Unit = {},
): Animation.AnimationListener {
    val listener = object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) {
            onStart(animation)
        }

        override fun onAnimationEnd(animation: Animation?) {
            onEnd(animation)
        }

        override fun onAnimationRepeat(animation: Animation?) {
            onRepeat(animation)
        }
    }
    setAnimationListener(listener)
    return listener
}


/**
 * 和doOnStart只能用一个
 */
public inline fun ViewPropertyAnimator.doOnEnd(
    crossinline onEnd: (animator: Animator) -> Unit
): ViewPropertyAnimator {
    setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animator: Animator) = onEnd(animator)
    })
    return this
}

/**
 * 和doOnEnd只能用一个
 */
public inline fun ViewPropertyAnimator.doOnStart(
    crossinline onEnd: (animator: Animator) -> Unit
): ViewPropertyAnimator {
    setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animator: Animator) = onEnd(animator)
    })
    return this
}

