package com.ssthouse.voicecontrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnStart;
    private Button btnPause;
    private Button btnStop;
    private ImageView ivHome;

    private Button btnUploadGrammar;
    private Button btnStartVoiceControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initEvent() {
        // 给语音控制的按钮 设置点击事件
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClickToast((String) v.getTag());
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
            }
        });

        //开启语音控制的按钮
        btnStartVoiceControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 启动讯飞命令词识别
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
