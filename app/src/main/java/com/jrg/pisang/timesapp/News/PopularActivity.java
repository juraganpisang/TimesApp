package com.jrg.pisang.timesapp.News;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewNewsAdapter;
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

public class PopularActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String key = "NyEIwDL51eeaoVhYGPaF";

    private RecyclerViewNewsAdapter recyclerViewNewsAdapter;

    private RecyclerView popularRecyclerView;

    private List<Data> populars = new ArrayList<>();

    private ShimmerFrameLayout popularShimmerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        popularRecyclerView = findViewById(R.id.popularRecyclerView);
        popularShimmerLayout = findViewById(R.id.popularShimmerLayout);

        popularShimmerLayout.startShimmer();

        setRecyclerView();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadJSON();
            }
        }, 2000);
    }
    private void setRecyclerView() {
        showLatest();
    }

    private void showLatest() {
        popularRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        popularRecyclerView.setItemAnimator(new DefaultItemAnimator());
        popularRecyclerView.setNestedScrollingEnabled(false);
        popularRecyclerView.setAdapter(recyclerViewNewsAdapter);
    }


    private void initListenerPopular() {
        recyclerViewNewsAdapter.setOnItemClickListener(new RecyclerViewNewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(PopularActivity.this, DetailNewsActivity.class);

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


    public void loadJSON() {
        swipeRefreshLayout.setRefreshing(true);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Headline> callPopular;

        callPopular = apiInterface.getNewsHeadline(key, "populer", 0, 30);
        callPopular.enqueue(new Callback<Headline>() {
            @Override
            public void onResponse(Call<Headline> call, Response<Headline> response) {
                if (response.isSuccessful() && response.body().getData() != null) {
                    if (!populars.isEmpty()) {
                        populars.clear();
                    }

                    populars = response.body().getData();
                    recyclerViewNewsAdapter = new RecyclerViewNewsAdapter(populars, getApplicationContext());
                    popularRecyclerView.setAdapter(recyclerViewNewsAdapter);
                    recyclerViewNewsAdapter.notifyDataSetChanged();

                    initListenerPopular();
                    swipeRefreshLayout.setRefreshing(false);

                    popularShimmerLayout.stopShimmer();
                    popularShimmerLayout.setVisibility(View.GONE);

                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getApplication(), "No Result!", Toast.LENGTH_SHORT).show();
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
        popularShimmerLayout.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        popularShimmerLayout.stopShimmer();
        popularShimmerLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        loadJSON();
    }
}
