package com.pichs.base.lazy

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

abstract class BaseLazyFragment : Fragment() {
    private var contentView: View? = null
    protected var mContext: Context? = null
    protected var mActivity: Activity? = null
    protected var container: ViewGroup? = null
    protected lateinit var inflater:LayoutInflater
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.inflater = inflater
        this.container = container
        onCreateView(savedInstanceState)
        return if (contentView == null) {
            super.onCreateView(inflater, container, savedInstanceState)
        } else contentView
    }

    protected open fun onCreateView(savedInstanceState: Bundle?) {}
    override fun onDestroyView() {
        super.onDestroyView()
        contentView = null
        container = null
    }

    open fun setContentView(layoutResID: Int) {
        if (inflater != null) {
           setContentView(inflater!!.inflate(layoutResID, container, false) as ViewGroup)
        }
    }

    open fun setContentView(view: View?) {
        this.contentView = view
    }

    fun getContentView(): View? {
        return contentView
    }


}