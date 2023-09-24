package com.pichs.xbase.utils

import android.text.TextUtils
import java.util.regex.Pattern

/**
 * @Description:
 * 网址校验
 */
object RegexUtils {

    /**
     * 正则网址格式校验
     * 一下几种都认为之网址，然后修复前缀即可
     * https://www.baidu.com
     * ://www.baidu.com
     * //www.baidu.com
     * :/www.baidu.com
     * /www.baidu.com
     * :www.baidu.com/aaa/n?id=0&oa=ni
     * www.baidu.com
     */
    const val URL_REGEX_CHECK =
        "(?<protocol>((/|:|:/|://|//)|(HTTP|HTTPS|http|https|ftp|sftp)://))?([A-Za-z0-9_]+@)?(?<host>([a-zA-Z0-9-_]+\\.)+([A-Za-z]{2,3})|([0-9]{1,3}\\.){3}[0-9]{1,3})(?<port>:[0-9]{1,5})?(?<params>[/|?][^，。；（『\\[ ]{0,1024})?"

    /**
     * 提取网址正则
     */
    const val URL_REGEX_MATCH =
        "(?<protocol>(HTTP|HTTPS|http|https|ftp|sftp)://)?([A-Za-z0-9_]+@)?(?<host>([a-zA-Z0-9-_]+\\.)+([A-Za-z]{2,3})|([0-9]{1,3}\\.){3}[0-9]{1,3})(?<port>:[0-9]{1,5})?(?<params>[/|?][^，。；（『\\[ ]{0,1024})?"


    /**
     * 校验是否是网址，使用正则 [URL_REGEX_CHECK]
     */
    @JvmStatic
    fun isUrl(url: String): Boolean {
        return Pattern.compile(URL_REGEX_CHECK).matcher(url).matches()
    }

    /**
     * 获取正则网址，使用正则 [URL_REGEX_MATCH]
     * 寻找符合规则的第一个网址
     */
    @JvmStatic
    fun findUrl(url: String): String {
        val matcher = Pattern.compile(URL_REGEX_MATCH).matcher(url)
        while (matcher.find()) {
            val group = matcher.group()
            if (!TextUtils.isEmpty(group)) {
                return group
            }
        }
        return ""
    }


    /**
     * 如果包含html标签，则认为是Html文本，直接使用html格式加载
     */
    const val REGEX_HTML_TAG = "((<[\\w]+[^>]*[\\S]*?>)[.|\\S]*(</(\\w+)([^>]*)>))|(<[\\w]+[^>]*/>)"

    /**
     * 是否有html标签
     */
    @JvmStatic
    fun isHtmlText(text: String?): Boolean {
        if (text == null) return false
        val pattern = Pattern.compile(REGEX_HTML_TAG)
        val matcher = pattern.matcher(text)
        return matcher.find()
    }

}