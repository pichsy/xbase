package com.pichs.xbase.audio

import android.app.Application
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import com.pichs.xbase.cache.BaseMMKVHelper
import com.pichs.xbase.utils.ThreadUtils

object SoundPoolPlayer {

    private var soundPool: SoundPool? = null
    private var isInit = false
    private var soundIDMap = hashMapOf<String, Int>()
    private var app: Application? = null

    init {
        init()
    }

    @JvmStatic
    fun init(application: Application) {
        app = application
    }

    private fun init() {
        if (isInit) return
        isInit = true
        soundPool = getSoundPool(20)
    }

    private fun getSoundPool(maxStreams: Int) = if (Build.VERSION.SDK_INT >= 21) {
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .build()
        SoundPool.Builder().setAudioAttributes(attributes)
            .setMaxStreams(maxStreams)
            .build()
    } else {
        SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0)
    }

    /**
     * 预加载一些音效。
     */
    fun preloadAudio(vararg assetsPath: String) {
        ThreadUtils.runOnIOThread {
            init()
            soundPool?.apply {
                if (assetsPath.isNullOrEmpty()) {
                    return@apply
                }
                for (path in assetsPath) {
                    if (soundIDMap[path] == null) {
                        val fd = (app ?: BaseMMKVHelper.getApplication()).assets.openFd(path)
                        load(fd, 1)
                        setOnLoadCompleteListener { soundPool, sampleId, status ->
                            soundIDMap[path] = sampleId
                        }
                    }
                }
            }
        }
    }


    fun openAssetsMusic(path: String) {
        ThreadUtils.runOnIOThread {
            init()
            soundPool?.apply {
                if (soundIDMap[path] == null) {
                    val fd = (app ?: BaseMMKVHelper.getApplication()).assets.openFd(path)
                    load(fd, 1)
                    setOnLoadCompleteListener { sp, soundId, _ ->
                        soundIDMap[path] = soundId
                        sp.play(soundId, 1f, 1f, 1, 0, 1f)
                    }
                } else {
                    soundIDMap[path]?.let { id ->
                        play(id, 1f, 1f, 1, 0, 1f)
                    }
                }
            }
        }
    }


    /**
     * 不要轻易使用，调用后，再点击就没有音效了
     */
    fun release() {
        isInit = false
        soundIDMap.clear()
        soundPool?.release()
    }
}