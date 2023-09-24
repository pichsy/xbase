package com.pichs.xbase.utils

import android.R
import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.WindowManager
import android.widget.FrameLayout
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.os.Build
import android.util.Log
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.pichs.common.widget.utils.XStatusBarHelper

object KeyBoardUtils {
    private const val TAG_ON_GLOBAL_LAYOUT_LISTENER = -8

    /**
     * Show the soft input. 此方法慎用，可能会失效
     * 建议使用 [KeyBoardUtils.showSoftInput] )}
     *
     */
    @Deprecated("")
    fun showSoftInput(activity: Activity) {
        if (!isSoftInputVisible(activity)) {
            toggleSoftInput(activity)
        }
    }

    /**
     * Show the soft input.
     * View 为正在得到焦点的View
     *
     * @param view The view.
     */
    fun showSoftInput(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm != null) {
            view.requestFocus()
            view.post { imm.showSoftInput(view, 0) }
        }
    }

    /**
     * Hide the soft input.
     *
     * @param activity The activity.
     */
    fun hideSoftInput(activity: Activity) {
        val view = activity.currentFocus
        if (view != null) {
            hideSoftInput(view)
        } else {
            if (isSoftInputVisible(activity)) {
                toggleSoftInput(activity)
            }
        }
    }

    /**
     * Hide the soft input.
     * View 为正在得到焦点的View
     *
     * @param view The view.
     */
    fun hideSoftInput(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * 切换 软键盘，（不显示则显示，显示则隐藏）
     *
     * @param view View
     */
    fun toggleSoftInput(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 切换 软键盘，（不显示则显示，显示则隐藏）
     */
    fun toggleSoftInput(activity: Activity) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    private var sDecorViewDelta = 0

    /**
     * Return whether soft input is visible.
     *
     * @param activity The activity.
     * @return `true`: yes<br></br>`false`: no
     */
    fun isSoftInputVisible(activity: Activity): Boolean {
        return getDecorViewInvisibleHeight(activity.window) > 0
    }

    //获取导航栏高度
    fun getNavigationBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }

    private fun getDecorViewInvisibleHeight(window: Window): Int {
        val decorView = window.decorView
        val outRect = Rect()
        decorView.getWindowVisibleDisplayFrame(outRect)
        Log.d(
            "KeyboardUtils", "getDecorViewInvisibleHeight: "
                    + (decorView.bottom - outRect.bottom)
        )
        val delta = Math.abs(decorView.bottom - outRect.bottom)
        if (delta <= XStatusBarHelper.getStatusBarHeight(window.context) + getNavigationBarHeight(window.context)) {
            sDecorViewDelta = delta
            return 0
        }
        return delta - sDecorViewDelta
    }

    /**
     * Register soft input changed listener.
     *
     * @param activity The activity.
     * @param listener The soft input changed listener.
     */
    fun registerSoftInputChangedListener(
        activity: Activity,
        listener: OnSoftInputChangedListener
    ) {
        registerSoftInputChangedListener(activity.window, listener)
    }

    /**
     * Register soft input changed listener.
     *
     * @param window   The window.
     * @param listener The soft input changed listener.
     */
    fun registerSoftInputChangedListener(
        window: Window,
        listener: OnSoftInputChangedListener
    ) {
        val flags = window.attributes.flags
        if (flags and WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS != 0) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        val contentView = window.findViewById<FrameLayout>(R.id.content)
        val decorViewInvisibleHeightPre = intArrayOf(getDecorViewInvisibleHeight(window))
        val onGlobalLayoutListener = OnGlobalLayoutListener {
            val height = getDecorViewInvisibleHeight(window)
            if (decorViewInvisibleHeightPre[0] != height) {
                listener.onSoftInputChanged(height)
                decorViewInvisibleHeightPre[0] = height
            }
        }
        contentView.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
        contentView.setTag(TAG_ON_GLOBAL_LAYOUT_LISTENER, onGlobalLayoutListener)
    }

    /**
     * Unregister soft input changed listener.
     *
     * @param window The window.
     */
    fun unregisterSoftInputChangedListener(window: Window) {
        val contentView = window.findViewById<FrameLayout>(R.id.content)
        val tag = contentView.getTag(TAG_ON_GLOBAL_LAYOUT_LISTENER)
        if (tag is OnGlobalLayoutListener) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                contentView.viewTreeObserver.removeOnGlobalLayoutListener(tag)
            }
        }
    }

    /**
     * Fix the bug of 5497 in Android.
     *
     * Don't set adjustResize
     *
     * @param activity The activity.
     */
    fun fixAndroidBug5497(activity: Activity) {
        fixAndroidBug5497(activity.window)
    }

    /**
     * Fix the bug of 5497 in Android.
     *
     * Don't set adjustResize
     *
     * @param window The window.
     */
    fun fixAndroidBug5497(window: Window) {
//        int softInputMode = window.getAttributes().softInputMode;
//        window.setSoftInputMode(softInputMode & ~WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        val contentView = window.findViewById<FrameLayout>(R.id.content)
        val contentViewChild = contentView.getChildAt(0)
        val paddingBottom = contentViewChild.paddingBottom
        val contentViewInvisibleHeightPre5497 = intArrayOf(getContentViewInvisibleHeight(window))
        contentView.viewTreeObserver
            .addOnGlobalLayoutListener {
                val height = getContentViewInvisibleHeight(window)
                if (contentViewInvisibleHeightPre5497[0] != height) {
                    contentViewChild.setPadding(
                        contentViewChild.paddingLeft,
                        contentViewChild.paddingTop,
                        contentViewChild.paddingRight,
                        paddingBottom + getDecorViewInvisibleHeight(window)
                    )
                    contentViewInvisibleHeightPre5497[0] = height
                }
            }
    }

    private fun getContentViewInvisibleHeight(window: Window): Int {
        val contentView = window.findViewById<View>(R.id.content) ?: return 0
        val outRect = Rect()
        contentView.getWindowVisibleDisplayFrame(outRect)
        Log.d(
            "KeyboardUtils", "getContentViewInvisibleHeight: "
                    + (contentView.bottom - outRect.bottom)
        )
        val delta = Math.abs(contentView.bottom - outRect.bottom)
        return if (delta <= XStatusBarHelper.getStatusBarHeight(window.context) + getNavigationBarHeight(window.context)) {
            0
        } else delta
    }

    /**
     * 显示软件盘，推荐
     *
     * @param view EditText
     */
    fun showKeyboard(view: EditText) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm != null) {
            view.requestFocus()
            view.post { imm.showSoftInput(view, 0) }
        }
    }

    /**
     * 显示软件盘备用 ，没有EditText时可用此法
     *
     * @param context Context
     * @param view    View
     */
    fun showKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm != null) {
            view.requestFocus()
            view.post { imm.showSoftInput(view, 0) }
        }
    }

    /**
     * 隐藏键盘，推荐
     *
     * @param view EditText
     */
    fun hideKeyboard(view: EditText) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * 隐藏键盘，备用方式，没有EditText时可用此法
     *
     * @param context Context
     * @param view    View
     */
    fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    ///////////////////////////////////////////////////////////////////////////
    //   interface
    ///////////////////////////////////////////////////////////////////////////
    interface OnSoftInputChangedListener {
        fun onSoftInputChanged(height: Int)
    }
}