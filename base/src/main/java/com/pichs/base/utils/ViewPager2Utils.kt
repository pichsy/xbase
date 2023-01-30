package com.pichs.base.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

object ViewPager2Utils {

    fun neverOverScrollMode(viewPager2: ViewPager2) {
        val v = viewPager2.getChildAt(0)
        if (v is RecyclerView) {
            v.overScrollMode = View.OVER_SCROLL_NEVER
        }
    }

}