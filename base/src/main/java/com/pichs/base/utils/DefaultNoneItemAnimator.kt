package com.pichs.base.utils

import androidx.recyclerview.widget.DefaultItemAnimator

class DefaultNoneItemAnimator : DefaultItemAnimator() {

    override fun getSupportsChangeAnimations(): Boolean {
        return false
    }
}