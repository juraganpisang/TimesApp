package com.jrg.pisang.timesapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.jrg.pisang.timesapp.Adapter.ExploreAdapter;
import com.jrg.pisang.timesapp.Adapter.NewsAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    ExploreAdapter exploreAdapter;

    public ExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        /////*     initialize view   */////
        viewPager = view.findViewById(R.id.viewPagerExplore);

        /////*     initialize ViewPager   */////
        exploreAdapter = new ExploreAdapter(getFragmentManager());

        /////*     add adapter to ViewPager  */////
        viewPager.setAdapter(exploreAdapter);
        tabLayout = view.findViewById(R.id.tabLayoutExplore);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabRippleColor(null);
        return view;
    }
}