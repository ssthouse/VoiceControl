package com.ssthouse.voicecontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by ssthouse on 02/03/2017.
 */

public class DemoActivity extends AppCompatActivity {

    @BindView(R.id.id_tb)
    Toolbar mTb;

    @BindView(R.id.id_tv_msg)
    TextView mTvMsg;

    private String mTitle;

    private static String KEY_TITLE = "title";

    public static void start(Activity activity, String title) {
        Intent intent = new Intent(activity, DemoActivity.class);
        intent.putExtra(KEY_TITLE, title);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);
        mTitle = getIntent().getStringExtra(KEY_TITLE);
        initView();
    }

    private void initView() {
        if (mTb == null) {
            Timber.e("what the fuck");
        }
        setSupportActionBar(mTb);
        getSupportActionBar().setTitle(mTitle);
        mTvMsg.setText(mTitle);
    }
}
