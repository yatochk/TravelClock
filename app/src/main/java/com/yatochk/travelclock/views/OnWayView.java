package com.yatochk.travelclock.views;

import android.support.v4.app.FragmentActivity;
import android.view.View;

public class OnWayView extends OnMapView {

    public OnWayView(FragmentActivity activity, int layout) {
        super(activity, layout);
    }

    public void showUp() {
        
    }

    public void showDown() {

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
