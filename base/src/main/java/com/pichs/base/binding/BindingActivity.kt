package com.pichs.base.binding

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.pichs.base.stack.IStackChild
import com.pichs.base.stack.StackManager

/**
 * 基于ViewBinding 实现的基类
 * 使用了ViewBinding的基类
 */
abstract class BindingActivity<ViewBinder : ViewBinding> : AppCompatActivity(), IStackChild {

    lateinit var binding: ViewBinder
    protected lateinit var mActivity: AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onAddActivity()
        mActivity = this
        beforeOnCreate(savedInstanceState)
        binding = ViewBindingUtil.inflateWithGeneric(this, LayoutInflater.from(this))
        setContentView(binding.root)
        afterOnCreate()
    }

    abstract fun beforeOnCreate(savedInstanceState: Bundle?)
    abstract fun afterOnCreate()

    override fun onAddActivity() {
        StackManager.get().addActivity(this)
    }

    override fun onRemoveActivity() {
        StackManager.get().removeActivity(this)
    }
}