package com.jrg.pisang.timesapp.News;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
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
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewNewsAdapter;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewPopularAdapter;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewRelatedAdapter;
import com.jrg.pisang.timesapp.Adapter.TagsAdapter;
import com.jrg.pisang.timesapp.Api.ApiClient;
import com.jrg.pisang.timesapp.Api.ApiInterface;
import com.jrg.pisang.timesapp.Model.Data;
import com.jrg.pisang.timesapp.Model.Headline;
import com.jrg.pisang.timesapp.Model.NewsModel;
import com.jrg.pisang.timesapp.R;
import com.jrg.pisang.timesapp.Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailNewsActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    public static final String key = "NyEIwDL51eeaoVhYGPaF";

    private RecyclerViewPopularAdapter recyclerViewPopularAdapter;
    private TagsAdapter tagsAdapter;

    private RecyclerView relatedRecyclerView, tagRecyclerView;

    private List<Data> relateds = new ArrayList<>();
    private ArrayList<String> listTags = new ArrayList<>();

    private ShimmerFrameLayout relatedShimmerLayout, detailShimmerLayout;

    private ImageView imageView;
    private TextView appbar_title, appbar_subtile, caption, title, source, date;
    private WebView content;
    private boolean isHideToolbar = false;
    private LinearLayout titleAppbar;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private String mImage, mTitle, mDate, mSource, mCaption, mContent, mUrl, mId, tempTags, mTags;
    private String[] separatorTags;
    private int mIdNews;

    private NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

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

        imageView = findViewById(R.id.backdrop);
        caption = findViewById(R.id.textViewCaption);
        title = findViewById(R.id.textViewTitle);
        source = findViewById(R.id.textViewSource);
        date = findViewById(R.id.textViewDate);
        content = findViewById(R.id.webViewContent);

        relatedRecyclerView = findViewById(R.id.relatedRecyclerView);
        tagRecyclerView = findViewById(R.id.tagRecyclerView);

        relatedShimmerLayout.startShimmer();
        detailShimmerLayout.startShimmer();

        //get intent
        Intent intent = getIntent();
        mId = intent.getStringExtra("id");
        mTitle = intent.getStringExtra("title");
        mCaption = intent.getStringExtra("caption");
        mImage = intent.getStringExtra("image");
        mContent = intent.getStringExtra("content");
        mDate = intent.getStringExtra("date");
        mSource = intent.getStringExtra("source");
        mUrl = intent.getStringExtra("url");
        tempTags = intent.getStringExtra("tags");

        separatorTags = tempTags.split(",");
        mTags = separatorTags[0];

        mIdNews = Integer.valueOf(mId);
        //call intent
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(Utils.getRandomDrawbleColor());

        Glide.with(DetailNewsActivity.this)
                .load(mImage)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);

        appbar_title.setText(mTitle);
        appbar_subtile.setText("timesindonesia.co.id" + mUrl);
        title.setText(mTitle);
        date.setText(" \u2022 " + mDate);
        caption.setText(mCaption);
        content.getSettings().getJavaScriptEnabled();
        content.loadData(mContent, "text/html", "utf-8");
        source.setText(mSource);

        addListTag();

        setRecyclerView();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadJSON();
            }
        }, 2000);
    }

    private void addListTag() {
        for (int i = 0; i < separatorTags.length; i++) {
            listTags.add(separatorTags[i]);
        }
    }

    private void setRecyclerView() {
        showRelated();
        showKeyword();
    }


    private void showKeyword() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        tagRecyclerView.setLayoutManager(layoutManager);
        tagsAdapter = new TagsAdapter(listTags, this);
        tagRecyclerView.setAdapter(tagsAdapter);
    }

    private void showRelated() {
        relatedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        relatedRecyclerView.setItemAnimator(new DefaultItemAnimator());
        relatedRecyclerView.setNestedScrollingEnabled(false);
        relatedRecyclerView.setAdapter(recyclerViewPopularAdapter);
    }

    private void initListenerRelated() {
        recyclerViewPopularAdapter.setOnItemClickListener(new RecyclerViewPopularAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(DetailNewsActivity.this, DetailNewsActivity.class);

                Data data = relateds.get(position);
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
        Call<Headline> callRelated;

        callRelated = apiInterface.getNewsRelated(key, "related_new", mIdNews, mTags, 0, 10);
        callRelated.enqueue(new Callback<Headline>() {
            @Override
            public void onResponse(Call<Headline> call, Response<Headline> response) {
                if (response.isSuccessful() && response.body().getData() != null) {
                    if (!relateds.isEmpty()) {
                        relateds.clear();
                    }

                    relateds = response.body().getData();
                    recyclerViewPopularAdapter = new RecyclerViewPopularAdapter(relateds, DetailNewsActivity.this);
                    relatedRecyclerView.setAdapter(recyclerViewPopularAdapter);
                    recyclerViewPopularAdapter.notifyDataSetChanged();

                    initListenerRelated();

                    detailShimmerLayout.stopShimmer();
                    detailShimmerLayout.setVisibility(View.GONE);
                    relatedShimmerLayout.stopShimmer();
                    relatedShimmerLayout.setVisibility(View.GONE);
                    appBarLayout.setVisibility(View.VISIBLE);
                    nestedScrollView.setVisibility(View.VISIBLE);

                } else {
                    Toast.makeText(DetailNewsActivity.this, "No Result!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Headline> call, Throwable t) {
                Toast.makeText(DetailNewsActivity.this, "gagal", Toast.LENGTH_SHORT).show();
            }
        });

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

    //
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_detail, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.homeAsUp:
//                onBackPressed();
//                break;
//        }
//        return true;
//    }

}
