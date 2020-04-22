package com.jrg.pisang.timesapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jrg.pisang.timesapp.R;

import java.util.ArrayList;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.ViewHolder> {

    private ArrayList<String> mList = new ArrayList<>();
    private Context context;
    private OnItemClickListener onItemClickListener;

    public TagsAdapter(ArrayList<String> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tags, parent, false);
        return new ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTags.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTags;
        OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);
            mTags = itemView.findViewById(R.id.textViewTags);

            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
