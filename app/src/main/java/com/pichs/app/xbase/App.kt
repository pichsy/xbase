package com.pichs.app.xbase

import android.app.Application
import com.pichs.xbase.audio.SoundPoolPlayer
import com.pichs.xbase.cache.BaseMMKVHelper
import com.pichs.xbase.clickhelper.ClickPlayer
import com.pichs.xbase.utils.UiKit
import com.pichs.xbase.xlog.XLog

/**
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        UiKit.init(this)
        XLog.init(this)
        ClickPlayer.initSound(null)
        SoundPoolPlayer.init(this)
        BaseMMKVHelper.init(this)

    }
}