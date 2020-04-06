package com.jrg.pisang.timesapp.News;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewNewsAdapter;
import com.jrg.pisang.timesapp.Model.NewsModel;
import com.jrg.pisang.timesapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeadlineNewsFragment extends Fragment {

    RecyclerViewNewsAdapter recyclerViewNewsAdapter;
    RecyclerView recyclerView;
    ArrayList<NewsModel> models = new ArrayList<>();
    ShimmerFrameLayout parentShimmerLayout;

    CollapsingToolbarLayout collapsingToolbarLayout;

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

        recyclerViewNewsAdapter = new RecyclerViewNewsAdapter(models, getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(recyclerViewNewsAdapter);

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

                recyclerViewNewsAdapter.isShimmer = false;

                parentShimmerLayout.stopShimmer();
                parentShimmerLayout.setShimmer(null);
                parentShimmerLayout.setVisibility(View.GONE);

                Log.e("SHIMMER", String.valueOf(recyclerViewNewsAdapter.isShimmer));
                recyclerViewNewsAdapter.notifyDataSetChanged();
            }
        }, 5000);

        return view;
    }

}
