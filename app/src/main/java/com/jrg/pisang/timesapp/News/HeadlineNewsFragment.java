package com.jrg.pisang.timesapp.News;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewNewsAdapter;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewPopularAdapter;
import com.jrg.pisang.timesapp.Api.ApiClient;
import com.jrg.pisang.timesapp.Api.ApiInterface;
import com.jrg.pisang.timesapp.Model.DataModel;
import com.jrg.pisang.timesapp.Model.HeadlineModel;
import com.jrg.pisang.timesapp.OneSignalApplication;
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

    private List<DataModel> datas = new ArrayList<>();
    private List<DataModel> populars = new ArrayList<>();
    private List<DataModel> trendings = new ArrayList<>();

    private ShimmerFrameLayout headlineShimmerLayout, subheadlineShimmerLayout, trendingShimmerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    private LinearLayout populerLL, trendingLL;

    //error layout
    private RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle, errorMessage;
    private Button btnRetry;

    private OneSignalApplication mInstance;

    public HeadlineNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_headline_news, container, false);

        mInstance = OneSignalApplication.getInstance();

        //error layout
        errorLayout = view.findViewById(R.id.errorLayout);
        errorImage = view.findViewById(R.id.errorImage);
        errorTitle = view.findViewById(R.id.errorTitle);
        errorMessage = view.findViewById(R.id.errorMessage);
        btnRetry = view.findViewById(R.id.btnRetry);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        headlineShimmerLayout = view.findViewById(R.id.headlineShimmerLayout);
        subheadlineShimmerLayout = view.findViewById(R.id.subheadlineShimmerLayout);
        trendingShimmerLayout = view.findViewById(R.id.trendingShimmerLayout);

        headlineRecyclerView = view.findViewById(R.id.headlineRecyclerView);
        popularRecyclerView = view.findViewById(R.id.popularRecyclerView);
        trendingRecyclerView = view.findViewById(R.id.trendingRecyclerView);

        populerLL = view.findViewById(R.id.populerLL);
        trendingLL = view.findViewById(R.id.trendingLL);

        populerLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PopularActivity.class);
                startActivity(i);
            }
        });

        trendingLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), TrendingActivity.class);
                startActivity(i);
            }
        });
        subheadlineShimmerLayout.startShimmer();
        headlineShimmerLayout.startShimmer();
        trendingShimmerLayout.startShimmer();

        setRecyclerView();

        loadJSON();

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

                DataModel data = datas.get(position);
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

                DataModel data = populars.get(position);
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

                DataModel data = trendings.get(position);
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

        errorLayout.setVisibility(View.GONE);

        swipeRefreshLayout.setRefreshing(true);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<HeadlineModel> callHeadline, callPopular, callTrending;

        callHeadline = apiInterface.getNewsHeadline(key, "headline", 0, 5);
        callHeadline.enqueue(new Callback<HeadlineModel>() {
            @Override
            public void onResponse(Call<HeadlineModel> call, Response<HeadlineModel> response) {
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

                    String errorCode;
                    switch (response.code()) {
                        case 404:
                            errorCode = " 404 not found ";
                            break;
                        case 500:
                            errorCode = " 500 Server broken ";
                            break;
                        default:
                            errorCode = "Uknown error";
                            break;
                    }
                    showErrorMessage(R.drawable.no_result, "No Result", "Please Try Again\n" +
                            "" + errorCode);
                }
            }

            @Override
            public void onFailure(Call<HeadlineModel> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                showErrorMessage(R.drawable.no_result, "Oopss..", "Network failure, Please Try Again\n" +
                        "" + t.toString());
            }
        });

        //popular
        callPopular = apiInterface.getNewsHeadline(key, "populer", 0, 10);
        callPopular.enqueue(new Callback<HeadlineModel>() {
            @Override
            public void onResponse(Call<HeadlineModel> call, Response<HeadlineModel> response) {
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
            public void onFailure(Call<HeadlineModel> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        //trending
        callTrending = apiInterface.getNewsHeadline(key, "trending", 0, 10);
        callTrending.enqueue(new Callback<HeadlineModel>() {
            @Override
            public void onResponse(Call<HeadlineModel> call, Response<HeadlineModel> response) {
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
            public void onFailure(Call<HeadlineModel> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
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

    private void showErrorMessage(int imageView, String title, String message) {
        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }

        errorImage.setImageResource(imageView);
        errorTitle.setText(title);
        errorMessage.setText(message);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
    }
}
