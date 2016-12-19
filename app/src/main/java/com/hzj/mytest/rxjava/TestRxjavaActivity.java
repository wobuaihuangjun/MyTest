package com.hzj.mytest.rxjava;

import android.app.Activity;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hzj.mytest.R;
import com.hzj.mytest.util.ThreadUtil;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class TestRxjavaActivity extends Activity {

    private static final String TAG = "TestRxjava-hzjdemo：";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @OnClick({R.id.test_delay, R.id.test_just, R.id.test_takeFirst})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test_delay:
                testDelay();
                break;
            case R.id.test_just:
                testJust();
                break;
            case R.id.test_takeFirst:
                testTakeFirst();
                break;
            default:
                break;
        }
    }

    private int count;

    private void testTakeFirst() {
        count++;
        Observable
                .just(count % 2 == 0)
                .takeFirst(new Func1<Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean aBoolean) {
                        Log.d(TAG, "takeFirst ，" + aBoolean);
                        return aBoolean;
                    }
                })
                .map(new Func1<Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean aBoolean) {
                        Log.d(TAG, "map ，" + aBoolean);
                        return aBoolean;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        Log.d(TAG, "Action1 ，" + aBoolean);
                    }
                });
    }

    private void testJust() {
        Log.d(TAG, "testJust");
        Observable.just("testJust")
                .map(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String ssid) {
                        Log.i(TAG, ssid);
                        ThreadUtil.throwExceptionInMainThread();
                        return true;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean result) {
                        Log.i(TAG, String.valueOf(result));
                    }
                });
    }

    private void testDelay() {
        Log.d(TAG, "testDelay");
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
