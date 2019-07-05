package com.hzj.mytest.stroe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.hzj.mytest.R;
import com.tencent.mmkv.MMKV;

public class TestStoreActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "TestStore-hzjdemo：";

    MMKV kv;
    SharedTool sharedTool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        findViewById(R.id.start1).setOnClickListener(this);
        findViewById(R.id.start2).setOnClickListener(this);
        findViewById(R.id.start3).setOnClickListener(this);

        kv = MMKV.defaultMMKV();
        sharedTool = SharedTool.getInstance(this);
    }

    private void testMMKV() {
        System.out.println("testMMKV");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            kv.encode("test" + i, "test Hello World" + i);
        }
        System.out.println("testMMKV time: " + (System.currentTimeMillis() - start));
    }

    private void testSp() {
        System.out.println("testSp");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            sharedTool.saveString("test" + i, "test Hello World" + i);
        }
        System.out.println("testSp time: " + (System.currentTimeMillis() - start));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start1:
                testMMKV();
                break;
            case R.id.start2:
                testSp();
                break;
            case R.id.start3:

                break;
        }
    }

}
