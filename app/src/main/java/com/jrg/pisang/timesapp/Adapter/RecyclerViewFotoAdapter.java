package com.jrg.pisang.timesapp.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.jrg.pisang.timesapp.Model.DataFotoModel;
import com.jrg.pisang.timesapp.Model.DataKoran;
import com.jrg.pisang.timesapp.R;
import com.jrg.pisang.timesapp.Utils;

import java.util.List;

public class RecyclerViewFotoAdapter extends RecyclerView.Adapter<RecyclerViewFotoAdapter.MyViewHolder> {

    private List<DataFotoModel> dataFotoModels;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public RecyclerViewFotoAdapter(List<DataFotoModel> dataFotoModels, Context context) {
        this.dataFotoModels = dataFotoModels;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_media_foto, parent, false);
        return new MyViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewFotoAdapter.MyViewHolder holder, int position) {

        DataFotoModel model = dataFotoModels.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(context)
                .load(model.getGal_cover())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);

        holder.title.setText(model.getGal_title());
    }

    @Override
    public int getItemCount() {
        return dataFotoModels.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView title;
        private RecyclerViewFotoAdapter.OnItemClickListener onItemClickListener;

        public MyViewHolder(@NonNull View itemView, RecyclerViewFotoAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);

            title = itemView.findViewById(R.id.textViewTitle);
            imageView = itemView.findViewById(R.id.imageViewNews);

            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
