package com.yatochk.travelclock.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yatochk.travelclock.CompleteFragment;
import com.yatochk.travelclock.SearchFragment;
import com.yatochk.travelclock.SettingFragment;
import com.yatochk.travelclock.fragment.Location;

public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {

    private String[] tabs;

    public TabsPagerFragmentAdapter(FragmentManager fm) {
        super(fm);

        tabs = new String[] {
                "Location",
                "Search",
                "Setting",
                "Complete"
        };
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return Location.getInstance();

            case 1:
                return SearchFragment.getInstance();

            case 2:
                return SettingFragment.getInstance();

            case 3:
                return CompleteFragment.getInstance();
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
