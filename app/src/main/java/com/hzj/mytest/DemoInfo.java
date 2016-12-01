package com.hzj.mytest;

import android.app.Activity;

/**
 * Created by hzj on 2016/10/24.
 */
public class DemoInfo {

    public int title;
    public Class<? extends Activity> demoClass;

    public DemoInfo(int title,
                    Class<? extends Activity> demoClass) {
        this.title = title;
        this.demoClass = demoClass;
    }
}
