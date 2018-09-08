package com.yatochk.travelclock.views;

import android.support.v4.app.FragmentActivity;
import android.view.View;

public class SettingsView extends OnMapView {


    public SettingsView(FragmentActivity activity, int layout) {
        super(activity, layout);
    }

    @Override
    public void show() {
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        view.setVisibility(View.INVISIBLE);
    }
}
