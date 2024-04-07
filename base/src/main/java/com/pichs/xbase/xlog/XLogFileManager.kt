package com.pichs.xbase.xlog

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private const val TAG = "LogFileManager"

@RequiresApi(Build.VERSION_CODES.O)
object XLogFileManager {

    private var singleFileMaxSize = 10 * 1024 * 1024L
    private val logCacheList = mutableListOf<String>()

    fun checkLogFile(dirPath: String, msg: String) {
        runCatching {
            val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))
            val content = "$time ${XLog.packageName} $msg\n"
            if (logCacheList.size < XLog.maxCacheLog) {
                logCacheList.add(content)
                return
            }
            val cacheTmpList = mutableListOf<String>().apply { addAll(logCacheList) }
            logCacheList.clear()
            cacheTmpList.add(content)

            val dir = File(dirPath)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            // LocalDate 转string
            val nowDate = LocalDateTime.now()
            val fileName = nowDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

            val files = File(dirPath).listFiles()
            val firstFile = files?.minByOrNull { it.lastModified() }
            val firstTime = firstFile?.name?.substring(0, 10)
            // yyyy-MM-dd 转 date
            if (!firstTime.isNullOrBlank()) {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.CHINA)
                val date = LocalDate.parse(firstTime, formatter)
                val isBefore = date.isBefore(nowDate.toLocalDate().minusDays(7))
                if (isBefore) {
                    // 删除文件
                    firstFile?.delete()
                    Log.i(TAG, "checkLogFile: 删除了 ${firstFile?.name}")
                }
            }

            // 时分秒
            val accurateTime = nowDate.format(DateTimeFormatter.ofPattern("HHmmss"))
            val todayLastFiles = files?.filter { it.name.contains(fileName) }
                ?.maxByOrNull {
                    it.name.substring(10)
                        .replace("_", "")
                        .replace(".log", "").toIntOrNull() ?: 0
                }

            // 如果文件名不包含当天日期，新建文件
            var newFileName = ""
            if (todayLastFiles == null || !todayLastFiles.name.contains(fileName)) {
                newFileName = "${fileName}_$accurateTime.log"
            } else if (todayLastFiles.length() >= singleFileMaxSize) {
                // 如果文件大小大于10M，则不再写入 精确到秒
                newFileName = "${fileName}_$accurateTime.log"
            } else {
                // 否则继续写入
                newFileName = todayLastFiles.name
            }
            val newFile = File(dirPath, newFileName)
            if (!newFile.exists()) {
                newFile.createNewFile()
            }

            XLogFileUtils.writeToFile(newFile.absolutePath, cacheTmpList)
        }.onFailure {
            it.printStackTrace()
        }

    }


    fun createLogDir(path: String = XLog.logPath): String {
        var fileName = ""
        runCatching {
            val dir = File(path)
            if (!dir.exists()) {
                dir.mkdirs()
            }

            val nowDate = LocalDateTime.now()
            fileName = nowDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss")) + ".log"
            val newFile = File(path, fileName)
            if (!newFile.exists()) {
                newFile.createNewFile()
            }
        }.onFailure {
            it.printStackTrace()
        }
        return fileName
    }


}

