package com.hzj.mytest.voice;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.Environment;

import com.hzj.mytest.R;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by hzj on 2017/2/10.
 */

public class TestAudioTrackActivity extends Activity {
    private AudioTrack audioTrack = null;
    private Thread writePCMThread = null;
    private FileInputStream fileInputStream = null;
    private byte buffer[] = new byte[16 * 10000];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jlayer);
    }

    public void finish() {
        super.finish();
        System.out.println("finish");
        try {
            writePCMThread.interrupt();
        } catch (Exception e) {
        }
        try {
            fileInputStream.close();
        } catch (Exception e) {
        }
        try {
            audioTrack.stop();
            audioTrack.release();
        } catch (Exception e) {
        }
    }


    protected void onResume() {
        super.onResume();
        System.out.println("back on!!!!!!!!!!!");
        initAudioTrack();
        File audioFile = new File(Environment.getExternalStorageDirectory().getPath(), "testwav.wav");
        System.out.println(audioFile.length());
        try {
            fileInputStream = new FileInputStream(audioFile);
            fileInputStream.skip(0x2c);
        } catch (Exception e) {
        }

        writePCMThread = new Thread(new Runnable() {
            public void run() {
                try {
                    while (fileInputStream.read(buffer) >= 0) {
                        System.out.println("write pcm data");
                        audioTrack.write(buffer, 0, buffer.length);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        writePCMThread.start();
    }

    private void initAudioTrack() {
        int minBufferSize = AudioTrack.getMinBufferSize(0xac44, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT);
        System.out.println("minBufferSize = " + minBufferSize);
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 0xac44,
                AudioFormat.CHANNEL_CONFIGURATION_STEREO, AudioFormat.ENCODING_PCM_16BIT, minBufferSize * 2, AudioTrack.MODE_STREAM);
        audioTrack.setStereoVolume(1.0f, 1.0f);// 设置当前音量大小
        System.out.println("initAudioTrack over");
        audioTrack.play();
    }
}
