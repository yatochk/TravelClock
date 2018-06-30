package com.yatochk.travelclock.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.yatochk.travelclock.R;


public class SettingFragment extends Fragment {

    private static SettingFragment instance;

    private View fragmentView;

    public SeekBar distanceSeekBar;
    public Switch trackingSwitch;

    public SeekBar volumeSeekBar;
    public Switch vibrationSwitch;

    private TextView volumeTextView;
    private TextView distanceTextView;

    @SuppressLint("ValidFragment")
    private SettingFragment() {
    }

    public static SettingFragment getInstance() {
        if (instance == null){
            Bundle args = new Bundle();
            instance = new SettingFragment();
            instance.setArguments(args);
        }

        return instance;
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
            }
        });

        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volumeTextView.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
