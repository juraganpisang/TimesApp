package com.jrg.pisang.timesapp.News;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewNewsAdapter;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewRelatedAdapter;
import com.jrg.pisang.timesapp.Model.NewsModel;
import com.jrg.pisang.timesapp.R;

import java.util.ArrayList;

public class DetailNewsActivity extends AppCompatActivity {

    RecyclerViewRelatedAdapter recyclerViewRelatedAdapter;
    RecyclerView recyclerView;
    ArrayList<NewsModel> models = new ArrayList<>();

    private MaterialToolbar materialToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        materialToolbar = findViewById(R.id.materialToolbar);
        setSupportActionBar(materialToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("");

        recyclerView = findViewById(R.id.relatedRecyclerView);
        recyclerViewRelatedAdapter = new RecyclerViewRelatedAdapter(models, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(recyclerViewRelatedAdapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                models.add(new NewsModel("GARA GARA CORONA STIKI DITUTUP", "SAKTI", "20/01/2020"));
                models.add(new NewsModel("GARA GARA CORONA STIKI DITUTUP", "SAKTI", "20/01/2020"));
                models.add(new NewsModel("GARA GARA CORONA STIKI DITUTUP", "SAKTI", "20/01/2020"));

                recyclerViewRelatedAdapter.notifyDataSetChanged();
            }
        }, 2000);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.homeAsUp:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
