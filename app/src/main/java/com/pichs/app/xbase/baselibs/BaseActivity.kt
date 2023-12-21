package com.pichs.app.xbase.baselibs

import androidx.viewbinding.ViewBinding
import com.pichs.xbase.binding.BindingActivity

abstract class BaseActivity<ViewBinder:ViewBinding> : BindingActivity<ViewBinder>() {


    override fun onCreate() {
        super.onCreate()
        // 处理公共数据


    }



}