package io.agora.usecase.chorus;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class GuideActivity extends AppCompatActivity {

    public boolean isLoadSuccess = false;

    private EditText mEtChannelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        mEtChannelName = findViewById(R.id.et_channel_name);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            requestPermissions(new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
        } else {
            isLoadSuccess = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200)
            isLoadSuccess = true;
    }

    public void onBroadcasterClicked(View v) {
        if (TextUtils.isEmpty(mEtChannelName.getText())) {
            Toast.makeText(this, "please input channel name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isLoadSuccess) {
            Intent i = new Intent(GuideActivity.this, SongsAccompanimentActivity.class);
            i.putExtra("CHANNEL_NAME", mEtChannelName.getText().toString());
            startActivity(i);
        }
    }

    public void onListenerClicked(View v) {
        if (TextUtils.isEmpty(mEtChannelName.getText())) {
            Toast.makeText(this, "please input channel name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isLoadSuccess) {
            Intent i = new Intent(GuideActivity.this, SingerActivity.class);
            i.putExtra("CHANNEL_NAME", mEtChannelName.getText().toString());
            startActivity(i);
        }
    }
}
