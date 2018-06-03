package com.yatochk.travelclock;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class CompleteFragment extends Fragment {

    public static CompleteFragment getInstance() {
        Bundle args = new Bundle();
        CompleteFragment completeFragment = new CompleteFragment();
        completeFragment.setArguments(args);

        return completeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_complete, container, false);
    }

}