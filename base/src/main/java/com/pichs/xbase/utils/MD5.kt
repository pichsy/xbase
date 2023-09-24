package com.pichs.xbase.utils

import java.io.FileInputStream
import java.io.InputStream
import java.io.UnsupportedEncodingException
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.math.pow

/**
 * md5 tool class
 *
 * @author Crane
 */
object MD5 {
    private var digest: MessageDigest? = null

    /**
     * MD5 encode
     *
     * @param data
     * @return
     */
    @Synchronized
    fun md5(data: String): String {
        if (digest == null) {
            try {
                digest = MessageDigest.getInstance("MD5")
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
        }
        try {
            digest!!.update(data.toByteArray(charset("utf-8")))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return encodeHex(digest!!.digest())
    }

    @Synchronized
    fun fileCheckSum(filename: String?): String {
        if (digest == null) {
            try {
                digest = MessageDigest.getInstance("MD5")
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
        }
        try {
            val fis: InputStream = FileInputStream(filename)
            val buffer = ByteArray(1024)
            var numRead: Int
            do {
                numRead = fis.read(buffer)
                if (numRead > 0) {
                    digest!!.update(buffer, 0, numRead)
                }
            } while (numRead != -1)
            fis.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return encodeHex(digest!!.digest())
    }

    private fun encodeHex(bytes: ByteArray): String {
        val buf = StringBuffer(bytes.size * 2)
        var i: Int = 0
        while (i < bytes.size) {
            if (bytes[i].toInt() and 0xff < 0x10) {
                buf.append("0")
            }
            buf.append(java.lang.Long.toString((bytes[i].toInt() and 0xff).toLong(), 16))
            i++
        }
        return buf.toString()
    }


    /**
     * Base64  编码
     *
     * @param data
     * @return
     */
    @Synchronized
    fun base64(data: String): String? {
        try {
            val b = data.toByteArray(charset("utf-8"))
            return Base64Utils.encode(b)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * Base64 解码
     *
     * @param data
     * @return
     */
    @Synchronized
    fun decodeBase64(data: String): String {
        return Base64Utils.decode(data).contentToString()
    }

    /**
     * base64 编码
     */
    fun encodeBase64(bytes: ByteArray?): String? {
        return Base64Utils.encode(bytes)
    }

    /**
     * md5 大数处理
     * 针对jni使用
     */
    @Deprecated("对jni开发，暂不对外，请谨慎使用，不要删除")
    @JvmStatic
    fun md5Hex2IntString(md5: String, powValue: Int): String {
        return try {
            val bigInteger = BigInteger(md5, 16)
            val doubleValue = bigInteger.toDouble()
            val result = (doubleValue % 10.0.pow(powValue)).toInt()
            result.toString()
        } catch (e: Exception) {
            "00000000"
        }
    }
}