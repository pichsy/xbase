package com.pichs.xbase.imageloader

import androidx.annotation.IntDef
import androidx.annotation.DrawableRes
import android.text.TextUtils
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.IntRange
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition

/**
 * 图片加载
 */
class ImageLoader {

    @IntDef(DiskCacheStrategy.NONE, DiskCacheStrategy.ALL, DiskCacheStrategy.RESULT, DiskCacheStrategy.SOURCE, DiskCacheStrategy.AUTO)
    annotation class DiskCacheStrategy {
        companion object {
            const val NONE = -1
            const val ALL = 0 // 全部缓存
            const val RESULT = 1 // 剪裁后的图片缓存
            const val SOURCE = 2 // 原图缓存
            const val AUTO = 3 // 自动
        }
    }

    @DrawableRes
    private var globalPlaceHolder = 0

    @DrawableRes
    private var globalErrorHolder = 0

    @DiskCacheStrategy
    private var globalDiskCacheStrategy = DiskCacheStrategy.ALL


    fun setGlobalPlaceHolder(@DrawableRes placeHolder: Int): ImageLoader {
        globalPlaceHolder = placeHolder
        return this
    }

    fun setGlobalErrorHolder(@DrawableRes errorHolder: Int): ImageLoader {
        globalErrorHolder = errorHolder
        return this
    }

    fun setGlobalDiskCacheStrategy(@DiskCacheStrategy diskCacheStrategy: Int): ImageLoader {
        globalDiskCacheStrategy = diskCacheStrategy
        return this
    }

    fun <T> load(url: T): Builder<T> {
        return Builder(url)
    }

    class Builder<T> internal constructor(private val url: T) {
        @DrawableRes
        private var placeHolder: Int

        @DrawableRes
        private var errorHolder: Int

        @DiskCacheStrategy
        private var diskCacheStrategy: Int
        private var cropType = 0
        private var overrideWidth = 0
        private var overrideHeight = 0
        private var timeout = 0
        private var dontAnimate = true
        private var asGif = false
        private var useAnimationPool = false
        private var skipMemoryCache = false

        init {
            placeHolder = with().globalPlaceHolder
            errorHolder = with().globalErrorHolder
            diskCacheStrategy = with().globalDiskCacheStrategy
            if (url != null && url is String && !TextUtils.isEmpty(url as String) && (url as String).endsWith(".gif")) {
                asGif = true
            }
        }

        fun placeholder(@DrawableRes placeHolder: Int): Builder<T> {
            this.placeHolder = placeHolder
            return this
        }

        fun error(@DrawableRes errorHolder: Int): Builder<T> {
            this.errorHolder = errorHolder
            return this
        }

        fun skipMemoryCache(skipMemoryCache: Boolean): Builder<T> {
            this.skipMemoryCache = skipMemoryCache
            return this
        }

        fun diskCacheStrategy(@DrawableRes diskCacheStrategy: Int): Builder<T> {
            this.diskCacheStrategy = diskCacheStrategy
            return this
        }

        fun override(width: Int, height: Int): Builder<T> {
            overrideWidth = width
            overrideHeight = height
            return this
        }

        fun centerCrop(): Builder<T> {
            cropType = 1
            return this
        }

        fun circleCrop(): Builder<T> {
            cropType = 2
            return this
        }

        fun asGif(): Builder<T> {
            asGif = true
            return this
        }

        fun fitCenter(): Builder<T> {
            cropType = 3
            return this
        }

        fun centerInside(): Builder<T> {
            cropType = 4
            return this
        }

        fun useAnimate(): Builder<T> {
            dontAnimate = false
            return this
        }

        fun useAnimationPool(): Builder<T> {
            useAnimationPool = true
            return this
        }

        fun timeout(@IntRange(from = 0) timeout: Int): Builder<T> {
            this.timeout = timeout
            return this
        }

        @SuppressLint("CheckResult")
        fun into(iv: ImageView) {
            val requestOptions = RequestOptions()
            if (placeHolder != 0) {
                requestOptions.placeholder(placeHolder)
            }
            if (errorHolder != 0) {
                requestOptions.error(errorHolder)
            }
            if (overrideHeight != 0 && overrideWidth != 0) {
                requestOptions.override(overrideWidth, overrideHeight)
            }
            if (cropType == 1) {
                requestOptions.centerCrop()
            } else if (cropType == 2) {
                requestOptions.circleCrop()
            } else if (cropType == 3) {
                requestOptions.fitCenter()
            } else if (cropType == 4) {
                requestOptions.centerInside()
            }

            // 是否跳过内存缓存
            requestOptions.skipMemoryCache(skipMemoryCache)
            if (diskCacheStrategy == DiskCacheStrategy.NONE) {
                requestOptions.diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.NONE)
            } else if (diskCacheStrategy == DiskCacheStrategy.RESULT) {
                requestOptions.diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.RESOURCE)
            } else if (diskCacheStrategy == DiskCacheStrategy.AUTO) {
                requestOptions.diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC)
            } else if (diskCacheStrategy == DiskCacheStrategy.SOURCE) {
                requestOptions.diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.DATA)
            } else {
                requestOptions.diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.ALL)
            }
            if (timeout > 0) {
                requestOptions.timeout(timeout)
            }
            if (dontAnimate) {
                requestOptions.dontAnimate()
            } else if (useAnimationPool) {
                requestOptions.useAnimationPool(true)
            }
            if (asGif) {
                Glide.with(iv).asGif().load(url).apply(requestOptions).into(iv)
            } else {
                Glide.with(iv).load(url).apply(requestOptions).into(iv)
            }
        }

        @SuppressLint("CheckResult")
        fun into(view: View, target: ((Bitmap?) -> Unit)? = null) {
            val requestOptions = RequestOptions()
            if (placeHolder != 0) {
                requestOptions.placeholder(placeHolder)
            }
            if (errorHolder != 0) {
                requestOptions.error(errorHolder)
            }
            if (overrideHeight != 0 && overrideWidth != 0) {
                requestOptions.override(overrideWidth, overrideHeight)
            }
            if (cropType == 1) {
                requestOptions.centerCrop()
            } else if (cropType == 2) {
                requestOptions.circleCrop()
            } else if (cropType == 3) {
                requestOptions.fitCenter()
            } else if (cropType == 4) {
                requestOptions.centerInside()
            }

            // 是否跳过内存缓存
            requestOptions.skipMemoryCache(skipMemoryCache)
            if (diskCacheStrategy == DiskCacheStrategy.NONE) {
                requestOptions.diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.NONE)
            } else if (diskCacheStrategy == DiskCacheStrategy.RESULT) {
                requestOptions.diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.RESOURCE)
            } else if (diskCacheStrategy == DiskCacheStrategy.AUTO) {
                requestOptions.diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC)
            } else if (diskCacheStrategy == DiskCacheStrategy.SOURCE) {
                requestOptions.diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.DATA)
            } else {
                requestOptions.diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.ALL)
            }
            if (timeout > 0) {
                requestOptions.timeout(timeout)
            }
            if (dontAnimate) {
                requestOptions.dontAnimate()
            } else if (useAnimationPool) {
                requestOptions.useAnimationPool(true)
            }
            if (asGif) {
                if (view is ImageView) {
                    Glide.with(view)
                        .asGif()
                        .load(url)
                        .apply(requestOptions)
                        .into(view)
                }
            } else {
                if (target == null) {
                    if (view is ImageView) {
                        Glide.with(view).load(url).apply(requestOptions).into(view)
                    }
                } else {
                    Glide.with(view).asBitmap().load(url).apply(requestOptions).into(object : CustomViewTarget<View, Bitmap>(view) {
                        override fun onLoadFailed(errorDrawable: Drawable?) {

                        }

                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            target?.invoke(resource)
                        }

                        override fun onResourceCleared(placeholder: Drawable?) {

                        }
                    })
                }
            }
        }
    }

    companion object {

        private val mHelper by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { ImageLoader() }

        fun with(): ImageLoader {
            return mHelper
        }

    }
}