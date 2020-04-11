package com.jrg.pisang.timesapp.News;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import com.jrg.pisang.timesapp.Adapter.RecyclerViewNewsAdapter;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewPopularAdapter;
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
public class HeadlineNewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String key = "NyEIwDL51eeaoVhYGPaF";

    private RecyclerViewNewsAdapter recyclerViewNewsAdapter;
    private RecyclerViewPopularAdapter recyclerViewPopularAdapter;

    private RecyclerView headlineRecyclerView, popularRecyclerView, trendingRecyclerView;

    private List<Data> datas = new ArrayList<>();
    private List<Data> populars = new ArrayList<>();
    private List<Data> trendings = new ArrayList<>();

    ShimmerFrameLayout headlineShimmerLayout, subheadlineShimmerLayout, trendingShimmerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    public HeadlineNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_headline_news, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        headlineShimmerLayout = view.findViewById(R.id.headlineShimmerLayout);
        subheadlineShimmerLayout = view.findViewById(R.id.subheadlineShimmerLayout);
        trendingShimmerLayout = view.findViewById(R.id.trendingShimmerLayout);

        headlineRecyclerView = view.findViewById(R.id.headlineRecyclerView);
        popularRecyclerView = view.findViewById(R.id.popularRecyclerView);
        trendingRecyclerView = view.findViewById(R.id.trendingRecyclerView);

        subheadlineShimmerLayout.startShimmer();
        headlineShimmerLayout.startShimmer();
        trendingShimmerLayout.startShimmer();

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
        showPopular();
        showTrending();
    }

    private void showHeadline() {
        headlineRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        headlineRecyclerView.setItemAnimator(new DefaultItemAnimator());
        headlineRecyclerView.setNestedScrollingEnabled(false);
        headlineRecyclerView.setAdapter(recyclerViewNewsAdapter);
    }

    private void showPopular() {
        popularRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        popularRecyclerView.setItemAnimator(new DefaultItemAnimator());
        popularRecyclerView.setNestedScrollingEnabled(false);
        popularRecyclerView.setAdapter(recyclerViewPopularAdapter);
    }

    private void showTrending() {
        trendingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        trendingRecyclerView.setItemAnimator(new DefaultItemAnimator());
        trendingRecyclerView.setNestedScrollingEnabled(false);
        trendingRecyclerView.setAdapter(recyclerViewPopularAdapter);
    }

    private void initListenerHeadline() {
        recyclerViewNewsAdapter.setOnItemClickListener(new RecyclerViewNewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), DetailNewsActivity.class);

                Data data = datas.get(position);
                intent.putExtra("id", data.getNews_id());
                intent.putExtra("title", data.getNews_title());
                intent.putExtra("caption", data.getNews_caption());
                intent.putExtra("image", data.getNews_image_new());
                intent.putExtra("content", data.getNews_content());
                intent.putExtra("date", data.getNews_datepub());
                intent.putExtra("source", data.getNews_writer());
                intent.putExtra("url", data.getUrl_ci());
                intent.putExtra("tags", data.getNews_tags());

                startActivity(intent);
            }
        });
    }

    private void initListenerPopular() {
        recyclerViewPopularAdapter.setOnItemClickListener(new RecyclerViewPopularAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), DetailNewsActivity.class);

                Data data = populars.get(position);
                intent.putExtra("id", data.getNews_id());
                intent.putExtra("title", data.getNews_title());
                intent.putExtra("caption", data.getNews_caption());
                intent.putExtra("image", data.getNews_image_new());
                intent.putExtra("content", data.getNews_content());
                intent.putExtra("date", data.getNews_datepub());
                intent.putExtra("source", data.getNews_writer());
                intent.putExtra("url", data.getUrl_ci());
                intent.putExtra("tags", data.getNews_tags());

                startActivity(intent);
            }
        });
    }

    private void initListenerTrending() {
        recyclerViewPopularAdapter.setOnItemClickListener(new RecyclerViewPopularAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), DetailNewsActivity.class);

                Data data = trendings.get(position);
                intent.putExtra("id", data.getNews_id());
                intent.putExtra("title", data.getNews_title());
                intent.putExtra("caption", data.getNews_caption());
                intent.putExtra("image", data.getNews_image_new());
                intent.putExtra("content", data.getNews_content());
                intent.putExtra("date", data.getNews_datepub());
                intent.putExtra("source", data.getNews_writer());
                intent.putExtra("url", data.getUrl_ci());
                intent.putExtra("tags", data.getNews_tags());

                startActivity(intent);
            }
        });
    }

    public void loadJSON() {
        swipeRefreshLayout.setRefreshing(true);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Headline> callHeadline, callPopular, callTrending;

        callHeadline = apiInterface.getNewsHeadline(key, "headline", 0, 5);
        callHeadline.enqueue(new Callback<Headline>() {
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

                    initListenerHeadline();
                    swipeRefreshLayout.setRefreshing(false);

                    headlineShimmerLayout.stopShimmer();
                    headlineShimmerLayout.setVisibility(View.GONE);

                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(), "No Result!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Headline> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        //popular
        callPopular = apiInterface.getNewsHeadline(key, "populer", 0, 10);
        callPopular.enqueue(new Callback<Headline>() {
            @Override
            public void onResponse(Call<Headline> call, Response<Headline> response) {
                if (response.isSuccessful() && response.body().getData() != null) {
                    if (!populars.isEmpty()) {
                        populars.clear();
                    }

                    populars = response.body().getData();
                    recyclerViewPopularAdapter = new RecyclerViewPopularAdapter(populars, getContext());
                    popularRecyclerView.setAdapter(recyclerViewPopularAdapter);
                    recyclerViewPopularAdapter.notifyDataSetChanged();

                    initListenerPopular();

                    swipeRefreshLayout.setRefreshing(false);
                    subheadlineShimmerLayout.stopShimmer();
                    subheadlineShimmerLayout.setVisibility(View.GONE);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(), "No Result!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Headline> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        //trending
        callTrending = apiInterface.getNewsHeadline(key, "trending", 0, 10);
        callTrending.enqueue(new Callback<Headline>() {
            @Override
            public void onResponse(Call<Headline> call, Response<Headline> response) {
                if (response.isSuccessful() && response.body().getData() != null) {
                    if (!trendings.isEmpty()) {
                        trendings.clear();
                    }

                    trendings = response.body().getData();
                    recyclerViewPopularAdapter = new RecyclerViewPopularAdapter(trendings, getContext());
                    trendingRecyclerView.setAdapter(recyclerViewPopularAdapter);
                    recyclerViewPopularAdapter.notifyDataSetChanged();

                    initListenerTrending();

                    swipeRefreshLayout.setRefreshing(false);
                    trendingShimmerLayout.stopShimmer();
                    trendingShimmerLayout.setVisibility(View.GONE);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(), "No Result!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Headline> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
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

    @Override
    public void onRefresh() {
        loadJSON();
    }
}
