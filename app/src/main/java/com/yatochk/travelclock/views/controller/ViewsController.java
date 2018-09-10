package com.yatochk.travelclock.views.controller;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.yatochk.travelclock.R;
import com.yatochk.travelclock.views.OnMapView;
import com.yatochk.travelclock.views.OnWayView;
import com.yatochk.travelclock.views.SearchView;
import com.yatochk.travelclock.views.SettingsView;

import java.util.ArrayList;

public class ViewsController {

    public final int SEARCH_VIEW = 0;
    public final int ON_WAY_VIEW = 1;
    public final int SETTINGS_VIEW = 2;

    private OnMapView[] views;
    private ArrayList<OnMapView> openViews = new ArrayList<>();

    public ViewsController(FragmentActivity activity) {
        views = new OnMapView[]{
                new SearchView(activity, R.id.search),
                new OnWayView(activity, R.id.on_way),
                new SettingsView(activity, R.id.settings)
        };

        views[SEARCH_VIEW].show();
        activity.findViewById(R.id.settings_button).setOnClickListener(v -> showView(SETTINGS_VIEW));
    }

    private void onStartWay() {
        for (OnMapView view : views) {
            view.hide();
        }

        showView(ON_WAY_VIEW);
    }

    private void onFinishWay() {
        showView(SEARCH_VIEW);
    }

    public boolean onBackPressed() {
        if (!openViews.isEmpty()) {
            openViews.get(openViews.size() - 1).hide();
            openViews.remove(openViews.size() - 1);
            return true;
        } else
            return false;
    }

    public void showView(int viewCode) {
        if (views[viewCode].getView().getVisibility() != View.VISIBLE)
            views[viewCode].show();

        if (viewCode != SEARCH_VIEW)
            openViews.add(views[viewCode]);
    }

    public void hideView(int viewCode) {
        if (views[viewCode].getView().getVisibility() != View.INVISIBLE)
            views[viewCode].hide();

        if (viewCode != SEARCH_VIEW) {
            openViews.remove(views[viewCode]);
        }
    }
}
