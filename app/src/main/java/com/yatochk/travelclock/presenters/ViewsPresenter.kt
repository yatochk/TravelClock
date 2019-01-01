package com.yatochk.travelclock.presenters

import com.yatochk.travelclock.views.OnMapView

class ViewsPresenter {
    private val views = ArrayList<OnMapView>()

    fun addViews(vararg vs: OnMapView) {
        views.addAll(vs)
    }

}
