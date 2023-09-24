package com.pichs.xbase.binding

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * BindingFragment
 * 使用了ViewBinding的基类
 */
abstract class BindingFragment<ViewBinder : ViewBinding> : Fragment() {

    protected lateinit var binding: ViewBinder
    protected lateinit var mActivity: Activity
    protected lateinit var mAppCompatActivity: AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = requireActivity()
        if (mActivity is AppCompatActivity) {
            mAppCompatActivity = mActivity as AppCompatActivity
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        beforeOnCreateView(savedInstanceState)
        binding = ViewBindingUtil.inflateWithGeneric(this, inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        afterOnCreateView(view)
    }

     open fun beforeOnCreateView(savedInstanceState: Bundle?){

     }
    abstract fun afterOnCreateView(rootView: View?)

    fun <T : View> findViewById(@IdRes id: Int): T {
        return binding.root.findViewById(id)
    }

}