package com.jrg.pisang.timesapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.facebook.shimmer.ShimmerFrameLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class KanalFragment extends Fragment {
    ShimmerFrameLayout shimmerFrameLayout ;

    public KanalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kanal, container, false);
       // @bind(R.id.shimmer_view_container) ShimmerFrameLayout shimmerFrameLayout;
        //shimmerFrameLayout.bind(this);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);

    }

}
