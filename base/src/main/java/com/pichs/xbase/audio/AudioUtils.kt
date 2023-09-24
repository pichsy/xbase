package com.pichs.xbase.audio
import android.content.Context
import android.media.MediaPlayer

/**
 * @ClassName AudioUtils
 * @Description TODO
 * @Author jinlin
 * @Date 2021/9/4 10:19
 * @Version 1.0
 */
object AudioUtils {

    var audioGuideEnable = true
    var mediaPlayer: MediaPlayer? = null

    fun openAssetMusics(context: Context, assetsName: String) {
        openAssetMusics(context, assetsName, null)
    }

    /**
     * 打开assets下的音乐mp3文件
     */
    fun openAssetMusics(context: Context, assetsName: String, listener: AudioOnComplete? = null) {
        try {
            //播放 assets/a2.mp3 音乐文件
            val fd = context.assets.openFd(assetsName)
            if (mediaPlayer != null) {
                mediaPlayer?.stop()
//                mediaPlayer?.reset()
                mediaPlayer?.release()
                mediaPlayer = null
            }
            mediaPlayer = MediaPlayer()
            mediaPlayer?.reset()
            mediaPlayer?.setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)
            mediaPlayer?.prepare()
            mediaPlayer?.start()
            mediaPlayer?.setOnCompletionListener(MediaPlayer.OnCompletionListener {
                listener?.onComplete(true)
                stopPlaying()
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun closeMusics() {
        if (mediaPlayer != null) {
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    fun stopPlaying() {
        try {
            if (mediaPlayer != null) {
                mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    interface AudioOnComplete {
        fun onComplete(isSuccess: Boolean)
    }
}