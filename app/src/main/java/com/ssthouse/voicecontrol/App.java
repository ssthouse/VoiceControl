package com.ssthouse.voicecontrol;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

/**
 * Created by ssthouse on 02/03/2017.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=58b787fc");
    }
}
