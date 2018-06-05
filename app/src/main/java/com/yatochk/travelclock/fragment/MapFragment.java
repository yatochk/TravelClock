package com.yatochk.travelclock.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yatochk.travelclock.MainActivity;
import com.yatochk.travelclock.R;

import org.w3c.dom.Text;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private View mView;

    private TextView distanceTextView;
    private TextView timeTextView;

    private Context thisContext;

    private Marker locationMarker;
    private Marker arrivalMarker;
    private LocationManager fragmentLocationManager;

    private BitmapDescriptor iconLocation;
    private BitmapDescriptor iconArrivalMarker;

    private LatLng lastLocation = new LatLng(0, 0);
    public static double distance;
    public double alarmDistance = 200;

    public static boolean isTracked = true;

    public static MapFragment getInstance() {
        Bundle args = new Bundle();
        MapFragment mapFragment = new MapFragment();
        mapFragment.setArguments(args);

        return mapFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        thisContext = container.getContext();

        mView = inflater.inflate(R.layout.fragment_map, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) mView.findViewById(R.id.map);
        if (mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }

        fragmentLocationManager = MainActivity.locationManager;

        iconLocation = BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.arrow));
        iconArrivalMarker = BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.placeholder));

        distanceTextView = mView.findViewById(R.id.distanceTextView);
        timeTextView = mView.findViewById(R.id.timeTextView);
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

        if (ActivityCompat.checkSelfPermission( thisContext,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( thisContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fragmentLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000, 1, locationListener);
        fragmentLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                1000, 1, locationListener);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

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
    }

    private GoogleMap.OnMapLongClickListener longClickListener = new GoogleMap.OnMapLongClickListener() {
        @Override
        public void onMapLongClick(LatLng latLng) {
            arrivalMarker.remove();
            arrivalMarker = mGoogleMap.addMarker(new MarkerOptions()
                    .position(latLng).title("suda?")
                    .icon(iconArrivalMarker));
            distance = calculateDistance(lastLocation, arrivalMarker.getPosition());
            setDistance(distanceTextView, distance);
            setTime(timeTextView, distance);
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

            if (arrivalMarker != null) {
                distance = calculateDistance(lastLocation, arrivalMarker.getPosition());
                setDistance(distanceTextView, distance);
                setTime(timeTextView, distance);
                if (distance <= alarmDistance) alarming();
            }

            if (isTracked) mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
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
        float azimuth = (float) Math.atan2(dLat, dLon);

        locationMarker.setRotation(azimuth);
    }

    private double calculateDistance(LatLng location, LatLng markerLocation) {

        final float EARTH_RAD = 112200;
        double radDistance = Math.acos(Math.sin(location.latitude)
                * Math.sin(markerLocation.latitude)
                + Math.cos(location.latitude) * Math.cos(markerLocation.latitude)
                * Math.cos(location.longitude - markerLocation.longitude));

        return EARTH_RAD * radDistance;
    }

    private void alarming() {
        Toast toast = Toast.makeText(getContext(), "ALARMING!!!", Toast.LENGTH_LONG);
        toast.show();
    }

    @SuppressLint("SetTextI18n")
    private void setDistance(TextView textView, double distance){
        String dimension = "";
        int distanceInteger;

        if (distance >= 1000){
            distance /= 1000;
            dimension = "km";
            textView.setText(distance + dimension);
        }
        else{
            distanceInteger = (int) Math.round(distance);
            dimension = "m";
            textView.setText(distanceInteger + dimension);
        }

    }

    @SuppressLint("SetTextI18n")
    private void setTime(TextView textView, double distance){
        int time = (int) (distance / 11);
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
}
