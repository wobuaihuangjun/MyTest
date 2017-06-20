package com.hzj.mytest.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by huangzj on 2016/3/17.
 */
public class JavaDomain {

    public static void main(String[] args) {

        startScan(110, new int[]{1, 6, 11});

    }

    public static boolean startScan(int chtime, int[] channels) {
        boolean result = false;
        try {
            Class<?> c = Class.forName("com.hzj.mytest.reflect.TestReflect");
            Method startScan = c.getDeclaredMethod("startScan", int.class,
                    channels.getClass());
            startScan.setAccessible(true);
            result = (boolean) startScan.invoke(c.newInstance(), chtime, channels);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean startScan(int chtime) {
        boolean result = false;
        try {
            Class<?> c = Class.forName("com.hzj.mytest.reflect.TestReflect");
            Method startScan = c.getMethod("startScan", int.class);
            result = (boolean) startScan.invoke(c.newInstance(), chtime);

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return result;
    }
}
