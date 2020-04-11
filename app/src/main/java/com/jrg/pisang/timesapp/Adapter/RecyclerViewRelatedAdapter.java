package com.jrg.pisang.timesapp.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.jrg.pisang.timesapp.Model.NewsModel;
import com.jrg.pisang.timesapp.R;

import java.util.ArrayList;

public class RecyclerViewRelatedAdapter extends RecyclerView.Adapter<RecyclerViewRelatedAdapter.ViewHolder>{

    ArrayList<NewsModel> models;
    Context context;


    public RecyclerViewRelatedAdapter(ArrayList<NewsModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news_popular, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleNews.setText(models.get(position).getTitleNews());

        holder.sourceNews.setText(models.get(position).getSourceNews());

        holder.dateNews.setText(models.get(position).getDateNews());

        holder.imageNews.setImageDrawable(context.getDrawable(R.drawable.ic_android_black));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleNews, sourceNews, dateNews;
        ImageView imageNews, saveNews;

        public ViewHolder(View itemView) {
            super(itemView);

            imageNews = itemView.findViewById(R.id.imageViewNews);

            titleNews = itemView.findViewById(R.id.textViewTitle);
            sourceNews = itemView.findViewById(R.id.textViewSource);
            dateNews = itemView.findViewById(R.id.textViewDate);

        }
    }
}
