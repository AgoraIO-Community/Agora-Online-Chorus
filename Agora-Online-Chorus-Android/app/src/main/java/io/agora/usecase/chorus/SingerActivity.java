package io.agora.usecase.chorus;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.File;

import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;

public class SingerActivity extends AppCompatActivity {

    private RtcEngine mRtcEngine;
    private TextView mTvDisplay;
    private String mChannelName;
    private IRtcEngineEventHandler mHandler = new IRtcEngineEventHandler() {
        @Override
        public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
            super.onJoinChannelSuccess(channel, uid, elapsed);
            sendMessage("onJoinChannelSuccess: " + (uid & 0xFFFFFFFFL));
        }

        @Override
        public void onUserJoined(int uid, int elapsed) {
            super.onUserJoined(uid, elapsed);
            sendMessage("onUserJoined: " + (uid & 0xFFFFFFFFL));
        }

        @Override
        public void onUserOffline(int uid, int reason) {
            super.onUserOffline(uid, reason);
            sendMessage("onUserOffline: " + (uid & 0xFFFFFFFFL));
        }

        @Override
        public void onError(int err) {
            super.onError(err);
            sendMessage("onError: " + err);
        }

        @Override
        public void onWarning(int warn) {
            super.onWarning(warn);
            sendMessage("onWarning: " + warn);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singer);

        mChannelName = getIntent().getStringExtra("CHANNEL_NAME");

        init();
    }

    public void init() {
        mTvDisplay = findViewById(R.id.tv_listener_display);

        try {
            mRtcEngine = RtcEngine.create(this, getResources().getString(R.string.agora_app_id), mHandler);
            mRtcEngine.setLogFile(Environment.getExternalStorageDirectory()
                    + File.separator + "agora-chorus-broadcaster-singer.log");

            mRtcEngine.setParameters("{\"che.audio.enable.androidlowlatencymode\": true}");
            mRtcEngine.setParameters("{\"che.audio.lowlatency\":true}");
            mRtcEngine.setParameters("{\"rtc.lowlatency\":1}");

            mRtcEngine.joinChannel(null, mChannelName, "", 0);

            sendMessage("join channel: " + mChannelName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvDisplay.append(msg + "\n");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (mRtcEngine != null) {
            mRtcEngine.leaveChannel();
        }

        mRtcEngine = null;
    }
}
