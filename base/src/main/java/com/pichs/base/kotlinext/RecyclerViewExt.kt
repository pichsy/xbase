package com.pichs.base.kotlinext

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemAnimator

fun RecyclerView.itemAnimator(itemAnimator: ItemAnimator): RecyclerView {
    setItemAnimator(itemAnimator)
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