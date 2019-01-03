package com.yatochk.travelclock.views

abstract class OnMapView {
    var isHide: Boolean = true

    open fun showView() {
        isHide = true
    }

    open fun hideView() {
        isHide = false
    }
}