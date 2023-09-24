package com.pichs.xbase.binding

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.pichs.xbase.stack.IStackChild
import com.pichs.xbase.stack.StackManager

abstract class AbstractBaseActivity : AppCompatActivity(), IStackChild {

    protected var ismResumed = false
    protected var ismStopped = false

    lateinit var mActivity: AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this
        beforeOnCreate(savedInstanceState)
        val rootView = getContentView()
        if (rootView != null) {
            setContentView(rootView)
        }
        onCreate()
        afterOnCreate()
    }

    abstract fun getContentView(): View?

    open fun onCreate() {

    }

    abstract fun beforeOnCreate(savedInstanceState: Bundle?)

    abstract fun afterOnCreate()

    override fun onAddActivity() {
        StackManager.get().addActivity(this)
    }

    override fun onRemoveActivity() {
        StackManager.get().removeActivity(this)
    }

    open fun keepScreenOn() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    open fun removeKeepScreenOn() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onDestroy() {
        super.onDestroy()
        onRemoveActivity()
    }

    override fun onResume() {
        super.onResume()
        ismResumed = true
    }

    override fun onPause() {
        super.onPause()
        ismResumed = false
    }

    override fun onStop() {
        super.onStop()
        ismStopped = true
    }

    override fun onStart() {
        super.onStart()
        ismStopped = false
    }

    override fun isResume(): Boolean {
        return ismResumed
    }

    override fun isStop(): Boolean {
        return ismStopped
    }


}