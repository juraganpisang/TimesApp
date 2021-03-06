package com.jrg.pisang.timesapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewFokusAdapter;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewKanalAdapter;
import com.jrg.pisang.timesapp.Api.ApiClient;
import com.jrg.pisang.timesapp.Api.ApiInterface;
import com.jrg.pisang.timesapp.Explore.DetailFokusActivity;
import com.jrg.pisang.timesapp.Model.DataFokusModel;
import com.jrg.pisang.timesapp.Model.DataKanalModel;
import com.jrg.pisang.timesapp.Model.FokusModel;
import com.jrg.pisang.timesapp.Model.KanalModel;
import com.jrg.pisang.timesapp.Explore.DetailKanalActivity;
import com.jrg.pisang.timesapp.News.SearchNewsActivity;

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
    private RecyclerViewKanalAdapter recyclerViewKanalAdapter;

    private RecyclerView fokusRecyclerView, kanalRecyclerView;

    private DataFokusModel lists;

    private List<DataFokusModel> fokus = new ArrayList<>();
    private List<DataKanalModel> kanal = new ArrayList<>();

    private ShimmerFrameLayout fokusShimmerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    private View searchView;

    public ExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        searchView = view.findViewById(R.id.SearchExplore);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), SearchNewsActivity.class);
                startActivity(i);
            }
        });

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        fokusShimmerLayout = view.findViewById(R.id.fokusShimmerLayout);

        fokusRecyclerView = view.findViewById(R.id.fokusRecyclerView);
        kanalRecyclerView = view.findViewById(R.id.kanalRecyclerView);

        fokusShimmerLayout.startShimmer();

        setRecyclerView();
        loadJSON();
        return view;
    }


    private void setRecyclerView() {
        showFocus();
        showListKanal();
    }

    private void showFocus() {

        fokusRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.HORIZONTAL, false));
//        fokusRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fokusRecyclerView.setItemAnimator(new DefaultItemAnimator());
        fokusRecyclerView.setNestedScrollingEnabled(false);
        fokusRecyclerView.setAdapter(recyclerViewFokusAdapter);
    }

    private void showListKanal() {
        kanalRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.HORIZONTAL, false));
//        fokusRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        kanalRecyclerView.setItemAnimator(new DefaultItemAnimator());
        kanalRecyclerView.setNestedScrollingEnabled(false);
        kanalRecyclerView.setAdapter(recyclerViewKanalAdapter);
    }

    private void initListenerFokus() {
        recyclerViewFokusAdapter.setOnItemClickListener(new RecyclerViewFokusAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), DetailFokusActivity.class);

                DataFokusModel data = fokus.get(position);
                intent.putExtra("id", data.getFocnews_id());
                startActivity(intent);
            }
        });
    }


    private void initListenerKanal() {
        recyclerViewKanalAdapter.setOnItemClickListener(new RecyclerViewKanalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                DataKanalModel data = kanal.get(position);
                Intent intent = new Intent(getContext(), DetailKanalActivity.class);
                intent.putExtra("id", data.getId());
                intent.putExtra("name", data.getSlug());
                intent.putExtra("url", data.getUrl());
                if (data.isNew()) {
                    intent.putExtra("new", data.getName());
                }
                startActivity(intent);
            }
        });
    }

    public void loadJSON() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<FokusModel> callFokus;
        Call<KanalModel> callListKanalDetail;

        callFokus = apiInterface.getFokus(key, 0, 9);
        callFokus.enqueue(new Callback<FokusModel>() {
            @Override
            public void onResponse(Call<FokusModel> call, Response<FokusModel> response) {
                if (response.isSuccessful() && response.body().getData() != null) {

                    fokus = response.body().getData();
                    recyclerViewFokusAdapter = new RecyclerViewFokusAdapter(fokus, getContext());
                    fokusRecyclerView.setAdapter(recyclerViewFokusAdapter);
                    recyclerViewFokusAdapter.notifyDataSetChanged();

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
            public void onFailure(Call<FokusModel> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        //kanal
        callListKanalDetail = apiInterface.getKanal(key);
        callListKanalDetail.enqueue(new Callback<KanalModel>() {
            @Override
            public void onResponse(Call<KanalModel> call, Response<KanalModel> response) {
                if (response.isSuccessful() && response.body().getData() != null) {

                    kanal = response.body().getData();
                    //tambah kanal
                    DataKanalModel kanalTv = new DataKanalModel();
                    kanalTv.setName("Times TV");
                    kanalTv.setId("99");
                    kanalTv.setUrl("/tag/timesvlog");
                    kanalTv.setNew(true);
                    DataKanalModel kanalBola = new DataKanalModel();
                    kanalBola.setName("Jadwal Sepakbola");
                    kanalBola.setId("100");
                    kanalBola.setUrl("/tag/jadwal-sepakbola");
                    kanalBola.setNew(true);
                    kanal.add(kanalTv);
                    kanal.add(kanalBola);

                    recyclerViewKanalAdapter = new RecyclerViewKanalAdapter(kanal, getContext());
                    kanalRecyclerView.setAdapter(recyclerViewKanalAdapter);
                    recyclerViewKanalAdapter.notifyDataSetChanged();
                    initListenerKanal();
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    Toast.makeText(getContext(), "No Result!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KanalModel> call, Throwable t) {
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
