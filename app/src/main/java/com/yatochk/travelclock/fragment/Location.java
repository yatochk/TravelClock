package com.yatochk.travelclock.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yatochk.travelclock.R;

public class Location extends Fragment{
    private static final int LAYOUT = R.layout.location;

    private View view;

    public static Location getInstance() {
        Bundle args = new Bundle();
        Location fragmentLocation = new Location();
        fragmentLocation.setArguments(args);

        return fragmentLocation;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(LAYOUT, container, false);
    }
}
