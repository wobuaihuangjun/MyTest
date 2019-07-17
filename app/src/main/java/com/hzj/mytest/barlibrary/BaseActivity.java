package com.hzj.mytest.barlibrary;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

import com.hzj.mytest.R;

/**
 * Activity基类
 */
public abstract class BaseActivity extends Activity {

    private static final String TAG = "BaseActivity";

    protected ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //禁止横屏切换

        // 设置沉浸式状态栏
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
    }

    /**
     * 初始化沉浸式状态栏
     */
    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .fitsSystemWindows(true);

        if (isImmersionBarWhite()) {
            if (ImmersionBarUtil.isSupportWhiteStatusBarDevice()) {
                mImmersionBar.statusBarDarkFont(true, 0.2f);
            } else {
                mImmersionBar.statusBarColor(R.color.black_525252);
            }
        }

        mImmersionBar.init();
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 是否使用白色状态栏
     *
     * @return the boolean
     */
    protected boolean isImmersionBarWhite() {
        return true;
    }
}
