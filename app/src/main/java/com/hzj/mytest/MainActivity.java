package com.hzj.mytest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hzj.mytest.collection.TestCollectionActivity;
import com.hzj.mytest.handler.TestHandlerActivity;
import com.hzj.mytest.rxjava.TestRxjavaActivity;
import com.hzj.mytest.thread.TestThreadActivity;
import com.hzj.mytest.view.TestViewActivity;
import com.hzj.mytest.voice.VoiceEncryptionActivity;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity-hzjdemo：";

    private static final DemoInfo[] DEMOS = {
            new DemoInfo(R.string.test_collection, TestCollectionActivity.class),
            new DemoInfo(R.string.test_handler, TestHandlerActivity.class),
            new DemoInfo(R.string.test_rxjava, TestRxjavaActivity.class),
            new DemoInfo(R.string.test_view, TestViewActivity.class),
            new DemoInfo(R.string.test_thread, TestThreadActivity.class),
            new DemoInfo(R.string.test_voice, VoiceEncryptionActivity.class),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.lv_module_list);

        DemoListAdapter adapter = new DemoListAdapter(this, DEMOS);
        adapter.setOnItemClickListener(new DemoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                onListItemClick(position);
            }
        });

        listView.setAdapter(adapter);
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
