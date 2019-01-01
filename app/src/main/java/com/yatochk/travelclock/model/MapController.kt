package com.yatochk.travelclock.model

import android.support.v4.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.yatochk.travelclock.R
import com.yatochk.travelclock.getBitmap

class MapController(val activity: FragmentActivity)
    : OnMapReadyCallback {
    private var map: GoogleMap? = null
    private var locationMarker: Marker? = null
    private var arrivalMarker: Marker? = null

    var onMapClickListener: ((LatLng) -> Unit)? = null
    var onCameraIdleListener: (() -> Unit)? = null
    var onCameraMoveStartedListener: ((Int) -> Unit)? = null

    private val onMarkerClickListener = { marker: Marker -> false }

    init {
        mapInit()
    }

    private fun mapInit() {
        val mapFragment = activity.supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        locationMarker = googleMap.addMarker(MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.arrow, activity)))
                .position(LatLng(0.0, 0.0)))

        arrivalMarker = googleMap.addMarker(MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.placeholder, activity)))
                .position(LatLng(0.0, 0.0)))

        googleMap.setOnMapClickListener(onMapClickListener)
        googleMap.setOnCameraMoveStartedListener(onCameraMoveStartedListener)
        googleMap.setOnCameraIdleListener(onCameraIdleListener)
        googleMap.setOnMarkerClickListener(onMarkerClickListener)
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(activity, R.raw.map_style))
    }


    fun moveToLocation(location: LatLng) {
        map!!.animateCamera(CameraUpdateFactory.newLatLngZoom(location,
                if (map!!.cameraPosition.zoom < 10) 10f else map!!.cameraPosition.zoom))
    }

    fun moveLocationMarker(newLocation: LatLng) {
        locationMarker!!.position = newLocation
    }

    fun moveDestinationMarker(position: LatLng) {
        arrivalMarker!!.position = position
    }
}
