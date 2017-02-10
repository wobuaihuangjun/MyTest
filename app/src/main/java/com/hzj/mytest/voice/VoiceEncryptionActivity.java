package com.hzj.mytest.voice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hzj.mytest.R;

public class VoiceEncryptionActivity extends Activity implements
        OnClickListener {

    private static final String TAG = "VoiceEncryptionActivity";
    private static final String seed = "guess"; // 种子
    private MediaPlayer mPlayer;
    private File oldFile;
    // 音频文件的路径，在res\raw\recording_old.3gpp中找到音频文件，再放到外部存储的根目录下。用于测试
    private FileInputStream fis = null;
    private FileOutputStream fos = null;

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_encryption);
        Button mPlayButton = (Button) findViewById(R.id.playButton);
        mPlayButton.setOnClickListener(this);
        Button mEncryptionButton = (Button) findViewById(R.id.encryptionButton);
        mEncryptionButton.setOnClickListener(this);
        Button mDecryptionButton = (Button) findViewById(R.id.decryptionButton);
        mDecryptionButton.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.file_name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    boolean isSuccess = true;

    private boolean prepare() {
        String fileName = editText.getText().toString();
        oldFile = new File(Environment.getExternalStorageDirectory().getPath(),
                TextUtils.isEmpty(fileName) ? "recording_old.3gpp" : fileName);
        if (!oldFile.exists()) {
            Toast.makeText(this, "文件获取失败", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (!prepare()) {
            return;
        }
        switch (v.getId()) {
            case R.id.playButton:
                play();
                break;
            case R.id.encryptionButton:
                // 加密保存
                encrypt();
                break;
            case R.id.decryptionButton:
                // 解密保存
                decryption();
                break;
            default:
                break;
        }
    }

    private void play() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
        // mPlayer = MediaPlayer.create(this, R.raw.recording_old);

        try {
            fis = new FileInputStream(oldFile);
            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(fis.getFD());
            mPlayer.prepare(); // 去掉会出错
            mPlayer.start();
        } catch (FileNotFoundException e) {
            isSuccess = false;
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            isSuccess = false;
            e.printStackTrace();
        } catch (IllegalStateException e) {
            isSuccess = false;
            e.printStackTrace();
        } catch (IOException e) {
            isSuccess = false;
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!isSuccess)
            Toast.makeText(this, "播放失败", Toast.LENGTH_SHORT).show();
    }

    private void decryption() {
        isSuccess = true;
        long start = System.currentTimeMillis();
        try {
            fis = new FileInputStream(oldFile);
            byte[] oldByte = new byte[(int) oldFile.length()];
            fis.read(oldByte);

            long c = System.currentTimeMillis();
            Log.d(TAG, "读取字节：" + (c - start));

            // 解密
            byte[] newByte = AESUtils.decryptVoice(seed, oldByte);

            long d = System.currentTimeMillis();
            Log.d(TAG, "解密：" + (d - c));

            fos = new FileOutputStream(oldFile);
            fos.write(newByte);

            long f = System.currentTimeMillis();
            Log.d(TAG, "写入文件：" + (f - d));
        } catch (FileNotFoundException e) {
            isSuccess = false;
            e.printStackTrace();
        } catch (IOException e) {
            isSuccess = false;
            e.printStackTrace();
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }
        try {
            if (fis != null)
                fis.close();
            if (fos != null)
                fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        if (isSuccess)
            Toast.makeText(this, "解密成功(" + time + ")", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "解密失败(" + time + ")", Toast.LENGTH_SHORT).show();
    }

    private void encrypt() {
        isSuccess = true;
        long start = System.currentTimeMillis();
        try {
            fis = new FileInputStream(oldFile);
            byte[] oldByte = new byte[(int) oldFile.length()];
            fis.read(oldByte); // 读取

            long c = System.currentTimeMillis();
            Log.d(TAG, "读取字节：" + (c - start));

            // 加密
            byte[] newByte = AESUtils.encryptVoice(seed, oldByte);

            long d = System.currentTimeMillis();
            Log.d(TAG, "加密：" + (d - c));

            fos = new FileOutputStream(oldFile);
            fos.write(newByte);

            long f = System.currentTimeMillis();
            Log.d(TAG, "写入文件：" + (f - d));
        } catch (FileNotFoundException e) {
            isSuccess = false;
            e.printStackTrace();
        } catch (IOException e) {
            isSuccess = false;
            e.printStackTrace();
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        if (isSuccess)
            Toast.makeText(this, "加密成功(" + time + ")", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "加密失败(" + time + ")", Toast.LENGTH_SHORT).show();

        Log.i(TAG, "保存成功：" + time);
    }
}
