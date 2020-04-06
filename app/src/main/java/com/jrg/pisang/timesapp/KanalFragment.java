package com.jrg.pisang.timesapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;
import com.jrg.pisang.timesapp.Adapter.NewsAdapter;
import com.jrg.pisang.timesapp.News.DetailNewsActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class KanalFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    NewsAdapter viewNewsAdapter;

    public KanalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kanal, container, false);

        /////*     initialize view   */////
        viewPager = view.findViewById(R.id.viewPager);

        /////*     initialize ViewPager   */////
        viewNewsAdapter = new NewsAdapter(getFragmentManager());

        /////*     add adapter to ViewPager  */////
        viewPager.setAdapter(viewNewsAdapter);
        tabLayout = view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabRippleColor(null);

//        detail_berita = view.findViewById(R.id.detail_berita);
//        detail_berita.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i;
//                i = new Intent(getActivity(), DetailNewsActivity.class);
//                startActivity(i);
//            }
//        });

        return view;
    }
}
