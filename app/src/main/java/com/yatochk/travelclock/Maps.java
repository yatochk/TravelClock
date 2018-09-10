package com.yatochk.travelclock;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Maps implements OnMapReadyCallback {

    private FragmentActivity activity;
    private GoogleMap map;
    private Marker locationMarker;
    private Marker arrivalMarker;
    private GoogleMap.OnCameraMoveStartedListener onCameraMoveStartedListener;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;

    Maps(FragmentActivity activity, GoogleMap.OnCameraMoveStartedListener onCameraMoveStartedListener, GoogleMap.OnCameraIdleListener onCameraIdleListener) {
        this.activity = activity;
        this.onCameraMoveStartedListener = onCameraMoveStartedListener;
        this.onCameraIdleListener = onCameraIdleListener;
        mapInit();
    }

    private void mapInit() {
        SupportMapFragment mapFragment = (SupportMapFragment) activity.getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        locationMarker = googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.arrow)))
                .position(new LatLng(0, 0)));

        arrivalMarker = googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.placeholder)))
                .position(new LatLng(0, 0)));

        googleMap.setOnMapClickListener(onMapClickListener);
        googleMap.setOnCameraMoveStartedListener(onCameraMoveStartedListener);
        googleMap.setOnCameraIdleListener(onCameraIdleListener);
        googleMap.setOnMarkerClickListener(onMarkerClickListener);
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(activity, R.raw.map_style));
    }

    private OnMarkerClickListener onMarkerClickListener = (Marker marker) -> false;

    private OnMapClickListener onMapClickListener = (LatLng latLng) -> arrivalMarker.setPosition(latLng);

    private Bitmap getBitmap(int drawableRes) {
        Drawable drawable = activity.getResources().getDrawable(drawableRes);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public void moveToLocation(LatLng location) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(location,
                (map.getCameraPosition().zoom < 10) ? 10 : map.getCameraPosition().zoom));
    }

    public void moveMarker(LatLng newLocation) {
        locationMarker.setPosition(newLocation);
    }
}
