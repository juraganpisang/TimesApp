package com.jrg.pisang.timesapp.Explore;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewEkoranAdapter;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewFocusAdapter;
import com.jrg.pisang.timesapp.Api.ApiClient;
import com.jrg.pisang.timesapp.Api.ApiInterface;
import com.jrg.pisang.timesapp.EKoran.DetailEkoranActivity;
import com.jrg.pisang.timesapp.Model.DataFokus;
import com.jrg.pisang.timesapp.Model.DataKoran;
import com.jrg.pisang.timesapp.Model.Ekoran;
import com.jrg.pisang.timesapp.Model.Fokus;
import com.jrg.pisang.timesapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class KanalExploreFragment extends Fragment {

    public static final String key = "NyEIwDL51eeaoVhYGPaF";

    private RecyclerViewFocusAdapter recyclerViewFocusAdapter;

    private RecyclerView fokusRecyclerView;

    private List<DataFokus> fokus = new ArrayList<>();

    private ShimmerFrameLayout fokusShimmerLayout;
    public KanalExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kanal_explore, container, false);
        fokusShimmerLayout = view.findViewById(R.id.fokusRecyclerView);

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
        fokusRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
//        ekoranRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        ekoranRecyclerView.setNestedScrollingEnabled(false);
        fokusRecyclerView.setAdapter(recyclerViewFocusAdapter);
        fokusRecyclerView.setHasFixedSize(true);
    }

//    private void initListenerFokus() {
//        recyclerViewFocusAdapter.setOnItemClickListener(new RecyclerViewFocusAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(getContext(), DetailEkoranActivity.class);
//
//                DataFokus data = fokus.get(position);
//                intent.putExtra("id", data.getFocnews_id());
//                startActivity(intent);
//            }
//        });
//    }

    public void loadJSON() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Fokus> callFokus;

        callFokus = apiInterface.getFokus(key, 0, 15);
        callFokus.enqueue(new Callback<Fokus>() {
            @Override
            public void onResponse(Call<Fokus> call, Response<Fokus> response) {
                if (response.isSuccessful() && response.body().getData() != null) {
                    if (!fokus.isEmpty()) {
                        fokus.clear();
                    }

                    fokus = response.body().getData();
                    recyclerViewFocusAdapter = new RecyclerViewFocusAdapter(fokus, getContext());
                    fokusRecyclerView.setAdapter(recyclerViewFocusAdapter);
                    recyclerViewFocusAdapter.notifyDataSetChanged();

                    //initListenerEkoran();

                    fokusShimmerLayout.stopShimmer();
                    fokusShimmerLayout.setVisibility(View.GONE);

                } else {
                    Toast.makeText(getContext(), "No Result!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Fokus> call, Throwable t) {
                //swipeRefreshLayout.setRefreshing(false);
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
//
//    @Override
//    public void onRefresh() {
//        loadJSON();
//    }

}
