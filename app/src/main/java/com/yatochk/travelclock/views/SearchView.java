package com.yatochk.travelclock.views;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.yatochk.travelclock.R;

public class SearchView {

    private FragmentActivity activity;
    private RelativeLayout mainLayout;
    private View view;

    public SearchView(FragmentActivity activity) {
        this.activity = activity;
        this.mainLayout = activity.findViewById(R.id.mainLayout);

        view = activity.getLayoutInflater().inflate(R.layout.search_layout, mainLayout, false);
    }

    public void show() {
        mainLayout.addView(view);
    }
}
