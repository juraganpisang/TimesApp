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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewNewsAdapter;
import com.jrg.pisang.timesapp.Api.ApiClient;
import com.jrg.pisang.timesapp.Api.ApiInterface;
import com.jrg.pisang.timesapp.Model.DataModel;
import com.jrg.pisang.timesapp.Model.HeadlineModel;
import com.jrg.pisang.timesapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LatestNewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String key = "NyEIwDL51eeaoVhYGPaF";

    private RecyclerViewNewsAdapter recyclerViewNewsAdapter;

    private RecyclerView latestRecyclerView;

    private List<DataModel> latests = new ArrayList<>();

    private ShimmerFrameLayout latestShimmerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    //error layout
    private RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle, errorMessage;
    private Button btnRetry;

    public LatestNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_latest_news, container, false);

        //error layout
        errorLayout = view.findViewById(R.id.errorLayout);
        errorImage = view.findViewById(R.id.errorImage);
        errorTitle = view.findViewById(R.id.errorTitle);
        errorMessage = view.findViewById(R.id.errorMessage);
        btnRetry = view.findViewById(R.id.btnRetry);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        latestShimmerLayout = view.findViewById(R.id.latestShimmerLayout);

        latestRecyclerView = view.findViewById(R.id.latestRecyclerView);

        latestShimmerLayout.startShimmer();

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
        showLatest();
    }

    private void showLatest() {
        latestRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        latestRecyclerView.setItemAnimator(new DefaultItemAnimator());
        latestRecyclerView.setNestedScrollingEnabled(false);
        latestRecyclerView.setAdapter(recyclerViewNewsAdapter);
    }


    private void initListenerLatest() {
        recyclerViewNewsAdapter.setOnItemClickListener(new RecyclerViewNewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), DetailNewsActivity.class);

                DataModel data = latests.get(position);
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
        Call<HeadlineModel> callLatest;

        callLatest = apiInterface.getNewsHeadline(key, "all", 0, 20);
        callLatest.enqueue(new Callback<HeadlineModel>() {
            @Override
            public void onResponse(Call<HeadlineModel> call, Response<HeadlineModel> response) {
                if (response.isSuccessful() && response.body().getData() != null) {
                    if (!latests.isEmpty()) {
                        latests.clear();
                    }

                    latests = response.body().getData();
                    recyclerViewNewsAdapter = new RecyclerViewNewsAdapter(latests, getContext());
                    latestRecyclerView.setAdapter(recyclerViewNewsAdapter);
                    recyclerViewNewsAdapter.notifyDataSetChanged();

                    initListenerLatest();
                    swipeRefreshLayout.setRefreshing(false);

                    latestShimmerLayout.stopShimmer();
                    latestShimmerLayout.setVisibility(View.GONE);

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
                            ""+errorCode);
                }
            }

            @Override
            public void onFailure(Call<HeadlineModel> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setRefreshing(false);
                showErrorMessage(R.drawable.no_result, "Oopss..", "Network failure, Please Try Again\n" +
                        ""+t.toString());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setRecyclerView();
        latestShimmerLayout.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        latestShimmerLayout.stopShimmer();
        latestShimmerLayout.setVisibility(View.GONE);
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
