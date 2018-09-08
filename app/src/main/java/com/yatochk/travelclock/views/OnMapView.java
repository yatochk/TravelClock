package com.yatochk.travelclock.views;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.yatochk.travelclock.R;

public abstract class OnMapView {

    View view;

    OnMapView(FragmentActivity activity, Integer layout) {

        RelativeLayout mainLayout = activity.findViewById(R.id.mainLayout);
        view = activity.findViewById(layout);
        view.setVisibility(View.INVISIBLE);
    }

    public View getView() {
        return view;
    }

    public abstract void show();

    public abstract void hide();
}
