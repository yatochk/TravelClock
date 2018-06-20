package com.yatochk.travelclock.fragment.inMapFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yatochk.travelclock.R;

public class OnWayFragment extends Fragment {
    private View thisLayout;
    public static TextView distanceTextView;
    public static TextView timeTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        thisLayout = inflater.inflate(R.layout.fragment_on_way, container, false);
        return thisLayout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        distanceTextView = thisLayout.findViewById(R.id.distanceTextView);
        timeTextView = thisLayout.findViewById(R.id.timeTextView);
    }
}
