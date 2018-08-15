package io.agora.usecase.chorus;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;

import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;

public class SongsAccompanimentActivity extends AppCompatActivity {

    private RtcEngine mRtcEngine;
    private TextView mTvDisplay;
    private TextView mTvProgress;
    private SeekBar mSbVolume;
    private boolean mIsJoinSuccess = false;
    private String mChannelName;

    private IRtcEngineEventHandler mHandler = new IRtcEngineEventHandler() {
        @Override
        public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
            super.onJoinChannelSuccess(channel, uid, elapsed);
            mIsJoinSuccess = true;
            sendMessage("onJoinChannelSuccess: " + (uid & 0xFFFFFFFFL));
        }

        @Override
        public void onAudioMixingFinished() {
            super.onAudioMixingFinished();
            sendMessage("Audio Mixing Finished");
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
        setContentView(R.layout.activity_songs_accompaniment);

        mChannelName = getIntent().getStringExtra("CHANNEL_NAME");

        init();
    }

    public void onFirstSongClicked(View v) {
        if (mIsJoinSuccess) {
            mRtcEngine.stopAudioMixing();
            mRtcEngine.startAudioMixing("/assets/xiaomaolv.mp3", false, true, 1);
            sendMessage("play: 我有一头小毛驴.mp3");
        } else {
            sendMessage("wait for join success!!");
        }
    }

    public void onSecondSongClicked(View v) {
        if (mIsJoinSuccess) {
            mRtcEngine.stopAudioMixing();
            mRtcEngine.startAudioMixing("/assets/tiger.mp3", false, true, 1);
            sendMessage("play: 两只老虎.mp3");
        } else {
            sendMessage("wait for join success!!");
        }
    }

    public void init() {
        mTvDisplay = findViewById(R.id.tv_display);
        mTvProgress = findViewById(R.id.tv_progress);

        mSbVolume = findViewById(R.id.sb_volume);
        mSbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
                mTvProgress.post(new Runnable() {
                    @Override
                    public void run() {
                        mTvProgress.setText(progress + "");
                    }
                });
                mRtcEngine.adjustAudioMixingVolume(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        try {
            mRtcEngine = RtcEngine.create(this, getResources().getString(R.string.agora_app_id), mHandler);
            mRtcEngine.setLogFile(Environment.getExternalStorageDirectory()
                    + File.separator + "agora-chorus-broadcaster-song.log");

            mRtcEngine.joinChannel(null, mChannelName, "", 0);

            mRtcEngine.muteAllRemoteAudioStreams(true);
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
            mRtcEngine.stopAudioMixing();
            mRtcEngine.leaveChannel();
        }

        mRtcEngine = null;
    }
}
