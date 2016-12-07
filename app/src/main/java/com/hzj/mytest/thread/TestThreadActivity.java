package com.hzj.mytest.thread;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hzj.mytest.R;
import com.hzj.mytest.util.UUIDUtil;

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

    @OnClick({R.id.start, R.id.clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                test();
                break;
            case R.id.clear:
                PersonCache.getInstance().hasUpdatedData();
                break;
        }
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
                    Log.i(TAG, Thread.currentThread().getName() + " === set === " + person.toString());
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
                    if (person != null) {
                        Log.d(TAG, Thread.currentThread().getName() + " === get === " + person.toString());
                    } else {
                        Log.d(TAG, Thread.currentThread().getName() + " === get === " + null);
                    }

                }
            }).start();
        }
    }

}
