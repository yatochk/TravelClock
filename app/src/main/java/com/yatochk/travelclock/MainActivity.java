package com.yatochk.travelclock;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.yatochk.travelclock.views.controller.ViewsController;

import static com.google.android.gms.maps.GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private ViewsController viewsController;
    private Maps map;
    private boolean isTracking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        map = new Maps(this, onCameraMoveStartedListener, onCameraIdleListener);
        viewsController = new ViewsController(this);
        isTracking = true;
    }

    private GoogleMap.OnCameraMoveStartedListener onCameraMoveStartedListener = (reason) -> {
        if (reason == REASON_GESTURE) {
            isTracking = false;
            viewsController.hideView(viewsController.SEARCH_VIEW);
        }
    };

    private GoogleMap.OnCameraIdleListener onCameraIdleListener = () ->
            viewsController.showView(viewsController.SEARCH_VIEW);

    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    100, 1, locationListener);

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    100, 1, locationListener);
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            LatLng newLocation = new LatLng(location.getLatitude(), location.getLongitude());

            map.moveMarker(newLocation);
            if (isTracking) map.moveToLocation(newLocation);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    public void onBackPressed() {
        if (!viewsController.onBackPressed())
            super.onBackPressed();
    }
}
