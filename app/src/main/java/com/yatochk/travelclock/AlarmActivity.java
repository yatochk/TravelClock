package com.yatochk.travelclock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.SoundPool;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yatochk.travelclock.fragment.SettingFragment;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity implements SoundPool.OnLoadCompleteListener{


    private Vibrator vibrator;
    private SoundPool soundPool;
    private int soundId;
    private int streamId;
    private boolean isPlaying;
    private TextView timeTextView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        if (SettingFragment.getInstance().vibrationSwitch.isChecked()){
            long[] pattern = { 0, 300, 200, 400 };
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(pattern, 2);
            }
        }

        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vibrator != null) vibrator.cancel();
                finish();
            }
        });

        Button muteButton = findViewById(R.id.muteButton);
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

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        timeTextView = findViewById(R.id.timeTextView);
        timeTextView.setText(hour + ":" + minute);
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        float volumeSound = SettingFragment.getInstance().volumeSeekBar.getProgress() / 100;
        isPlaying = true;
        streamId = soundPool.play(soundId, volumeSound, volumeSound, 1, 5, 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        soundPool.release();
    }
}
