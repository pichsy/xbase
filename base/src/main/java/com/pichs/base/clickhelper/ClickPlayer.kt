package com.pichs.base.clickhelper

import com.pichs.base.audio.SoundPoolPlayer

/**
 * 点击音效 播放器。
 * 音效的初始化。[ClickPlayer.initSound]
 * eg: ClickPlayer.initSound("assetsPathName.mp3", isPlaySoundDefault)
 * 播放时是否 默认播放音频
 */
object ClickPlayer {

    private var musicPath: String? = null

    /**
     * 是否 默认播放 点击音效，默认开启，
     * 可在application中初始化 修改默认，改为true后，则默认有点击音效
     * 再次之前还需要进行 音效的初始化。[ClickPlayer.initSound] ("assetsPathName.mp3")
     * 当然，代码也可以设置 不播放音效。该参数仅为：设置点击事件时不设置 playAudio时的默认值。
     * 详见：[ClickHelper.clicks(
     *       vararg views: View,
     *       playAudio: Boolean = IS_PLAY_SOUND_DEFAULT,
     *       listener: View.OnClickListener?
     *      )]
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