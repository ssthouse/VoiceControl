package com.ssthouse.voicecontrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainAty";

    private ListView lv;

    private Button btnStart;
    private Button btnPause;
    private Button btnStop;
    private ImageView ivHome;

    private Button btnUploadGrammar;
    private Button btnStartVoiceControl;

    private VoiceControlImpl mVoiceControlImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        mVoiceControlImpl = new VoiceControlImpl(this);
        mVoiceControlImpl.setRecognizerListener(new RecognizerListener() {
            @Override
            public void onVolumeChanged(int i, byte[] bytes) {
                Log.e(TAG, "volumeChange: " + i);
            }

            @Override
            public void onBeginOfSpeech() {
                Log.e(TAG, "begin of speech: ");
            }

            @Override
            public void onEndOfSpeech() {
                Log.e(TAG, "end of speech: ");
                //mSpeechRecognizer.startListening(mRecognizerListener);
            }

            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                Log.e(TAG, "result: " + recognizerResult.getResultString());
                mVoiceControlImpl.startListening();
                Gson gson = new Gson();
                String cmdStr = gson.fromJson(recognizerResult.getResultString(), ResultBean.class).getWs().get(0).getCw().get(0).getW();
                Log.e(TAG, cmdStr + "***********");
                if (cmdStr.contains(btnStart.getTag() + "")) {
                    btnStart.performClick();
                }
                if (cmdStr.contains(btnPause.getTag() + "")) {
                    btnPause.performClick();
                }
                if (cmdStr.contains(btnStop.getTag() + "")) {
                    btnStop.performClick();
                }
            }

            @Override
            public void onError(SpeechError speechError) {
                Log.e(TAG, "error: ");
                mVoiceControlImpl.startListening();
            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {
                Log.e(TAG, "on event: ");
            }
        });
    }

    private void initEvent() {
        // 给语音控制的按钮 设置点击事件
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClickToast((String) v.getTag());
                DemoActivity.start(MainActivity.this, v.getTag() + "");
            }
        };
        btnStart.setOnClickListener(onClickListener);
        btnPause.setOnClickListener(onClickListener);
        btnStop.setOnClickListener(onClickListener);
        ivHome.setOnClickListener(onClickListener);

        // 上传语法文件
        btnUploadGrammar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 调用 sdk 上传语法词汇
                mVoiceControlImpl.uploadGrammar();
            }
        });

        //开启语音控制的按钮
        btnStartVoiceControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 启动讯飞命令词识别
                mVoiceControlImpl.startVoiceControl();
            }
        });
    }

    private void showClickToast(String msg) {
        Toast.makeText(this, "点击了: " + msg, Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        btnStart = (Button) findViewById(R.id.id_btn_start);
        btnStart.setTag("开始按钮");

        btnPause = (Button) findViewById(R.id.id_btn_pause);
        btnPause.setTag("暂停按钮");

        btnStop = (Button) findViewById(R.id.id_btn_stop);
        btnStop.setTag("结束按钮");

        ivHome = (ImageView) findViewById(R.id.id_iv_home);
        ivHome.setTag("头像图片");

        btnUploadGrammar = (Button) findViewById(R.id.id_upload_grammar);
        btnStartVoiceControl = (Button) findViewById(R.id.id_btn_voice_control);
    }
}
