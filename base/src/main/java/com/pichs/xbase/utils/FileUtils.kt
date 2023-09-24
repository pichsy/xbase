package com.pichs.xbase.utils

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*

object FileUtils {


    /**
     * 复制文件
     */
    suspend fun copyFile(srcFile: File, destFile: File): File? {
        return withContext(Dispatchers.IO) {
            if (!srcFile.exists()) {
                return@withContext null
            }
            if (srcFile.absolutePath == destFile.absolutePath) {
                return@withContext destFile
            }
            return@withContext srcFile.copyTo(destFile, true, 1024)
        }
    }

    /**
     * 复制文件
     */
    suspend fun copyFile(path: String, destFile: File): File? {
        return withContext(Dispatchers.IO) {
            val srcFile = File(path)
            if (!srcFile.exists()) {
                return@withContext null
            }
            if (srcFile.absolutePath == destFile.absolutePath) {
                return@withContext destFile
            }
            return@withContext srcFile.copyTo(destFile, true, 1024)
        }
    }


    /**
     * 复制文件
     */
    suspend fun copyFile(path: String, destPath: String): File? {
        return withContext(Dispatchers.IO) {
            val srcFile = File(path)
            if (!srcFile.exists()) {
                return@withContext null
            }
            if (path == destPath) {
                return@withContext srcFile
            }
            return@withContext srcFile.copyTo(File(destPath), true, 1024)
        }
    }


    /**
     * 复制文件
     * @return 复制后的文件
     */
    suspend fun copyFileAutoName(path: String, destFileDir: File): File? {
        return withContext(Dispatchers.IO) {
            val srcFile = File(path)
            if (!srcFile.exists()) {
                return@withContext null
            }
            val destFile = File(destFileDir, getFileName(path) ?: "${System.currentTimeMillis()}.apk")
            if (path == destFile.absolutePath) {
                return@withContext destFile
            }
            return@withContext srcFile.copyTo(destFile, true, 1024)
        }
    }


    /**
     * 删除文件
     */
    suspend fun deleteFile(file: File): Boolean {
        return withContext(Dispatchers.IO) {
            if (!file.exists()) {
                return@withContext false
            }
            try {
                return@withContext file.delete()
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext false
            }
        }
    }

    fun deleteFileAsync(file: File) {
        ThreadUtils.runOnIOThread {
            try {
                if (file.exists()) {
                    file.delete()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    /**
     * 获取文件名
     */
    fun getFileName(path: String?): String? {
        if (path.isNullOrEmpty()) return null
        val index = path.lastIndexOf(File.separatorChar)
        if (index == -1) return null
        return path.substring(index + 1)
    }


    fun isFileExists(file: File): Boolean {
        return file.exists()
    }


    private const val sBufferSize = 524288

    /**
     * Write file from input stream.
     *
     * @param file     The file.
     * @param is       The input stream.
     * @param append   True to append, false otherwise.
     * @param listener The progress update listener.
     * @return `true`: success<br></br>`false`: fail
     */
    fun writeFileFromIS(
        file: File,
        `is`: InputStream?,
        append: Boolean,
    ): Boolean {
        if (`is` == null || !createOrExistsFile(file)) {
            Log.e("FileIOUtils", "create file <$file> failed.")
            return false
        }
        var os: OutputStream? = null
        return try {
            os = BufferedOutputStream(FileOutputStream(file, append), sBufferSize)
            val data = ByteArray(sBufferSize)
            var len: Int
            while (`is`.read(data).also { len = it } != -1) {
                os.write(data, 0, len)
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        } finally {
            try {
                `is`.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                os?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun createOrExistsFile(file: File): Boolean {
        if (file == null) return false
        if (file.exists()) return file.isFile
        if (!createOrExistsDir(file.parentFile)) return false
        return try {
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun createOrExistsDir(file: File?): Boolean {
        return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
    }
}