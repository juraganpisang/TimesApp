package com.jrg.pisang.timesapp.EKoran;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewEkoranAdapter;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewNewsAdapter;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewPopularAdapter;
import com.jrg.pisang.timesapp.Api.ApiClient;
import com.jrg.pisang.timesapp.Api.ApiInterface;
import com.jrg.pisang.timesapp.Model.DataKoran;
import com.jrg.pisang.timesapp.Model.Ekoran;
import com.jrg.pisang.timesapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EkoranFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String key = "NyEIwDL51eeaoVhYGPaF";

    private RecyclerViewEkoranAdapter recyclerViewEkoranAdapter;

    private RecyclerView ekoranRecyclerView;

    private List<DataKoran> korans = new ArrayList<>();

    private ShimmerFrameLayout ekoranShimmerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    public EkoranFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ekoran, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        ekoranShimmerLayout = view.findViewById(R.id.ekoranShimmerLayout);

        ekoranRecyclerView = view.findViewById(R.id.ekoranRecyclerView);

        ekoranShimmerLayout.startShimmer();

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
        showEkoran();
    }

    private void showEkoran() {
        ekoranRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
//        ekoranRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        ekoranRecyclerView.setNestedScrollingEnabled(false);
        ekoranRecyclerView.setAdapter(recyclerViewEkoranAdapter);
        ekoranRecyclerView.setHasFixedSize(true);
    }

    private void initListenerEkoran() {
        recyclerViewEkoranAdapter.setOnItemClickListener(new RecyclerViewEkoranAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), DetailEkoranActivity.class);

                DataKoran data = korans.get(position);
                intent.putExtra("id", data.getId());
                startActivity(intent);
            }
        });
    }

    public void loadJSON() {
        swipeRefreshLayout.setRefreshing(true);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Ekoran> callKoran;

        callKoran = apiInterface.getEKoran(key, 0, 15);
        callKoran.enqueue(new Callback<Ekoran>() {
            @Override
            public void onResponse(Call<Ekoran> call, Response<Ekoran> response) {
                if (response.isSuccessful() && response.body().getData() != null) {
                    if (!korans.isEmpty()) {
                        korans.clear();
                    }

                    korans = response.body().getData();
                    recyclerViewEkoranAdapter = new RecyclerViewEkoranAdapter(korans, getContext());
                    ekoranRecyclerView.setAdapter(recyclerViewEkoranAdapter);
                    recyclerViewEkoranAdapter.notifyDataSetChanged();

                    initListenerEkoran();
                    swipeRefreshLayout.setRefreshing(false);

                    ekoranShimmerLayout.stopShimmer();
                    ekoranShimmerLayout.setVisibility(View.GONE);

                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(), "No Result!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ekoran> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ekoranShimmerLayout.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        ekoranShimmerLayout.stopShimmer();
        ekoranShimmerLayout.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        loadJSON();
    }
}
