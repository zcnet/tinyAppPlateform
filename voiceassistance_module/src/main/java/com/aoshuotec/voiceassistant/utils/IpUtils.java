package com.aoshuotec.voiceassistant.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.z.tinyapp.utils.logs.sLog;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Created by sun on 2019/1/3
 */

public class IpUtils {
    private static final String TAG = "sunIpUtils";

    /**
     * 获取当前IP
     */
    public static String getIPAddress(Context context) {
        sLog.i(TAG, "getIPAddress: get ip start");
        ConnectivityManager manager = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE));
        if (manager == null) {
            Logg.i(TAG, "getIPAddress: get ip failed ConnectivityManager null");
            return null;
        }
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface netWorkInterface = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddress = netWorkInterface.getInetAddresses(); enumIpAddress.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddress.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                sLog.i(TAG, "getIPAddress: get ip success TYPE_MOBILE->"+inetAddress.getHostAddress());
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    sLog.e(TAG, "getIPAddress: get ip", e);
                }
            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                if (wifiManager == null) {
                    Logg.i(TAG, "getIPAddress: get ip failed WifiManager null");
                    return null;
                }
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                sLog.i(TAG, "getIPAddress: get ip success TYPE_WIFI ->"+intIP2StringIP(wifiInfo.getIpAddress()));
                return intIP2StringIP(wifiInfo.getIpAddress());
            }
        } else {
            //当前无网络连接,请在设置中打开网络
            Logg.i(TAG, "getIPAddress: get ip failed no internet");
            return null;
        }
        return null;
    }


    /**
     * 将得到的int类型的IP转换为String类型
     */
    private static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    public static String[] getAllNetInterface() {
        ArrayList<String> availableInterface = new ArrayList<>();
        String [] interfaces = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }

                    String ip = ia.getHostAddress();
                    sLog.d(TAG,"getAllNetInterface,available interface:"+ni.getName()+",address:"+ip);
                    // 过滤掉127段的ip地址
                    if (!"127.0.0.1".equals(ip)) {
                        availableInterface.add(ni.getName());
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        sLog.d(TAG,"all interface:"+availableInterface.toString());
        int size = availableInterface.size();
        if (size > 0) {
            interfaces = new String[size];
            for(int i = 0; i < size; i++) {
                interfaces[i] = availableInterface.get(i);
            }
        }
        return interfaces;
    }
}
