package com.jrg.pisang.timesapp.Media;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewFotoAdapter;
import com.jrg.pisang.timesapp.Adapter.RecyclerViewNewsAdapter;
import com.jrg.pisang.timesapp.Api.ApiClient;
import com.jrg.pisang.timesapp.Api.ApiInterface;
import com.jrg.pisang.timesapp.Model.DataFotoModel;
import com.jrg.pisang.timesapp.Model.DataModel;
import com.jrg.pisang.timesapp.Model.FotoModel;
import com.jrg.pisang.timesapp.Model.HeadlineModel;
import com.jrg.pisang.timesapp.News.DetailNewsActivity;
import com.jrg.pisang.timesapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoMediaFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String key = "NyEIwDL51eeaoVhYGPaF";

    private RecyclerViewNewsAdapter recyclerViewNewsAdapter;

    private RecyclerView videoRecyclerView;

    private List<DataModel> videos = new ArrayList<>();

    private ShimmerFrameLayout videoShimmerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    public VideoMediaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video_media, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        videoShimmerLayout = view.findViewById(R.id.videoShimmerLayout);

        videoRecyclerView = view.findViewById(R.id.videoRecyclerView);

        videoShimmerLayout.startShimmer();

        setRecyclerView();

        loadJSON();

        return view;
    }

    private void setRecyclerView() {
        showVideo();
    }

    private void showVideo() {

        videoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        videoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        videoRecyclerView.setNestedScrollingEnabled(false);
        videoRecyclerView.setAdapter(recyclerViewNewsAdapter);
//        videoRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
////        ekoranRecyclerView.setItemAnimator(new DefaultItemAnimator());
////        ekoranRecyclerView.setNestedScrollingEnabled(false);
//        videoRecyclerView.setAdapter(recyclerViewNewsAdapter);
        videoRecyclerView.setHasFixedSize(true);
    }

    private void initListenerVideo(){
        recyclerViewNewsAdapter.setOnItemClickListener(new RecyclerViewNewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), DetailNewsActivity.class);

                DataModel data = videos.get(position);
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
        Call<HeadlineModel> callFoto;

        callFoto = apiInterface.getListTag(key, "tag", "timesvlog", 0, 10);
        callFoto.enqueue(new Callback<HeadlineModel>() {
            @Override
            public void onResponse(Call<HeadlineModel> call, Response<HeadlineModel> response) {
                if (response.isSuccessful() && response.body().getData() != null) {

                    videos = response.body().getData();
                    recyclerViewNewsAdapter = new RecyclerViewNewsAdapter(videos, getContext());
                    videoRecyclerView.setAdapter(recyclerViewNewsAdapter);
                    recyclerViewNewsAdapter.notifyDataSetChanged();

                    initListenerVideo();
                    swipeRefreshLayout.setRefreshing(false);

                    videoShimmerLayout.stopShimmer();
                    videoShimmerLayout.setVisibility(View.GONE);

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
        videoShimmerLayout.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        videoShimmerLayout.stopShimmer();
        videoShimmerLayout.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        loadJSON();
    }
}
