package com.pichs.xbase.utils

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import java.lang.Exception
import java.util.concurrent.*

object ThreadUtils {

    @SuppressLint("StaticFieldLeak")
    private var mHandler: Handler? = null

    // 线程池
    private var mExecutorService: ExecutorService? = null

    // 可用核心数
    private val NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()
    private const val KEEP_ALIVE_TIME = 10L
    private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS

    // 队列
    var mTaskQueue: BlockingQueue<Runnable> = LinkedBlockingQueue()

    init {
        mHandler = Handler(Looper.getMainLooper())
        mExecutorService = ThreadPoolExecutor(
            NUMBER_OF_CORES,
            NUMBER_OF_CORES * 2,
            KEEP_ALIVE_TIME,
            KEEP_ALIVE_TIME_UNIT,
            mTaskQueue
        )
    }

    /**
     * 在主线程中执行
     */
    fun runOnUiThread(runnable: Runnable?) {
        runnable?.let {
            mHandler?.post(it)
        }
    }

    fun removeRunnable(runnable: Runnable?) {
        runnable?.let {
            mHandler?.removeCallbacks(it)
        }
    }

    /**
     * 在主线程中执行
     */
    fun runOnUiThread(delay: Long, runnable: Runnable?) {
        runnable?.let {
            mHandler?.postDelayed(it, delay)
        }
    }

    /**
     * 切换至主线程执行，可以延迟 timeMills 时间
     * 为了使用习惯，适配Handler的方法名，但不完全一样。
     * kotlin写法会更香一些
     */
    fun postDelay(timeMills: Long, runnable: Runnable?) {
        runnable?.let {
            mHandler?.postDelayed(it, timeMills)
        }
    }

    /**
     * 在子线程中执行，使用线程池，防止线程爆炸
     */
    fun runOnIOThread(runnable: Runnable?) {
        if (runnable == null) return
        if (mExecutorService?.isShutdown == true) {
            mExecutorService = ThreadPoolExecutor(
                NUMBER_OF_CORES,
                NUMBER_OF_CORES * 2,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                mTaskQueue
            )
        }
        try {
            // 优先使用线程池
            mExecutorService?.execute(runnable)
        } catch (e: Exception) {
            Thread(runnable).start()
        }
    }

    fun releaseExecutor() {
        mExecutorService?.shutdown()
    }


}