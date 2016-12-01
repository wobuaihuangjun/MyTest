package com.hzj.mytest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hzj.mytest.handler.DataListener;
import com.hzj.mytest.handler.DataUpdateThreadMode;
import com.hzj.mytest.handler.DealHandler;
import com.hzj.mytest.handler.TestHandlerActivity;
import com.hzj.mytest.rxjava.TestRxjavaActivity;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity-hzjdemo：";

    private static final DemoInfo[] DEMOS = {
            new DemoInfo(R.string.test_handler, TestHandlerActivity.class),
            new DemoInfo(R.string.test_rxjava, TestRxjavaActivity.class),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.lv_module_list);

        listView.setAdapter(new DemoListAdapter(this, DEMOS));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemClick(position);

            }
        });
    }

    void onListItemClick(int index) {
        Intent intent = new Intent(MainActivity.this, DEMOS[index].demoClass);
        this.startActivity(intent);
    }

}
