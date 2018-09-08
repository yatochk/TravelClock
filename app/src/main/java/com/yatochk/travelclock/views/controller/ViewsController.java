package com.yatochk.travelclock.views.controller;

import android.support.v4.app.FragmentActivity;

import com.yatochk.travelclock.R;
import com.yatochk.travelclock.views.OnMapView;
import com.yatochk.travelclock.views.OnWayView;
import com.yatochk.travelclock.views.SearchView;
import com.yatochk.travelclock.views.SettingsView;

public class ViewsController {

    private OnMapView[] views;
    private final int SEARCH_VIEW = 0;
    private final int ON_WAY_VIEW = 1;
    private final int SETTINGS_VIEW = 2;
    private OnMapView[] openView;

    public ViewsController(FragmentActivity activity) {
        views = new OnMapView[]{
                new SearchView(activity, R.id.search),
                new OnWayView(activity, R.id.on_way),
                new SettingsView(activity, R.id.settings)
        };

        views[SEARCH_VIEW].show();
    }

    private void onStartWay() {
        for (OnMapView view : views) {
            view.hide();
        }

        views[ON_WAY_VIEW].show();
    }

    private void onFinishWay() {
        views[SEARCH_VIEW].show();
    }

    public boolean onBackPressed() {
        return false;
    }
}
