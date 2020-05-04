package com.jrg.pisang.timesapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jrg.pisang.timesapp.Model.DataFokusModel;
import com.jrg.pisang.timesapp.Model.DataKanalModel;
import com.jrg.pisang.timesapp.R;

import java.util.List;

public class RecyclerViewKanalAdapter extends RecyclerView.Adapter<RecyclerViewKanalAdapter.MyViewHolder> {

    private List<DataKanalModel> data;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public RecyclerViewKanalAdapter(List<DataKanalModel> data, Context context) {
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
        DataKanalModel model = data.get(position);

        holder.kanal_name.setText(model.getName());

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

        TextView kanal_name;
        OnItemClickListener onItemClickListener;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);

            kanal_name = itemView.findViewById(R.id.focnews_title);

            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}