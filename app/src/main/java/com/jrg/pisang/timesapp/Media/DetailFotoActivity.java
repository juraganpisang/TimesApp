package com.jrg.pisang.timesapp.Media;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewPopularAdapter;
import com.jrg.pisang.timesapp.Api.ApiClient;
import com.jrg.pisang.timesapp.Api.ApiInterface;
import com.jrg.pisang.timesapp.Model.DataFotoModel;
import com.jrg.pisang.timesapp.Model.FotoDetailModel;
import com.jrg.pisang.timesapp.Model.FotoModel;
import com.jrg.pisang.timesapp.R;
import com.jrg.pisang.timesapp.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailFotoActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    public static final String key = "NyEIwDL51eeaoVhYGPaF";

    private RecyclerViewPopularAdapter recyclerViewPopularAdapter;
    private DataFotoModel dataFotoModel;


    private ShimmerFrameLayout detailShimmerLayout;

    private ImageView imageView;
    private TextView appbar_title, appbar_subtile, caption, title, source, date;
    private WebView content;
    private boolean isHideToolbar = false;
    private LinearLayout titleAppbar;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private String mId;
    private int mIdNews;

    private NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_foto);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        detailShimmerLayout.startShimmer();

        //get intent
        Intent intent = getIntent();
        mId = intent.getStringExtra("id");

        mIdNews = Integer.valueOf(mId);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadJSON();
            }
        }, 2000);
    }

    private void loadJSON() {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<FotoDetailModel> callDetailFoto;

        callDetailFoto = apiInterface.getFotoDetail(mIdNews, key);
        callDetailFoto.enqueue(new Callback<FotoDetailModel>() {
            @Override
            public void onResponse(Call<FotoDetailModel> call, Response<FotoDetailModel> response) {
                if (response.isSuccessful() && response.body().getData() != null) {

                    dataFotoModel = response.body().getData();

                    content.getSettings().getJavaScriptEnabled();
                    content.loadData(dataFotoModel.getGal_content(), "text/html", "utf-8");

                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.error(Utils.getRandomDrawbleColor());
                    appbar_title.setText(dataFotoModel.getGal_title());
                    appbar_subtile.setText("timesindonesia.co.id"+dataFotoModel.getUrlPath());

                    Glide.with(DetailFotoActivity.this)
                            .load(dataFotoModel.getGal_cover())
                            .apply(requestOptions)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(imageView);

                    caption.setText(dataFotoModel.getGal_description());
                    title.setText(dataFotoModel.getGal_title());
                    source.setText(dataFotoModel.getGal_pewarta());
                    date.setText(dataFotoModel.getGal_datepub());

                    detailShimmerLayout.stopShimmer();
                    detailShimmerLayout.setVisibility(View.GONE);
                    appBarLayout.setVisibility(View.VISIBLE);
                    nestedScrollView.setVisibility(View.VISIBLE);

                } else {
                    Toast.makeText(DetailFotoActivity.this, "No Result!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FotoDetailModel> call, Throwable t) {
                Toast.makeText(DetailFotoActivity.this, "gagal", Toast.LENGTH_SHORT).show();
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
        detailShimmerLayout.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        detailShimmerLayout.stopShimmer();
        detailShimmerLayout.setVisibility(View.GONE);
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
