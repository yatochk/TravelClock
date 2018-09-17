package com.yatochk.travelclock.views;

import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.yatochk.travelclock.R;

public class OnWayView extends OnMapView {

    private ObjectAnimator btnUpAnim;
    private ObjectAnimator btnDownAnim;
    private View settingsButton;
    private Point size = new Point();

    public OnWayView(FragmentActivity activity, int layout) {
        super(activity, layout);

        activity.getWindowManager().getDefaultDisplay().getSize(size);
        Button cancelButton = activity.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> hide());
        settingsButton = activity.findViewById(R.id.settings_button);
    }

    public void showUp() {

    }

    public void showDown() {

    }

    @Override
    public void show() {
        btnUpAnim = ObjectAnimator.ofFloat(settingsButton, "translationY", 0f, -(size.y - getView().getTop() - settingsButton.getHeight() / 2));
        btnUpAnim.setDuration(300);
        btnUpAnim.start();
        getView().setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        btnDownAnim = ObjectAnimator.ofFloat(settingsButton, "translationY", 0f);
        btnDownAnim.setDuration(300);
        btnDownAnim.start();
        getView().setVisibility(View.INVISIBLE);
    }
}
