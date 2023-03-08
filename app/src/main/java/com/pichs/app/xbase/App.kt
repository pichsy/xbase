package com.pichs.app.xbase

import android.app.Application
import com.pichs.base.audio.SoundPoolPlayer
import com.pichs.base.cache.BaseMMKVHelper
import com.pichs.base.cache.CacheHelper
import com.pichs.base.clickhelper.ClickPlayer

/**
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ClickPlayer.initSound(null)
        SoundPoolPlayer.init(this)
        BaseMMKVHelper.init(this)

    }
}