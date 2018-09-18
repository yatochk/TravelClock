package com.yatochk.travelclock.views;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.yatochk.travelclock.R;

public class OnWayView extends OnMapView {

    private View settingsButton;
    private Point size = new Point();

    public OnWayView(FragmentActivity activity, int layout) {
        super(activity, layout);

        activity.getWindowManager().getDefaultDisplay().getSize(size);
        Button cancelButton = activity.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> hide());
        settingsButton = activity.findViewById(R.id.settings_button);
    }

    /**
     * triggered when the user's slide up
     */
    public void showUp() {

    }

    /**
     * triggered when the user's slide down
     */
    public void showDown() {

    }

    @Override
    public void show() {
        Animator btnUpAnim = ObjectAnimator.ofFloat(settingsButton, "translationY",
                0f, -(size.y - getView().getTop() - settingsButton.getHeight() / 2));
        btnUpAnim.setDuration(300);

        Animator showViewAnim = ObjectAnimator.ofFloat(getView(), "translationY",
                (size.y - getView().getTop()), 0f);
        showViewAnim.setDuration(300);


        btnUpAnim.start();
        getView().setVisibility(View.VISIBLE);
        showViewAnim.start();
    }

    @Override
    public void hide() {
        Animator btnDownAnim = ObjectAnimator.ofFloat(settingsButton, "translationY", 0f);
        btnDownAnim.setDuration(300);

        Animator hideViewAnim = ObjectAnimator.ofFloat(getView(), "translationY",
                0f, (size.y - getView().getTop()));
        hideViewAnim.setDuration(300);
        hideViewAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                getView().setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        hideViewAnim.start();
        btnDownAnim.start();
    }
}
