package com.jrg.pisang.timesapp.Media;


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
import com.jrg.pisang.timesapp.Adapter.RecyclerViewFotoAdapter;
import com.jrg.pisang.timesapp.Api.ApiClient;
import com.jrg.pisang.timesapp.Api.ApiInterface;
import com.jrg.pisang.timesapp.Model.DataFotoModel;
import com.jrg.pisang.timesapp.Model.FotoModel;
import com.jrg.pisang.timesapp.News.DetailNewsActivity;
import com.jrg.pisang.timesapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FotoMediaFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {

    public static final String key = "NyEIwDL51eeaoVhYGPaF";

    private RecyclerViewFotoAdapter recyclerViewFotoAdapter;

    private RecyclerView fotoRecyclerView;

    private List<DataFotoModel> fotos = new ArrayList<>();

    private ShimmerFrameLayout fotoShimmerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    public FotoMediaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_foto_media, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        fotoShimmerLayout = view.findViewById(R.id.fotoShimmerLayout);

        fotoRecyclerView = view.findViewById(R.id.fotoRecyclerView);

        fotoShimmerLayout.startShimmer();

        setRecyclerView();

        loadJSON();

        return view;
    }
    private void setRecyclerView() {
        showFoto();
    }

    private void showFoto() {
        fotoRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        ekoranRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        ekoranRecyclerView.setNestedScrollingEnabled(false);
        fotoRecyclerView.setAdapter(recyclerViewFotoAdapter);
        fotoRecyclerView.setHasFixedSize(true);
    }
    private void initListenerFoto() {
        recyclerViewFotoAdapter.setOnItemClickListener(new RecyclerViewFotoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), DetailFotoActivity.class);

                DataFotoModel data = fotos.get(position);
                intent.putExtra("id", data.getGal_id());
                startActivity(intent);
            }
        });
    }

    public void loadJSON() {
        swipeRefreshLayout.setRefreshing(true);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<FotoModel> callFoto;

        callFoto = apiInterface.getFoto(key, "all", 0, 20);
        callFoto.enqueue(new Callback<FotoModel>() {
            @Override
            public void onResponse(Call<FotoModel> call, Response<FotoModel> response) {
                if (response.isSuccessful() && response.body().getData() != null) {

                    fotos = response.body().getData();
                    recyclerViewFotoAdapter = new RecyclerViewFotoAdapter(fotos, getContext());
                    fotoRecyclerView.setAdapter(recyclerViewFotoAdapter);
                    recyclerViewFotoAdapter.notifyDataSetChanged();

                    initListenerFoto();
                    swipeRefreshLayout.setRefreshing(false);

                    fotoShimmerLayout.stopShimmer();
                    fotoShimmerLayout.setVisibility(View.GONE);

                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(), "No Result!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FotoModel> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        fotoShimmerLayout.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        fotoShimmerLayout.stopShimmer();
        fotoShimmerLayout.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        loadJSON();
    }
}
