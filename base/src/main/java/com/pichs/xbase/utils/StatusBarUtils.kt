package com.pichs.xbase.utils

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi

object StatusBarUtils {

    /**
     * 沉浸式状态栏
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun immersiveStatusBar(activity: ComponentActivity) {
        transparentStatusBar(activity.window)
        transparentNavigationBar(activity.window)
    }

    fun setFullScreen(activity: ComponentActivity) {

    }


    /**
     * 透明状态栏
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun transparentStatusBar(window: Window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        var systemUiVisibility = window.decorView.systemUiVisibility
        systemUiVisibility =
            systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.decorView.systemUiVisibility = systemUiVisibility
        window.statusBarColor = Color.TRANSPARENT
    }

    /**
     * 透明导航栏
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun transparentNavigationBar(window: Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        var systemUiVisibility = window.decorView.systemUiVisibility
        systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        window.decorView.systemUiVisibility = systemUiVisibility
        window.navigationBarColor = Color.TRANSPARENT
    }

    /**
     * 黑色状态栏文字
     *  设置状态栏文字颜色
     *  @param isDark true 黑色 false 白色
     *
     */
    fun setStatusBarFontDark(window: Window, isDark: Boolean) {
        val decor = window.decorView
        if (isDark) {
            decor.systemUiVisibility =
                decor.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decor.systemUiVisibility =
                decor.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }
}