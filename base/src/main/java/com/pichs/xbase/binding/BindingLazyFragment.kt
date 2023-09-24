package com.pichs.xbase.binding

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.viewbinding.ViewBinding
import com.pichs.xbase.lazy.LazyFragment

/**
 * BindingFragment
 * 使用了ViewBinding的基类
 */
abstract class BindingLazyFragment<ViewBinder : ViewBinding> : LazyFragment() {

    protected lateinit var binding: ViewBinder

    open fun beforeOnCreateView(savedInstanceState: Bundle?) {

    }

    abstract fun afterOnCreateView(rootView: View)

    override fun onCreateViewLazy(savedInstanceState: Bundle?) {
        beforeOnCreateView(savedInstanceState)
        binding = ViewBindingUtil.inflateWithGeneric(this, inflater, container, false)
        setContentView(binding.root)
        afterOnCreateView(binding.root)
    }

    fun <T : View> findViewById(@IdRes id: Int): T {
        return binding.root.findViewById(id)
    }

}