package com.pichs.app.xbase

import android.app.Application
import com.pichs.base.cache.CacheHelper
import com.pichs.base.clickhelper.ClickPlayer

/**
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        ClickPlayer.initSound(null)

        CacheHelper.init(this)


    }
}