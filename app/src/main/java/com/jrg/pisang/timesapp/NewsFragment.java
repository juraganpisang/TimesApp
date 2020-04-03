package com.jrg.pisang.timesapp;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.jrg.pisang.timesapp.Adapter.NewsAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    NewsAdapter viewNewsAdapter;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        /////*     initialize view   */////
        viewPager = view.findViewById(R.id.viewPager);

        /////*     initialize ViewPager   */////
        viewNewsAdapter = new NewsAdapter(getFragmentManager());

        /////*     add adapter to ViewPager  */////
        viewPager.setAdapter(viewNewsAdapter);
        tabLayout = view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabRippleColor(null);

        return view;
    }

}
