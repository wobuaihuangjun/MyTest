package com.hzj.mytest.h5;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.hzj.mytest.R;
import com.hzj.mytest.util.ScreenUtil;

public class TestWebViewActivity extends Activity {

    private static final String TAG = "TestWebViewActivity：";

    String HELP_INDEX =
            "http://static.watch.okii.com/watch/public/feedback/fiveg_wifi.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        WebView webView = (WebView)findViewById(R.id.web_view);


        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js

        /**
         * LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
         * LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
         * LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
         * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
         */
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.

        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);

        webView.loadUrl(HELP_INDEX);
    }

}
