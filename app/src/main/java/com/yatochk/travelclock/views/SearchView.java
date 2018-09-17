package com.yatochk.travelclock.views;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.yatochk.travelclock.R;

public class SearchView extends OnMapView {

    private Animation openAnimation;
    private Animation closeAnimation;

    public SearchView(FragmentActivity activity, int layout) {
        super(activity, layout);

        openAnimation = AnimationUtils.loadAnimation(activity, R.anim.open_search);
        closeAnimation = AnimationUtils.loadAnimation(activity, R.anim.close_search);
    }

    @Override
    public void show() {
        getView().setVisibility(View.VISIBLE);
        getView().startAnimation(openAnimation);
    }

    @Override
    public void hide() {
        closeAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                getView().setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        getView().startAnimation(closeAnimation);
    }
}
