package com.pichs.xbase.utils

import android.content.ContentResolver
import android.net.Uri
import android.os.Build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

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


    /**
     * Create a file if it doesn't exist, otherwise do nothing.
     *
     * @param file The file.
     * @return `true`: exists or creates successfully<br></br>`false`: otherwise
     */
    fun createOrExistsFile(file: File?): Boolean {
        if (file == null) return false
        if (file.exists()) return file.isFile
        return if (!createOrExistsDir(file.parentFile)) false else try {
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Create a directory if it doesn't exist, otherwise do nothing.
     *
     * @param file The file.
     * @return `true`: exists or creates successfully<br></br>`false`: otherwise
     */
    fun createOrExistsDir(file: File?): Boolean {
        return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
    }


    /**
     * Return whether the file exists.
     *
     * @param file The file.
     * @return `true`: yes<br></br>`false`: no
     */
    fun isFileExists(file: File?): Boolean {
        if (file == null) return false
        return if (file.exists()) {
            true
        } else isFileExists(file.absolutePath)
    }

    /**
     * Return whether the file exists.
     *
     * @param filePath The path of file.
     * @return `true`: yes<br></br>`false`: no
     */
    fun isFileExists(filePath: String): Boolean {
        val file: File = getFileByPath(filePath) ?: return false
        return if (file.exists()) {
            true
        } else isFileExistsApi29(filePath)
    }

    private fun isFileExistsApi29(filePath: String): Boolean {
        if (Build.VERSION.SDK_INT >= 29) {
            try {
                val uri = Uri.parse(filePath)
                val cr: ContentResolver = UiKit.getApplication().contentResolver
                val afd = cr.openAssetFileDescriptor(uri, "r") ?: return false
                try {
                    afd.close()
                } catch (ignore: IOException) {
                }
            } catch (e: FileNotFoundException) {
                return false
            }
            return true
        }
        return false
    }

    fun isSpace(s: String?): Boolean {
        if (s == null) return true
        var i = 0
        val len = s.length
        while (i < len) {
            if (!Character.isWhitespace(s[i])) {
                return false
            }
            ++i
        }
        return true
    }

    /**
     * Return the file by path.
     *
     * @param filePath The path of file.
     * @return the file
     */
    fun getFileByPath(filePath: String?): File? {
        return if (isSpace(filePath)) null else File(filePath)
    }

}