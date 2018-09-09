package com.yatochk.travelclock.views;

import android.support.v4.app.FragmentActivity;
import android.view.View;

public abstract class OnMapView {

    View view;

    OnMapView(FragmentActivity activity, Integer layout) {
        view = activity.findViewById(layout);
        view.setVisibility(View.INVISIBLE);
    }

    public View getView() {
        return view;
    }

    public abstract void show();

    public abstract void hide();
}
