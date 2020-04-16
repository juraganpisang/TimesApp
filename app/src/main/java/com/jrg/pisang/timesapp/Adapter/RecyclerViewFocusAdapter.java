package com.jrg.pisang.timesapp.Adapter;

import androidx.recyclerview.widget.RecyclerView;

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
import com.jrg.pisang.timesapp.Model.DataFokus;
import com.jrg.pisang.timesapp.Model.DataKoran;
import com.jrg.pisang.timesapp.R;
import com.jrg.pisang.timesapp.Utils;

import java.util.List;

public class RecyclerViewFocusAdapter extends RecyclerView.Adapter<RecyclerViewFocusAdapter.MyViewHolder> {

    private List<DataFokus> dataFokus;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public RecyclerViewFocusAdapter(List<DataFokus> dataFokus, Context context) {
        this.dataFokus = dataFokus;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_fokus, parent, false);
        return new MyViewHolder(view, (RecyclerViewEkoranAdapter.OnItemClickListener) onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewFocusAdapter.MyViewHolder holder, int position) {
        DataFokus model = dataFokus.get(position);
        holder.focnews_title.setText(model.getFocnews_title());
    }

    @Override
    public int getItemCount() {
        return dataFokus.size();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView focnews_title;
        private RecyclerViewFocusAdapter.OnItemClickListener onItemClickListener;

        public MyViewHolder(@NonNull View itemView, RecyclerViewEkoranAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);

            focnews_title = itemView.findViewById(R.id.focnews_title);

            this.onItemClickListener = (OnItemClickListener) onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
