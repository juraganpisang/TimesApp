package com.jrg.pisang.timesapp.News;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewNewsAdapter;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewSubNewsAdapter;
import com.jrg.pisang.timesapp.Api.ApiClient;
import com.jrg.pisang.timesapp.Api.ApiInterface;
import com.jrg.pisang.timesapp.Model.Data;
import com.jrg.pisang.timesapp.Model.Headline;
import com.jrg.pisang.timesapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeadlineNewsFragment extends Fragment {

    public static final String key = "NyEIwDL51eeaoVhYGPaF";

    private RecyclerViewNewsAdapter recyclerViewNewsAdapter;
    private RecyclerViewSubNewsAdapter recyclerViewSubNewsAdapter;

    private RecyclerView headlineRecyclerView, subheadlineRecyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private List<Data> datas = new ArrayList<>();
    ShimmerFrameLayout headlineShimmerLayout, subheadlineShimmerLayout;

    public HeadlineNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_headline_news, container, false);

        headlineShimmerLayout = view.findViewById(R.id.headlineShimmerLayout);
        subheadlineShimmerLayout = view.findViewById(R.id.subheadlineShimmerLayout);

        headlineRecyclerView = view.findViewById(R.id.headlineRecyclerView);
        subheadlineRecyclerView = view.findViewById(R.id.subheadlineRecyclerView);

        subheadlineShimmerLayout.startShimmer();
        headlineShimmerLayout.startShimmer();

        setRecyclerView();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadJSON();
            }
        }, 2000);

        return view;
    }

    private void setRecyclerView() {
        showHeadline();
    }

    private void showHeadline() {
        headlineRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        headlineRecyclerView.setItemAnimator(new DefaultItemAnimator());
        headlineRecyclerView.setNestedScrollingEnabled(false);
        headlineRecyclerView.setAdapter(recyclerViewNewsAdapter);
    }

//    private void showSubheadline() {
//        recyclerViewSubNewsAdapter = new RecyclerViewSubNewsAdapter(models, getContext());
//
//        subheadlineRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        subheadlineRecyclerView.hasFixedSize();
//        subheadlineRecyclerView.setAdapter(recyclerViewSubNewsAdapter);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                models.add(new NewsModel("GARA GARA CORONA STIKI DITUTUP", "SAKTI", "20.00 WIB"));
//                models.add(new NewsModel("GARA GARA CORONA STIKI DITUTUP", "SAKTI", "20.00 WIB"));
//
//                subheadlineShimmerLayout.stopShimmer();
//                subheadlineShimmerLayout.setShimmer(null);
//                subheadlineShimmerLayout.setVisibility(View.GONE);
//
//                recyclerViewSubNewsAdapter.notifyDataSetChanged();
//            }
//        }, 5000);
//    }

    public void loadJSON() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Headline> call;

        call = apiInterface.getNewsHeadline("NyEIwDL51eeaoVhYGPaF", "headline", 0, 5);
        call.enqueue(new Callback<Headline>() {
            @Override
            public void onResponse(Call<Headline> call, Response<Headline> response) {
                if (response.isSuccessful() && response.body().getData() != null) {
                    if (!datas.isEmpty()) {
                        datas.clear();
                    }
                    datas = response.body().getData();
                    recyclerViewNewsAdapter = new RecyclerViewNewsAdapter(datas, getContext());
                    headlineRecyclerView.setAdapter(recyclerViewNewsAdapter);
                    recyclerViewNewsAdapter.notifyDataSetChanged();

                    subheadlineShimmerLayout.stopShimmer();
                    headlineShimmerLayout.stopShimmer();
                    subheadlineShimmerLayout.setVisibility(View.GONE);
                    headlineShimmerLayout.setVisibility(View.GONE);

                } else {
                    Toast.makeText(getContext(), "No Result!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Headline> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setRecyclerView();
        subheadlineShimmerLayout.startShimmer();
        headlineShimmerLayout.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        subheadlineShimmerLayout.stopShimmer();
        headlineShimmerLayout.stopShimmer();
        subheadlineShimmerLayout.setVisibility(View.GONE);
        headlineShimmerLayout.setVisibility(View.GONE);
    }
}
