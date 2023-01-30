package com.pichs.base.cache

import android.os.Parcelable
import com.tencent.mmkv.MMKV
import org.jetbrains.annotations.NotNull

abstract class BaseMMKVHelper {

    abstract fun getMMapID(): String?

    private var kv: MMKV? = null

    /**
     * 创建MMKV对象
     */
    init {
        kv = if (getMMapID() == null) {
            getDefaultMMKV()
        } else {
            getMMKVWithID(getMMapID()!!)
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

    fun containsKey(key: String): Boolean {
        return kv?.containsKey(key) ?: false
    }

    fun totalSize(): Long {
        return kv?.totalSize() ?: 0L
    }

    fun count(): Long {
        return kv?.count() ?: 0L
    }

    fun remove(key: String) {
        kv?.remove(key)
    }

    fun removeValueForKey(key: String) {
        kv?.removeValueForKey(key)
    }

    fun removeValuesForKeys(keys: Array<String>) {
        kv?.removeValuesForKeys(keys)
    }

    fun allKeys(): Array<String>? {
        return kv?.allKeys()
    }

    fun clearAll() {
        kv?.clearAll()
    }

}