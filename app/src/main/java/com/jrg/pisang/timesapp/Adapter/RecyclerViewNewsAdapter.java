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

public class RecyclerViewNewsAdapter extends RecyclerView.Adapter<RecyclerViewNewsAdapter.ViewHolder>{

    ArrayList<NewsModel> models;
    Context context;

    public boolean isShimmer = true;
    int shimmerNumber = 5;

    public RecyclerViewNewsAdapter(ArrayList<NewsModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news_headline, parent, false);
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

            holder.titleNews.setBackground(null);
            holder.titleNews.setText(models.get(position).getTitleNews());

            holder.sourceNews.setBackground(null);
            holder.sourceNews.setText(models.get(position).getSourceNews());

            holder.dateNews.setBackground(null);
            holder.dateNews.setText(models.get(position).getDateNews());

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
        TextView titleNews, sourceNews, dateNews;
        ImageView imageNews, saveNews;

        public ViewHolder(View itemView) {
            super(itemView);

            shimmerFrameLayout = itemView.findViewById(R.id.shimmerNewsLayout);
            imageNews = itemView.findViewById(R.id.imageViewNews);
            saveNews = itemView.findViewById(R.id.saveNews);

            titleNews = itemView.findViewById(R.id.textViewTitle);
            sourceNews = itemView.findViewById(R.id.textViewSource);
            dateNews = itemView.findViewById(R.id.textViewDate);

        }
    }
}
