package com.pichs.xbase.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.pichs.xbase.stack.IStackChild

/**
 * 基于ViewBinding 实现的基类
 * 使用了ViewBinding的基类
 */
abstract class BindingActivity<ViewBinder : ViewBinding> : AbstractBaseActivity(), IStackChild {

    protected lateinit var binding: ViewBinder

    override fun getContentView(): View? {
        binding = ViewBindingUtil.inflateWithGeneric(this, LayoutInflater.from(this))
        return binding.root
    }

    override fun onCreate() {

    }

    override fun beforeOnCreate(savedInstanceState: Bundle?) {

    }

}