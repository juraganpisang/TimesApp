package com.jrg.pisang.timesapp.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.jrg.pisang.timesapp.Model.DataKoran;
import com.jrg.pisang.timesapp.R;
import com.jrg.pisang.timesapp.Utils;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class EkoranSliderAdapter extends SliderViewAdapter<EkoranSliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<String> dataImg;

    public EkoranSliderAdapter(List<String> data, Context context) {
        Log.e("size", String.valueOf(data.size()));
        this.dataImg = data;
        this.context = context;
    }

    public void renewItems(List<String> data) {
        this.dataImg = data;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.dataImg.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(String data) {
        this.dataImg.add(data);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ekoran_detail, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        String image = dataImg.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.fitCenter();

        if (image != null) {
            Glide.with(context)
                    .load(image)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(viewHolder.imageView);
        }
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return dataImg.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageView;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewEkoran);
            this.itemView = itemView;
        }
    }
}
