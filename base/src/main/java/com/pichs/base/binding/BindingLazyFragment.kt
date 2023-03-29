package com.pichs.base.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.IdRes
import androidx.viewbinding.ViewBinding
import com.pichs.base.lazy.LazyFragment

/**
 * BindingFragment
 * 使用了ViewBinding的基类
 */
abstract class BindingLazyFragment<ViewBinder : ViewBinding> : LazyFragment() {

    lateinit var binding: ViewBinder

    abstract fun beforeOnCreateView(savedInstanceState: Bundle?)

    abstract fun afterOnCreateView(rootView: View)

    override fun onCreateViewLazy(savedInstanceState: Bundle?) {
        beforeOnCreateView(savedInstanceState)
        binding = ViewBindingUtil.inflateWithGeneric(this, inflater, container, false)
        setContentView(binding.root)
        onCreate()
        afterOnCreateView(binding.root)
    }


    /**
     * 在 beforeOnCreateView 之后调用 ，afterOnCreateView 之前调用
     * 给自己的base封装用。
     */
    open fun onCreate() {
    }


    fun <T : View> findViewById(@IdRes id: Int): T {
        return binding.root.findViewById(id)
    }

}