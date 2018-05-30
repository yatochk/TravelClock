package com.yatochk.travelclock;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private Marker locationMarker;
    private Marker arrivalMarker;
    private LocationManager locationManager;

    private BitmapDescriptor iconLocation;
    private BitmapDescriptor iconArrivalMarker;

    private LatLng lastLocation;
    private double distance;
    public double alarmDistance = 200;

    public boolean isTracked = true;

    private CardView[] cardViews = new CardView[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        iconLocation = BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.arrow));
        iconArrivalMarker = BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.placeholder));

        cardViews[Constants.ARRIVAL_CARD] = findViewById(R.id.arrivalCard);
        cardViews[Constants.ARRIVAL_CARD].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardController.exitCardInLeft(cardViews[Constants.ARRIVAL_CARD]);
            }
        });
    }


    //Return bitmap by id drawableRes
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
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMaxZoomPreference(19);

        locationMarker = map.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .anchor(0.5f, 0.5f)
                .rotation(180)
                .title("u here")
                .flat(true)
                .icon(iconLocation));

        arrivalMarker = map.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .icon(null)
                .visible(false));

        map.animateCamera(CameraUpdateFactory.zoomTo(10));
        map.setOnMapLongClickListener(longClickListener);
        map.setOnMarkerClickListener(markerClickListener);
    }


    private GoogleMap.OnMapLongClickListener longClickListener = new GoogleMap.OnMapLongClickListener() {
        @Override
        public void onMapLongClick(LatLng latLng) {
            arrivalMarker.remove();
            arrivalMarker = map.addMarker(new MarkerOptions()
                    .position(latLng).title("suda?")
                    .icon(iconArrivalMarker));
            distance = calculateDistance(lastLocation, arrivalMarker.getPosition());
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
            lastLocation = latLng;
            locationMarker.setPosition(latLng);

            if (arrivalMarker != null) {
                distance = calculateDistance(lastLocation, arrivalMarker.getPosition());
                if (distance <= alarmDistance) alarming();
            }

            if (isTracked) map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
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
    protected void onResume() {

        super.onResume();

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000, 1, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                1000, 1, locationListener);
    }


    @Override
    protected void onPause() {
        super.onPause();
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
        Toast toast = Toast.makeText(this, "ALARMING!!!", Toast.LENGTH_LONG);
        toast.show();
    }

}
