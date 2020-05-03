package com.jrg.pisang.timesapp.News;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewDetailFokusAdapter;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewDetailKanalAdapter;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewPopularAdapter;
import com.jrg.pisang.timesapp.Adapter.TagsAdapter;
import com.jrg.pisang.timesapp.Api.ApiClient;
import com.jrg.pisang.timesapp.Api.ApiInterface;
import com.jrg.pisang.timesapp.Explore.DetailFokusActivity;
import com.jrg.pisang.timesapp.Model.DataModel;
import com.jrg.pisang.timesapp.Model.DataFokusModel;
import com.jrg.pisang.timesapp.Model.DetailKanalModel;
import com.jrg.pisang.timesapp.Model.FokusDetailModel;
import com.jrg.pisang.timesapp.Model.HeadlineModel;
import com.jrg.pisang.timesapp.News.DetailNewsActivity;
import com.jrg.pisang.timesapp.News.TagActivity;
import com.jrg.pisang.timesapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.jrg.pisang.timesapp.R;

public class DetailKanalActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{
    private int id;
    private String name;

    public static final String key = "NyEIwDL51eeaoVhYGPaF";

    private RecyclerViewDetailKanalAdapter recyclerViewDetailKanalAdapter;

    private RecyclerView keywordRecyclerView;

    private TagsAdapter tagsAdapter;

    private ShimmerFrameLayout relatedShimmerLayout, detailShimmerLayout;

    private TextView appbar_title, appbar_subtile, title, date, description;
    private boolean isHideToolbar = false;
    private LinearLayout titleAppbar;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;

    private NestedScrollView nestedScrollView;

    private String[] keyword;
    private ArrayList<String> listKeyword = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kanal);

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
        //listRecyclerView = findViewById(R.id.listRecyclerView);

        relatedShimmerLayout.startShimmer();
        detailShimmerLayout.startShimmer();

        //get intent
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        Log.e("bambang:", name);
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
    }

    private void showKeyword() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        keywordRecyclerView.setLayoutManager(layoutManager);
        tagsAdapter = new TagsAdapter(listKeyword, this);
        keywordRecyclerView.setAdapter(tagsAdapter);
        initListenerKeyword();
    }

    private void loadJSON() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DetailKanalModel> callDetailKanal;

        //detail
        callDetailKanal = apiInterface.getDetailKanal(name, key);
        callDetailKanal.enqueue(new Callback<DetailKanalModel>() {
            @Override
            public void onResponse(Call<DetailKanalModel> call, Response<DetailKanalModel> response) {
                if (response.isSuccessful() && response.body().getData() != null) {

                    detailShimmerLayout.stopShimmer();
                    detailShimmerLayout.setVisibility(View.GONE);
                    relatedShimmerLayout.stopShimmer();
                    relatedShimmerLayout.setVisibility(View.GONE);
                    appBarLayout.setVisibility(View.VISIBLE);
                    nestedScrollView.setVisibility(View.VISIBLE);

                    //set data

                    appbar_title.setText(response.body().getData().getCatnews_title());
                    //appbar_subtile.setText("timesindonesia.co.id"+response.body().getDetailKanal().get());
                    title.setText(response.body().getData().getCatnews_title());
                    date.setText(response.body().getData().getCreated()+" WIB");
                    description.setText(response.body().getData().getCatnews_description());
                    keyword = response.body().getData().getCatnews_keyword().split(",");

                    addListKeyword();

                } else {
                    Toast.makeText(DetailKanalActivity.this, "No Result!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailKanalModel> call, Throwable t) {
                Toast.makeText(DetailKanalActivity.this, "gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addListKeyword() {
        for (int i=0; i<keyword.length; i++){
            listKeyword.add(keyword[i]);
        }
    }
    private void initListenerKeyword() {
        tagsAdapter.setOnItemClickListener(new TagsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(DetailKanalActivity.this, TagActivity.class);

                intent.putExtra("tag", listKeyword.get(position));

                startActivity(intent);
            }
        });

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
        //setRecyclerView();
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
