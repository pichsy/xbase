package com.pichs.base.clickhelper

import com.pichs.base.audio.SoundPoolPlayer

/**
 * 点击音效 播放器。
 */
object ClickPlayer {

    private var musicPath: String? = null

    fun initSound(musicAssetsName: String?) {
        musicPath = musicAssetsName
    }

    fun play() {
        musicPath?.let {
            SoundPoolPlayer.openAssetsMusic(it)
        }
    }
}