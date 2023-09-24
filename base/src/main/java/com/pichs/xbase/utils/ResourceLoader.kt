package com.pichs.xbase.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.view.LayoutInflater
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.content.res.XmlResourceParser
import android.graphics.drawable.Drawable
import android.view.View

/**
 * 资源强制获取帮助类。
 * 通过名字获取相关资源。
 */
class ResourceLoader private constructor(private val mContext: Context) {
    private val mConfiguration = Configuration()
    private var mResources: Resources = mContext.resources
    private var mInflater: LayoutInflater? = null

    init {
        mInflater = LayoutInflater.from(mContext)
    }

    /*
     * 取Color资源
     */
    fun getColor(colorname: String?): Int {
        return run {
            val id = mResources.getIdentifier(colorname, COLOR_CLASS_NAME, mContext.packageName)
            mResources.getColor(id)
        }
    }

    /*
     * 取Color资源
     */
    fun getColorRes(colorname: String?): Int {
        return run {
            val id = mResources.getIdentifier(colorname, COLOR_CLASS_NAME, mContext.packageName)
            mResources.getColor(id)
        }
    }

    /*
     * 取 String 资源
     */
    fun getString(strname: String?): String? {
        return run {
            val id = mResources.getIdentifier(strname, STRING_CLASS_NAME, mContext.packageName)
            if (id == 0) {
                null
            } else mResources.getString(id)
        }
    }

    fun getString(strname: String?, vararg formatArgs: Any?): String {
        val raw = getString(strname)
        return String.format(mConfiguration.locale, raw!!, *formatArgs)
    }

    private fun getStringForId(strname: String): Int {
        return mResources.getIdentifier(strname, STRING_CLASS_NAME, mContext.packageName)
    }

    /*
     * 取Drawable资源
     */
    fun getDrawable(drawname: String?): Drawable? {
        return run {
            val id = mResources.getIdentifier(drawname, DRAWABLE_CLASS_NAME, mContext.packageName)
            if (id == 0) {
                null
            } else mResources.getDrawable(id)
        }
    }

    fun getDrawableForId(drawname: String?): Int {
        return mResources.getIdentifier(drawname, DRAWABLE_CLASS_NAME, mContext.packageName)
    }

    fun getLayoutForId(layoutname: String?): Int {
        return mResources.getIdentifier(layoutname, LAYOUT_CLASS_NAME, mContext.packageName)
    }

    /*
     * 取Layout资源
     */
    fun getLayoutForView(layoutname: String?): View? {
        return run {
            val id = mResources.getIdentifier(layoutname, LAYOUT_CLASS_NAME, mContext.packageName)
            if (mInflater != null && id != 0) {
                mInflater?.inflate(id, null)
            } else {
                null
            }
        }
    }

    /*
     * 取id资源
     */
    fun getId(idname: String?): Int {
        return mResources.getIdentifier(idname, ID_CLASS_NAME, mContext.packageName)
    }

    /*
     * 获取Raw资源
     */
    fun getRaw(rawName: String?): Int {
        return mResources.getIdentifier(rawName, RAW_CLASS_NAME, mContext.packageName)
    }

    /*
     * 取Bitmap 资源
     */
    fun getDrawableBitmap(imgname: String?): Bitmap? {
        return run {
            val id = mResources.getIdentifier(imgname, DRAWABLE_CLASS_NAME, mContext.packageName)
            if (id == 0) {
                return null
            }
            BitmapFactory.decodeResource(mResources, id)
        }
    }

    fun getStyle(stylename: String?): Int {
        return mResources.getIdentifier(stylename, STYLE_CLASS_NAME, mContext.packageName)
    }

    fun getAnim(animname: String?): Int {
        return mResources.getIdentifier(animname, ANIM_CLASS_NAME, mContext.packageName)
    }

    fun getXmlForParser(xmlname: String?): XmlResourceParser? {
        return run {
            val id = mResources.getIdentifier(xmlname, XML_CLASS_NAME, mContext.packageName)
            if (id == 0) {
                null
            } else mResources.getXml(id)
        }
    }

    fun getXmlForId(xmlname: String?): Int {
        return mResources.getIdentifier(xmlname, XML_CLASS_NAME, mContext.packageName)
    }

    fun getArrayId(arrayName: String?): Int {
        return mResources.getIdentifier(arrayName, ARRAY_CLASS_NAME, mContext.packageName)
    }

    fun getIntArray(arrayName: String?): IntArray {
        val arrayId = getArrayId(arrayName)
        return mResources.getIntArray(arrayId)
    }

    fun getStringArray(arrayName: String?): Array<String> {
        val arrayId = getArrayId(arrayName)
        return mResources.getStringArray(arrayId)
    }

    fun getDimenId(dimenName: String?): Int {
        return mResources.getIdentifier(dimenName, DIMEN_CLASS_NAME, mContext.packageName)
    }

    fun getDimensionPixelSize(dimenName: String?): Int {
        return run {
            val id = getDimenId(dimenName)
            mResources.getDimensionPixelSize(id)
        }
    }

    fun getMipmapForId(mipmapName: String?): Int {
        return mResources.getIdentifier(mipmapName, MIPMAP_CLASS_NAME, mContext.packageName)
    }

    fun getMipmapBitmap(mipmapName: String?): Bitmap? {
        return run {
            val id = mResources.getIdentifier(mipmapName, MIPMAP_CLASS_NAME, mContext.packageName)
            if (id == 0) {
                null
            } else BitmapFactory.decodeResource(mResources, id)
        }
    }

    /*
     * 取 mipmap目录下的Drawable资源
     */
    fun getDrawableInMipmap(drawname: String?): Drawable? {
        return run {
            val id = mResources.getIdentifier(drawname, MIPMAP_CLASS_NAME, mContext.packageName)
            if (id == 0) {
                null
            } else{
                mResources.getDrawable(id)
            }
        }
    }

    companion object {
        private const val DRAWABLE_CLASS_NAME = "drawable"
        private const val MIPMAP_CLASS_NAME = "mipmap"
        private const val ID_CLASS_NAME = "id"
        private const val LAYOUT_CLASS_NAME = "layout"
        private const val XML_CLASS_NAME = "xml"
        private const val ANIM_CLASS_NAME = "anim"
        private const val STYLE_CLASS_NAME = "style"
        private const val STRING_CLASS_NAME = "string"
        private const val COLOR_CLASS_NAME = "color"
        private const val RAW_CLASS_NAME = "raw"
        private const val ARRAY_CLASS_NAME = "array"
        private const val DIMEN_CLASS_NAME = "dimen"

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var mInstance: ResourceLoader? = null

        fun getInstance(context: Context): ResourceLoader {
            if (mInstance == null) {
                synchronized(ResourceLoader::class.java) {
                    if (mInstance == null) {
                        mInstance = ResourceLoader(context)
                    }
                }
            }
            return mInstance!!
        }
    }
}