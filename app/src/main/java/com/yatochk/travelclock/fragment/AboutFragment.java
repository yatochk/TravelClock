package com.yatochk.travelclock.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.yatochk.travelclock.R;

public class AboutFragment extends Fragment {

    private static AboutFragment instance;

    private View thisLayout;
    private Context thisContext;
    private final String APP_ID = "ca-app-pub-7097853215442719~9148897799";
    private AdView mAdView;

    @SuppressLint("ValidFragment")
    private AboutFragment() {
    }

    public static AboutFragment getInstance() {
        if (instance == null){
            Bundle args = new Bundle();
            instance = new AboutFragment();
            instance.setArguments(args);
        }

        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        thisContext = container.getContext();
        thisLayout = inflater.inflate(R.layout.fragment_about, container, false);
        return thisLayout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MobileAds.initialize(thisContext, APP_ID);
        mAdView = thisLayout.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
