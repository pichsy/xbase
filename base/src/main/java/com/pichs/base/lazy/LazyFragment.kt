package com.pichs.base.lazy

import android.view.LayoutInflater
import android.app.Activity
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import com.pichs.base.lazy.BaseLazyFragment
import android.widget.FrameLayout
import com.pichs.base.lazy.LazyFragment

/**
 * <h1>懒加载Fragment</h1> 只有创建并显示的时候才会调用onCreateViewLazy方法<br></br>
 * <br></br>
 *
 *
 * 懒加载的原理onCreateView的时候Fragment有可能没有显示出来。<br></br>
 * 但是调用到setUserVisibleHint(boolean isVisibleToUser),isVisibleToUser =
 * true的时候就说明有显示出来<br></br>
 * 但是要考虑onCreateView和setUserVisibleHint的先后问题所以才有了下面的代码
 *
 *
 * 注意：<br></br>
 * 《1》原先的Fragment的回调方法名字后面要加个Lazy，比如Fragment的onCreateView方法， 就写成onCreateViewLazy <br></br>
 * 《2》使用该LazyFragment会导致多一层布局深度
 *
 *
 */
abstract class LazyFragment : BaseLazyFragment() {
    private var isInit = false
    private var savedInstanceState: Bundle? = null
    private var isLazyLoad = true
    private var layout: FrameLayout? = null

    @Deprecated("请使用 onCreateViewLazy")
    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)
        this.savedInstanceState = savedInstanceState
        val bundle = arguments
        if (bundle != null) {
            isLazyLoad = bundle.getBoolean(INTENT_BOOLEAN_LAZYLOAD, isLazyLoad)
        }
        //因为v4的25的版本 已经调用 setUserVisibleHint(true)，结果到这里getUserVisibleHint是false
        // （ps:看了FragmentManager源码Fragment被重新创建有直接赋值isVisibleToUser不知道是不是那里和之前v4有改动的地方）
        //所以我默认VISIBLE_STATE_NOTSET，之前没有调用setUserVisibleHint方法，就用系统的getUserVisibleHint，否则就用setUserVisibleHint后保存的值
        //总之就是调用了setUserVisibleHint 就使用setUserVisibleHint的值
        val isVisibleToUser: Boolean = if (isVisibleToUserState == VISIBLE_STATE_NOTSET) {
            userVisibleHint
        } else {
            isVisibleToUserState == VISIBLE_STATE_VISIABLE
        }
        if (isLazyLoad) {
            if (isVisibleToUser && !isInit) {
                isInit = true
                onCreateViewLazy(savedInstanceState)
            } else {
                var layoutInflater = inflater
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(mContext)
                }
                layout = FrameLayout(layoutInflater!!.context)
                val view = getPreviewLayout(layoutInflater, layout)
                if (view != null) {
                    layout!!.addView(view)
                }
                layout!!.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                super.setContentView(layout)
            }
        } else {
            isInit = true
            onCreateViewLazy(savedInstanceState)
        }
    }

    private var isVisibleToUserState = VISIBLE_STATE_NOTSET
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isVisibleToUserState = if (isVisibleToUser) VISIBLE_STATE_VISIABLE else VISIBLE_STATE_GONE
        if (isVisibleToUser && !isInit && getContentView() != null) {
            isInit = true
            onCreateViewLazy(savedInstanceState)
            onResumeLazy()
        }
        if (isInit && getContentView() != null) {
            if (isVisibleToUser) {
                isStart = true
                onFragmentStartLazy()
            } else {
                isStart = false
                onFragmentStopLazy()
            }
        }
    }

    /**
     * 重载此方法可以拦截并自定义 contentView
     */
    protected open fun getPreviewLayout(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return null
    }

    @Deprecated("请使用 onFragmentStartLazy")
    override fun onStart() {
        super.onStart()
        if (isInit && !isStart && userVisibleHint) {
            isStart = true
            onFragmentStartLazy()
        }
    }

    @Deprecated("请使用 onFragmentStopLazy")
    override fun onStop() {
        super.onStop()
        if (isInit && isStart && userVisibleHint) {
            isStart = false
            onFragmentStopLazy()
        }
    }

    private var isStart = false
    protected abstract fun onCreateViewLazy(savedInstanceState: Bundle?)

    protected fun onFragmentStartLazy() {}
    protected fun onFragmentStopLazy() {}
    protected fun onResumeLazy() {}
    protected fun onPauseLazy() {}
    protected fun onDestroyViewLazy() {}

    override fun setContentView(layoutResID: Int) {
        if (isLazyLoad && getContentView() != null && getContentView()?.parent != null) {
            layout!!.removeAllViews()
            val view = inflater!!.inflate(layoutResID, layout, false)
            layout!!.addView(view)
        } else {
            super.setContentView(layoutResID)
        }
    }

    override fun setContentView(view: View?) {
        if (isLazyLoad && getContentView() != null && getContentView()?.parent != null) {
            layout!!.removeAllViews()
            layout!!.addView(view)
        } else {
            super.setContentView(view)
        }
    }

    @Deprecated("请使用 onResumeLazy")
    override fun onResume() {
        super.onResume()
        if (isInit) {
            onResumeLazy()
        }
    }

    @Deprecated("请使用 onPauseLazy")
    override fun onPause() {
        super.onPause()
        if (isInit) {
            onPauseLazy()
        }
    }

    @Deprecated("请使用 onDestroyViewLazy")
    override fun onDestroyView() {
        super.onDestroyView()
        if (isInit) {
            onDestroyViewLazy()
        }
        isInit = false
    }

    companion object {
        const val INTENT_BOOLEAN_LAZYLOAD = "intent_boolean_lazyLoad"
        //未设置值
        private const val VISIBLE_STATE_NOTSET = -1
        //可见
        private const val VISIBLE_STATE_VISIABLE = 1
        //不可见
        private const val VISIBLE_STATE_GONE = 0
    }
}