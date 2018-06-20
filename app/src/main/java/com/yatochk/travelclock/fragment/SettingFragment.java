package com.yatochk.travelclock.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.yatochk.travelclock.R;


public class SettingFragment extends Fragment {

    private View fragmentView;

    private SeekBar distanceSeekBar;
    private Switch trackingSwitch;

    private SeekBar volumeSeekBar;
    private Switch vibrationSwitch;

    private TextView volumeTextView;
    private TextView distanceTextView;

    public static SettingFragment getInstance() {
        Bundle args = new Bundle();
        SettingFragment settingFragment = new SettingFragment();
        settingFragment.setArguments(args);

        return settingFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_setting, container, false);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        distanceSeekBar = fragmentView.findViewById(R.id.distanceSeekBar);
        distanceTextView = fragmentView.findViewById(R.id.distanceTextView);

        volumeSeekBar = fragmentView.findViewById(R.id.volumeSeekBar);
        volumeTextView = fragmentView.findViewById(R.id.volumeTextView);

        trackingSwitch = fragmentView.findViewById(R.id.trackingSwitch);
        vibrationSwitch = fragmentView.findViewById(R.id.vibrationSwitch);

        distanceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distanceTextView.setText(progress + "m");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MapFragment.alarmDistance = seekBar.getProgress();
            }
        });

        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volumeTextView.setText(progress + "%");
                MapFragment.volumeSound = progress / 100;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        trackingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MapFragment.isTracked = isChecked;
            }
        });

        vibrationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MapFragment.isVibrate = isChecked;
            }
        });

    }



}
