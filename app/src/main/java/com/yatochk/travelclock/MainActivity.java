package com.yatochk.travelclock;

import android.app.Activity;
import android.location.LocationManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.yatochk.travelclock.adapter.TabsPagerFragmentAdapter;
import com.yatochk.travelclock.fragment.MapFragment;

public class MainActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_main;

    public static boolean appIsPaused;
    public static boolean needBackToConfirm;

    public static LocationManager locationManager;
    public static Button findLocationButton;
    public static Button backButton;

    public static Activity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        thisActivity = this;
        findLocationButton = findViewById(R.id.findLocationButton);
        backButton = findViewById(R.id.backButton);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        initTabs();
    }

    private void initTabs(){
        ViewPager viewPager = findViewById(R.id.viewPager);
        TabsPagerFragmentAdapter adapter = new TabsPagerFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.bottomTab);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();

        appIsPaused = false;
        if (needBackToConfirm){
            MapFragment.getInstance().backToConfirm();
            needBackToConfirm = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        appIsPaused = true;
    }
}
