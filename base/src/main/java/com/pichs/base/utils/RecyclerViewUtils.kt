package com.pichs.base.utils

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

object RecyclerViewUtils {


    /**
     * 出去默认条目动画
     */
    fun removeDefaultItemAnimator(recyclerView: RecyclerView?) {
        recyclerView?.let {
            if (it.itemAnimator is DefaultItemAnimator) {
                (it.itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
            }
        }
    }


    /**
     * 移除全部的ItemDecoration
     */
    fun removeItemDecoration(recyclerView: RecyclerView?) {
        recyclerView?.let {
            if (it.itemDecorationCount == 0) {
                for (i in 0..it.itemDecorationCount) {
                    recyclerView.removeItemDecoration(recyclerView.getItemDecorationAt(i))
                }
            }
        }
    }

}