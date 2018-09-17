package com.yatochk.travelclock.views;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.SeekBar;

import com.yatochk.travelclock.R;
import com.yatochk.travelclock.Settings;

public class SettingsView extends OnMapView {

    private View settingsButton;
    private Animator openAnimator;
    private Animator closeAnimator;

    public SettingsView(FragmentActivity activity, int layout) {
        super(activity, layout);

        settingsButton = activity.findViewById(R.id.settings_button);
        SeekBar volumeSeekBar = activity.findViewById(R.id.volume_seek_bar);
        SeekBar distanceSeekBar = activity.findViewById(R.id.distance_seek_bar);

        openAnimator = AnimatorInflater.loadAnimator(activity, R.animator.open_settings);
        closeAnimator = AnimatorInflater.loadAnimator(activity, R.animator.close_settings);

        openAnimator.setTarget(getView());
        closeAnimator.setTarget(getView());

        closeAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                getView().setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        distanceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Settings.getInstance().distanceToAlarm = seekBar.getProgress();
            }
        });
        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Settings.getInstance().alarmVolume = seekBar.getProgress() / 100;
            }
        });
    }

    @Override
    public void show() {
        getView().setVisibility(View.VISIBLE);
        openAnimator.start();
        settingsButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hide() {
        closeAnimator.start();
        settingsButton.setVisibility(View.VISIBLE);
    }
}
