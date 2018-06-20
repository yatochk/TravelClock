package com.yatochk.travelclock.fragment.inMapFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yatochk.travelclock.R;
import com.yatochk.travelclock.fragment.MapFragment;


public class ConfirmDestination extends Fragment {
    private View thisLayout;
    private Button buttonGo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        thisLayout = inflater.inflate(R.layout.fragment_confirm_destination, container, false);
        return thisLayout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonGo = thisLayout.findViewById(R.id.buttonGo);
        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MapFragment.readyToGo){
                    MapFragment.getInstance().onStartWay();
                }
            }
        });
    }
}
