package com.hzj.mytest.thread;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hzj.mytest.R;
import com.hzj.mytest.util.UUIDUtil;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestThreadActivity extends Activity {

    private static final String TAG = "hzjdemo：";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.start, R.id.clear, R.id.map_test})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                test();
                break;
            case R.id.clear:
                PersonCache.getInstance().hasUpdatedData();
                break;
            case R.id.map_test:
                map_test();
                break;
        }
    }

    private void map_test() {
        Map<String, Object> map1 = new HashMap<>();// 线程不安全
        Map<String, Object> map2 = new Hashtable<>(); // 线程安全
        Map<String, Object> map3 = new ConcurrentHashMap<>();// 线程安全

        map1.put("key", "value");
        map2.put("key", "value");
        map3.put("key", "value");

        int count = 100000;
        long startTime = System.nanoTime();

        for (int i = 0; i < count; i++) {
            map1.get("key");
        }
        long endTime = System.nanoTime();
        Log.i(TAG, " === map1 useTime === " + (endTime - startTime));

        startTime = System.nanoTime();
        for (int i = 0; i < count; i++) {
            map2.get("key");
        }
        endTime = System.nanoTime();
        Log.i(TAG, " === map2 useTime === " + (endTime - startTime));

        startTime = System.nanoTime();
        for (int i = 0; i < count; i++) {
            map3.get("key");
        }
        endTime = System.nanoTime();
        Log.i(TAG, " === map3 useTime === " + (endTime - startTime));
    }

    int count = 0;
    String ID = UUIDUtil.getUUID();

    private void test() {
        Log.d(TAG, " === test === " + ID);
        new Thread(new Runnable() {
            @Override
            public void run() {
                write();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                read();
            }
        }).start();
    }

    private void write() {
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Person person = new Person();
                    person.setId(ID);
                    person.setValue(count++);
                    PersonCache.getInstance().setPerson(person);
                }
            }).start();
        }
    }

    private void read() {
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Person person = PersonCache.getInstance().getPerson(ID);
                }
            }).start();
        }
    }

}
