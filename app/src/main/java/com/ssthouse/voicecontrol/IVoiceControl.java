package com.ssthouse.voicecontrol;


import com.iflytek.cloud.RecognizerListener;

/**
 * Created by ssthouse on 02/03/2017.
 */

public interface IVoiceControl {
    void startVoiceControl();

    void stopVoiceControl();

    void uploadGrammar();

    void setRecognizerListener(RecognizerListener recognizerListener);
}
