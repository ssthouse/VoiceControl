package com.ssthouse.voicecontrol;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.GrammarListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;

/**
 * Created by ssthouse on 02/03/2017.
 */

public class VoiceControlImpl implements IVoiceControl {

    private static final String TAG = "VoiceControlImpl";

    public static String grammarId = "5c1ab6a88e3a74440ef248c2e5a9fc75";

    public Context mContext;

    private SpeechRecognizer mSpeechRecognizer;

    public VoiceControlImpl(Context mContext) {
        this.mContext = mContext;
        mSpeechRecognizer = SpeechRecognizer.createRecognizer(mContext, null);
    }

    @Override
    public void uploadGrammar() {
        final String grammar = "#ABNF 1.0 gb2312;\n" +
                "language zh-CN; \n" +
                "mode voice;\n" +
                "\n" +
                "root $main;\n" +
                "$main = $click 一下$tag ;\n" +
                "$click = 点击 | 按;\n" +
                "$tag = 开始按钮 | 暂停按钮 | 结束按钮 | 头像图片;";
        mSpeechRecognizer.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        int result = mSpeechRecognizer.buildGrammar("abnf", grammar, new GrammarListener() {
            @Override
            public void onBuildFinish(String s, SpeechError speechError) {
                if (speechError != null || TextUtils.isEmpty(s)) {
                    Log.e(TAG, "语法构建失败,错误码：");
                    return;
                }
                Log.e(TAG, "语法 id 获取成功: " + s);
                grammarId = s;
            }
        });
        if (result != ErrorCode.SUCCESS) {
            Log.e(TAG, "语法构建失败,错误码：" + result);
        } else {
            Log.e(TAG, "语法构建成功");
        }
    }

    @Override
    public void setRecognizerListener(RecognizerListener recognizerListener) {
        this.mRecognizerListener = recognizerListener;
    }

    @Override
    public void startVoiceControl() {
        if (mSpeechRecognizer == null) {
            return;
        }
        mSpeechRecognizer.setParameter(SpeechConstant.ENGINE_TYPE, "cloud");
        mSpeechRecognizer.setParameter(SpeechConstant.CLOUD_GRAMMAR, grammarId);
        int result = mSpeechRecognizer.startListening(mRecognizerListener);
        if (result != ErrorCode.SUCCESS) {
            Log.d(TAG, "识别失败,错误码: " + result);
        }
    }

    @Override
    public void stopVoiceControl() {
        if (mSpeechRecognizer != null) {
            mSpeechRecognizer.stopListening();
        }
    }

    // 这里暂时初始化为 log 打印的监听器
    private RecognizerListener mRecognizerListener = new RecognizerListener() {
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
            mSpeechRecognizer.startListening(mRecognizerListener);
        }

        @Override
        public void onError(SpeechError speechError) {
            Log.e(TAG, "error: ");
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {
            Log.e(TAG, "on event: ");
        }
    };

    public void startListening() {
        mSpeechRecognizer.startListening(mRecognizerListener);
    }

}
