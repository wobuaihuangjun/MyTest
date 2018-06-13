package com.hzj.mytest.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * 手机屏幕工具类
 * <p>
 * Created by lhd on 2015/9/21.
 */
public class ScreenUtil {
    public static final String TAG = "ScreenUtil";

    //获得手机的宽度和高度像素单位为px
    // 通过WindowManager获取
    public static void getScreenDensity_ByWindowManager(Activity activity) {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();//屏幕分辨率容器
        activity.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;
        float density = mDisplayMetrics.density;
        int densityDpi = mDisplayMetrics.densityDpi;
        Log.d(TAG, "Screen Ratio: [" + width + "x" + height + "],density=" + density + ",densityDpi=" + densityDpi);
        Log.d(TAG, "Screen mDisplayMetrics: " + mDisplayMetrics);
    }

    // 通过Resources获取
    public static void getScreenDensity_ByResources(Activity activity) {
        DisplayMetrics mDisplayMetrics = activity.getResources().getDisplayMetrics();
        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;
        float density = mDisplayMetrics.density;
        int densityDpi = mDisplayMetrics.densityDpi;
        Log.d(TAG, "Screen Ratio: [" + width + "x" + height + "],density=" + density + ",densityDpi=" + densityDpi);
        Log.d(TAG, "Screen mDisplayMetrics: " + mDisplayMetrics);

    }

    // 获取屏幕的默认分辨率
    public static void getDefaultScreenDensity(Activity activity) {
        Display mDisplay = activity.getWindowManager().getDefaultDisplay();
        int width = mDisplay.getWidth();
        int height = mDisplay.getHeight();
        Log.d(TAG, "Screen Default Ratio: [" + width + "x" + height + "]");
        Log.d(TAG, "Screen mDisplay: " + mDisplay);
    }

    /**
     * 获取屏幕宽度(像素px)
     */
    public static int getPxWidth(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        metric = context.getResources().getDisplayMetrics();
        return metric.widthPixels;
    }

    /**
     * 获取屏幕高度(像素px)
     */
    public static int getPxHeight(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        metric = context.getResources().getDisplayMetrics();
        return metric.heightPixels;
    }

    /**
     * 判断手机是否是全面屏
     * 全面屏的定义：屏幕纵横比大于16:9 = 1.777778；屏占比大于80%
     * <p>
     * 所以可以认为当高宽比大于1.8，即为全面屏。
     * <p>
     * 小米MIX 17：9 = 1.8888；
     * MIX 2  18:9 = 2；
     * LG G6 18:9 = 2；
     * 三星S8  18.5:9 = 2.05；
     * Essential Phone 19:10 = 1.9
     * iPhone X  13：6 = 2.17
     */
    public static boolean isMaxAspect(Context context) {
        boolean isMaxAspect = false;
        // 手机宽高比大于默认的值1.8，即认为是全面屏
        double defaultRatio = 1.8;
        // 保留5位小数
        int scaleValue = 5;

        Log.d(TAG, "getPxHeight:" + getPxHeight(context));
        Log.d(TAG, "getPxWidth:" + getPxWidth(context));
        double aspectRatio = new BigDecimal((float) getPxHeight(context) / getPxWidth(context))
                .setScale(scaleValue, BigDecimal.ROUND_HALF_UP).doubleValue();

        Log.d(TAG, "aspectRatio:" + aspectRatio);

        if (aspectRatio >= defaultRatio) {
            isMaxAspect = true;
        }
        Log.d(TAG, "isMaxAspect:" + isMaxAspect);
        return isMaxAspect;
    }

    /**
     * 判断是否为刘海屏
     */
    public static boolean hasNotch(Context context) {
        if (!isMaxAspect(context)) {
            return false;
        }
//        if (DeviceUtil.isOPPODevice()) {
//            return hasNotchInOppo(context);
//        }
//        if (DeviceUtil.isVIVODevice()) {
//            return hasNotchInVivo(context);
//        }
        return false;
    }

    /**
     * 获取刘海高度
     */
    public static int getNotchHeight(Context context) {
        if (!isMaxAspect(context)) {
            return 0;
        }

//        if (DeviceUtil.isOPPODevice() && hasNotchInOppo(context)) {
//            return 80;// OPPO固定80px
//        }
        return 0;
    }

    /**
     * 判断设备是否为刘海屏 OPPO
     *
     * @param context Context
     * @return hasNotch
     */
    public static boolean hasNotchInOppo(Context context) {
        return context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }

    /**
     * 判断设备是否为刘海屏 VIVO
     * <p>
     * android.util.FtFeature
     * public static boolean isFeatureSupport(int mask);
     * <p>
     * 参数:
     * 0x00000020表示是否有凹槽;
     * 0x00000008表示是否有圆角。
     *
     * @param context Context
     * @return hasNotch
     */
    private static boolean hasNotchInVivo(Context context) {
        boolean hasNotch = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class ftFeature = cl.loadClass("android.util.FtFeature");
            Method[] methods = ftFeature.getDeclaredMethods();
            if (methods != null) {
                for (int i = 0; i < methods.length; i++) {
                    Method method = methods[i];
                    if (method.getName().equalsIgnoreCase("isFeatureSupport")) {
                        hasNotch = (boolean) method.invoke(ftFeature, 0x00000020);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            hasNotch = false;
        }
        return hasNotch;
    }


    /**
     * 判断设备是否为刘海屏 HUAWEI
     * com.huawei.android.util.HwNotchSizeUtil
     * public static boolean hasNotchInScreen()
     *
     * @param context Context
     * @return hasNotch
     */
    public static boolean hasNotchInHuaWei(Context context) {
        boolean hasNotch = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            hasNotch = (boolean) get.invoke(HwNotchSizeUtil);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNotch;
    }

    /**
     * 获取状态栏高度(像素px)
     *
     * @param activity
     * @return
     */
    public static int getStatusBarPxHeight(Activity activity) {
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);

        int height = localRect.top;
        if (height != 0) {
            return height;
        }

        try {
            Class<?> localClass = Class.forName("com.android.internal.R$dimen");
            Object localObject = localClass.newInstance();
            int i5 = Integer.parseInt(localClass.getField("status_bar_height")
                    .get(localObject)
                    .toString());

            height = activity.getResources().getDimensionPixelSize(i5);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return height;
    }

    /**
     * 设置全屏
     */
    public static void setScreenFull(Activity context, boolean isFull) {
        if (isFull) {
            context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }
    }


    public static void fullScreen(Activity context) {
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            context.getWindow().getDecorView().setSystemUiVisibility(
                    flags | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.getWindow().getDecorView().setSystemUiVisibility(flags);
        }
    }

    /**
     * 获取 屏幕高度
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int height = 0;
        Resources resources = context.getResources();
        int statusBarId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (statusBarId > 0) {
            height = resources.getDimensionPixelSize(statusBarId);
        }
        return height;
    }

    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 判断触摸点(x,y)是否在view的区域内
     */
    public static boolean isTouchPointInView(View view, int x, int y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        return y >= top && y <= bottom && x >= left
                && x <= right;
    }

}
