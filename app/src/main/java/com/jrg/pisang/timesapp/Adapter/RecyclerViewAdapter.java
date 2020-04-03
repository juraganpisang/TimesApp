package com.jrg.pisang.timesapp.Adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.jrg.pisang.timesapp.Model.NewsModel;
import com.jrg.pisang.timesapp.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    ArrayList<NewsModel> models;
    Context context;

    public boolean isShimmer = true;
    int shimmerNumber = 5;

    public RecyclerViewAdapter(ArrayList<NewsModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shimmer_news_view, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(isShimmer){
            Log.e("SHIMMER", "IKI TRUE");
            holder.shimmerFrameLayout.startShimmer();
        }else {
            Log.e("SHIMMER", "LEK IKI FOLSE");
            holder.shimmerFrameLayout.stopShimmer();
            holder.shimmerFrameLayout.setShimmer(null);

            holder.captionNews.setBackground(null);
            holder.captionNews.setText(models.get(position).getCaptionNews());

            holder.sourceNews.setBackground(null);
            holder.sourceNews.setText(models.get(position).getSourceNews());

            holder.timeNews.setBackground(null);
            holder.timeNews.setText(models.get(position).getTimeNews());

            holder.imageNews.setBackground(null);
            holder.imageNews.setImageDrawable(context.getDrawable(R.drawable.ic_android_black));

            holder.saveNews.setBackground(null);
            holder.saveNews.setImageDrawable(context.getDrawable(R.drawable.ic_bookmark_border));
        }
    }

    @Override
    public int getItemCount() {
        return isShimmer?shimmerNumber:models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ShimmerFrameLayout shimmerFrameLayout;
        TextView captionNews, sourceNews, timeNews;
        ImageView imageNews, saveNews;

        public ViewHolder(View itemView) {
            super(itemView);

            shimmerFrameLayout = itemView.findViewById(R.id.shimmerNewsLayout);
            imageNews = itemView.findViewById(R.id.imageNews);
            saveNews = itemView.findViewById(R.id.saveNews);
            captionNews = itemView.findViewById(R.id.captionNews);
            sourceNews = itemView.findViewById(R.id.sourceNews);
            timeNews = itemView.findViewById(R.id.timeNews);

        }
    }
}
