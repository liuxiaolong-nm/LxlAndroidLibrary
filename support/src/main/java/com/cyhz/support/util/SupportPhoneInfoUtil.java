package com.cyhz.support.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 *
 * 设备信息工具类
 *
 * 需要权限
 * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
 * Created by qinghua on 2015/6/16.
 */
public class SupportPhoneInfoUtil {

    /**
     * 获取手机MAC地址
     * @param context
     * @return
     */
    public static String getLocalMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    /**
     * 获取手机IMEI号
     * @param context
     * @return
     */
    public static String getIMEI(Context context){
        return ((TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE)).getDeviceId();
    }

    /**
     * 获取AndroidID
     * @param context
     * @return
     */
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取手机系统版本。
     *
     * @return
     */
    public static String getOsVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号。
     *
     * @return
     */
    public static String getDeviceSeries() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取项目版本号。
     *
     * @param context
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public static String getAppVersion(Context context) throws PackageManager.NameNotFoundException {
        return getPackageInfo(context).versionName;
    }

    /**
     * 获取项目包信息。
     *
     * @param context
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public static PackageInfo getPackageInfo(Context context) throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
    }

}
