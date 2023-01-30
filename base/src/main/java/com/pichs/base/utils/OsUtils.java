package com.pichs.base.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.RequiresPermission;

import java.io.File;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 系统服务和系统参数获取工具类
 */
public class OsUtils {

    private static String carrier;
    // 系统服务管理器
    private static PackageManager packageManager;
    private static WifiManager wifiManager;
    private static WindowManager windowManager;
    private static TelephonyManager telephonyManager;
    private static ConnectivityManager connectivityManager;
    private static String imei;
    private static String imsi;
    private static String mac;
    private static String androidId;
    private static String networkType;
    private static String serial;

    /**
     * 判断service是否在运行
     */
    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int max = 1000;
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(max);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className)) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    public static ConnectivityManager getConnectivityManager(Context context) {
        if (connectivityManager == null) {
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        return connectivityManager;
    }

    public static TelephonyManager getTelephonyManager(Context context) {
        if (telephonyManager == null) {
            telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        }
        return telephonyManager;
    }

    public static WindowManager getWindowManager(Context context) {
        if (windowManager == null) {
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return windowManager;
    }

    @SuppressLint("WifiManagerPotentialLeak")
    public static WifiManager getWifiManager(Context context) {
        if (wifiManager == null) {
            wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        }
        return wifiManager;
    }

    public static PackageManager getPackageManager(Context context) {
        if (packageManager == null) {
            packageManager = context.getApplicationContext().getPackageManager();
        }
        return packageManager;
    }

    public static ApplicationInfo getApplicationInfo(Context context) {
        try {
            return getPackageManager(context).getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAppFilePath(Context context) {
        try {
            ApplicationInfo applicationInfo = getApplicationInfo(context);
            return applicationInfo == null ? null : applicationInfo.sourceDir;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 获取app的VersionName ，即app的版本号
    public static String getVersionName(Context context) {
        String packName = context.getPackageName();
        try {
            PackageManager pm = getPackageManager(context);
            return pm.getPackageInfo(packName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    // 获取app的VersionCode，即app的版本code
    public static int getVersionCode(Context context) {
        String packName = context.getPackageName();
        try {
            PackageManager pm = getPackageManager(context);
            return pm.getPackageInfo(packName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    // 获取app的name
    public static String getAppName(Context context) {
        ApplicationInfo appInfo = getApplicationInfo(context);
        return (String) (appInfo != null ? getPackageManager(context).getApplicationLabel(appInfo) : "");
    }

    // 获取app的Logo图片
    public static Drawable getAppIcon(Context context) {
        return getPackageManager(context).getApplicationIcon(getApplicationInfo(context));
    }

    // 获取时区名字
    public static String getTimeZoneName() {
        return getTimeZone().getID();
    }

    // 获取时区偏差
    public static long getTimeZoneOffset() {
        return getTimeZone().getOffset(System.currentTimeMillis());
    }

    // 获取时区
    public static TimeZone getTimeZone() {
        return TimeZone.getDefault();
    }

    // 获取manufacturer
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    // 获取设备名字
    public static String getModel() {
        return Build.MODEL;
    }

    // 获取设备名字
    public static String getBrand() {
        return Build.BRAND;
    }

    // 获取系统版本
    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    // 获取Carrier
    public static String getCarrier(Context context) {
        if (TextUtils.isEmpty(carrier)) {
            try {
                if (getTelephonyManager(context).getSimState() == TelephonyManager.SIM_STATE_READY) {
                    carrier = OsUtils.getTelephonyManager(context).getSimOperatorName();
                }
            } catch (Exception e) {
                return null;
            }
        }
        return carrier;
    }

    public static String getLocale(Context context) {
        Resources res = context.getResources();
        Configuration conf = res.getConfiguration();
        Locale locale = conf.locale;
        return locale.toString();
    }

    // 获取app的rootDir
    public static String getAppRootDir(Context context) {
        String root = null;
        File cacheDir = null;
        // Get external cache directory
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            cacheDir = context.getExternalCacheDir();
        }
        // Get internal cache directory if sdcard is not mounted
        if (cacheDir == null) {
            cacheDir = context.getCacheDir();
        }
        if (cacheDir == null) {
        } else {
            root = cacheDir.getAbsolutePath();
        }
        return root;
    }


    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getImei(Context context) {
        if (TextUtils.isEmpty(imei)) {
            try {
                imei = getTelephonyManager(context).getDeviceId();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return imei;
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getImsi(Context context) {
        if (TextUtils.isEmpty(imsi)) {
            try {
                imsi = getTelephonyManager(context).getSubscriberId();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return imsi;
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public static String getMacAddressStr(Context context) {
        if (TextUtils.isEmpty(mac)) {
            try {
                mac = getLocalMacAddress(context);
                if (TextUtils.isEmpty(mac)) {
                    mac = getWifiManager(context).getConnectionInfo().getMacAddress();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mac;
    }

    private static String getLocalMacAddress(Context context) {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return null;
                }
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }
                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @SuppressLint("HardwareIds")
    public static String getAndroidIdStr(Context context) {
        if (TextUtils.isEmpty(androidId)) {
            try {
                androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return androidId;
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static String getNetworkTypeStr(Context context) {
        if (TextUtils.isEmpty(networkType)) {
            try {
                NetworkInfo info = getConnectivityManager(context).getActiveNetworkInfo();
                if (info != null) {
                    networkType = info.getTypeName();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return networkType;
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getSerial() {
        if (serial != null && !"unknown".equalsIgnoreCase(serial)) {
            return serial;
        }
        // 9.0适配，想要获取此属性，必须有READ_PHONE_STATE权限，否则为unknown
        if (Build.VERSION.SDK_INT >= 28) {
            try {
                serial = Build.getSerial();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                serial = Build.SERIAL;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (TextUtils.isEmpty(serial)) {
            serial = "unknown";
        }
        return serial;
    }

    public static String getCountry() {
        if (Build.VERSION.SDK_INT >= 24) {
            return Locale.getDefault(Locale.Category.DISPLAY).getCountry();
        } else {
            return Locale.getDefault().getCountry();
        }
    }

    public static String getLanguage() {
        if (Build.VERSION.SDK_INT >= 24) {
            return Locale.getDefault(Locale.Category.DISPLAY).getLanguage();
        } else {
            return Locale.getDefault().getLanguage();
        }
    }


    /**
     * 获取网络IP地址(优先获取wifi地址)
     *
     * @param ctx Context
     * @return String ip
     */
    public static String getIPAddress(Context ctx) {
        try {
            WifiManager wifiManager = (WifiManager) ctx.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifiManager != null) {
                return wifiManager.isWifiEnabled() ? getWifiIP(wifiManager) : getLocalIpAddress();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /*
     * 获取WIFI连接下的ip地址
     * 者必须要权限ACCESS_NETWORK_STATE，和wifi的权限
     */
    private static String getWifiIP(WifiManager wifiManager) {
        try {
            @SuppressLint("MissingPermission")
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String ip;
            if (wifiInfo != null) {
                ip = intToIp(wifiInfo.getIpAddress());
                return ip;
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    private static String getLocalIpAddress() {
        String ipv4 = "";
        try {
            ArrayList<NetworkInterface> nilist = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface ni : nilist) {
                ArrayList<InetAddress> ialist = Collections.list(ni.getInetAddresses());
                for (InetAddress address : ialist) {
                    if (!address.isLoopbackAddress() && address instanceof Inet4Address) {
                        ipv4 = address.getHostAddress();
                    }
                }

            }

        } catch (SocketException ex) {
            ipv4 = "";
        }
        return ipv4;
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
    }

    /**
     * 获取屏幕宽
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕高
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕真实宽
     *
     * @param context Context
     * @return RealScreenWidth
     */
    public static int getRealScreenWidth(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return getScreenWidth(context);
        } else {
            Display display = getWindowManager(context).getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            @SuppressWarnings("rawtypes")
            Class c;
            try {
                c = Class.forName("android.view.Display");
                @SuppressWarnings("unchecked")
                Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, displayMetrics);
                return displayMetrics.widthPixels;
            } catch (Exception e) {
                return getScreenWidth(context);
            }
        }
    }

    /**
     * 获取屏幕真实高
     *
     * @param context Context
     * @return RealScreenHeight
     */
    public static int getRealScreenHeight(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return getScreenHeight(context);
        } else {
            Display display = getWindowManager(context).getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            @SuppressWarnings("rawtypes")
            Class c;
            try {
                c = Class.forName("android.view.Display");
                @SuppressWarnings("unchecked")
                Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, displayMetrics);
                return displayMetrics.heightPixels;
            } catch (Exception e) {
                return getScreenHeight(context);
            }
        }
    }

    /**
     * 获取屏幕物理高
     *
     * @param context Context
     * @return getPhysicalHeight
     */
    public static int getPhysicalHeight(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return getScreenWidth(context);
        } else {
            Display display = getWindowManager(context).getDefaultDisplay();
            @SuppressWarnings("rawtypes")
            Class c;
            try {
                c = Class.forName("android.view.Display");
                @SuppressWarnings("unchecked")
                Method method = c.getMethod("getPhysicalHeight");
                return (int) method.invoke(display);
            } catch (Exception e) {
                return getRealScreenHeight(context);
            }
        }
    }

    /**
     * 获取屏幕物理宽
     *
     * @param context Context
     * @return getPhysicalWidth
     */
    public static int getPhysicalWidth(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return getScreenHeight(context);
        } else {
            Display display = getWindowManager(context).getDefaultDisplay();
            @SuppressWarnings("rawtypes")
            Class c;
            try {
                c = Class.forName("android.view.Display");
                @SuppressWarnings("unchecked")
                Method method = c.getMethod("getPhysicalWidth");
                return (int) method.invoke(display);
            } catch (Exception e) {
                return getRealScreenWidth(context);
            }
        }
    }

    /**
     * 判断手机是否包插了SIM卡
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static boolean hasSIMCard(Context context) {
        TelephonyManager telMgr = getTelephonyManager(context);
        if (telMgr == null) {
            return false;
        }
        boolean result = true;
        try {
            int simState = telMgr.getSimState();
            switch (simState) {
                case TelephonyManager.SIM_STATE_ABSENT:
                case TelephonyManager.SIM_STATE_UNKNOWN:
                    result = false;
                    break;
            }
        } catch (Exception e) {
            result = false;
        }
        return result;
    }
}
