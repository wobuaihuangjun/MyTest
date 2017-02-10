package com.hzj.mytest.voice;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.hzj.mytest.R;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.SampleBuffer;

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

    private AudioTrack mAudioTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_encryption);
        findViewById(R.id.playButton).setOnClickListener(this);
        findViewById(R.id.encryptionButton).setOnClickListener(this);
        findViewById(R.id.decryptionButton).setOnClickListener(this);
        findViewById(R.id.playAudioTrackButton).setOnClickListener(this);
        findViewById(R.id.stop).setOnClickListener(this);

        editText = (EditText) findViewById(R.id.file_name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop();
    }

    private void stop() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }

        if (mAudioTrack != null) {
            mAudioTrack.stop();
            mAudioTrack.release();
            mAudioTrack = null;
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
            case R.id.playAudioTrackButton:
                playByAudioTrack();
                break;
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
            case R.id.stop:
                stop();
                break;
            default:
                break;
        }
    }

    Decoder mDecoder;

    private void playByAudioTrack() {
        isSuccess = true;
        mDecoder = new Decoder();

        final int sampleRate = 44100;
        final int minBufferSize = AudioTrack.getMinBufferSize(sampleRate,
                //MI3：CHANNEL_OUT_STEREO //[]AudioFormat.CHANNEL_OUT_STEREO
                //CHANNEL_OUT_MONO影响不大，只要是new AudioTrack构建时选择AudioFormat.CHANNEL_OUT_STEREO即可
                AudioFormat.CHANNEL_OUT_STEREO,
                AudioFormat.ENCODING_PCM_16BIT);
        mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate,
                AudioFormat.CHANNEL_OUT_STEREO, // CHANNEL_OUT_STEREO 声音嘈杂 ，CHANNEL_OUT_DEFAULT，CHANNEL_IN_DEFAULT，也是有噪音
                AudioFormat.ENCODING_PCM_16BIT,//AudioFormat.CHANNEL_CONFIGURATION_DEFAULT也是有声音
                2 * minBufferSize,
                AudioTrack.MODE_STREAM);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fis = new FileInputStream(oldFile);
                    byte[] oldByte = new byte[(int) oldFile.length()];
                    fis.read(oldByte);

                    // 解密
                    byte[] newByte = AESUtils.decryptVoice(seed, oldByte);

                    fis.close();
                    InputStream in = new ByteArrayInputStream(newByte);
                    Bitstream bitstream = new Bitstream(in);
                    //大约需要14s，但是歌曲可以完整保存下来
//                   final int READ_THRESHOLD = 2147483647;//我试着改动了，没有变化;
                    //需要3s，但是音乐没有播放完就结束了
                    final int READ_THRESHOLD = 1024;//我试着改动了，没有变化;
                    int framesReaded = READ_THRESHOLD;

                    ByteArrayOutputStream outStream = new ByteArrayOutputStream(1024 * 2);
                    Header header;
                    //先比较（y--）是否大于0，再让y=y-1，如果是--y>0,就是先让y=y-1,再比较现在的y值是否大于0
                    for (; framesReaded-- > 0 && (header = bitstream.readFrame()) != null; ) {
                        SampleBuffer sampleBuffer = (SampleBuffer) mDecoder.decodeFrame(header, bitstream);
                        Log.e("header", String.valueOf(header.framesize));
                        //方法1
//                        short[] buffer = sampleBuffer.getBuffer();
//                        mAudioTrack.write(buffer, 0, buffer.length);
                        //方法2
                        short[] buffer = sampleBuffer.getBuffer();
                        for (short s : buffer) {
                            //& 0xff 是为了保证补码一致  http://www.cnblogs.com/think-in-java/p/5527389.html
                            outStream.write(s & 0xff);
                            outStream.write((s >> 8) & 0xff);
                        }
                        bitstream.closeFrame();
                    }
                    byte[] Byte_JLayer = outStream.toByteArray();
                    mAudioTrack.write(Byte_JLayer, 0, Byte_JLayer.length);

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
            }
        }).start();

        mAudioTrack.play();
        mAudioTrack.setPlaybackPositionUpdateListener(null);
        if (!isSuccess)
            Toast.makeText(this, "播放失败", Toast.LENGTH_SHORT).show();
    }

    private void play() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
        // mPlayer = MediaPlayer.create(this, R.raw.recording_old);
        isSuccess = true;
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
