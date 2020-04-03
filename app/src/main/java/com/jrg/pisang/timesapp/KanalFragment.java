package com.jrg.pisang.timesapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toolbar;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.jrg.pisang.timesapp.News.DetailBerita;


/**
 * A simple {@link Fragment} subclass.
 */
public class KanalFragment extends Fragment {
    Button detail_berita;

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
        detail_berita = view.findViewById(R.id.detail_berita);
        detail_berita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(getActivity(), DetailBerita.class);
                startActivity(i);
            }
        });

    }

}
