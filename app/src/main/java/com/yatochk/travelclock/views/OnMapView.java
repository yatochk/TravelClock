package com.yatochk.travelclock.views;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.yatochk.travelclock.R;

public abstract class OnMapView {

    private View view;
    private Animation showAnimation;
    private Animation hideAnimation;

    OnMapView(FragmentActivity activity, Integer layout) {
        view = activity.findViewById(layout);
        view.setVisibility(View.INVISIBLE);

        showAnimation = AnimationUtils.loadAnimation(activity, R.anim.open_search);
        hideAnimation = AnimationUtils.loadAnimation(activity, R.anim.close_search);
    }

    public View getView() {
        return view;
    }

    public void hideOnMove() {
        hideAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(hideAnimation);
    }

    public void showAfterMove() {
        view.setVisibility(View.VISIBLE);
        view.startAnimation(showAnimation);
    }

    public void showView() {
        show();
    }

    public void hideView() {
        hide();
    }

    abstract void show();

    abstract void hide();
}
