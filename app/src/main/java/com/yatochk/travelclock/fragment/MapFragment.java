package com.yatochk.travelclock.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yatochk.travelclock.AlarmActivity;
import com.yatochk.travelclock.MainActivity;
import com.yatochk.travelclock.R;
import com.yatochk.travelclock.fragment.inMapFragments.ConfirmDestination;
import com.yatochk.travelclock.fragment.inMapFragments.OnWayFragment;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static MapFragment mapFragment;

    private FragmentManager fragmentManager;
    private static FragmentTransaction fragmentTransaction;

    private static OnWayFragment onWayFragment;
    private ConfirmDestination confirmDestination;

    private GoogleMap mGoogleMap;
    private View thisLayout;

    private TextView distanceTextView;
    private TextView timeTextView;
    private Button findLocationButton;
    private Button backButton;

    public static float volumeSound;
    public static boolean isVibrate = true;

    private Context thisContext;

    private Marker locationMarker;
    private Marker arrivalMarker;
    private LocationManager fragmentLocationManager;

    private BitmapDescriptor iconLocation;
    private BitmapDescriptor iconArrivalMarker;

    private LatLng lastLocation = new LatLng(0, 0);
    public static double distance;
    public static double alarmDistance = 200;

    public static boolean isTracked = true;
    public static boolean isOnWay;
    public static boolean readyToGo;

    public static MapFragment getInstance() {
        if (mapFragment == null) {
            Bundle args = new Bundle();
            mapFragment = new MapFragment();
            mapFragment.setArguments(args);
        }

        return mapFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        thisContext = container.getContext();
        thisLayout = inflater.inflate(R.layout.fragment_map, container, false);
        return thisLayout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapView mMapView = thisLayout.findViewById(R.id.map);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }

        fragmentLocationManager = MainActivity.locationManager;

        iconLocation = BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.arrow));
        iconArrivalMarker = BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.placeholder));

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        onWayFragment = new OnWayFragment();
        confirmDestination = new ConfirmDestination();
        fragmentTransaction.add(R.id.inMapFragment, confirmDestination);
        fragmentTransaction.commit();

        distanceTextView = OnWayFragment.distanceTextView;
        timeTextView = OnWayFragment.timeTextView;
    }

    private Bitmap getBitmap(int drawableRes) {
        Drawable drawable = getResources().getDrawable(drawableRes);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(thisContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(thisContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.thisActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else{
            fragmentLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    100, 1, locationListener);

            fragmentLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    100, 1, locationListener);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (!isOnWay) fragmentLocationManager.removeUpdates(locationListener);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setBuildingsEnabled(false);
        googleMap.setMaxZoomPreference(19);

        locationMarker = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .anchor(0.5f, 0.5f)
                .rotation(180)
                .title("u here")
                .flat(true)
                .icon(iconLocation));

        arrivalMarker = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .icon(null)
                .visible(false));

        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        googleMap.setOnMapLongClickListener(longClickListener);
        googleMap.setOnMarkerClickListener(markerClickListener);

        findLocationButton = MainActivity.findLocationButton;
        findLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGoogleMap.getCameraPosition().zoom < 10)
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationMarker.getPosition(), 10));
                else
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(locationMarker.getPosition()));
            }
        });

        backButton = MainActivity.backButton;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnWay){
                    backToConfirm();
                }
            }
        });
    }

    private GoogleMap.OnMapLongClickListener longClickListener = new GoogleMap.OnMapLongClickListener() {
        @Override
        public void onMapLongClick(LatLng latLng) {
            if (!isOnWay){
                arrivalMarker.remove();
                arrivalMarker = mGoogleMap.addMarker(new MarkerOptions()
                        .position(latLng).title("suda?")
                        .icon(iconArrivalMarker));
                distance = calculateDistance(lastLocation, arrivalMarker.getPosition());
                setDistance(distanceTextView, distance);
                setTime(timeTextView, distance);
                readyToGo = true;
            }

        }
    };

    private GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            return false;
        }
    };

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            markerRotation(locationMarker, lastLocation, latLng);
            lastLocation = latLng;
            locationMarker.setPosition(latLng);

            distance = calculateDistance(lastLocation, arrivalMarker.getPosition());
            setDistance(distanceTextView, distance);
            setTime(timeTextView, distance);
            if (isTracked) mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            if (distance <= alarmDistance && isOnWay) alarming();
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

    private void markerRotation(Marker locationMarker, LatLng lastLocation, LatLng newLocation) {
        int dLat = (int) Math.round(newLocation.latitude - lastLocation.latitude);
        int dLon = (int) Math.round(newLocation.longitude - lastLocation.longitude);
        double azimuth = Math.atan2(dLat, dLon);

        locationMarker.setRotation( (float) Math.toDegrees(azimuth));
    }

    private double calculateDistance(LatLng location, LatLng markerLocation) {

        final float EARTH_RAD = 112200;
        double radDistance = Math.acos(Math.sin(location.latitude)
                * Math.sin(markerLocation.latitude)
                + Math.cos(location.latitude) * Math.cos(markerLocation.latitude)
                * Math.cos(location.longitude - markerLocation.longitude));

        return EARTH_RAD * radDistance;
    }

    @SuppressLint("SetTextI18n")
    private void setDistance(TextView textView, double distance){
        String dimension = "";
        int distanceInteger;

        if (distance >= 1000){
            distanceInteger = (int) Math.round(distance / 1000);
            dimension = "km";
            textView.setText(distanceInteger + dimension);
        }
        else{
            distanceInteger = (int) Math.round(distance);
            dimension = "m";
            textView.setText(distanceInteger + dimension);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setTime(TextView textView, double distance){
        int velocity = 4;
        int time = (int) (distance / velocity);
        int minutes, hours;

        if (time < 60){
            textView.setText(time + "s");
        }
        else {
            if (time >= 3600){
                hours = time / 3600;
                minutes = (time - hours * 3600) / 60;
                textView.setText(hours + "h " + minutes + "m");
            }
            else{
                minutes = time / 60;
                textView.setText(minutes + "m");
            }
        }

    }

    public void onStartWay(){
        isTracked = true;
        isOnWay = true;
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down);
        fragmentTransaction.remove(confirmDestination);
        fragmentTransaction.commit();
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationMarker.getPosition(), 15));
    }

    public void backToConfirm(){
        isOnWay = false;
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down);
        fragmentTransaction.add(R.id.inMapFragment, confirmDestination);
        fragmentTransaction.commit();
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationMarker.getPosition(), 10));
    }

    private void alarming() {
        if (MainActivity.appIsPaused)
            MainActivity.needBackToConfirm = true;
        else
            backToConfirm();

        Intent alarmActivity = new Intent(thisContext, AlarmActivity.class);
        startActivity(alarmActivity);
    }
}
