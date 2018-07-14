package com.sololaunches.www.keralarailandmetro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.startapp.android.publish.adsCommon.StartAppSDK;


/**
 * Created by jithinarayan365@gmail.com on 7/5/2017.
 */

public class RailwayToFromFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StartAppSDK.init(getActivity(), "203651630", false);
        View view = inflater.inflate(R.layout.first_fragment_railways, container, false);
        return view;
    }


}

