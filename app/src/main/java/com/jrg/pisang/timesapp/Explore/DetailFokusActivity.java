package com.jrg.pisang.timesapp.Explore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewDetailFokusAdapter;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewPopularAdapter;
import com.jrg.pisang.timesapp.Adapter.TagsAdapter;
import com.jrg.pisang.timesapp.Api.ApiClient;
import com.jrg.pisang.timesapp.Api.ApiInterface;
import com.jrg.pisang.timesapp.Model.Data;
import com.jrg.pisang.timesapp.Model.DataFokus;
import com.jrg.pisang.timesapp.Model.DataFokusDetail;
import com.jrg.pisang.timesapp.Model.Fokus;
import com.jrg.pisang.timesapp.Model.Headline;
import com.jrg.pisang.timesapp.News.DetailNewsActivity;
import com.jrg.pisang.timesapp.R;
import com.jrg.pisang.timesapp.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

public class DetailFokusActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private int id;
    private String mId;

    public static final String key = "NyEIwDL51eeaoVhYGPaF";

    private RecyclerViewDetailFokusAdapter recyclerViewDetailFokusAdapter;
    private RecyclerView listRecyclerView, keywordRecyclerView;
    private DataFokus lists;

    private TagsAdapter tagsAdapter;

    private ShimmerFrameLayout relatedShimmerLayout, detailShimmerLayout;

    private RecyclerViewPopularAdapter recyclerViewPopularAdapter;
    private TextView appbar_title, appbar_subtile, title, date, description;
    private boolean isHideToolbar = false;
    private LinearLayout titleAppbar;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;

    private NestedScrollView nestedScrollView;

    private String[] keyword;
    private ArrayList<String> listKeyword = new ArrayList<>();
    private List<Data> listNews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_fokus);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        relatedShimmerLayout = findViewById(R.id.relatedShimmerLayout);
        detailShimmerLayout = findViewById(R.id.detailShimmerLayout);

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");

        nestedScrollView = findViewById(R.id.detailNSV);
        appBarLayout = findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(this);
        titleAppbar = findViewById(R.id.title_appbar);
        appbar_title = findViewById(R.id.title_on_appbar);
        appbar_subtile = findViewById(R.id.subtitle_on_appbar);

        title = findViewById(R.id.textViewTitle);
        date = findViewById(R.id.textViewDate);
        description = findViewById(R.id.textViewDescription);

        keywordRecyclerView = findViewById(R.id.keywordRecyclerView);
        listRecyclerView = findViewById(R.id.listRecyclerView);

        relatedShimmerLayout.startShimmer();
        detailShimmerLayout.startShimmer();

        //get intent
        Intent intent = getIntent();
        mId = intent.getStringExtra("id");
        id = Integer.valueOf(mId);
//
        setRecyclerView();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadJSON();
            }
        }, 2000);
    }

    private void setRecyclerView() {
        showKeyword();
        showListFokus();
    }

    private void showKeyword() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        keywordRecyclerView.setLayoutManager(layoutManager);
        tagsAdapter = new TagsAdapter(listKeyword, this);
        keywordRecyclerView.setAdapter(tagsAdapter);
    }

    private void showListFokus() {
        listRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        listRecyclerView.setItemAnimator(new DefaultItemAnimator());
        listRecyclerView.setNestedScrollingEnabled(false);
        listRecyclerView.setAdapter(recyclerViewDetailFokusAdapter);
    }


    private void initListenerList() {
        recyclerViewPopularAdapter.setOnItemClickListener(new RecyclerViewPopularAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(DetailFokusActivity.this, DetailNewsActivity.class);

                Data data = listNews.get(position);
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

    private void loadJSON() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DataFokusDetail> callFokusDetail;
        Call<Headline> callListFokusDetail;

        //detail
        callFokusDetail = apiInterface.getFokusDetail(id, key);
        callFokusDetail.enqueue(new Callback<DataFokusDetail>() {
            @Override
            public void onResponse(Call<DataFokusDetail> call, Response<DataFokusDetail> response) {
                if (response.isSuccessful() && response.body().getData() != null) {

                    detailShimmerLayout.stopShimmer();
                    detailShimmerLayout.setVisibility(View.GONE);
                    relatedShimmerLayout.stopShimmer();
                    relatedShimmerLayout.setVisibility(View.GONE);
                    appBarLayout.setVisibility(View.VISIBLE);
                    nestedScrollView.setVisibility(View.VISIBLE);

                    //set data

                    appbar_title.setText(response.body().getData().getFocnews_title());
                    appbar_subtile.setText("timesindonesia.co.id"+response.body().getData().getUrlPath());
                    title.setText(response.body().getData().getFocnews_title());
                    date.setText(response.body().getData().getCreated()+" WIB");
                    description.setText(response.body().getData().getFocnews_description());
                    keyword = response.body().getData().getFocnews_keyword().split(",");

                    addListKeyword();

                } else {
                    Toast.makeText(DetailFokusActivity.this, "No Result!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataFokusDetail> call, Throwable t) {
                Toast.makeText(DetailFokusActivity.this, "gagal", Toast.LENGTH_SHORT).show();
            }
        });

        //list berita
        callListFokusDetail = apiInterface.getListFokus(key, "focus", id, 0, 10);
        callListFokusDetail.enqueue(new Callback<Headline>() {
            @Override
            public void onResponse(Call<Headline> call, Response<Headline> response) {
                if (response.isSuccessful() && response.body().getData() != null) {
                    if (!listNews.isEmpty()) {
                        listNews.clear();
                    }

                    listNews = response.body().getData();
                    recyclerViewPopularAdapter = new RecyclerViewPopularAdapter(listNews, DetailFokusActivity.this);
                    listRecyclerView.setAdapter(recyclerViewPopularAdapter);
                    recyclerViewPopularAdapter.notifyDataSetChanged();
                    initListenerList();
                    //swipeRefreshLayout.setRefreshing(false);
                    detailShimmerLayout.stopShimmer();
                    detailShimmerLayout.setVisibility(View.GONE);
                } else {
                    //detailShimmerLayout.setRefreshing(false);
                    Toast.makeText(DetailFokusActivity.this, "No Result!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Headline> call, Throwable t) {
                //swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void addListKeyword() {
        for (int i=0; i<keyword.length; i++){
            listKeyword.add(keyword[i]);
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        if (percentage == 1f && isHideToolbar) {
            titleAppbar.setVisibility(View.VISIBLE);
            isHideToolbar = !isHideToolbar;
        } else if (percentage < 1f && isHideToolbar) {
            titleAppbar.setVisibility(View.GONE);
            isHideToolbar = !isHideToolbar;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        setRecyclerView();
        detailShimmerLayout.startShimmer();
        relatedShimmerLayout.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        detailShimmerLayout.stopShimmer();
        detailShimmerLayout.setVisibility(View.GONE);
        relatedShimmerLayout.stopShimmer();
        relatedShimmerLayout.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}