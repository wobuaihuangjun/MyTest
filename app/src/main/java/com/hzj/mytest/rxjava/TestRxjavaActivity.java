package com.hzj.mytest.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.hzj.mytest.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class TestRxjavaActivity extends Activity {

    private static final String TAG = "TestRxjava-hzjdemo：";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

        test();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void test() {
        Log.d(TAG, "begin");
        Observable.just(null)
                .observeOn(AndroidSchedulers.mainThread())
                .delaySubscription(3000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        Log.d(TAG, "end");
                    }
                });
    }

}
