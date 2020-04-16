package com.jrg.pisang.timesapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.jrg.pisang.timesapp.Adapter.MediaAdapter;
import com.jrg.pisang.timesapp.Adapter.NewsAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MediaFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MediaAdapter viewMediaAdapter;

    public MediaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_media, container, false);
        /////*     initialize view   */////
        viewPager = view.findViewById(R.id.mediaViewPager);

        /////*     initialize ViewPager   */////
        viewMediaAdapter = new MediaAdapter(getFragmentManager());

        /////*     add adapter to ViewPager  */////
        viewPager.setAdapter(viewMediaAdapter);
        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabRippleColor(null);

        return view;

    }

}
