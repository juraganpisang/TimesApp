package com.jrg.pisang.timesapp.EKoran;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewEkoranAdapter;
import com.jrg.pisang.timesapp.Api.ApiClient;
import com.jrg.pisang.timesapp.Api.ApiInterface;
import com.jrg.pisang.timesapp.Model.DataKoranModel;
import com.jrg.pisang.timesapp.Model.EkoranModel;
import com.jrg.pisang.timesapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EkoranFragment extends Fragment {

    public static final String key = "NyEIwDL51eeaoVhYGPaF";
    private RecyclerView ekoranRecyclerView;
    private ProgressBar progressBar;
    private GridLayoutManager layoutManager;

    private ApiInterface apiInterface;
    private RecyclerViewEkoranAdapter recyclerViewEkoranAdapter;

    private List<DataKoranModel> korans = new ArrayList<>();

    //private ShimmerFrameLayout ekoranShimmerLayout;
    //private SwipeRefreshLayout swipeRefreshLayout;

    private int page_number = 1;
    private int item_count = 15;

    private boolean isLoading = false;
    private int pastVisibleItem, visibleItemCOunt,totalItemCount=0;

    public EkoranFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ekoran, container, false);


//        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
//        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        progressBar=view.findViewById(R.id.progressBar);

//        ekoranShimmerLayout = view.findViewById(R.id.ekoranShimmerLayout);
//        ekoranShimmerLayout.startShimmer();
        ekoranRecyclerView = view.findViewById(R.id.ekoranRecyclerView);
        layoutManager = new GridLayoutManager(getContext(), 3);
        ekoranRecyclerView.setHasFixedSize(true);
        ekoranRecyclerView.setLayoutManager(layoutManager);
        ekoranRecyclerView.setItemAnimator(new DefaultItemAnimator());

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        progressBar.setVisibility(View.VISIBLE);
        Call<EkoranModel> callKoran;

        callKoran = apiInterface.getEKoran(key, page_number, item_count);

        callKoran.enqueue(new Callback<EkoranModel>() {
            @Override
            public void onResponse(Call<EkoranModel> call, Response<EkoranModel> response) {
                if (response.isSuccessful() && response.body().getData() != null) {
                    korans = response.body().getData();
                    recyclerViewEkoranAdapter = new RecyclerViewEkoranAdapter(korans, getContext());
                    ekoranRecyclerView.setAdapter(recyclerViewEkoranAdapter);
                    recyclerViewEkoranAdapter.notifyDataSetChanged();

                    initListenerEkoran();
//                    swipeRefreshLayout.setRefreshing(false);
//
//                    ekoranShimmerLayout.stopShimmer();
//                    ekoranShimmerLayout.setVisibility(View.GONE);

                    progressBar.setVisibility(View.GONE);
                    //item_count++;
                } else {
                    // swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(), "No Result!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EkoranModel> call, Throwable t) {
                //swipeRefreshLayout.setRefreshing(false);
            }
        });

        ekoranRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCOunt = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisibleItem = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading ) {
                    if ((visibleItemCOunt + pastVisibleItem) >= totalItemCount
                            && pastVisibleItem >= 0) {
                        page_number=page_number+item_count;
                        performPagination();
                        isLoading=false;
                    }
                }else{
                    //Toast.makeText(getContext(), "No Result 5", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //setRecyclerView();
        return view;
    }

//    private void setRecyclerView() {
//        showEkoran();
//    }

//    private void showEkoran() {
//        ekoranRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
////        ekoranRecyclerView.setItemAnimator(new DefaultItemAnimator());
////        ekoranRecyclerView.setNestedScrollingEnabled(false);
//        ekoranRecyclerView.setAdapter(recyclerViewEkoranAdapter);
//        ekoranRecyclerView.setHasFixedSize(true);
//    }

    private void initListenerEkoran() {
        recyclerViewEkoranAdapter.setOnItemClickListener(new RecyclerViewEkoranAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), DetailEkoranActivity.class);

                DataKoranModel data = korans.get(position);
                intent.putExtra("id", data.getId());
                startActivity(intent);
            }
        });
    }

    public void loadJSON() {
        //swipeRefreshLayout.setRefreshing(true);

    }

    public void performPagination() {
        progressBar.setVisibility(View.VISIBLE);
        //apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<EkoranModel> callKoran;

        callKoran = apiInterface.getEKoran(key, page_number, item_count);

        callKoran.enqueue(new Callback<EkoranModel>() {
            @Override
            public void onResponse(Call<EkoranModel> call, Response<EkoranModel> response) {
                if (response.isSuccessful() && response.body().getData() != null) {
                    List<DataKoranModel> dataKoranModelList = response.body().getData();
                    recyclerViewEkoranAdapter.addPagin(dataKoranModelList);

                } else {
                    //swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(), "No Result!", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<EkoranModel> call, Throwable t) {
                //swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//       // ekoranShimmerLayout.startShimmer();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
////        ekoranShimmerLayout.stopShimmer();
////        ekoranShimmerLayout.setVisibility(View.GONE);
//    }
}
