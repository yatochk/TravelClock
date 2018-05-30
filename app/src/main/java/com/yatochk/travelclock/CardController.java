package com.yatochk.travelclock;

import android.animation.ObjectAnimator;
import android.support.v7.widget.CardView;

import java.sql.Time;

class CardController {

    public static void upCardView(CardView exitCard, CardView entryCard){
        ObjectAnimator animationExit = ObjectAnimator.ofFloat(exitCard, "translationY", -110f);
        animationExit.setDuration(300);
        animationExit.start();

        ObjectAnimator animatorEntry = ObjectAnimator.ofFloat(entryCard, "alpha", 1f);
        animatorEntry.setDuration(300);
        animatorEntry.start();
    }

    public static void downCardView(CardView entryCard, CardView exitCard){
        ObjectAnimator animatorExit = ObjectAnimator.ofFloat(exitCard, "alpha", -1f);
        animatorExit.setDuration(300);
        animatorExit.start();

        ObjectAnimator animationEntry = ObjectAnimator.ofFloat(entryCard, "translationY", 0f);
        animationEntry.setDuration(300);
        animationEntry.start();
    }

    public static void stretchCardUp(CardView cardView){
        ObjectAnimator animation = ObjectAnimator.ofFloat(cardView, "translationX", 100f);
        animation.setDuration(500);
        animation.start();
    }
}
