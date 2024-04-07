package com.pichs.xbase.xlog

import android.content.Context
import android.os.Build
import android.util.Log
import java.util.regex.Pattern

object XLog {

    private const val isPrintLog = true
    private const val LOG_MAXLENGTH = 2000
    // 当前应用包名
    var packageName = ""
        private set

    // 日志根目录文件名
    private const val DIR_LOG = "xlogs"

    /**
     * 所有日志根目录
     */
    var rootLogPath = ""
        private set

    /**
     * 当前日志目录
     * 一般为 rootLogPath/UID/packageName 或 rootLogPath/UID
     */
    var logPath = ""
        private set
    var UID = "unknown_uid"
        private set


    var maxCacheLog = 1000
    /**
     * android 10 以上外部存储目录需要申请权限 [android.permission.MANAGE_EXTERNAL_STORAGE]
     *      val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
     *      startActivity(intent)
     * @param context Context
     * @param path String
     */
    fun init(context: Context, path: String = "") {
        packageName = context.applicationInfo.packageName
        rootLogPath = path
        if (rootLogPath.isBlank()){
            rootLogPath = context.filesDir.absolutePath
        }
        if (!rootLogPath.endsWith("/")){
            rootLogPath += "/"
        }
        rootLogPath += DIR_LOG
        changeLogPath()
    }

    fun setUid(uid: String){
        UID = uid
        changeLogPath()
    }

    private fun changeLogPath(){
        if (rootLogPath.contains(packageName)){
            logPath = "$rootLogPath/$UID"
        }else{
            logPath = "$rootLogPath/$packageName/$UID"
        }
    }

    /**
     * 判断当前日志目录是否为私有目录
     * 根目录下没有当前包名则为公有目录，实际存储的时候会补充当前包名，否则为私有目录
     * @return Boolean
     */
    fun isPrivateRootLogPath(): Boolean {
        return rootLogPath.contains(packageName)
    }




    fun v(msg: String) {
        v("LogUtil", msg)
    }

    fun v(tagName: String, msg: String) {
        if (isPrintLog) {
            val strLength = msg.length
            var start = 0
            var end = LOG_MAXLENGTH
            for (i in 0..99) {
                if (strLength > end) {
                    Log.v(tagName + i, msg.substring(start, end))
                    start = end
                    end = end + LOG_MAXLENGTH
                } else {
                    Log.v(tagName + i, msg.substring(start, strLength))
                    break
                }
            }
        }
        checkLogFile("V", tagName, msg)
    }

    fun d(msg: String) {
        d("LogUtil", msg)
    }

    fun d(tagName: String, msg: String) {
        if (isPrintLog) {
            val strLength = msg.length
            var start = 0
            var end = LOG_MAXLENGTH
            for (i in 0..99) {
                if (strLength > end) {
                    Log.d(tagName + i, msg.substring(start, end))
                    start = end
                    end = end + LOG_MAXLENGTH
                } else {
                    Log.d(tagName + i, msg.substring(start, strLength))
                    break
                }
            }
        }
        checkLogFile("D", tagName, msg)
    }

    fun i(msg: String) {
        i("LogUtil", msg)
    }

    fun i(tagName: String, msg: String) {
        if (isPrintLog) {
            val strLength = msg.length
            var start = 0
            var end = LOG_MAXLENGTH
            for (i in 0..99) {
                if (strLength > end) {
                    Log.i(tagName + i, msg.substring(start, end))
                    start = end
                    end = end + LOG_MAXLENGTH
                } else {
                    Log.i(tagName + i, msg.substring(start, strLength))
                    break
                }
            }
        }
        checkLogFile("I", tagName, msg)
    }

    fun w(msg: String) {
        w("LogUtil", msg)
    }

    fun w(tagName: String, msg: String) {
        if (isPrintLog) {
            val strLength = msg.length
            var start = 0
            var end = LOG_MAXLENGTH
            for (i in 0..99) {
                if (strLength > end) {
                    Log.w(tagName + i, msg.substring(start, end))
                    start = end
                    end = end + LOG_MAXLENGTH
                } else {
                    Log.w(tagName + i, msg.substring(start, strLength))
                    break
                }
            }
        }
        checkLogFile("W", tagName, msg)
    }

    fun e(msg: String) {
        e("LogUtil", msg)
    }

    fun e(tagName: String, msg: String) {
        if (isPrintLog) {
            val strLength = msg.length
            var start = 0
            var end = LOG_MAXLENGTH
            for (i in 0..99) {
                if (strLength > end) {
                    Log.e(tagName + i, msg.substring(start, end))
                    start = end
                    end = end + LOG_MAXLENGTH
                } else {
                    Log.e(tagName + i, msg.substring(start, strLength))
                    break
                }
            }
        }
        checkLogFile("E", tagName, msg)
    }


    /**
     * 含有unicode 的字符串转一般字符串
     * @param unicodeStr 混有 Unicode 的字符串
     * @return
     */
    fun unicodeStr2String(unicodeStr: String): String? {
        val length = unicodeStr.length
        var count = 0
        //正则匹配条件，可匹配“\\u”1到4位，一般是4位可直接使用 String regex = "\\\\u[a-f0-9A-F]{4}";
        val regex = "\\\\u[a-f0-9A-F]{1,4}"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(unicodeStr)
        val sb = StringBuffer()
        while (matcher.find()) {
            val oldChar = matcher.group() //原本的Unicode字符
            val newChar = unicode2String(oldChar) //转换为普通字符
            // int index = unicodeStr.indexOf(oldChar);
            // 在遇见重复出现的unicode代码的时候会造成从源字符串获取非unicode编码字符的时候截取索引越界等
            val index = matcher.start()
            sb.append(unicodeStr.substring(count, index)) //添加前面不是unicode的字符
            sb.append(newChar) //添加转换后的字符
            count = index + oldChar.length //统计下标移动的位置
        }
        sb.append(unicodeStr.substring(count, length)) //添加末尾不是Unicode的字符
        return sb.toString()
    }

    /**
     * unicode 转字符串
     * @param unicode 全为 Unicode 的字符串
     * @return
     */
    fun unicode2String(unicode: String): String {
        val string = StringBuffer()
        val hex = unicode.split("\\\\u".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        for (i in 1 until hex.size) {
            // 转换出每一个代码点
            val data = hex[i].toInt(16)
            // 追加成string
            string.append(data.toChar())
        }
        return string.toString()
    }

    private fun checkLogFile(level: String, tagName: String, msg: String){
        if (rootLogPath.isBlank() || logPath.isBlank()){
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            XLogFileManager.checkLogFile(logPath, "level:$level: TAG:$tagName message:$msg")
        }
    }
}