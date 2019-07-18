package com.hzj.mytest.device;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.Locale;

/**
 * 跟手机设备相关的工具类
 * <p>
 * Created by hzj on 2018/3/20.
 */

public class DeviceUtil {
    public static final String TAG = "DeviceUtil";

    private static final String OPPO_PHONE = "oppo";

    private static final String VIVO_PHONE = "vivo";

    private static final String HTC_PHONE = "htc";

    private static final String HUAWEI_PHONE = "huawei";

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }


    /**
     * 获取手机品牌
     */
    public static String getPhoneBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机生产商
     */
    public static String getPhoneProduct() {
        return android.os.Build.MANUFACTURER;
    }

    /**
     * 获取android系统版本
     *
     * @return
     */
    public static String getAndroidOsVersion() {
        return android.os.Build.VERSION.RELEASE;
    }


    /**
     * 返回手机运营商名称
     */
    public static String getProvidersName(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSimOperatorName();
    }

    /**
     * 获取 APP 当前语言
     *
     * @return APP 当前语言, zh, en, th...
     */
    public static String getAppLanguage() {
        Locale locale = Locale.getDefault();
        return locale.getLanguage();
    }

    private static boolean isSameDevice(String deviceName) {
        String product = getPhoneProduct();
        if (!TextUtils.isEmpty(product) && product.toLowerCase(Locale.ENGLISH).contains(deviceName)) {
            return true;
        }

        String brand = getPhoneBrand();
        if (!TextUtils.isEmpty(brand) && brand.toLowerCase(Locale.ENGLISH).contains(deviceName)) {
            return true;
        }

        String model = getPhoneModel();
        return !TextUtils.isEmpty(model) && model.toLowerCase(Locale.ENGLISH).contains(deviceName);
    }

    public static boolean isOPPODevice() {
        return isSameDevice(OPPO_PHONE);
    }

    public static boolean isVIVODevice() {
        return isSameDevice(VIVO_PHONE);
    }

    public static boolean isHtcDevice() {
        return isSameDevice(HTC_PHONE);
    }

    public static boolean isHuaweiDevice() {
        return isSameDevice(HUAWEI_PHONE);
    }

}
