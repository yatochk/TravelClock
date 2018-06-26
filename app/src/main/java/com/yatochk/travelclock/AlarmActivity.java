package com.yatochk.travelclock;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yatochk.travelclock.fragment.MapFragment;

public class AlarmActivity extends AppCompatActivity implements SoundPool.OnLoadCompleteListener{

    private Button exitButton;
    private Button muteButton;
    private Vibrator vibrator;

    private SoundPool soundPool;
    private int soundId;
    private int streamId;
    private boolean isPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        if (MapFragment.isVibrate){
            long[] pattern = { 0, 300, 200, 400 };
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(pattern, 2);
            }
        }

        exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vibrator != null) vibrator.cancel();
                finish();
            }
        });

        muteButton = findViewById(R.id.muteButton);
        muteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vibrator != null) vibrator.cancel();
                if (isPlaying){
                    isPlaying = false;
                    soundPool.stop(streamId);
                }
            }
        });

        soundPool = new SoundPool.Builder().build();
        soundPool.setOnLoadCompleteListener(this);
        soundId = soundPool.load(this, R.raw.alarm_sound, 1);
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        isPlaying = true;
        streamId = soundPool.play(soundId, 1, 1, 1, 5, 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        soundPool.release();
    }
}
