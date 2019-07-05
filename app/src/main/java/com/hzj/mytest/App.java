package com.hzj.mytest;

import android.app.Application;
import android.os.Environment;

import com.getkeepsafe.relinker.ReLinker;
import com.tencent.mars.xlog.Log;
import com.tencent.mars.xlog.Xlog;


/**
 * Application
 * <p/>
 * Created by hzj on 2016/10/8.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

//        ReLinker.loadLibrary(this, "stlport_shared");
//        ReLinker.loadLibrary(this, "marsxlog");
//
//        final String SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();
//        final String logPath = SDCARD + "/xtc/xlog";
//
//        //init xlog
//        if (BuildConfig.DEBUG) {
//            Xlog.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, "", logPath, "MarsSample");
//            Xlog.setConsoleLogOpen(true);
//        } else {
//            Xlog.appenderOpen(Xlog.LEVEL_INFO, Xlog.AppednerModeAsync, "", logPath, "MarsSample");
//            Xlog.setConsoleLogOpen(false);
//        }
//
//        Log.setLogImp(new Xlog());
    }
}
