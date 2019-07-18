package com.hzj.mytest.device;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;

/**
 * Mac地址获取工具类
 */
public class MacAddressUtil {

    /**
     * android 7.0以下 获取mac地址
     */
    public static String getMacAddress(Context context) {
        // 如果是6.0以下，直接通过wifimanager获取
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            String macAddress = getMacAddress0(context);
            if (!TextUtils.isEmpty(macAddress) && !macAddress.equals("02:00:00:00:00:00")) {
                return macAddress;
            }
        }
        return getMacShell();
    }

    private static String getMacAddress0(Context context) {
        if (checkPermission(context, "android.permission.ACCESS_WIFI_STATE")) {
            WifiManager wifiMgr = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            try {
                WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
                return wifiInfo.getMacAddress();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    /**
     * android 7.0及以上 （1）根据IP地址获取MAC地址，关闭WiFi获取不到
     */
    public static String getMacAddressForOpen() {
        String strMacAddr = null;
        try {
            // 获得IpD地址
            InetAddress ip = getLocalInetAddress();
            byte[] b = NetworkInterface.getByInetAddress(ip)
                    .getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strMacAddr;
    }

    /**
     * 获取移动设备本地IP
     */
    private static InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            // 列举
            Enumeration<NetworkInterface> en_netInterface = NetworkInterface.getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) {// 是否还有元素
                NetworkInterface ni = en_netInterface
                        .nextElement();// 得到下一个元素
                Enumeration<InetAddress> en_ip = ni.getInetAddresses();// 得到一个ip地址的列举
                while (en_ip.hasMoreElements()) {
                    ip = en_ip.nextElement();
                    if (!ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":") == -1)
                        break;
                    else
                        ip = null;
                }

                if (ip != null) {
                    break;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ip;
    }

    /**
     * android 7.0及以上 （2）扫描各个网络接口获取mac地址,获取设备HardwareAddress地址，重启会变化
     */
    public static String getMachineHardwareAddress() {
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        String hardWareAddress = null;
        NetworkInterface iF = null;
        if (interfaces == null) {
            return null;
        }
        while (interfaces.hasMoreElements()) {
            iF = interfaces.nextElement();
            try {
                hardWareAddress = bytesToString(iF.getHardwareAddress());
                if (hardWareAddress != null)
                    break;
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        return hardWareAddress;
    }

    /***
     * byte转为String
     */
    private static String bytesToString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        for (byte b : bytes) {
            buf.append(String.format("%02X:", b));
        }
        if (buf.length() > 0) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }


    /**
     * 未开启WiFi的情况也能获取到mac地址
     */
    public static String getMacByJavaAPI() {
        try {
            Enumeration var0 = NetworkInterface.getNetworkInterfaces();

            NetworkInterface var1;
            do {
                if (!var0.hasMoreElements()) {
                    return null;
                }

                var1 = (NetworkInterface) var0.nextElement();
            } while (!"wlan0".equals(var1.getName()) && !"eth0".equals(var1.getName()));

            byte[] var2 = var1.getHardwareAddress();
            if (var2 != null && var2.length != 0) {
                StringBuilder var3 = new StringBuilder();
                byte[] var4 = var2;
                int var5 = var2.length;

                for (int var6 = 0; var6 < var5; ++var6) {
                    byte var7 = var4[var6];
                    var3.append(String.format("%02X:", var7));
                }

                if (var3.length() > 0) {
                    var3.deleteCharAt(var3.length() - 1);
                }

                return var3.toString().toLowerCase(Locale.getDefault());
            } else {
                return null;
            }
        } catch (Throwable var8) {
            var8.printStackTrace();
            return null;
        }
    }

    /**
     * 不一定能获取到，但是准确
     */
    private static String getMacShell() {
        try {
            String[] var0 = new String[]{"/sys/class/net/wlan0/address", "/sys/class/net/eth0/address", "/sys/devices/virtual/net/wlan0/address"};

            for (int var2 = 0; var2 < var0.length; ++var2) {
                try {
                    String var1 = reaMac(var0[var2]);
                    if (var1 != null) {
                        return var1;
                    }
                } catch (Throwable var4) {
                    var4.printStackTrace();
                }
            }
        } catch (Throwable var5) {
            var5.printStackTrace();
        }

        return null;
    }

    private static String reaMac(String var0) {
        String var1 = null;

        try {
            FileReader var2 = new FileReader(var0);
            BufferedReader var3 = null;
            if (var2 != null) {
                try {
                    var3 = new BufferedReader(var2, 1024);
                    var1 = var3.readLine();
                } finally {
                    if (var2 != null) {
                        try {
                            var2.close();
                        } catch (Throwable var14) {
                            var14.printStackTrace();
                        }
                    }

                    if (var3 != null) {
                        try {
                            var3.close();
                        } catch (Throwable var13) {
                            var13.printStackTrace();
                        }
                    }

                }
            }
        } catch (Throwable var16) {
            var16.printStackTrace();
        }

        return var1;
    }


    private static boolean checkPermission(Context context, String permission) {
        if (context == null) {
            return false;
        }
        boolean isGranted = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class var3 = Class.forName("android.content.Context");
                Method var4 = var3.getMethod("checkSelfPermission", String.class);
                int var5 = (Integer) var4.invoke(context, permission);
                if (var5 == 0) {
                    isGranted = true;
                } else {
                    isGranted = false;
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                isGranted = false;
            }
        } else {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                isGranted = true;
            }
        }

        return isGranted;

    }



}
