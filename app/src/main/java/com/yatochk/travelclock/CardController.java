package com.yatochk.travelclock;

import android.animation.ObjectAnimator;
import android.support.v7.widget.CardView;

class CardController {

    public static void shiftCards(CardView exitCard, CardView entryCard){
        exitCardInLeft(exitCard);
        entryCardInLeft(entryCard);
    }

    public static void exitCardInLeft(CardView exitCard){
        ObjectAnimator animationExit = ObjectAnimator.ofFloat(exitCard, "translationX", -1000f);
        animationExit.setDuration(500);
        animationExit.start();
    }

    public static void entryCardInLeft(CardView entryCard){
        ObjectAnimator animationEntry = ObjectAnimator.ofFloat(entryCard, "translationX", -1000f);
        animationEntry.setDuration(500);
        animationEntry.start();
    }

    public static void stretchCardUp(CardView cardView){
        ObjectAnimator animation = ObjectAnimator.ofFloat(cardView, "translationX", 100f);
        animation.setDuration(500);
        animation.start();
    }
}
