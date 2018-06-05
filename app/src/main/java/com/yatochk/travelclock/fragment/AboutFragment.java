package com.yatochk.travelclock.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yatochk.travelclock.R;


public class AboutFragment extends Fragment {

    public static AboutFragment getInstance() {
        Bundle args = new Bundle();
        AboutFragment aboutFragment = new AboutFragment();
        aboutFragment.setArguments(args);

        return aboutFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

}
