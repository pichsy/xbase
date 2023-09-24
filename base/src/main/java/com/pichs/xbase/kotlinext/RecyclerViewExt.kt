package com.pichs.xbase.kotlinext

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager

fun RecyclerView.itemAnimator(itemAnimator: ItemAnimator): RecyclerView {
    setItemAnimator(itemAnimator)
    return this
}

fun RecyclerView.setItemAnimatorDisable(): RecyclerView {
    if (itemAnimator is DefaultItemAnimator) {
        (itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
    }
    return this
}

fun RecyclerView.removeDefaultItemAnimator(): RecyclerView {
    itemAnimator = null
    return this
}

fun RecyclerView.removeItemDecoration(): RecyclerView {
    if (itemDecorationCount == 0) {
        for (i in 0..itemDecorationCount) {
            removeItemDecoration(getItemDecorationAt(i))
        }
    }
    return this
}


/**
 * 创建[LinearLayoutManager]  线性列表
 * @param orientation 列表方向
 * @param reverseLayout 是否反转列表
 * @param scrollEnabled 是否允许滚动
 */
fun RecyclerView.linear(
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
    reverseLayout: Boolean = false,
): RecyclerView {
    return apply {
        layoutManager = LinearLayoutManager(context, orientation, reverseLayout)
    }
}


/**
 * 创建[GridLayoutManager] 网格列表
 * @param spanCount 网格跨度数量
 * @param orientation 列表方向
 * @param reverseLayout 是否反转
 * @param scrollEnabled 是否允许滚动
 */
fun RecyclerView.grid(
    spanCount: Int = 1,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
    reverseLayout: Boolean = false,
): RecyclerView {
    return apply {
        layoutManager = GridLayoutManager(context, spanCount, orientation, reverseLayout)
    }
}

/**
 * 创建[StaggeredGridLayoutManager] 交错列表
 * @param spanCount 网格跨度数量
 * @param orientation 列表方向
 * @param reverseLayout 是否反转
 * @param scrollEnabled 是否允许滚动
 */
fun RecyclerView.staggered(
    spanCount: Int,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
    reverseLayout: Boolean = false,
): RecyclerView {
    layoutManager = StaggeredGridLayoutManager(spanCount, orientation).apply {
        this.reverseLayout = reverseLayout
    }
    return this
}