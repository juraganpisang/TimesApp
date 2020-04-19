package com.jrg.pisang.timesapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jrg.pisang.timesapp.Model.DataFokus;
import com.jrg.pisang.timesapp.R;

import java.util.List;

public class RecyclerViewFokusAdapter extends RecyclerView.Adapter<RecyclerViewFokusAdapter.MyViewHolder> {

    private List<DataFokus> data;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public RecyclerViewFokusAdapter(List<DataFokus> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_explore_fokus, parent, false);
        return new MyViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        DataFokus model = data.get(position);

        holder.focnews_title.setText(model.getFocnews_title());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView focnews_title;
        OnItemClickListener onItemClickListener;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);

            focnews_title = itemView.findViewById(R.id.focnews_title);

            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}