package com.yatochk.travelclock.views.controller;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.yatochk.travelclock.R;
import com.yatochk.travelclock.views.OnMapView;
import com.yatochk.travelclock.views.OnWayView;
import com.yatochk.travelclock.views.SearchView;
import com.yatochk.travelclock.views.SettingsView;

import java.util.ArrayList;

public class ViewsController {

    private static final int SEARCH_VIEW = 0;
    public static final int ON_WAY_VIEW = 1;
    private static final int SETTINGS_VIEW = 2;

    private OnMapView[] views;
    private ArrayList<OnMapView> openViews = new ArrayList<>();
    private ArrayList<OnMapView> openViewsBeforeMove = new ArrayList<>();
    private Button goButton;

    public ViewsController(FragmentActivity activity) {
        views = new OnMapView[]{
                new SearchView(activity, R.id.search),
                new OnWayView(activity, R.id.on_way),
                new SettingsView(activity, R.id.settings)
        };

        views[SEARCH_VIEW].showView();
        activity.findViewById(R.id.settings_button).setOnClickListener(v -> showView(SETTINGS_VIEW));

        goButton = activity.findViewById(R.id.go);
        goButton.setVisibility(View.INVISIBLE);
        goButton.setOnClickListener(v -> {
            v.setVisibility(View.INVISIBLE);
            showView(ON_WAY_VIEW);
        });
    }

    public void onPickDestination() {
        goButton.setVisibility(View.VISIBLE);
    }

    /**
     * Methods for hide view during move map camera by user
     */
    public void hideViewOnMapMove() {
        for (OnMapView view : views) {
            if (view.getView().getVisibility() == View.VISIBLE) {
                openViewsBeforeMove.add(view);
                view.hideOnMove();
            }
        }
    }

    public void showViewAfterMapMove() {
        for (OnMapView view : openViewsBeforeMove)
            view.showAfterMove();
        openViewsBeforeMove.clear();
    }

    private void onStartWay() {
        for (OnMapView view : views) {
            view.hideView();
        }

        showView(ON_WAY_VIEW);
    }

    public boolean onBackPressed() {
        int lastOpenView = openViews.size() - 1;

        if (!openViews.isEmpty()) {
            openViews.get(lastOpenView).hideView();
            openViews.remove(openViews.size() - 1);
            return true;
        } else
            return false;
    }

    public void showView(int viewCode) {
        if (views[viewCode].getView().getVisibility() != View.VISIBLE)
            views[viewCode].showView();

        if (viewCode != SEARCH_VIEW)
            openViews.add(views[viewCode]);
    }

    public void hideView(int viewCode) {
        if (views[viewCode].getView().getVisibility() != View.INVISIBLE)
            views[viewCode].hideView();

        if (viewCode != SEARCH_VIEW) {
            openViews.remove(views[viewCode]);
        }
    }

    public OnMapView[] getViews() {
        return views;
    }
}
