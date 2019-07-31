package com.hzj.mytest.device;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 生成设备唯一UUID，App卸载也可用
 */
public class DeviceUUIDFactory {

    /**
     * 生成一个唯一的DeviceUUID，通过androidId，serialNumber，macAddress
     */
    public static String buildDeviceUUID(Context context) {
        String deviceUUID = "";
        String androidId = getAndroidId(context);

        if (TextUtils.isEmpty(androidId)) {
            deviceUUID = getUUID();
            System.out.println("from uuid. deviceUUID= " + deviceUUID);
            return deviceUUID;
        }

        if (Build.VERSION.SDK_INT >= 29) { //Android Q获取不到设备的macAddress和serial
            deviceUUID = androidId + getDeviceInfo();
            System.out.println("from android_id and device info. deviceUUID= " + deviceUUID);
            return deviceUUID;
        }

        String macAddress = getMacAddress(context);
        if (!TextUtils.isEmpty(macAddress)) {
            deviceUUID = androidId + macAddress;
            System.out.println("from android_id and macAddress. deviceUUID= " + deviceUUID);
            return deviceUUID;
        }

        String serialNumber = getSerial();
        if (!TextUtils.isEmpty(serialNumber)) {
            deviceUUID = androidId + serialNumber;
            System.out.println("from android_id and serialNumber. deviceUUID= " + deviceUUID);
            return deviceUUID;
        }

        if (TextUtils.isEmpty(deviceUUID)) {
            deviceUUID = getUUID();
            System.out.println("from uuid. deviceUUID= " + deviceUUID);
        }

        return deviceUUID;
    }

    private static String getAndroidId(Context context) {
        String androidId = Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (androidId == null || androidId.equals("9774d56d682e549c")) {
            androidId = "";
        }
        return androidId;
    }

    private static String getMacAddress(Context context) {
        String macAddress = null;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {// 6.0及以下
            macAddress = MacAddressUtil.getMacAddress(context);
        }
        if (TextUtils.isEmpty(macAddress) || macAddress.equals("02:00:00:00:00:00")) {
            macAddress = MacAddressUtil.getMacByJavaAPI();
        }
        if (TextUtils.isEmpty(macAddress) || macAddress.equals("02:00:00:00:00:00")) {
            macAddress = MacAddressUtil.getMachineHardwareAddress();
        }

        if (TextUtils.isEmpty(macAddress) || macAddress.equals("02:00:00:00:00:00")) {
            macAddress = "";
        } else {
            macAddress = macAddress.replace(":", "");
        }
        return macAddress;
    }

    /**
     * Android Q获取不到
     */
    private static String getSerial() {
        String serialNumber = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                Class var2 = Class.forName("android.os.Build");
                Method var3 = var2.getMethod("getSerial");
                serialNumber = (String) var3.invoke(var2);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
            serialNumber = Build.SERIAL;
        }
        if (serialNumber == null || Build.UNKNOWN.equals(serialNumber)) {
            serialNumber = null;
        }

        return serialNumber;
    }

    /**
     * 获得一个UUID
     */
    private static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        System.out.println("getUUID uuid= " + uuid);
        return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
    }

    private static String getDeviceInfo() {
        return Build.BOARD + ":"//oppo6771_17197
                + Build.BRAND + ":"//OPPO
                + Build.CPU_ABI + ":"//arm64-v8a
                + Build.DEVICE + ":"//PACM00
                + Build.MANUFACTURER + ":"//OPPO
                + Build.MODEL + ":"//PACM00
                + Build.PRODUCT;//PACM00

//        ::::::
//        oppo6771_17197-OPPO-arm64-v8a-PACM00-OPPO-PACM00-PACM00
    }
}
