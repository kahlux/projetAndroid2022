package com.example.projetandroid2022.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetandroid2022.R;
import com.example.projetandroid2022.entities.Resource;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.MyViewHolder> {

    Context context;
    List<Resource> mData;
    ResourceItemClickListener resourceItemClickListener;
    public ResourceAdapter(Context context, List<Resource> mData, ResourceItemClickListener listener) {
        this.context = context;
        this.mData = mData;
        resourceItemClickListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.TvTitle.setText(mData.get(i).getName());
        String url = mData.get(i).getPosterURL();
        if(url != null) {
            Picasso.get().load(mData.get(i).getPosterURL()).into(myViewHolder.ImgMovie);
        } else {
            myViewHolder.ImgMovie.setImageResource(R.drawable.default_poster);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView TvTitle;
        private final ImageView ImgMovie;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TvTitle = itemView.findViewById(R.id.home_item_text);
            ImgMovie = itemView.findViewById(R.id.home_item_img);

            itemView.setOnClickListener((v) -> {
                resourceItemClickListener.onResourceClick(mData.get(this.getAdapterPosition()), ImgMovie);
            });
        }
    }
}
