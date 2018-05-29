package com.yatochk.travelclock;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.content.SharedPreferences.Editor;
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
    private SupportMapFragment mapFragment;

    private BitmapDescriptor iconLocation;
    private BitmapDescriptor iconArrivalMarker;

    private LatLng lastLocation;
    private double distance;
    private double alarmDistance = 200;

    public boolean isTracked = true;
    private Fragment menuFragment;

    private boolean colorChanging;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int themeId = sharedPreferences.getInt("THEME", R.style.DeepBlue);
        super.setTheme(themeId);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuFragment = new Menu();

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        iconLocation = BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.arrow));
        iconArrivalMarker = BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.placeholder));

        themeChanger = findViewById(R.id.themeChanger);
        bottomMenu = findViewById(R.id.bottomMenu);
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
            setDistance(distance);
        }
    };

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
                setDistance(distance);
            } else {
                setDistance(444d);
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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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


    public void setMenuFragment(View view) {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down);

        if (!menuFragment.isVisible()) {
            transaction.add(R.id.map, menuFragment);
            closeThemeChanger();
        } else {
            setMapFragment(view);
            closeThemeChanger();
        }

        transaction.commit();
    }


    public void setMapFragment(View view) {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down);

        if (menuFragment.isVisible())
            transaction.replace(R.id.map, mapFragment);
        else {
            isTracked = !isTracked;
            if (isTracked)
                findLocation();
            onTrackedChanging();
            if (colorChanging) closeThemeChanger();
        }

        transaction.commit();
    }


    public void findLocation() {
        if (map.getCameraPosition().zoom < 10)
            map.animateCamera(CameraUpdateFactory.zoomTo(10));

        map.animateCamera(CameraUpdateFactory.newLatLng(lastLocation));
    }


    private double calculateDistance(LatLng location, LatLng markerLocation) {
        final float EARTH_RAD = 112200;
        double radDistance = Math.acos(Math.sin(location.latitude) * Math.sin(markerLocation.latitude) + Math.cos(location.latitude) * Math.cos(markerLocation.latitude) * Math.cos(location.longitude - markerLocation.longitude));
        return EARTH_RAD * radDistance;
    }


    @SuppressLint("SetTextI18n")
    private void setDistance(double distance) {
        TextView textDistance = findViewById(R.id.distanceView);
        textDistance.setText(Long.toString(Math.round(distance)));
    }


    public void setMyTheme(View view) {

        switch (view.getId()) {
            case R.id.deep:
                super.setTheme(R.style.DeepBlue);
                setThemeToPreference(R.style.DeepBlue);
                break;

            case R.id.greenBlue:
                super.setTheme(R.style.GreenBlue);
                setThemeToPreference(R.style.GreenBlue);
                break;

            case R.id.sweet:
                super.setTheme(R.style.SweetBlue);
                setThemeToPreference(R.style.SweetBlue);
                break;

            case R.id.violet:
                super.setTheme(R.style.Violet);
                setThemeToPreference(R.style.Violet);
                break;
        }
    }


    private void setThemeToPreference(int themeId) {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putInt("THEME", themeId);
        editor.apply();
    }


    private View themeChanger, bottomMenu;


    public void openThemeChanger(View view) {

        int[] location = new int[2];
        themeChanger.getLocationOnScreen(location);

        if (!colorChanging) {
            TranslateAnimation animation = new TranslateAnimation(0, 0, location[0], location[0] - (bottomMenu.getHeight() - 1));
            animation.setDuration(300);
            animation.setFillAfter(true);
            themeChanger.startAnimation(animation);
            colorChanging = true;
        } else {
            TranslateAnimation animation = new TranslateAnimation(0, 0, location[0] - (bottomMenu.getHeight() - 1), location[0]);
            animation.setDuration(300);
            animation.setFillAfter(true);
            themeChanger.startAnimation(animation);
            colorChanging = false;
        }

    }


    private void closeThemeChanger() {
        if (colorChanging) {
            int[] location = new int[2];
            themeChanger.getLocationOnScreen(location);

            TranslateAnimation animation = new TranslateAnimation(0, 0, location[0] - (bottomMenu.getHeight() - 1), location[0]);
            animation.setDuration(300);
            animation.setFillAfter(true);
            themeChanger.startAnimation(animation);
            colorChanging = false;
        }
    }


    private void alarming() {
        Toast toast = Toast.makeText(this, "ALARMING!!!", Toast.LENGTH_LONG);
        toast.show();
    }


    private void onTrackedChanging() {
        Toast toast = Toast.makeText(this, "Tracked mod " + (isTracked ? "on" : "off"), Toast.LENGTH_LONG);
        toast.show();
    }
}
