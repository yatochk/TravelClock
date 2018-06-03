package com.yatochk.travelclock;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SearchFragment extends Fragment {

    public static SearchFragment getInstance() {
        Bundle args = new Bundle();
        SearchFragment searchFragment = new SearchFragment();
        searchFragment.setArguments(args);

        return searchFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

}
