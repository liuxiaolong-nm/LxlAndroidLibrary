package com.cyhz.support.util;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.util.List;

/**
 * Created by liuxiaolong on 16/8/13.
 */
public class SupportNetUtil {

    public static boolean isNetworkEnable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService("connectivity");
        if(connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if(info != null) {
                for(int i = 0; i < info.length; ++i) {
                    if(info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static boolean isGpsEnabled(Context context) {
        LocationManager locationManager = (LocationManager)context.getSystemService("location");
        List accessibleProviders = locationManager.getProviders(true);
        return accessibleProviders != null && accessibleProviders.size() > 0;
    }

    public static boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.getType() == 1;
    }

    public static boolean isMobleNet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.getType() == 0;
    }

    public static String getProvider(Context context) {
        String provider = "无";

        try {
            TelephonyManager e = (TelephonyManager)context.getSystemService("phone");
            String IMSI = e.getSubscriberId();
            if(IMSI == null) {
                if(5 == e.getSimState()) {
                    String operator = e.getSimOperator();
                    if(operator != null) {
                        if(!operator.equals("46000") && !operator.equals("46002") && !operator.equals("46007")) {
                            if(operator.equals("46001")) {
                                provider = "中国联通";
                            } else if(operator.equals("46003")) {
                                provider = "中国电信";
                            }
                        } else {
                            provider = "中国移动";
                        }
                    }
                }
            } else if(!IMSI.startsWith("46000") && !IMSI.startsWith("46002") && !IMSI.startsWith("46007")) {
                if(IMSI.startsWith("46001")) {
                    provider = "中国联通";
                } else if(IMSI.startsWith("46003")) {
                    provider = "中国电信";
                }
            } else {
                provider = "中国移动";
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return provider;
    }
}
