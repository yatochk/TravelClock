package com.yatochk.travelclock.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yatochk.travelclock.fragment.AboutFragment;
import com.yatochk.travelclock.fragment.MapFragment;
import com.yatochk.travelclock.fragment.SettingFragment;

public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {

    private String[] tabs;

    public TabsPagerFragmentAdapter(FragmentManager fm) {
        super(fm);

        tabs = new String[] {
                "Settings",
                "Map",
                "About"
        };
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SettingFragment.getInstance();

            case 1:
                return MapFragment.getInstance();

            case 2:
                return AboutFragment.getInstance();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
}
