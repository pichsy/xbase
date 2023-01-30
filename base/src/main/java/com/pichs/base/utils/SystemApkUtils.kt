package com.pichs.base.utils

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.FileProvider
import android.widget.Toast
import android.media.MediaScannerConnection
import android.net.Uri
import java.io.File
import java.lang.Exception

/**
 * 系统应用的工具类。
 */
object SystemApkUtils {

    /**
     * 市场上常用的一些app报名
     */
    const val PACKAGE_NAME_WECHAT = "com.tencent.mm" // 微信
    const val PACKAGE_NAME_QQ = "com.tencent.mobileqq" // QQ
    const val PACKAGE_NAME_QZONE = "com.qzone" // QQ空间app
    const val PACKAGE_NAME_SINA = "com.sina.weibo" // 新浪微博
    const val PACKAGE_NAME_QQMUSIC = "com.tencent.qqmusic" // QQ 音乐
    const val PACKAGE_NAME_NETEASE_MUSIC = "com.netease.cloudmusic" // 网易云音乐
    const val PACKAGE_NAME_KUGOU = "com.kugou.android" // 酷狗音乐
    const val PACKAGE_NAME_KUWO = "cn.kuwo.player" // 酷我音乐
    const val PACKAGE_NAME_DINGDING = "com.alibaba.android.rimet" // 钉钉
    const val PACKAGE_NAME_TENCENT_VIDEO = "com.tencent.qqlive" // 腾讯视频
    const val PACKAGE_NAME_BILIBILI = "tv.danmaku.bili" // 哔哩哔哩
    const val PACKAGE_NAME_TWITTER = "com.twitter.android" // twitter
    const val COMPONENT_NAME_TWITTER = "com.twitter.composer.ComposerActivity"
    const val PACKAGE_NAME_INSTAGRAM = "com.instagram.android" // ins
    const val PACKAGE_NAME_FACEBOOK = "com.facebook.katana" // facebook
    const val PACKAGE_NAME_WHATSAPP = "com.whatsapp" // whatsapp

    fun checkAppInstalled(context: Context, packageName: String?): Boolean {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = context.packageManager.getPackageInfo(packageName!!, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return packageInfo != null
    }

    fun installApk(activity: Activity, targetFilePath: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        val file = File(targetFilePath)
        //判断是否Android N 或更高的版本
        if (Build.VERSION.SDK_INT >= 24) {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            val contentUri =
                FileProvider.getUriForFile(activity, activity.packageName + ".fileProvider", file)
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive")
        } else {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
        }
        activity.startActivity(intent)
    }

    /**
     * 打开微信扫一扫
     */
    fun openWechatScan(activity: Activity) {
        try {
            val intent = activity.packageManager.getLaunchIntentForPackage("com.tencent.mm")
            intent?.putExtra("LauncherUI.From.Scaner.Shortcut", true)
            activity.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(activity.applicationContext, "打开失败", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 通知系统相册刷新
     *
     * @param filePath 图片路径
     */
    fun notifyGalleryRefresh(context: Context?, filePath: String?) {
        try {
            context!!.sendBroadcast(
                Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    UriUtils.getUri(context, UriUtils.UriType.IMAGE, File(filePath))
                )
            )
        } catch (e: Exception) {
            if (context == null || filePath == null) {
                return
            }
            MediaScannerConnection.scanFile(
                context, arrayOf(filePath), arrayOf("image/jpeg")
            ) { s, uri -> }
        }
    }

    /**
     * 启动app
     *
     * @param pkgName 包名
     */
    fun startAPP(context: Context, pkgName: String) {
        try {
            val intent = context.packageManager.getLaunchIntentForPackage(pkgName)
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 跳转应用市场
    const val MARKET_GOOGLE_PLAY = "com.android.vending"
    const val MARKET_HUAWEI = "com.huawei.appmarket"
    const val MARKET_XIAOMI = "com.xiaomi.market"
    const val MARKET_MEIZU = "com.meizu.mstore"
    const val MARKET_WANDOUJIA = "com.wandoujia.phoenix2"
    const val MARKET_360 = "com.qihoo.appstore"
    const val MARKET_YYB = "com.tencent.android.qqdownloader"
    const val MARKET_BAIDU = "com.baidu.appsearch"
    fun toMarket(context: Context, appPkg: String, marketPkg: String?): Boolean {
        return try {
            val uri = Uri.parse("market://details?id=$appPkg")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            if (marketPkg != null && marketPkg.trim { it <= ' ' }.length > 0) {
                // 如果没给市场的包名，则系统会弹出市场的列表让你进行选择。
                intent.setPackage(marketPkg.trim { it <= ' ' })
            }
            context.startActivity(intent)
            true
        } catch (ex: Exception) {
            ex.printStackTrace()
            false
        }
    }

    // 淘宝
    const val APP_TAOBAO = "com.taobao.taobao"

    // 京东
    const val APP_JINGDONG = "com.jingdong.app.mall"

    /**
     * 打开淘宝
     */
    fun startTaoBaoApp(activity: Activity) {
        if (checkAppInstalled(activity, APP_TAOBAO)) {
            startAPP(activity, APP_TAOBAO)
        } else {
            Toast.makeText(activity, "未安装淘宝客户端", Toast.LENGTH_SHORT).show()
        }
    }
}