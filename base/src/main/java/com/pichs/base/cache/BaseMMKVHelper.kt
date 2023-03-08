package com.pichs.base.cache

import android.app.Application
import android.os.Parcelable
import com.google.gson.reflect.TypeToken
import com.pichs.base.utils.GsonUtils
import com.tencent.mmkv.MMKV
import org.jetbrains.annotations.NotNull
import java.io.File
import java.io.Serializable

abstract class BaseMMKVHelper constructor(val mmapID: String?) {

    private var kv: MMKV? = null

    companion object {
        private lateinit var application: Application

        /**
         * 初始化MMKV，在Application的 onCreate 中初始化
         */
        @JvmStatic
        fun init(app: Application) {
            application = app
            MMKV.initialize(application, "${app.filesDir.absolutePath}${File.separator}mmkv")
        }

        /**
         * 获取应用的上下文
         * @return Application
         */
        fun getApplication(): Application {
            return application
        }
    }

    /**
     * 创建MMKV对象
     */
    init {
        kv = if (mmapID == null) {
            getDefaultMMKV()
        } else {
            getMMKVWithID(mmapID)
        }
    }

    private fun getDefaultMMKV(): MMKV? {
        return MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, null)
    }

    private fun getMMKVWithID(mmapID: String): MMKV? {
        return MMKV.mmkvWithID(mmapID, MMKV.MULTI_PROCESS_MODE, null)
    }

    fun setString(@NotNull key: String, value: String?) {
        kv?.encode(key, value)
    }

    @JvmOverloads
    fun getString(@NotNull key: String, defValue: String? = null): String? {
        return kv?.decodeString(key, defValue) ?: defValue
    }

    fun setInt(@NotNull key: String, value: Int) {
        kv?.encode(key, value)
    }

    @JvmOverloads
    fun getInt(@NotNull key: String, defValue: Int = 0): Int {
        return kv?.decodeInt(key, defValue) ?: defValue
    }

    fun setLong(@NotNull key: String, value: Long) {
        kv?.encode(key, value)
    }

    @JvmOverloads
    fun getLong(@NotNull key: String, defValue: Long = 0): Long {
        return kv?.decodeLong(key, defValue) ?: defValue
    }

    fun setBool(@NotNull key: String, value: Boolean) {
        kv?.encode(key, value)
    }

    @JvmOverloads
    fun getBool(@NotNull key: String, defValue: Boolean = false): Boolean {
        return kv?.decodeBool(key, defValue) ?: defValue
    }

    fun setFloat(@NotNull key: String, value: Float) {
        kv?.encode(key, value)
    }

    @JvmOverloads
    fun getFloat(@NotNull key: String, defValue: Float = 0.0f): Float {
        return kv?.decodeFloat(key, defValue) ?: defValue
    }


    fun setDouble(@NotNull key: String, value: Double) {
        kv?.encode(key, value)
    }

    @JvmOverloads
    fun getDouble(@NotNull key: String, defValue: Double = 0.0): Double {
        return kv?.decodeDouble(key, defValue) ?: defValue
    }

    fun <T : Parcelable> setParcelable(@NotNull key: String, value: T) {
        kv?.encode(key, value)
    }

    @JvmOverloads
    fun <T : Parcelable> getParcelable(
        @NotNull key: String,
        clz: Class<T>,
        defValue: T? = null
    ): T? {
        return kv?.decodeParcelable(key, clz, defValue) ?: defValue
    }


    fun setBytes(@NotNull key: String, value: ByteArray) {
        kv?.encode(key, value)
    }

    @JvmOverloads
    fun getBytes(@NotNull key: String, defValue: ByteArray? = null): ByteArray? {
        return kv?.decodeBytes(key, defValue) ?: defValue
    }

    fun setStringSet(@NotNull key: String, value: Set<String>?) {
        kv?.encode(key, value)
    }

    @JvmOverloads
    fun getStringSet(@NotNull key: String, defValue: Set<String>? = null): Set<String>? {
        return kv?.decodeStringSet(key, defValue) ?: defValue
    }

    fun <T> setObject(@NotNull key: String, obj: T?) {
        if (obj != null) {
            val str = GsonUtils.toJson(obj)
            setString(key, str)
        } else {
            setString(key, null)
        }
    }


    @JvmOverloads
    fun <T> getObject(key: String, clazz: Class<T>, defaultValue: T? = null): T? {
        getString(key, null)?.let { str ->
            try {
                return GsonUtils.fromJson(str, clazz) ?: defaultValue
            } catch (e: RuntimeException) {
                return defaultValue
            }
        }
        return defaultValue
    }

    @JvmOverloads
    fun <T> getObject(key: String, typeToken: TypeToken<T>, defaultValue: T? = null): T? {
        getString(key, null)?.let { str ->
            try {
                return GsonUtils.fromJson(str, typeToken) ?: defaultValue
            } catch (e: RuntimeException) {
                return defaultValue
            }
        }
        return defaultValue
    }

    fun containsKey(@NotNull key: String): Boolean {
        return kv?.containsKey(key) ?: false
    }

    fun totalSize(): Long {
        return kv?.totalSize() ?: 0L
    }

    fun count(): Long {
        return kv?.count() ?: 0L
    }

    fun remove(@NotNull key: String) {
        kv?.remove(key)
    }

    fun removeValueForKey(@NotNull key: String) {
        kv?.removeValueForKey(key)
    }

    fun removeValuesForKeys(@NotNull keys: Array<String>) {
        kv?.removeValuesForKeys(keys)
    }

    fun allKeys(): Array<String>? {
        return kv?.allKeys()
    }

    fun clearAll() {
        kv?.clearAll()
    }

    fun clearMemoryCache() {
        kv?.clearMemoryCache()
    }

}