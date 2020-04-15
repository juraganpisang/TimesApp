package com.jrg.pisang.timesapp.Explore;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jrg.pisang.timesapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class KanalExploreFragment extends Fragment {


    public KanalExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kanal_explore, container, false);
    }

}
