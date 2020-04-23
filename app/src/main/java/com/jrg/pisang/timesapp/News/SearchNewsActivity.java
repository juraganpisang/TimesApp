package com.jrg.pisang.timesapp.News;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

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

public class SearchNewsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SearchView searchView;
    public static final String key = "NyEIwDL51eeaoVhYGPaF";

    private RecyclerViewNewsAdapter recyclerViewNewsAdapter;

    private RecyclerView headlineRecyclerView;

    private List<DataModel> datas = new ArrayList<>();

    private ShimmerFrameLayout headlineShimmerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    private String tempKeyword = "";

    //error layout
    private RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle, errorMessage;
    private Button btnRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_news);

        //error layout
        errorLayout = findViewById(R.id.errorLayout);
        errorImage = findViewById(R.id.errorImage);
        errorTitle = findViewById(R.id.errorTitle);
        errorMessage = findViewById(R.id.errorMessage);
        btnRetry = findViewById(R.id.btnRetry);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        headlineShimmerLayout = findViewById(R.id.headlineShimmerLayout);

        headlineRecyclerView = findViewById(R.id.headlineRecyclerView);

        searchView = findViewById(R.id.svSearch);

        //search
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.length() > 2){
                    loadJSON(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //loadJSON(newText);
                return false;
            }
        });

        showHeadline();

        loadJSON(tempKeyword);
    }


    private void showHeadline() {
        headlineRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        headlineRecyclerView.setItemAnimator(new DefaultItemAnimator());
        headlineRecyclerView.setNestedScrollingEnabled(false);
        headlineRecyclerView.setAdapter(recyclerViewNewsAdapter);
    }

    private void initListenerHeadline() {
        recyclerViewNewsAdapter.setOnItemClickListener(new RecyclerViewNewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), DetailNewsActivity.class);

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

    public void loadJSON(final String keyword) {

        errorLayout.setVisibility(View.GONE);

        swipeRefreshLayout.setRefreshing(true);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<HeadlineModel> callHeadline;

        if(keyword.length() > 0){
            tempKeyword = keyword;
            callHeadline = apiInterface.getListTag(key, "search", keyword , 0, 10);
        }else{
            callHeadline = apiInterface.getNewsHeadline(key, "headline" ,0, 5);
        }
        callHeadline.enqueue(new Callback<HeadlineModel>() {
            @Override
            public void onResponse(Call<HeadlineModel> call, Response<HeadlineModel> response) {
                if (response.isSuccessful() && response.body().getData() != null) {
                    if (!datas.isEmpty()) {
                        datas.clear();
                    }

                    datas = response.body().getData();
                    recyclerViewNewsAdapter = new RecyclerViewNewsAdapter(datas, getApplicationContext());
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
    }

    @Override
    public void onResume() {
        super.onResume();
        headlineShimmerLayout.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        headlineShimmerLayout.stopShimmer();
        headlineShimmerLayout.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        loadJSON(tempKeyword);
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
    }}
