package com.jrg.pisang.timesapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewFokusAdapter;
import com.jrg.pisang.timesapp.Api.ApiClient;
import com.jrg.pisang.timesapp.Api.ApiInterface;
import com.jrg.pisang.timesapp.Explore.DetailFokusActivity;
import com.jrg.pisang.timesapp.Model.DataFokus;
import com.jrg.pisang.timesapp.Model.Fokus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String key = "NyEIwDL51eeaoVhYGPaF";

    private RecyclerViewFokusAdapter recyclerViewFokusAdapter;

    private RecyclerView fokusRecyclerView;

    private List<DataFokus> fokus = new ArrayList<>();

    private ShimmerFrameLayout fokusShimmerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    public ExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        fokusShimmerLayout = view.findViewById(R.id.fokusShimmerLayout);

        fokusRecyclerView = view.findViewById(R.id.fokusRecyclerView);

        fokusShimmerLayout.startShimmer();

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
        showFocus();
    }

    private void showFocus() {
        fokusRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fokusRecyclerView.setItemAnimator(new DefaultItemAnimator());
        fokusRecyclerView.setNestedScrollingEnabled(false);
        fokusRecyclerView.setAdapter(recyclerViewFokusAdapter);
    }

    private void initListenerFokus() {
        recyclerViewFokusAdapter.setOnItemClickListener(new RecyclerViewFokusAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), DetailFokusActivity.class);

                DataFokus data = fokus.get(position);
                intent.putExtra("id", data.getFocnews_id());
                startActivity(intent);
            }
        });
    }

    public void loadJSON() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Fokus> callFokus;

        callFokus = apiInterface.getFokus(key, 0, 15);
        callFokus.enqueue(new Callback<Fokus>() {
            @Override
            public void onResponse(Call<Fokus> call, Response<Fokus> response) {
                if (response.isSuccessful() && response.body().getData() != null) {

                    fokus = response.body().getData();
                    recyclerViewFokusAdapter = new RecyclerViewFokusAdapter(fokus, getContext());
                    fokusRecyclerView.setAdapter(recyclerViewFokusAdapter);
                    recyclerViewFokusAdapter.notifyDataSetChanged();

                    Log.e("MASUK LOAD", fokus.toString());

                    initListenerFokus();

                    swipeRefreshLayout.setRefreshing(false);
                    fokusShimmerLayout.stopShimmer();
                    fokusShimmerLayout.setVisibility(View.GONE);

                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(), "No Result!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Fokus> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        fokusShimmerLayout.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        fokusShimmerLayout.stopShimmer();
        fokusShimmerLayout.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        loadJSON();
    }

}
