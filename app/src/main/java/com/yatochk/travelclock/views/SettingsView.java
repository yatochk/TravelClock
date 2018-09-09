package com.yatochk.travelclock.views;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.yatochk.travelclock.R;

public class SettingsView extends OnMapView {

    private View settingsBtn;

    public SettingsView(FragmentActivity activity, int layout) {
        super(activity, layout);
        settingsBtn = activity.findViewById(R.id.settings_button);
    }

    @Override
    public void show() {
        view.setVisibility(View.VISIBLE);
        settingsBtn.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hide() {
        view.setVisibility(View.INVISIBLE);
        settingsBtn.setVisibility(View.VISIBLE);
    }
}
