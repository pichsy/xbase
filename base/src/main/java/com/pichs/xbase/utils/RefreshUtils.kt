package com.pichs.xbase.utils

import android.graphics.Color
import android.view.Gravity
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.header.FalsifyFooter
import com.scwang.smart.refresh.header.FalsifyHeader
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * 刷新控件
 */
object RefreshUtils {

    init {
        // header
        ClassicsHeader.REFRESH_HEADER_PULLING = "下拉刷新"
        ClassicsHeader.REFRESH_HEADER_REFRESHING = "正在刷新"
        ClassicsHeader.REFRESH_HEADER_LOADING = "正在刷新"
        ClassicsHeader.REFRESH_HEADER_RELEASE = "松开刷新"
        ClassicsHeader.REFRESH_HEADER_FINISH = "刷新完成"
        ClassicsHeader.REFRESH_HEADER_FAILED = "刷新失败"
        ClassicsHeader.REFRESH_HEADER_SECONDARY = "进入二楼"
        ClassicsHeader.REFRESH_HEADER_UPDATE = "上次更新 M-d HH:mm"
        // footer
        ClassicsFooter.REFRESH_FOOTER_PULLING = "上拉加载"
        ClassicsFooter.REFRESH_FOOTER_RELEASE = "松开加载"
        ClassicsFooter.REFRESH_FOOTER_LOADING = "正在加载"
        ClassicsFooter.REFRESH_FOOTER_REFRESHING = "正在加载"
        ClassicsFooter.REFRESH_FOOTER_FINISH = ""
        ClassicsFooter.REFRESH_FOOTER_FAILED = ""
        ClassicsFooter.REFRESH_FOOTER_NOTHING = "---哎呀, 到底了---"
    }


    fun bounce(refresh: SmartRefreshLayout) {
        refresh.setEnablePureScrollMode(true)
        refresh.setEnableOverScrollDrag(true)
        val header = FalsifyHeader(refresh.context)
        refresh.setRefreshHeader(header)
        val footer = FalsifyFooter(refresh.context)
        refresh.setRefreshFooter(footer)
    }

    /**
     * 仅仅刷新头进行可回弹
     */
    fun bounceHeader(refresh: SmartRefreshLayout) {
        val header = FalsifyHeader(refresh.context)
        refresh.setRefreshHeader(header)
        refresh.setOnRefreshListener {
            refresh.finishRefresh()
        }
    }

    /**
     * 经典刷新
     */
    fun classic(refresh: SmartRefreshLayout, color: Int = Color.WHITE) {
        // header
        val classicsHeader = ClassicsHeader(refresh.context)
        classicsHeader.getChildAt(0)?.setBackgroundColor(Color.TRANSPARENT)
        classicsHeader.setBackgroundColor(Color.TRANSPARENT)
        classicsHeader.setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
        classicsHeader.setAccentColor(color)
        classicsHeader.setEnableLastTime(false)
        classicsHeader.setDrawableSize(20f)
        // footer
        val classicsFooter = ClassicsFooter(refresh.context)
        classicsFooter.setDrawableSize(20f)
        classicsFooter.setBackgroundColor(Color.TRANSPARENT)
        classicsFooter.setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
        classicsFooter.setBackgroundColor(Color.TRANSPARENT)
        classicsFooter.setAccentColor(color)
        refresh.setRefreshHeader(classicsHeader)
        refresh.setRefreshFooter(classicsFooter)
    }


    /**
     * google样式
     * material风格
     */
    fun google(refresh: SmartRefreshLayout) {
        // header
        val header = MaterialHeader(refresh.context)
        header.setColorSchemeColors(Color.BLUE)
        header.setProgressBackgroundColorSchemeColor(Color.WHITE)
        header.setShowBezierWave(false)
        header.setBackgroundColor(Color.TRANSPARENT)
        header.setScrollableWhenRefreshing(true)
        // footer
        val footer = ClassicsFooter(refresh.context)
        footer.setDrawableSize(20f)
        footer.setBackgroundColor(Color.TRANSPARENT)
        footer.setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
        footer.setBackgroundColor(Color.TRANSPARENT)
        // setting
        refresh.setRefreshHeader(header)
        refresh.setRefreshFooter(footer)
    }


    /**
     * 初始化Google样式，material风格
     */
    fun initGlobalGoogleStyle() {
        // 设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            // 全局设置主题颜色
            val header = MaterialHeader(context)
            header.setColorSchemeColors(Color.BLUE)
            header.setProgressBackgroundColorSchemeColor(Color.WHITE)
            header.setShowBezierWave(false)
            header.setBackgroundColor(Color.TRANSPARENT)
            header.setScrollableWhenRefreshing(true)
            header
        }
        // 设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            // 指定为经典Footer，默认是 BallPulseFooter
            val footer = ClassicsFooter(context)
            footer.setDrawableSize(20f)
            footer.setBackgroundColor(Color.TRANSPARENT)
            footer.setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
            footer.setBackgroundColor(Color.TRANSPARENT)
            footer
        }
    }

    /**
     * 初始化全局默认样式
     */
    @JvmStatic
    fun initGlobalClassicStyle() {
        // 设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            // 全局设置主题颜色
            val classicsHeader = ClassicsHeader(context)
            classicsHeader.setBackgroundColor(Color.TRANSPARENT)
            classicsHeader.setAccentColor(Color.parseColor("#FF946545"))
            classicsHeader.setDrawableSize(20f)
            classicsHeader
        }
        // 设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            // 指定为经典Footer，默认是 ClassicsFooter
            val classicsFooter = ClassicsFooter(context)
            classicsFooter.setDrawableSize(20f)
            classicsFooter.setBackgroundColor(Color.TRANSPARENT)
            classicsFooter.setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
            classicsFooter.setAccentColor(Color.parseColor("#FF946545"))
            classicsFooter
        }
    }

}

/**
 * 可回弹列表，不具有刷新功能
 * 顶部和底部都可以回弹
 */
fun SmartRefreshLayout.bounceHeaderFooter() {
    RefreshUtils.bounce(this)
}

/**
 * 仅仅刷新头进行可回弹
 */
fun SmartRefreshLayout.bounceHeader() {
    RefreshUtils.bounceHeader(this)
}

/**
 * 经典刷新样式
 */
fun SmartRefreshLayout.classicStyle() {
    RefreshUtils.classic(this)
}

/**
 * google刷新样式
 */
fun SmartRefreshLayout.googleStyle() {
    RefreshUtils.google(this)
}

