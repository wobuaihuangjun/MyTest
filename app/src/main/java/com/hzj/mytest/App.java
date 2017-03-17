package com.hzj.mytest;

import android.app.Application;
import android.os.Environment;

import com.tencent.mars.xlog.Log;
import com.tencent.mars.xlog.Xlog;

import java.io.File;


/**
 * Application
 * <p/>
 * Created by hzj on 2016/10/8.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        System.loadLibrary("stlport_shared");
        System.loadLibrary("marsxlog");

        final String SDCARD = getExternalCacheDir().getAbsolutePath();
        final String logPath = SDCARD + "/xtc/xlog";

        //init xlog
        if (BuildConfig.DEBUG) {
            Xlog.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, "", logPath, "MarsSample");
            Xlog.setConsoleLogOpen(true);
        } else {
            Xlog.appenderOpen(Xlog.LEVEL_INFO, Xlog.AppednerModeAsync, "", logPath, "MarsSample");
            Xlog.setConsoleLogOpen(false);
        }

        Log.setLogImp(new Xlog());

        File file = new File(logPath);

        System.out.print("文件是否存在：" + file.exists());

    }
}
