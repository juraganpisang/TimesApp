package com.jrg.pisang.timesapp.News;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewRelatedAdapter;
import com.jrg.pisang.timesapp.Model.NewsModel;
import com.jrg.pisang.timesapp.R;
import com.jrg.pisang.timesapp.Utils;

import java.util.ArrayList;

public class DetailNewsActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    RecyclerViewRelatedAdapter recyclerViewRelatedAdapter;
    RecyclerView recyclerView;
    ArrayList<NewsModel> models = new ArrayList<>();

    private ImageView imageView;
    private TextView appbar_title, appbar_subtile, caption, title, source, date;
    private WebView content;
    private boolean isHideToolbar = false;
    private LinearLayout titleAppbar;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private String mImage, mTitle, mDate, mSource, mCaption, mContent, mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");

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

        Intent intent = getIntent();
        mTitle = intent.getStringExtra("title");
        mCaption = intent.getStringExtra("caption");
        mImage = intent.getStringExtra("image");
        mContent = intent.getStringExtra("content");
        mDate = intent.getStringExtra("date");
        mSource = intent.getStringExtra("source");
        mUrl = intent.getStringExtra("url");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(Utils.getRandomDrawbleColor());

        Glide.with(this)
                .load(mImage)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);

        appbar_title.setText(mTitle);
        appbar_subtile.setText(mUrl);
        title.setText(mTitle);
        date.setText(" \u2022 " + mDate);
        caption.setText(mCaption);
        content.getSettings().getJavaScriptEnabled();
        content.loadData(String.format(mContent), "text/html", "utf-8");

        if ((mSource.equalsIgnoreCase(null)) || (mSource.equalsIgnoreCase(""))) {
            mSource = "";
        }
        source.setText(mSource);


        recyclerView = findViewById(R.id.relatedRecyclerView);
        recyclerViewRelatedAdapter = new RecyclerViewRelatedAdapter(models, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(recyclerViewRelatedAdapter);
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
