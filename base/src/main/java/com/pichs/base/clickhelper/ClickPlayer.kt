package com.pichs.base.clickhelper

import com.pichs.base.audio.SoundPoolPlayer

/**
 * 点击音效 播放器。
 */
object ClickPlayer {

    private var musicPath: String? = null

    /**
     * 是否 默认播放 点击音效，默认关闭，
     * 可在application中初始化 修改默认，改为true后，则默认有点击音效
     * 再次之前还需要进行 音效的初始化。[ClickPlayer.initSound] ("assetsPathName.mp3")
     * 当然，代码也可以设置 不播放音效。
     */
    var IS_PLAY_SOUND_DEFAULT = true


    fun initSound(musicAssetsName: String?, isPlaySoundDefault: Boolean = true) {
        IS_PLAY_SOUND_DEFAULT = isPlaySoundDefault
        musicPath = musicAssetsName
    }

    fun play() {
        if (!musicPath.isNullOrEmpty() || !musicPath.isNullOrBlank()) {
            SoundPoolPlayer.openAssetsMusic(musicPath!!)
        }
    }
}