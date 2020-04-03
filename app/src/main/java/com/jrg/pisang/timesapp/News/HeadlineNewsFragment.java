package com.jrg.pisang.timesapp.News;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewAdapter;
import com.jrg.pisang.timesapp.Model.NewsModel;
import com.jrg.pisang.timesapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeadlineNewsFragment extends Fragment {

    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    ArrayList<NewsModel> models = new ArrayList<>();
    ShimmerFrameLayout parentShimmerLayout;

    public HeadlineNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_headline_news, container, false);
        parentShimmerLayout = view.findViewById(R.id.parentShimmerLayout);
        recyclerView = view.findViewById(R.id.newsRecyclerView);

        recyclerViewAdapter = new RecyclerViewAdapter(models, getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(recyclerViewAdapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                models.add(new NewsModel("GARA GARA CORONA STIKI DITUTUP", "SAKTI", "20.00 WIB"));
                models.add(new NewsModel("GARA GARA CORONA STIKI DITUTUP", "SAKTI", "20.00 WIB"));
                models.add(new NewsModel("GARA GARA CORONA STIKI DITUTUP", "SAKTI", "20.00 WIB"));
                models.add(new NewsModel("GARA GARA CORONA STIKI DITUTUP", "SAKTI", "20.00 WIB"));
                models.add(new NewsModel("GARA GARA CORONA STIKI DITUTUP", "SAKTI", "20.00 WIB"));
                models.add(new NewsModel("GARA GARA CORONA STIKI DITUTUP", "SAKTI", "20.00 WIB"));
                models.add(new NewsModel("GARA GARA CORONA STIKI DITUTUP", "SAKTI", "20.00 WIB"));
                models.add(new NewsModel("GARA GARA CORONA STIKI DITUTUP", "SAKTI", "20.00 WIB"));
                models.add(new NewsModel("GARA GARA CORONA STIKI DITUTUP", "SAKTI", "20.00 WIB"));
                models.add(new NewsModel("GARA GARA CORONA STIKI DITUTUP", "SAKTI", "20.00 WIB"));
                models.add(new NewsModel("GARA GARA CORONA STIKI DITUTUP", "SAKTI", "20.00 WIB"));

                recyclerViewAdapter.isShimmer = false;

                parentShimmerLayout.stopShimmer();
                parentShimmerLayout.setShimmer(null);
                parentShimmerLayout.setVisibility(View.GONE);

                Log.e("SHIMMER", String.valueOf(recyclerViewAdapter.isShimmer));
                recyclerViewAdapter.notifyDataSetChanged();
            }
        }, 5000);

        return view;
    }

}
